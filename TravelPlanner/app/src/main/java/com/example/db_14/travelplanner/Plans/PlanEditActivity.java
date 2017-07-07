package com.example.db_14.travelplanner.Plans;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.db_14.travelplanner.DB.DBHelper;
import com.example.db_14.travelplanner.R;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by db-14 on 2017. 6. 19..
 */

public class PlanEditActivity extends Activity implements View.OnClickListener {

    TextView tvToDate, tvFromDate;
    EditText pname;
    Button add_btn;
    String usrid, pname_t, pno, sdate, fdate;
    DateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);
    int callerId = -1;
    //  public static final String DATE_FORMAT = "yyyy/MM/dd";
    public static final String DATE_FORMAT = "EEE, MMM d, yyyy";

    private Context ctx = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paln_add);
        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "UserInfo.db", null, 1);
        usrid = dbHelper.getResult().get("usrid");
        pname_t = getIntent().getStringExtra("PNAME");
        pno = getIntent().getStringExtra("PNO");
        sdate = getIntent().getStringExtra("SDATE");
        fdate = getIntent().getStringExtra("FDATE");

        pname = (EditText) findViewById(R.id.plan_name);
        tvToDate = (TextView) findViewById(R.id.tvToDate);
        tvToDate.setOnClickListener(this);
        tvFromDate = (TextView) findViewById(R.id.tvFromDate);
        tvFromDate.setOnClickListener(this);
        add_btn = (Button) findViewById(R.id.add_ok_btn);
        add_btn.setOnClickListener(this);

        pname.setText(pname_t);
        tvToDate.setText(sdate);
        tvFromDate.setText(fdate);
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
            case R.id.add_ok_btn:
                Intent in = new Intent();
                editPlanDB(pname.getText().toString(), tvToDate.getText().toString(), tvFromDate.getText().toString());
                setResult(RESULT_OK, in);
                finish();
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
        datePicker.setTitle("날짜를 선택하세요.");
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
        String myear, mmonth, mday;
        myear = String.valueOf(year);
        mmonth = (month+1 < 10) ? "0"+String.valueOf(month+1) : String.valueOf(month+1);
        mday = (day<10) ? "0"+String.valueOf(day) : String.valueOf(day);
        String formatedDate = myear+mmonth+mday;

        switch (callerId) {
            case R.id.tvToDate:
                tvToDate.setText(formatedDate);
                break;
            case R.id.tvFromDate:
                tvFromDate.setText(formatedDate);
                break;
        }
    }

    protected void editPlanDB(String new_pname, String n_sdate, String n_fdate)
    {
        try{
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://52.79.131.13/db_update.php");

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            String query = "update plan set pname='"+new_pname+"', sdate="+n_sdate+", fdate="+n_fdate
                    + " where planno="+pno+" and pname='"+pname_t+"' and usrid='"+usrid+"'";
            nameValuePairs.add(new BasicNameValuePair("query", query));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
            HttpResponse response = httpClient.execute(httpPost);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
