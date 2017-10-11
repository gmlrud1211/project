package com.example.db_14.travelplanner.Reviews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.db_14.travelplanner.Dialogs.CustomDialog;
import com.example.db_14.travelplanner.DB.DBHelper;
import com.example.db_14.travelplanner.R;
import com.example.db_14.travelplanner.Sights.SightData;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by a0104 on 2017-06-19.
 */

public class SecondFragment extends Fragment {

    private String text, title, id, pno;
    String myid;
    String rev_pno;
    CustomDialog dialog;
    ArrayList<SightData> slist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.second_frag, container, false);
        TextView dt = (TextView) v.findViewById(R.id.dtFragSecond);
        TextView pn = (TextView) v.findViewById(R.id.review_pname);
        TextView pid = (TextView) v.findViewById(R.id.review_id);
        Button get_btn = (Button)v.findViewById(R.id.get_btn);
        dt.setText(text);
        pn.setText(title);
        pid.setText(id);

        DBHelper dbHelper = new DBHelper(v.getContext(), "UserInfo.db", null, 1);
        myid = dbHelper.getResult().get("usrid");

        get_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View.OnClickListener dListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getReviewPlan(pno);
                        getReviewPlanInfo();
                        Toast.makeText(v.getContext(), "가져오기를 완료했습니다.", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                };

                View.OnClickListener cListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "가져오기를 취소합니다.", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                };

                dialog = new CustomDialog(v.getContext(), cListener, dListener, "일정을 가져오시겠습니까?");
                dialog.show();
            }
        });

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            text = getArguments().getString("TEXT");
            title = getArguments().getString("PNAME");
            id = getArguments().getString("PID");
            pno = getArguments().getString("PNO");
            slist = (ArrayList<SightData>) getArguments().getSerializable("SLIST");
        }
    }

    public void getReviewPlan(String pno) {
        BufferedInputStream reader = null;
        URL url;
        StringBuffer buffer = null;

        try {
            url = new URL("http://52.79.131.13/plan_db.php");
            reader = new BufferedInputStream(url.openStream()); // url 오픈 후 페이지 내 텍스트 모두 읽어옴
            buffer = new StringBuffer();
            int i;
            byte[] b = new byte[4096];

            while ((i = reader.read(b)) != -1) {
                buffer.append(new String(b, 0, i));
            }

        }

        catch (Exception e) {
            Log.e("ERROR : ", e.getMessage());
        }

        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(buffer.toString());
            JSONArray array = (JSONArray) jsonObject.get("result");
            String pname = ""; String sdate=""; String fdate="";
            for (int i = 0; i < array.size(); i++) {
                JSONObject entity = (JSONObject) array.get(i);
                if(pno.equals(entity.get("planno").toString())) {
                    pname = entity.get("pname").toString();
                    sdate = entity.get("sdate").toString();
                    fdate = entity.get("fdate").toString();
                }
            }

            try{
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://52.79.131.13/db_insert.php");

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                String query = "insert into plan(pname, usrid, sdate, fdate) values ('"+pname+"','"+myid+"','"+sdate+"','"+fdate+"')"; // 쿼리문 수정 및 db 테이블 추가 필요
                nameValuePairs.add(new BasicNameValuePair("query", query));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
                HttpResponse response = httpClient.execute(httpPost);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                getRevPno(pname,sdate,fdate);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void getRevPno(String pname, String sdate, String fdate) {
        try {
            HttpPost httpPost;
            HttpResponse response;
            HttpClient httpClient;
            List<NameValuePair> nameValuePairs;

            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost("http://52.79.131.13/planno_db.php");
            String query = "select planno from plan where pname='"+pname+"' and usrid='"+myid+"' and sdate='"+sdate+"' and fdate='"+fdate+"'";
            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("query", query));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
            response = httpClient.execute(httpPost);

            ResponseHandler<String> responseHandler = new BasicResponseHandler();

            final String res = httpClient.execute(httpPost, responseHandler);
            rev_pno = res;
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        }
    public void getReviewPlanInfo() {
        String query = "";
        for(int i=0; i<slist.size(); i++) {
            query = "insert into plan_info(planno, usrid, sname, lat, lon, contentid) values ('"+rev_pno+"','"+myid+"','"+slist.get(i).getSight()+"','"
                    +slist.get(i).getLat()+"','"+slist.get(i).getLon()+"','"+slist.get(i).contentid+"')";
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://52.79.131.13/db_insert.php");

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("query", query));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
                HttpResponse response = httpClient.execute(httpPost);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
