package com.example.db_14.travelplanner.ShortestPath;

import android.util.Log;

import com.skp.Tmap.TMapPoint;

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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a0104 on 2017-07-06.
 */

public class PublicTransportRoute {

    private  ArrayList<TMapPoint> base_path, opt_path;
    int n;
    private boolean visit[];
    private long data[][];
    private int s, e;

    public PublicTransportRoute (ArrayList<TMapPoint> path) {
        base_path = path;
        n = base_path.size();
        visit = new boolean[n];
        data = new long[n][n];
        opt_path = new ArrayList<TMapPoint>();

        for(int i=0; i<n; i++)
        {
            visit[i] = false;
        }
    }

    public void setOptimalRoute() throws Exception
    {
        if(n<3)
        {
            opt_path = base_path;
            return;
        }

        setArrayDistance();
        findShortestPath();
    }

    private void setArrayDistance() {

        try {
            JSONParser jsonParser = new JSONParser();
            for (int i=0; i<n; i++)
            {
                for (int j=i; j<n; j++) {
                    JSONObject jsonObject = (JSONObject) jsonParser.parse(readUrl(
                            String.valueOf(base_path.get(i).getLongitude()),    //startX
                            String.valueOf(base_path.get(i).getLatitude()),     //startY
                            String.valueOf(base_path.get(j).getLongitude()),    //endX
                            String.valueOf(base_path.get(j).getLatitude())      //endY
                    ));   // url에서 json값 읽어옴
                    JSONObject json1 = (JSONObject) jsonObject.get("result");
                    JSONArray json2 = (JSONArray) json1.get("path");    // 경로 json array
//            JSONObject json3 = (JSONObject) json2.get("items");
                    JSONObject json3 = (JSONObject) json2.get(0);   // 최단 대중교통 경로 루트 json object
                    JSONObject info = (JSONObject) json3.get("info");     //
//            JSONArray subpath = (JSONArray) json3.get("subPath");     //
                    data[i][j] = data[j][i] = (long) info.get("totalTime");
                }
            }
            Log.d("d", "path end");
        }
        catch (Exception e) {
            Log.e("ERROR : ", e.getMessage());
        }
    }

    private void findShortestPath() {
        s = 0; e = n-1;

        int k = 0;
        double min = 1000000000;
        boolean find = false;

        visit[0] = true;
        opt_path.add(base_path.get(0));

        for(int i=s; i<e; i=k)
        {
            for(int j=0; j<e;j++)
            {
                if ( data[i][j]!=0 && data[i][j]<min && !visit[j] )
                {
                    min = data[i][j];
                    k = j;
                    find = true;
                }
            }

            if(!find)
            {
                visit[e] = true;
                opt_path.add(base_path.get(e));
                break;
            }

            visit[k] = true;
            opt_path.add(base_path.get(k));

            min = 1000000000;
            find = false;
        }

    }

    public ArrayList<TMapPoint> getOptimalRoute() throws Exception {
        return opt_path;
    }

    private String readUrl(String startX, String startY, String endX, String endY) {

        String res = "";
        try{
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://52.79.131.13/searchTransPath.php");

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("start_x", startX));
            nameValuePairs.add(new BasicNameValuePair("start_y", startY));
            nameValuePairs.add(new BasicNameValuePair("end_x", endX));
            nameValuePairs.add(new BasicNameValuePair("end_y", endY));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
            HttpResponse response = httpClient.execute(httpPost);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            res = httpClient.execute(httpPost, responseHandler);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }
}
