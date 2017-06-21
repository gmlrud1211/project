package com.example.db_14.travelplanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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

public class MyReviewActivity extends Activity {
    ListView review_activity_main;
    ArrayList<ReviewListViewItem> rlist;
    String usrid;
    RListAdapter adapter;
    Button remove_btn;
    int is_remove=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_main);
        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "UserInfo.db", null, 1);
        usrid = dbHelper.getResult().get("usrid");
        review_activity_main = (ListView)findViewById(R.id.review_activity_main);
        rlist = new ArrayList<ReviewListViewItem>();
        TextView rtitle = (TextView) findViewById(R.id.rtitle);
        remove_btn = (Button) findViewById(R.id.removereview);

        rtitle.setText("내 리뷰");

        getReview();

        adapter = new RListAdapter(getApplicationContext(),rlist);
        review_activity_main.setAdapter(adapter);

        remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplication(), "삭제할 항목을 선택하세요.", Toast.LENGTH_SHORT).show();
                is_remove=1;
            }
        });

        review_activity_main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(is_remove==1)
                {
                    deleteReview(rlist.get(i).getPno());
                    rlist.clear();
                    getReview();
                    adapter.notifyDataSetChanged();
                    return;
                }
                Intent intent = new Intent(MyReviewActivity.this, ReviewDetailActivity.class);
                intent.putExtra("PNO", rlist.get(i).getPno());
                intent.putExtra("PNAME", rlist.get(i).getTitle());
                intent.putExtra("PTEXT", rlist.get(i).getText());
                intent.putExtra("PLIKE", rlist.get(i).getLike());
                intent.putExtra("PID", rlist.get(i).getReviewer());
                startActivityForResult(intent, 1);
            }
        });
    }
    public void deleteReview(String pno)
    {
        try{
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://52.79.131.13/db_delete.php");

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
            String query = "delete from review_info where pno="+pno;
            nameValuePairs.add(new BasicNameValuePair("query", query));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
            HttpResponse response = httpClient.execute(httpPost);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();

            final String res = httpClient.execute(httpPost, responseHandler);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getReview() {
        BufferedInputStream reader = null;
        URL url;
        StringBuffer buffer = null;

        try {
            url = new URL("http://52.79.131.13/review_db.php");
            reader = new BufferedInputStream(url.openStream()); // url 오픈 후 페이지 내 텍스트 모두 읽어옴
            buffer = new StringBuffer();
            int i;
            byte[] b = new byte[4096];

            while ((i = reader.read(b)) != -1) {
                buffer.append(new String(b, 0, i));
            }

        } catch (Exception e) {
            Log.e("ERROR : ", e.getMessage());
        }

        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(buffer.toString());
            JSONArray array = (JSONArray) jsonObject.get("result");
            String title, text, like, id, pno;
            for (int i = 0; i < array.size(); i++) {
                JSONObject entity = (JSONObject) array.get(i);
                id = entity.get("usrid").toString();
                if (id.equals(usrid)) {
                    pno = entity.get("pno").toString();
                    title = entity.get("pname").toString();
                    like = entity.get("like").toString();
                    text = entity.get("text").toString();
                    rlist.add(new ReviewListViewItem(pno, title, id, Integer.parseInt(like), text));
                }
            }
        }
            catch(Exception e)
            {
                e.printStackTrace();
            }

        }

    private class RListAdapter extends BaseAdapter
    {
        private Context context = null;
        private ArrayList<ReviewListViewItem> list = new ArrayList<ReviewListViewItem>();

        public RListAdapter(Context context, ArrayList<ReviewListViewItem> list){
            super();
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount()
        {
            return list.size();
        }

        @Override
        public Object getItem(int position)
        {
            return list.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent)
        {
            if (convertView==null)
            {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.review_listview_item, parent, false);
            }

            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView reviewer = (TextView) convertView.findViewById(R.id.reviewer);
            TextView like = (TextView) convertView.findViewById(R.id.like);
            title.setText(list.get(pos).getTitle());
            reviewer.setText(list.get(pos).getReviewer());
            like.setText(String.valueOf(list.get(pos).getLike()));

            return convertView;
        }
    }

    @Override
    protected void onActivityResult(int request, int result, Intent in)
    {
        if (request==1)
        {
            if(result==RESULT_OK)
            {
                rlist.clear();
                getReview();
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
