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

import static com.example.db_14.travelplanner.R.id.acc_date;
import static com.example.db_14.travelplanner.R.id.acc_price;
import static com.example.db_14.travelplanner.R.id.acc_title;

/**
 * Created by db-14 on 2017. 6. 17..
 */

public class AccountActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    String usrid, pno;
    ListView accuview;
    Button add_accu, remove_accu;
    TextView total, stitle;
    private ArrayList<AccountData> blist;
    AccuAdapter adapter;
    int is_remove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "UserInfo.db", null, 1);
        usrid = dbHelper.getResult().get("usrid");

        accuview = (ListView) findViewById(R.id.account_list);
        total = (TextView) findViewById(R.id.account_total);
        stitle = (TextView) findViewById(R.id.account_title);
        add_accu = (Button) findViewById(R.id.addaccu);
        remove_accu = (Button) findViewById(R.id.removeaccu);

        blist = new ArrayList<AccountData>();
        pno = getIntent().getStringExtra("PLANNO");
        stitle.setText(getIntent().getStringExtra("PNAME"));

        getAccountlist(pno);

        adapter = new AccuAdapter(getApplicationContext(), blist);
        accuview.setAdapter(adapter);

        add_accu.setOnClickListener(this);
        remove_accu.setOnClickListener(this);
        accuview.setOnItemClickListener(this);
        getTotal(blist);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addaccu:
                Intent in = new Intent(AccountActivity.this, AccountAddActivity.class);
                in.putExtra("PNO", pno);
                startActivityForResult(in, 1);
                break;
            case R.id.removeaccu:
                Toast.makeText(this, "삭제할 항목을 선택하세요.", Toast.LENGTH_SHORT).show();
                is_remove=1;
                break;
        }
    }

    public void deleteAccount(String pno, AccountData data)
    {
        try{
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://52.79.131.13/db_delete.php");

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
            String query = "delete from bill_info where pno="+pno+" and date='"+data.date+"' and bill='"+data.title+"' and price="+data.price;
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


    @Override
    protected void onActivityResult(int request, int result, Intent in)
    {
        if (request==1)
        {
            if(result==RESULT_OK)
            {
                blist.clear();
                getAccountlist(pno);
                adapter.notifyDataSetChanged();
                getTotal(blist);
            }
        }
    }

    public void getTotal(ArrayList<AccountData> list)
    {
        int tprice = 0;

        for(int i=0; i<list.size(); i++)
        {
            tprice += Integer.parseInt(list.get(i).price);
        }

        total.setText(String.valueOf(tprice)+" 원");
    }

    public void getAccountlist(String pno)
    {
        BufferedInputStream reader = null;
        URL url;
        StringBuffer buffer = null;

        try {
            url = new URL("http://52.79.131.13/billinfo_db.php");
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
            AccountData data; String bdate, btitle, bprice;
            for (int i = 0; i < array.size(); i++) {
                JSONObject entity = (JSONObject) array.get(i);
                if(entity.get("planno").toString().equals(pno)) {
                    bdate = entity.get("date").toString();
                    btitle = entity.get("bill").toString();
                    bprice = entity.get("price").toString();
                    data = new AccountData(bdate, btitle, bprice);
                    blist.add(data);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parentView, View clickedView, int position, long id) {

        if(is_remove==1)
        {
            deleteAccount(pno, blist.get(position));
            blist.clear();
            getAccountlist(pno);
            adapter.notifyDataSetChanged();
            getTotal(blist);
            is_remove=0;
            return;
        }

        Intent intent = new Intent(AccountActivity.this, AccountEditActivity.class);
        intent.putExtra("PNO", pno);
        intent.putExtra("TITLE", blist.get(position).title);
        intent.putExtra("DATE", blist.get(position).date);
        intent.putExtra("PRICE", blist.get(position).price);
        startActivityForResult(intent, 1);
    }

    private class AccuAdapter extends BaseAdapter
    {
        private Context context = null;
        private ArrayList<AccountData> list = new ArrayList<AccountData>();

        public AccuAdapter(Context context, ArrayList<AccountData> list){
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
                convertView = inflater.inflate(R.layout.account_list_item, parent, false);
            }

            TextView title = (TextView) convertView.findViewById(acc_title);
            TextView date = (TextView) convertView.findViewById(acc_date);
            TextView price = (TextView) convertView.findViewById(acc_price);
            date.setText(list.get(pos).date);
            title.setText(list.get(pos).title);
            price.setText(list.get(pos).price);

            return convertView;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}

