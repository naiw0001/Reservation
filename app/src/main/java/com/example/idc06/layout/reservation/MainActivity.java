package com.example.idc06.layout.reservation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private View dialog_view, dialog_del_view;
    private EditText ed_name, del_name, del_idx;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar)findViewById(R.id.main_toolbar);
        toolbar.setTitle("인평자동차정보고등학교 세미나실")   ;
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");

    }
    // Button onClick

    public void reservation(View v){ // 예약 하기
        Intent intent_re = new Intent(MainActivity.this,ReservationActivity.class);
        intent_re.putExtra("id",id);
        startActivity(intent_re);
        finish();
    }

    public void cancel(View v){ // 예약 취소하기
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog_del_view = (View)View.inflate(MainActivity.this,R.layout.dialog_del_layout,null);
        dialog.setTitle("예약을 취소하시겠습니까?");
        dialog.setView(dialog_del_view);
        dialog.setPositiveButton("지우기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                del_name= (EditText)dialog_del_view.findViewById(R.id.del_name);
                del_idx = (EditText)dialog_del_view.findViewById(R.id.del_idx);
                String dName = del_name.getText().toString();
                String dIdx = del_idx.getText().toString();
                int idx = Integer.parseInt(dIdx);
                DB_reservation db_reservation = new DB_reservation(getApplicationContext(),"inpyung.db",null,1 );
                db_reservation.delete("DELETE FROM list WHERE _name='"+dName+"' AND _idx = "+idx+";");
                db_reservation.delete("DELETE FROM reservation WHERE _idx = "+idx+";");
                Toast.makeText(getApplicationContext(),"취소되었습니다.",Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setNegativeButton("취소",null);
        dialog.show();

    }

    public void show(View v){ // 예약 보기
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog_view = (View)View.inflate(MainActivity.this,R.layout.dialog_layout,null);
        dialog.setTitle("예약 확인");
        dialog.setView(dialog_view);
        dialog.setPositiveButton("보기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ed_name = (EditText)dialog_view.findViewById(R.id.ed_name);
                Intent intent = new Intent(MainActivity.this,ShowActivity.class);
                String _name = ed_name.getText().toString();
                intent.putExtra("name",_name);
                startActivity(intent);
            }
        });
        dialog.setNegativeButton("취소",null);
        dialog.show();
    }

}