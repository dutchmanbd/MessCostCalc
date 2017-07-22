package com.example.dutchman.messcostcalc.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.dutchman.messcostcalc.R;
import com.example.dutchman.messcostcalc.constants.Constant;
import com.example.dutchman.messcostcalc.constants.MySharedPref;
import com.example.dutchman.messcostcalc.db.MealDebitCreditDataSource;
import com.example.dutchman.messcostcalc.models.Credit;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MealDebitCreditEditActivity extends AppCompatActivity {


    private boolean mealCreditHasChanged = false;

    private EditText etMealCreditDate;
    private EditText etMealCreditTk;

    private AutoCompleteTextView acMealCreditName;

    private ImageButton ibMealCreditCalendar;

    private MealDebitCreditDataSource mealDebitCreditDataSource;
    private Context context;

    private Intent mealCreditIntent;

    private int mealCreditId;

    private Credit credit;

    private String activityType;


    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mealCreditHasChanged = true;
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_debit_credit_edit);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = getApplicationContext();

        mealCreditIntent = getIntent();

        initializeViews();

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }


    /**
     * This method is called after invalidateOptionsMenu(), so that the
     * menu can be updated (some menu items can be hidden or made visible).
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new credit, hide the "Delete" menu item.
        if (activityType.equals(Constant.ACTIVITY_TYPE_ADD)) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                // If the credit hasn't changed, continue with navigating up to parent activity
                // which is the {@link MainActivity}.
                if (!mealCreditHasChanged) {
                    NavUtils.navigateUpFromSameTask(MealDebitCreditEditActivity.this);
                    return true;
                }

                // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.
                                NavUtils.navigateUpFromSameTask(MealDebitCreditEditActivity.this);
                            }
                        };

                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;

            case R.id.action_save:
                saveMember();
                return true;

            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Pop up confirmation dialog for deletion
                showDeleteConfirmationDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {

        if (!mealCreditHasChanged) {
            super.onBackPressed();
            return;
        }

        // Otherwise if there are unsaved changes, setup a dialog to warn the user.
        // Create a click listener to handle the user confirming that changes should be discarded.
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "Discard" button, close the current activity.
                        finish();
                    }
                };

        // Show dialog that there are unsaved changes
        showUnsavedChangesDialog(discardButtonClickListener);

    }


    @Override
    protected void onPause() {
        super.onPause();
        //MainActivity.backFromMemberEditActivity = true;
        MySharedPref.save(context,Constant.Class.MEAL_CREDIT_DEBIT);
    }



    private void initializeViews(){

        etMealCreditDate = (EditText) findViewById(R.id.edit_text_meal_db_date);
        etMealCreditTk = (EditText) findViewById(R.id.edit_text_meal_db_tk);

        acMealCreditName = (AutoCompleteTextView) findViewById(R.id.auto_complete_meal_db_name);

        ibMealCreditCalendar = (ImageButton) findViewById(R.id.image_button_meal_db_calendar);

        mealDebitCreditDataSource = new MealDebitCreditDataSource(context);


        etMealCreditDate.setOnTouchListener(touchListener);
        etMealCreditTk.setOnTouchListener(touchListener);
        acMealCreditName.setOnTouchListener(touchListener);
        ibMealCreditCalendar.setOnTouchListener(touchListener);


        setActions();

    }

    private void setActions(){

        ibMealCreditCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");

            }
        });

        List<String> membersName = mealDebitCreditDataSource.getMembersName(1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, membersName);
        acMealCreditName.setAdapter(adapter);
        acMealCreditName.setThreshold(1);


        activityType = mealCreditIntent.getStringExtra(Constant.ACTIVITY_TYPE);

        String subtitle = "";
        if(activityType.equals(Constant.ACTIVITY_TYPE_ADD)){
            subtitle = "Add Member";
            invalidateOptionsMenu();
            setInitialDate();

        } else if(activityType.equals(Constant.ACTIVITY_TYPE_EDIT)){
            subtitle = "Edit Member";

            mealCreditId = mealCreditIntent.getIntExtra(Constant.MEMBER_ITEM_ID, -1);
            Log.e("MemberEditActivity", "debit list item position: " + mealCreditId);
            if (mealCreditId > -1) {
                credit = mealDebitCreditDataSource.getCredit(mealCreditId);
                etMealCreditDate.setText(credit.getDate());
                acMealCreditName.setText(credit.getName());
                etMealCreditTk.setText(credit.getTk()+"");
            } else {
                Toast.makeText(this, "Error loading debit!", Toast.LENGTH_SHORT).show();
            }

        }

        getSupportActionBar().setSubtitle(subtitle);



    }

    private void setInitialDate() {
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        etMealCreditDate.setText(date);
    }


    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    private void saveMember() {
        String creditDate = etMealCreditDate.getText().toString().trim();
        String creditName = acMealCreditName.getText().toString().trim();
        String creditTk = etMealCreditTk.getText().toString().trim();


        if (TextUtils.isEmpty(creditDate)) {
            Toast.makeText(this, "Please enter or select a date!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(creditName)) {
            Toast.makeText(this, "Please enter a name!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(creditTk)) {
            Toast.makeText(this, "Please enter advanced tk!", Toast.LENGTH_SHORT).show();
        } else {
            Credit credit = new Credit();

            String month = new SimpleDateFormat("MMMM").format(new Date());
            String year  = new SimpleDateFormat("yyyy").format(new Date());

            credit.setDate(creditDate);
            credit.setMonth(month);
            credit.setYear(year);
            credit.setName(creditName);
            credit.setTk(Double.parseDouble(creditTk));

            //Log.e(TAG, "system currentTimeMillis: " + System.currentTimeMillis() % 100000000);

            if (activityType.equals(Constant.ACTIVITY_TYPE_ADD)) {
                boolean inserted = mealDebitCreditDataSource.insertCredit(credit);
                if (inserted) {
                    Toast.makeText(this, "Credit saved!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Failed to save credit!", Toast.LENGTH_SHORT).show();
                }
            } else if (activityType.equals(Constant.ACTIVITY_TYPE_EDIT)) {
//                boolean updated = expenseDataSource.updateDebit(debitId, debit);
//                if (updated) {
//                    Toast.makeText(this, "Debit updated!", Toast.LENGTH_SHORT).show();
//                    finish();
//                } else {
//                    Toast.makeText(this, "Failed to update debit!", Toast.LENGTH_SHORT).show();
//                }
            }
        }
    }


    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_credit_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the debit.
                //deleteDebit();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the debit.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }



    public class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            month += 1;

            String date = String.format("%02d-%02d-%d",day,month,year);

            etMealCreditDate.setText(date);
        }

    }


}
