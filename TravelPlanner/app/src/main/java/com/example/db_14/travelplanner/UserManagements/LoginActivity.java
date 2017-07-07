package com.example.db_14.travelplanner.UserManagements;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.db_14.travelplanner.DB.DBHelper;
import com.example.db_14.travelplanner.MainUIActivity;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by db-14 on 2017. 2. 7..
 */
public class LoginActivity extends Activity {
    ViewFlipper vf;
    HttpPost httpPost;
    HttpResponse response;
    HttpClient httpClient;
    List<NameValuePair> nameValuePairs;
    Dialog dialog =  null;
    EditText usr_id;
    EditText usr_pwd;
    Button login;
    Button signup;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        dbHelper = new DBHelper(getApplicationContext(), "UserInfo.db", null, 1);
        usr_id = (EditText)findViewById(R.id.user_id);
        usr_pwd = (EditText)findViewById(R.id.user_pwd);
        signup = (Button)findViewById(R.id.signup);
        login = (Button)findViewById(R.id.login);
        dialog = new Dialog(this, R.style.MyDialog);
        dialog.setCancelable(true);
        dialog.addContentView(new ProgressBar(this), new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        if(dbHelper.getResult().get("isLogin").equals("y")) {
            Intent in = new Intent(LoginActivity.this, MainUIActivity.class);
            startActivity(in);
            finish();
        }
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        login();
                        Looper.loop();
                    }
                }).start();
            }
        });
    }

    void login()
    {
        try{
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost("http://52.79.131.13/app_login_db2.php");

            nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("id", usr_id.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("password", usr_pwd.getText().toString()));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response = httpClient.execute(httpPost);

            ResponseHandler<String> responseHandler = new BasicResponseHandler();

            final String response = httpClient.execute(httpPost, responseHandler);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                }
            });

            if(response.equalsIgnoreCase("User Found"))
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                    }
                });

                Intent intent = new Intent(LoginActivity.this, MainUIActivity.class);
                dbHelper.insert(usr_id.getText().toString(), "y");
                startActivity(intent);
                finish();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            dialog.dismiss();
            e.printStackTrace();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
