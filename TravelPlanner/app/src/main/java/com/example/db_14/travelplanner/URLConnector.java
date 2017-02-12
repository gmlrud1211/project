package com.example.db_14.travelplanner;

import android.util.Log;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by a0104 on 2017-02-12.
 */

public class URLConnector {

    String base = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/";
    String TOURKEY = "ServiceKey=dx6Je9L%2FluhYWHKwoLx0GoEk7VvDKF0ABstzCLgfe7MJIFpFQ3EhtGGs1TfPkuqbScvzFxVxbLjcrMrztNFV2w%3D%3D&MobileOS=ETC&MobileApp=TravelPlanner&numOfRows=50&_type=json";
    String reqCode, areaCode, sigunguCode;                             // 요청할 값 (areaCode 등등)
    ArrayList<HashMap<String, String>> dList;   // 결과값 담아줄 arraylist

    public URLConnector(String reqCode) {
        this.reqCode = reqCode;
        dList = new ArrayList<HashMap<String, String>>();   // arraylist 초기화
        ApiJSON();                                          // json 파싱 함수 호출
    }

    public URLConnector(String reqCode, String areaCode, String sigunguCode) {
        this.reqCode = reqCode;
        this.areaCode = areaCode;
        this.sigunguCode = sigunguCode;
        dList = new ArrayList<HashMap<String, String>>();   // arraylist 초기화
        ApiJSON2();                                          // json 파싱 함수 호출
    }

    private void ApiJSON() {

        HashMap<String, String> data;                       // jsonArray값 담아줄 hashmap

        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(readUrl(base + reqCode + TOURKEY));   // url에서 json값 읽어옴
            JSONObject json1 = (JSONObject) jsonObject.get("response");
            JSONObject json2 = (JSONObject) json1.get("body");
            JSONObject json3 = (JSONObject) json2.get("items");
            JSONArray array = (JSONArray) json3.get("item");

            for (int i = 0; i < array.size(); i++) {
                JSONObject entity = (JSONObject) array.get(i);
                String code, name;
                code = entity.get("code").toString();
                name = entity.get("name").toString();
                data = new HashMap<String, String>(2);
                data.put("code", code);
                data.put("name", name);
                dList.add(data);
            }
        }
        catch (Exception e) {
            Log.e("ERROR : ", e.getMessage());
        }
    }

    private void ApiJSON2() {

        HashMap<String, String> data;                       // jsonArray값 담아줄 hashmap

        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(readUrl(base + reqCode + areaCode + sigunguCode + "&" +TOURKEY));   // url에서 json값 읽어옴
            JSONObject json1 = (JSONObject) jsonObject.get("response");
            JSONObject json2 = (JSONObject) json1.get("body");
            JSONObject json3 = (JSONObject) json2.get("items");
            JSONArray array = (JSONArray) json3.get("item");

            for (int i = 0; i < array.size(); i++) {
                JSONObject entity = (JSONObject) array.get(i);

                String addr1, addr2, firstimage, mapx, mapy, title;

                addr1 = entity.get("addr1").toString();
                addr2 = entity.get("addr2").toString();
                firstimage = entity.get("firstimage").toString();
                mapx = entity.get("mapx").toString();
                mapy = entity.get("mapy").toString();
                title = entity.get("title").toString();

                data = new HashMap<String, String>();

                data.put("addr1", addr1);
                data.put("addr2", addr2);
                data.put("firstimage", firstimage);
                data.put("mapx", mapx);
                data.put("mapy", mapy);
                data.put("title", title);

                dList.add(data);
            }
        }
        catch (Exception e) {
            Log.e("ERROR : ", e.getMessage());
        }
    }

    public ArrayList<HashMap<String, String>> getList()
    {
        return dList;
    }

    private static String readUrl(String strUrl) {
        BufferedInputStream reader = null;
        URL url;
        StringBuffer buffer = null;

        try {
            url = new URL(strUrl);
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

        finally {
            return buffer.toString();
        }
    }
}
