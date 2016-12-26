package com.example.idc06.layout.reservation;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by idc06 on 2016-12-01.
 */

public class ShowActivity extends AppCompatActivity {
    private String name, id;
    private TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_layout);

        textView = (TextView)findViewById(R.id.text);
        Toolbar toolbar = (Toolbar)findViewById(R.id.tool);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        id = intent.getStringExtra("id");

        toolbar.setTitle(name +"님 예약 조회 목록");
        setSupportActionBar(toolbar);

//        DB_reservation db_reservation = new DB_reservation(getApplicationContext(),"inpyung.db",null,1 );
//        String show = db_reservation.printwhere(name);

        String show="";
        new ShowTask().execute("http://10.142.47.250:8000/naiw/show.php?index=0");

    }
    class ShowTask extends AsyncTask<String,Void,String>{
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textView.setText(s);
        }

        @Override
        protected String doInBackground(String... params) {
            StringBuilder json = new StringBuilder();
            String result = "";
            String link = params[0];
            try {
                String data = URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8");
                data += "&"+URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8");

                URL url = new URL(link);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestMethod("POST");
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();
                wr.close();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                while(true){
                    String line = bufferedReader.readLine();
                    if(line == null) break;
                    json.append(line+"\n");
                }
                bufferedReader.close();
                result = show(json.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        public String show(String result){
            String r="";
            try{
                JSONArray ja = new JSONArray(result);
                for(int i = 0 ;i<ja.length();i++){
                    JSONObject jo = ja.getJSONObject(i);
                    r += String.format("번호 : %s | 이름 : %s | 시간 : %s | 방번호 : %s | 사람 수 : %s | 제한 시간 : %s \n", jo.getString("_idx"),jo.getString("_name"),jo
                    .getString("_time"),jo.getString("_room"),jo.getString("_people"),jo.getString("_limit"));
                    Log.d("Asd",r);
                }
            }catch (Exception e){}
            return r;
        }
    }

}
