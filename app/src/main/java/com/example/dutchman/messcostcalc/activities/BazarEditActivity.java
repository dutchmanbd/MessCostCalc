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

import com.example.dutchman.messcostcalc.MainActivity;
import com.example.dutchman.messcostcalc.R;
import com.example.dutchman.messcostcalc.constants.Constant;
import com.example.dutchman.messcostcalc.constants.MySharedPref;
import com.example.dutchman.messcostcalc.db.BazarDataSource;
import com.example.dutchman.messcostcalc.models.Bazar;
import com.example.dutchman.messcostcalc.models.Member;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BazarEditActivity extends AppCompatActivity {


    private boolean bazarHasChanged = false;

    private EditText etBazarDate;
    private EditText etBazarTk;

    private AutoCompleteTextView acMemberName;

    private ImageButton ibBazarCalendar;

    private BazarDataSource bazarDataSource;
    private Context context;

    private Intent bazarIntent;

    private int bazarId;

    private Bazar bazar;

    private String activityType;


    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            bazarHasChanged = true;
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bazar_edit);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = getApplicationContext();

        bazarDataSource = BazarDataSource.getInstance(context);

        bazarIntent = getIntent();

        initializeViews();

    }

    @Override
    protected void onResume() {
        bazarDataSource = BazarDataSource.getInstance(context);
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MySharedPref.save(context, Constant.Class.BAZAR);

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
                if (!bazarHasChanged) {
                    NavUtils.navigateUpFromSameTask(BazarEditActivity.this);
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
                                NavUtils.navigateUpFromSameTask(BazarEditActivity.this);
                            }
                        };

                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;

            case R.id.action_save:
                saveBazar();
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

        if (!bazarHasChanged) {
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


    private void initializeViews(){

        etBazarDate = (EditText) findViewById(R.id.edit_text_bazar_date);
        etBazarTk = (EditText) findViewById(R.id.edit_text_bazar_tk);

        acMemberName = (AutoCompleteTextView) findViewById(R.id.auto_complete_bazar_name);

        ibBazarCalendar = (ImageButton) findViewById(R.id.image_button_bazar_calendar);

        etBazarDate.setOnTouchListener(touchListener);
        etBazarTk.setOnTouchListener(touchListener);
        acMemberName.setOnTouchListener(touchListener);
        ibBazarCalendar.setOnTouchListener(touchListener);


        setActions();
    }


    private void setActions(){

        ibBazarCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new BazarEditActivity.DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");

            }
        });


        List<String> membersName = bazarDataSource.getMembersName(1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, membersName);
        acMemberName.setAdapter(adapter);
        acMemberName.setThreshold(1);


        activityType = bazarIntent.getStringExtra(Constant.ACTIVITY_TYPE);

        String subtitle = "";
        if(activityType.equals(Constant.ACTIVITY_TYPE_ADD)){
            subtitle = "Add Member";
            invalidateOptionsMenu();
            setInitialDate();

        } else if(activityType.equals(Constant.ACTIVITY_TYPE_EDIT)){
            subtitle = "Edit Member";

            bazarId = bazarIntent.getIntExtra(Constant.MEMBER_ITEM_ID, -1);
            Log.e("MemberEditActivity", "debit list item position: " + bazarId);
            if (bazarId > -1) {
                bazar = bazarDataSource.getBazar(bazarId);
                etBazarDate.setText(bazar.getDate());
                acMemberName.setText(bazar.getmName());
                etBazarTk.setText(bazar.getTk()+"");

            } else {
                Toast.makeText(this, "Error loading debit!", Toast.LENGTH_SHORT).show();
            }

        }

        getSupportActionBar().setSubtitle(subtitle);



    }

    private void setInitialDate() {
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        etBazarDate.setText(date);
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


    private void saveBazar() {
        String memberDate = etBazarDate.getText().toString().trim();
        String memberName = acMemberName.getText().toString().trim();
        String memberTk = etBazarTk.getText().toString().trim();

        if (TextUtils.isEmpty(memberDate)) {
            Toast.makeText(this, "Please enter or select a date!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(memberName)) {
            Toast.makeText(this, "Please enter a name!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(memberTk)) {
            Toast.makeText(this, "Please enter advanced tk!", Toast.LENGTH_SHORT).show();
        } else {
            Bazar bazar = new Bazar();

            String month = new SimpleDateFormat("MMMM").format(new Date());
            String year  = new SimpleDateFormat("yyyy").format(new Date());

            bazar.setDate(memberDate);
            bazar.setMonth(month);
            bazar.setYear(year);
            bazar.setmName(memberName);
            bazar.setTk(Double.parseDouble(memberTk));


            //Log.e(TAG, "system currentTimeMillis: " + System.currentTimeMillis() % 100000000);

            if (activityType.equals(Constant.ACTIVITY_TYPE_ADD)) {
                boolean inserted = bazarDataSource.insertBazar(bazar);
                if (inserted) {
                    Toast.makeText(this, "Debit saved!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Failed to save debit!", Toast.LENGTH_SHORT).show();
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
            String finalDay = day > 9 ? ("" + day) : ("0" + day);
            String finalMonth = month > 9 ? ("" + month) : ("0" + month);
            String finalYear = "" + year;

            String date = String.format("%02d-%02d-%d",day,month,year);

            etBazarDate.setText(date);
        }

    }

}
