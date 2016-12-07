package com.example.idc06.layout.reservation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by idc06 on 2016-12-03.
 */

public class AdminActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView textView;
    private String member="",reservation="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_layout);
        toolbar = (Toolbar)findViewById(R.id.admin_toolbar);
        toolbar.setTitle("관리자 페이지");
        setSupportActionBar(toolbar);
        textView = (TextView)findViewById(R.id.db_text);
        DB_reservation db_reservation = new DB_reservation(getApplicationContext(),"inpyung.db",null,1 );
        member += db_reservation.member_print();
        reservation +=db_reservation.reservation_list();

        Log.d("asd",member);
        Log.d("zxc",reservation);
    }

    public void member_db(View v){
        textView.setText(member);
    }
    public void reservation_db(View v){
        textView.setText(reservation);
    }
}
