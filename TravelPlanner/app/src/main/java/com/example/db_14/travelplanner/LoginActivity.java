package com.example.db_14.travelplanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by db-14 on 2017. 2. 7..
 */
public class LoginActivity extends Activity {

    //EditText usr_id = (EditText)findViewById(R.id.user_id);
    //EditText usr_pwd = (EditText)findViewById(R.id.user_pwd);
    Button login;
    //Button signup = (Button)findViewById(R.id.signup);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
