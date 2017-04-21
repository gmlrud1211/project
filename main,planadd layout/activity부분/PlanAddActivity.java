package com.example.heekyoung.hi;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by heekyoung on 2017-04-22.
 */

@TargetApi(Build.VERSION_CODES.N)
public class PlanAddActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvToDate, tvFromDate;
    DateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);
    int callerId = -1;
    //  public static final String DATE_FORMAT = "yyyy/MM/dd";
    public static final String DATE_FORMAT = "EEE, MMM d, yyyy";

    private Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvToDate = (TextView) findViewById(R.id.tvToDate);
        tvToDate.setOnClickListener(this);

        tvFromDate = (TextView) findViewById(R.id.tvFromDate);
        tvFromDate.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvToDate:
                TextView et = (TextView) view;
                showDatePickerDialog(view.getId(), et.getText().toString().trim());
                break;
            case R.id.tvFromDate:
                TextView et1 = (TextView) view;
                showDatePickerDialog(view.getId(), et1.getText().toString().trim());
                break;

        }
    }

    /**
     * Method used to show date picker dialog
     *
     * @param callerId
     * @param dateText
     */
    public void showDatePickerDialog(int callerId, String dateText) {
        this.callerId = callerId;
        Date date = null;

        try {
            if (dateText.equals(""))
                date = new Date();
            else
                date = dateFormatter.parse(dateText);
        } catch (Exception exp) {
            // In case of expense initializa date with new Date
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH); // calendar month 0-11
        int day = calendar.get(Calendar.DATE);
        // date picker initialization
        DatePickerDialog datePicker = new DatePickerDialog(ctx, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                handleOnDateSet(year, month, day);
            }
        }, year, month, day);
        datePicker.setTitle("My date picker");
        datePicker.setButton(DatePickerDialog.BUTTON_POSITIVE, "Ok", datePicker);
        datePicker.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Cancel button clicked
            }
        });
        datePicker.show();
    }

    /**
     * Method called when user select a date on date picker dialog
     *
     * @param year
     * @param month
     * @param day
     */
    public void handleOnDateSet(int year, int month, int day) {
        Date date = new GregorianCalendar(year, month, day).getTime();
        String formatedDate = dateFormatter.format(date);

        switch (callerId) {
            case R.id.tvToDate:
                tvToDate.setText(formatedDate);
                break;
            case R.id.tvFromDate:
                tvFromDate.setText(formatedDate);
                break;
        }
    }


}