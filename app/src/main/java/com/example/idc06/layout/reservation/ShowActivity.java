package com.example.idc06.layout.reservation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

/**
 * Created by idc06 on 2016-12-01.
 */

public class ShowActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_layout);

        TextView textView = (TextView)findViewById(R.id.text);
        Toolbar toolbar = (Toolbar)findViewById(R.id.tool);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        toolbar.setTitle(name +"님 예약 조회 목록");
        setSupportActionBar(toolbar);

        DB_reservation db_reservation = new DB_reservation(getApplicationContext(),"inpyung.db",null,1 );
        String show = db_reservation.printwhere(name);
        textView.setText(show);



    }

}
