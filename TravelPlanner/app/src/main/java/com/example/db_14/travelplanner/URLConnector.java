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
    String TOURKEY = "ServiceKey=dx6Je9L%2FluhYWHKwoLx0GoEk7VvDKF0ABstzCLgfe7MJIFpFQ3EhtGGs1TfPkuqbScvzFxVxbLjcrMrztNFV2w%3D%3D&MobileOS=AND&MobileApp=TravelPlanner&numOfRows=100&_type=json";
    String sinfo_opt = "&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y";
    String sinfo_detail = "&listYN=Y";
    ArrayList<HashMap<String, String>> dList;  // 결과값 담아줄 arraylist

    public URLConnector() {
        dList = new ArrayList<HashMap<String, String>>();
    }

    public void APIareaCode(String reqCode) {

        HashMap<String, String> data;

        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(readUrl(base + reqCode + TOURKEY));   // url에서 json값 읽어옴
            JSONObject json1 = (JSONObject) jsonObject.get("response");
            JSONObject json2 = (JSONObject) json1.get("body");
            JSONObject json3 = (JSONObject) json2.get("items");
            JSONArray array = (JSONArray) json3.get("item");

            for (int i = 0; i < array.size(); i++) {

                data = new HashMap<String, String>();

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

    public void APIsights(String reqCode, String areaCode, String sigunguCode, String contentTypeid) {

        HashMap<String, String> data;

        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(readUrl(base + reqCode + "areaCode=" +areaCode + "&sigunguCode=" +
                    sigunguCode + "&contentTypeId=" + contentTypeid + "&" +TOURKEY));   // url에서 json값 읽어옴
            JSONObject json1 = (JSONObject) jsonObject.get("response");
            JSONObject json2 = (JSONObject) json1.get("body");
            JSONObject json3 = (JSONObject) json2.get("items");
            JSONArray array = (JSONArray) json3.get("item");

            for (int i = 0; i < array.size(); i++) {

                data = new HashMap<String, String>();

                JSONObject entity = (JSONObject) array.get(i);

                String title, contentid;

                contentid = entity.get("contentid").toString();
                title = entity.get("title").toString();


                data.put("title", title);
                data.put("contentid", contentid);

                dList.add(data);
            }
        }
        catch (Exception e) {
            Log.e("ERROR : ", e.getMessage());
        }
    }

    public void APIsightInfo(String reqCode, String contentid) {

        HashMap<String, String> data;

        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(readUrl(base + reqCode + TOURKEY + "&contentId=" + contentid + sinfo_opt));   // url에서 json값 읽어옴
            JSONObject json1 = (JSONObject) jsonObject.get("response");
            JSONObject json2 = (JSONObject) json1.get("body");
            JSONObject json3 = (JSONObject) json2.get("items");
            JSONObject item = (JSONObject) json3.get("item");


            data = new HashMap<String, String>();

            String overview = item.get("overview").toString();
            String mapx = item.get("mapx").toString();
            String mapy = item.get("mapy").toString();
            String firstimage;
            String addr1;

            if(item.get("addr1")!=null)
                addr1 = item.get("addr1").toString();
            else
                addr1 = "addr1 not Found";

            if (item.get("firstimage")!=null)
                firstimage = item.get("firstimage").toString();
            else
                firstimage = "Image Not Found";

            String title = item.get("title").toString();
            data.put("overview", overview);
            data.put("firstimage", firstimage);
            data.put("addr1", addr1);
            data.put("mapx", mapx);
            data.put("mapy", mapy);
            data.put("title", title);

            dList.add(data);
        }
        catch (Exception e) {
            Log.e("ERROR : ", e.getMessage());
        }
    }
    public void APIsightDetailInfo(String reqCode, String contentid) {

        HashMap<String, String> data;

        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(readUrl(base + reqCode + TOURKEY + "&contentId=" + contentid + sinfo_detail));   // url에서 json값 읽어옴
            JSONObject json1 = (JSONObject) jsonObject.get("response");
            JSONObject json2 = (JSONObject) json1.get("body");
            JSONObject json3 = (JSONObject) json2.get("items");
            JSONArray array = (JSONArray) json3.get("item");

            for (int i = 0; i < array.size(); i++) {

                data = new HashMap<String, String>();

                JSONObject entity = (JSONObject) array.get(i);
                String subname = entity.get("subname").toString();
                String subdetailimg;

                if (entity.get("subdetailimg")!=null)
                    subdetailimg = entity.get("subdetailimg").toString();
                else
                    subdetailimg = "Image Not Found";

                data.put("subdetailimg", subdetailimg);
                data.put("subname", subname);

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
