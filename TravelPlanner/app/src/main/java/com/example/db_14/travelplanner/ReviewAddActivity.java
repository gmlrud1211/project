package com.example.db_14.travelplanner;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by heekyoung on 2017-06-17.
 */

public class ReviewAddActivity extends Activity {

    EditText pname, subtext;
    Button add_btn;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_add);

        pname = (EditText)findViewById(R.id.plan_name);
        subtext = (EditText)findViewById(R.id.subtext);

        add_btn = (Button)findViewById(R.id.add_ok_btn);


    }

    protected void addReviewDB(String pname, String subtext) {
        try {

        }
        catch(Exception e) {
            e.getMessage();
        }
    }
}
