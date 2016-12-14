package com.example.idc06.layout.reservation;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by idc06 on 2016-12-14.
 */

public class Reservation_Task extends Activity{


    public void insert_reservation(String _link, String _result, String _room_num, String _people_num, String _limit_time, String _now, String _id){
    class reservationtask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            String link = params[0];
            String result = params[1];
            String room_num = params[2];
            String people_num = params[3];
            String limit_time = params[4];
            String now = params[5];
            String id = params[6];
            String data="";
            URL url;
            Log.d("asdzxc",link);
            BufferedReader bufferedReader;
            try{
                data += "?result="+ URLEncoder.encode(result,"UTF-8");
                data += "&room_num="+URLEncoder.encode(room_num,"UTF-8");
                data += "&people_num="+URLEncoder.encode(people_num,"UTF-8");
                data += "&limit_time="+URLEncoder.encode(limit_time,"UTF-8");
                data += "&now="+URLEncoder.encode(now,"UTF-8");
                data += "&id="+URLEncoder.encode(id,"UTF-8");

                link += data;
                Log.d("asdzxc",link);
                url = new URL(link);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String a = bufferedReader.readLine();
                return a;

            }catch (Exception e){e.printStackTrace();}

            return null;
        }
      }
        new reservationtask().execute(_link,_result,_room_num,_people_num,_limit_time,_now,_id);
    }
}
