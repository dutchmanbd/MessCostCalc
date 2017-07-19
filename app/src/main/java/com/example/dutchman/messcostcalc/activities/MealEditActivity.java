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
import com.example.dutchman.messcostcalc.db.MealDataSource;
import com.example.dutchman.messcostcalc.db.MemberDataSource;
import com.example.dutchman.messcostcalc.models.Meal;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MealEditActivity extends AppCompatActivity {


    private boolean mealHasChanged = false;

    private EditText etMealDate;
    private EditText etMeal;
    private EditText etMealYear;

    private AutoCompleteTextView acMealName;
    private AutoCompleteTextView acMealMonth;

    private ImageButton ibMealCalendar;

    private MealDataSource mealDataSource;
    private Context context;

    private Intent mealIntent;

    private int mealId;

    private Meal meal;

    private String activityType;


    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mealHasChanged = true;
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_edit);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = getApplicationContext();

        mealIntent = getIntent();

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
                if (!mealHasChanged) {
                    NavUtils.navigateUpFromSameTask(MealEditActivity.this);
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
                                NavUtils.navigateUpFromSameTask(MealEditActivity.this);
                            }
                        };

                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;

            case R.id.action_save:
                saveMeal();
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

        if (!mealHasChanged) {
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
        MySharedPref.save(context,Constant.Class.MEMBER);
    }


    private void initializeViews(){

        etMealDate = (EditText) findViewById(R.id.edit_text_meal_date);
        etMeal = (EditText) findViewById(R.id.edit_text_meal);
        etMealYear = (EditText) findViewById(R.id.edit_text_meal_year);


        acMealName = (AutoCompleteTextView) findViewById(R.id.auto_complete_meal_name);
        acMealMonth = (AutoCompleteTextView) findViewById(R.id.auto_complete_meal_month);

        ibMealCalendar = (ImageButton) findViewById(R.id.image_button_meal_calendar);

        mealDataSource = new MealDataSource(context);


        etMealDate.setOnTouchListener(touchListener);
        acMealMonth.setOnTouchListener(touchListener);
        etMealYear.setOnTouchListener(touchListener);
        etMeal.setOnTouchListener(touchListener);
        acMealName.setOnTouchListener(touchListener);
        ibMealCalendar.setOnTouchListener(touchListener);


        setActions();

    }

    private void setActions(){

        ibMealCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");

            }
        });

        List<String> membersName = MemberDataSource.getMembersName(this,1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, membersName);
        acMealName.setAdapter(adapter);
        acMealName.setThreshold(1);

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, R.array.month_list);
        acMealMonth.setAdapter(monthAdapter);
        acMealMonth.setThreshold(1);


        activityType = mealIntent.getStringExtra(Constant.ACTIVITY_TYPE);

        String subtitle = "";
        if(activityType.equals(Constant.ACTIVITY_TYPE_ADD)){
            subtitle = "Add Member";
            invalidateOptionsMenu();
            setInitialDate();

        } else if(activityType.equals(Constant.ACTIVITY_TYPE_EDIT)){
            subtitle = "Edit Member";

            mealId = mealIntent.getIntExtra(Constant.MEMBER_ITEM_ID, -1);
            Log.e("MemberEditActivity", "debit list item position: " + mealId);

            acMealMonth.setVisibility(View.VISIBLE);
            etMealYear.setVisibility(View.VISIBLE);



            if (mealId > -1) {
                meal = mealDataSource.getMeal(mealId);
                etMealDate.setText(meal.getDate());
                acMealMonth.setText(meal.getMonth());
                etMealYear.setText(meal.getYear());
                acMealName.setText(meal.getName());
                etMeal.setText(meal.getMeal()+"");

            } else {
                Toast.makeText(this, "Error loading debit!", Toast.LENGTH_SHORT).show();
            }

        }

        getSupportActionBar().setSubtitle(subtitle);



    }

    private void setInitialDate() {
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        etMealDate.setText(date);
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


    private void saveMeal() {
        String mealDate = etMealDate.getText().toString().trim();
        String mealName = acMealName.getText().toString().trim();
        String mealStr = etMeal.getText().toString().trim();

        if (TextUtils.isEmpty(mealDate)) {
            Toast.makeText(this, "Please enter or select a date!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(mealName)) {
            Toast.makeText(this, "Please enter a name!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(mealStr)) {
            Toast.makeText(this, "Please enter meal!", Toast.LENGTH_SHORT).show();
        } else {
            Meal meal = new Meal();

            String month = new SimpleDateFormat("MMMM").format(new Date());
            String year  = new SimpleDateFormat("yyyy").format(new Date());

            meal.setDate(mealDate);
            meal.setMonth(month);
            meal.setYear(year);
            meal.setName(mealName);
            meal.setMeal(Double.parseDouble(mealStr));

            //Log.e(TAG, "system currentTimeMillis: " + System.currentTimeMillis() % 100000000);

            if (activityType.equals(Constant.ACTIVITY_TYPE_ADD)) {
                boolean inserted = mealDataSource.insertMember(meal);
                if (inserted) {
                    Toast.makeText(this, "Meal saved!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Failed to save meal!", Toast.LENGTH_SHORT).show();
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

            etMealDate.setText(date);
        }

    }


}
