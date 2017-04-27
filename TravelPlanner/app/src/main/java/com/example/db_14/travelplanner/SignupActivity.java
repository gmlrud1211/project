package com.example.db_14.travelplanner;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
 * Created by a0104 on 2017-02-08.
 */

public class SignupActivity extends Activity {

    HttpPost httpPost;
    HttpResponse response;
    HttpClient httpClient;
    List<NameValuePair> nameValuePairs;
    ProgressDialog dialog = null;

    EditText id, pwd, name;
    Button btn_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        id = (EditText)findViewById(R.id.sign_id);
        pwd = (EditText)findViewById(R.id.sign_pwd);
        name = (EditText)findViewById(R.id.sign_name);
        btn_ok = (Button)findViewById(R.id.sign_ok);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(id.getText().toString().equals("") || pwd.getText().toString().equals("") || name.getText().toString().equals(""))
                {
                    Toast.makeText(getApplication(), "모두 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                dialog = ProgressDialog.show(SignupActivity.this, "", "Validating user...", true);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        signup(id.getText().toString(), pwd.getText().toString(), name.getText().toString());
                        Looper.loop();
                    }
                }).start();
            }
        });
    }

    void signup(String id, String pwd, String name)
    {
        try{
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost("http://52.79.131.13/signup_db.php");

            nameValuePairs = new ArrayList<NameValuePair>(3);
            nameValuePairs.add(new BasicNameValuePair("id", id));
            nameValuePairs.add(new BasicNameValuePair("password", pwd));
            nameValuePairs.add(new BasicNameValuePair("name", name));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
            response = httpClient.execute(httpPost);

            ResponseHandler<String> responseHandler = new BasicResponseHandler();

            final String response = httpClient.execute(httpPost, responseHandler);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                }
            });

            if(response.equalsIgnoreCase("1"))
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "회원가입 성공 로그인 해주세요."+response.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "회원가입 실패 (아이디 중복일 수 있습니다.)"+response.toString(), Toast.LENGTH_SHORT).show();
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
