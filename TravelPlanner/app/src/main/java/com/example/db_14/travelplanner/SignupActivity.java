package com.example.db_14.travelplanner;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by a0104 on 2017-02-08.
 */

public class SignupActivity extends Activity {

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

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String uname = name.getText().toString();
                        String uid = id.getText().toString();
                        String upwd = pwd.getText().toString();

                        try{
                            URL url = new URL("http://52.79.131.13/app_signup_db.php?"+"name="+ URLEncoder.encode(uname, "UTF-8")
                            + "&id=" + URLEncoder.encode(uid, "UTF-8") + "&password=" + URLEncoder.encode(upwd, "UTF-8"));

                            url.openStream();

                            String result = getXmlData("insertresult.xml", "result");

                            if(result.equals("1"))
                            {
                                Toast.makeText(getApplication(), "가입 완료", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "가입 실패( ID 중복일 수 있음 )", Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private String getXmlData(String filename, String str)
    {
        String rss = "http://52.79.131.13/";
        String ret = "";

        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            URL server = new URL(rss+filename);
            InputStream is = server.openStream();
            xpp.setInput(is, "UTF-8");

            int eventType = xpp.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT)
            {
                if(eventType == XmlPullParser.START_TAG)
                {
                    if(xpp.getName().equals(str))
                    {
                        ret = xpp.nextText();
                    }
                }
                eventType = xpp.next();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }
}
