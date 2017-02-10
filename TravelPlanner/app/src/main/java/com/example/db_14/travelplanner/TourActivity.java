package com.example.db_14.travelplanner;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.skp.Tmap.TMapView;

import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.io.BufferedInputStream;
import java.net.URL;

/**
 * Created by User on 2017-02-09.
 */

public class TourActivity extends Activity {

    String res;
    String APPKEY = "2cfca2bc-7f91-3031-b69d-3c7eed12970c";
    String TOURKEY = "?ServiceKey=dx6Je9L%2FluhYWHKwoLx0GoEk7VvDKF0ABstzCLgfe7MJIFpFQ3EhtGGs1TfPkuqbScvzFxVxbLjcrMrztNFV2w%3D%3D&MobileOS=ETC&MobileApp=TravelPlanner&_type=json";
    FrameLayout mapLayout;
    TextView tourtext;
    Button getbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);

        mapLayout = (FrameLayout) findViewById(R.id.tourview);
        tourtext = (TextView) findViewById(R.id.tourtest);
        getbtn = (Button) findViewById(R.id.getbtn);

        final TMapView tMapView = new TMapView(this);

        tMapView.setSKPMapApiKey(APPKEY);
        tMapView.setZoomLevel(10);
        mapLayout.addView(tMapView);

        getbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    res = ApiJSON("http://api.visitkorea.or.kr/openapi/service/rest/KorService/" + "areaCode" + TOURKEY);
                    tourtext.setText(res);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public String ApiJSON(String url) {
        String result = null;
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(readUrl(url));
            JSONObject json1 = (JSONObject) jsonObject.get("response");
            JSONObject json2 = (JSONObject) json1.get("body");
            JSONObject json3 = (JSONObject) json2.get("items");
            JSONArray array = (JSONArray) json3.get("item");

            for (int i = 0; i < array.size(); i++) {
                JSONObject entity = (JSONObject) array.get(i);
                String code, name, rnum;
                code = (String) entity.get("code");
                name = (String) entity.get("name");
                rnum = (String) entity.get("rnum");
                result += "code : " + code + "\n" + "name : " + name + "\n" + "rnum : " + rnum + "\n";
            }

        } catch (Exception e) {
            e.printStackTrace();
            result = "fail";
        }

        return result;
    }

    private static String readUrl(String strUrl) {
        BufferedInputStream reader = null;
        URL url;
        StringBuffer buffer;
        try {
            url = new URL(strUrl);
            reader = new BufferedInputStream(url.openStream());
            buffer = new StringBuffer();
//ok
            int i;
            byte[] b = new byte[4096];

            while ((i = reader.read(b)) != -1) {
                buffer.append(new String(b, 0, i));
            }
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }
}



