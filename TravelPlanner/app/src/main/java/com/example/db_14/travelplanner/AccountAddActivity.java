package com.example.db_14.travelplanner;

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
 * Created by db-14 on 2017. 6. 17..
 */

public class AccountAddActivity extends Activity implements View.OnClickListener {

    EditText btitle, bprice;
    TextView bDate;
    Button add_btn;
    String usrid, pno, seqno;
    DateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);
    public static final String DATE_FORMAT = "EEE, MMM d, yyyy";
    int callerId = -1;
    private Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill_add);
        btitle = (EditText) findViewById(R.id.bill_title);
        bprice = (EditText) findViewById(R.id.bill_price);
        bDate = (TextView) findViewById(R.id.billDate);
        add_btn = (Button) findViewById(R.id.bill_add_btn);

        usrid = getIntent().getStringExtra("USRID");
        pno = getIntent().getStringExtra("PNO");
        seqno = getIntent().getStringExtra("SEQNO");

        add_btn.setOnClickListener(this);
        bDate.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.billDate:
                TextView et = (TextView) view;
                showDatePickerDialog(view.getId(), et.getText().toString().trim());
                break;
            case R.id.bill_add_btn:
                Intent in = new Intent();
                addBillDB(pno, btitle.getText().toString(),bprice.getText().toString(), bDate.getText().toString());
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
        datePicker.setTitle("날짜 선택");
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
        mmonth = (month + 1 < 10) ? "0" + String.valueOf(month + 1) : String.valueOf(month + 1);
        mday = (day < 10) ? "0" + String.valueOf(day) : String.valueOf(day);
        String formatedDate = myear + mmonth + mday;

        switch (callerId) {
            case R.id.billDate:
                bDate.setText(formatedDate);
                break;
        }
    }

    protected void addBillDB(String pno, String btitle, String bprice, String bdate)
    {
        try{
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://52.79.131.13/db_insert.php");

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            String query = "insert into bill_info(pno, seqno, date, usrid, bill, price) values ('"+pno+"','"+seqno+"','"+bdate+"','"+usrid+"','"+btitle+"','"+bprice+"')"; // 쿼리문 수정 및 db 테이블 추가 필요
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