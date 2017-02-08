package com.example.db_14.travelplanner;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by db-14 on 2017. 2. 7..
 */
public class LoginActivity extends Activity {

//    URLConnector task = new URLConnector();
    EditText usr_id;
    EditText usr_pwd;
    Button btnlogin;
    Button btnsignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnlogin = (Button)findViewById(R.id.btn_login);
        usr_id = (EditText)findViewById(R.id.user_id);
        usr_pwd = (EditText)findViewById(R.id.user_pwd);
        btnsignup = (Button)findViewById(R.id.btn_signup);

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("debug : ", "test");
                Toast.makeText(LoginActivity.this, "click", Toast.LENGTH_SHORT);
            }
        });
//        btnlogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                URLConnector url = new URLConnector();
//                url.start();
//                try {
//                    url.join();
//                }
//                catch(Exception e){
//                    e.printStackTrace();
//                }
//
//                String result = url.getTemp();
//
//                Log.d("result : ", ParseJSON(result));
//                ParseJSON(result);
//            }
//                /*Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();*/
//        });
//    }
//
//    // JSON 데이터를 파싱합니다.
//    // URLConnector로부터 받은 String이 JSON 문자열이기 때문입니다.
//    public String ParseJSON(String target){
//
//        try {
//            JSONObject json = new JSONObject(target);
//
//            JSONArray arr = json.getJSONArray("result");
//
//            for(int i = 0; i < arr.length(); i++){
//                JSONObject json2 = arr.getJSONObject(i);
//
//                Log.v("usr_id : ", json2.getString("usr_id"));
//            }
//
//            return "";
//        }
//
//        catch(Exception e){
//            e.printStackTrace();
//        }
//
//        return null;
    }
}
