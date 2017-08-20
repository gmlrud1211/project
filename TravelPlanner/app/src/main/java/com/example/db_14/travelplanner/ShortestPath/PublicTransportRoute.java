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
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by a0104 on 2017-07-06.
 */

public class PublicTransportRoute {

    private  ArrayList<TMapPoint> base_path, opt_path;
    private HashMap<String, TransPortData> base_pass;
    private ArrayList<TransPortData> opt_pass;
    private int n, state=0;
    private int idx_pass[][];
    private boolean visit[];
    private long data[][];
    private int s, e;

    public PublicTransportRoute (ArrayList<TMapPoint> path) {
        base_path = path;
        n = base_path.size();
        visit = new boolean[n];
        data = new long[n][n];
        base_pass = new HashMap<String, TransPortData>();
        idx_pass = new int[n][n];
        opt_pass = new ArrayList<TransPortData>();
        opt_path = new ArrayList<TMapPoint>();

        for(int i=0; i<n; i++)
        {
            visit[i] = false;
        }
    }

    public int setOptimalRoute() throws Exception
    {
        if(n<2)
        {
            return -1;
        }

        setArrayDistance();
        if(state==1) return -1;
        findShortestPath();
        return 1;
    }

    private void setArrayDistance() {

        try {
            JSONParser jsonParser = new JSONParser();
            for (int i=0; i<n; i++)
            {
                for (int j=0; j<n; j++) {
                    if(i==j) {
                        data[i][j] = (long) 0;
                        continue;
                    }
                    JSONObject jsonObject = (JSONObject) jsonParser.parse(readUrl(
                            String.valueOf(base_path.get(i).getLongitude()),    //startX
                            String.valueOf(base_path.get(i).getLatitude()),     //startY
                            String.valueOf(base_path.get(j).getLongitude()),    //endX
                            String.valueOf(base_path.get(j).getLatitude())      //endY
                    ));   // url에서 json값 읽어옴

                    JSONObject json1 = (JSONObject) jsonObject.get("result");

                    if(json1==null) {
                        state = 1;
                        return;
                    }

                    JSONArray json2 = (JSONArray) json1.get("path");    // 경로 json array
                    JSONObject json3 = (JSONObject) json2.get(0);   // 최단 대중교통 경로 루트 json object
                    JSONObject info = (JSONObject) json3.get("info");     //
                    JSONArray subPath = (JSONArray) json3.get("subPath");
                    // 환승의 경우 고려해야함, (subPath - 1) / 2 == 경로의 수 ok
                    // 추가해주는 부분은 문제가 없으나 base_path의 배열이 한정되어 있음, 경로 개수 배열 추가, 해시맵을 이용해 경로별 인덱스를 부여하는 방식으로 해결 ok
                    int pass_cnt = (subPath.size() - 1) / 2;
                    for(int idx=subPath.size()-2; idx>0;idx-=2) {
                        JSONObject json4 = (JSONObject) subPath.get(idx);
                        String busNo="";
                        JSONObject lane = (JSONObject) json4.get("lane");
                        if(lane.get("name")==null) busNo = lane.get("busNo").toString();
                        else if(lane.get("busNo")==null) busNo = lane.get("name").toString();
                        // 환승 경로별로 내리는 정류장과 소요 시간이 다름, 고려 필요함
                        String sname = json4.get("startName").toString();
                        String ename = json4.get("endName").toString();
                        long sectiontime = (long) json4.get("sectionTime");
                        long totaltime = (long) info.get("totalTime");
                        idx_pass[i][j]++;
                        String pass_idx = i+","+j+"-"+pass_cnt--;
                        base_pass.put(pass_idx,new TransPortData(busNo, sname, ename, sectiontime));
                        data[i][j] = totaltime;
                    }
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
                for(int idx=1; idx<=idx_pass[i][e]; idx++) {
                    String pass_idx = i+","+e+"-"+idx;
                    opt_pass.add(base_pass.get(pass_idx));
                }

                break;
            }

            visit[k] = true;
            opt_path.add(base_path.get(k));
            for(int idx=1; idx<=idx_pass[i][k]; idx++) {
                String pass_idx = i+","+k+"-"+idx;
                opt_pass.add(base_pass.get(pass_idx));
            }

            min = 1000000000;
            find = false;
        }

    }

    public ArrayList<TMapPoint> getOptimalRoute() throws Exception {
        return opt_path;
    }

    public ArrayList<TransPortData> getOptimalPassList() throws Exception {
        return opt_pass;
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
