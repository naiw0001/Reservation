package com.example.idc06.layout.reservation;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText _id, _pw;
    private Button bt_login, bt_join;
    private AlertDialog.Builder dialog;
    private EditText ed_name, ed_age, ed_phone, ed_id,ed_pw;
    private View join_view;
    private String name,age,phone,gender,id,pw;
    private RadioButton j_man, j_woman;
    DB_reservation db_reservation;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        _id = (EditText)findViewById(R.id.id_edit);
        _pw = (EditText)findViewById(R.id.pw_edit);
        bt_login =(Button)findViewById(R.id.btn_login);
        bt_join = (Button)findViewById(R.id.btn_join);
        bt_login.setBackgroundColor(Color.argb(0,0,0,0));
        bt_join.setBackgroundColor(Color.argb(0,0,0,0));
        db_reservation = new DB_reservation(getApplicationContext(),"inpyungschool_reservation.db",null,1 );
    }

    //로그인 메소드
    public void login(View v){
        String id = _id.getText().toString();
        int pw = Integer.parseInt(_pw.getText().toString());
        DB_reservation db_reservation = new DB_reservation(getApplicationContext(),"inpyungschool_reservation.db",null,1 );
        String login = db_reservation.login(id,pw);

        if(login.equals("Ok")){
            Toast.makeText(this,"로그인 되었습니다",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            intent.putExtra("id",id);
             startActivity(intent);
            finish();
        }else if(login.equals("Fail")){
            _id.setText("");
            _pw.setText("");
            Toast.makeText(this,"아이디 또는 비밀번호가 틀렸습니다.",Toast.LENGTH_SHORT).show();
        }else if(login.equals("admin")){
            Toast.makeText(this,"관리자 모드",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this,AdminActivity.class);
            startActivity(intent);
            finish();
        }

    }
    //회원가입 메소드
    public void join(View v){
        join_view = (View)View.inflate(LoginActivity.this,R.layout.dialog_join,null);
        dialog = new AlertDialog.Builder(LoginActivity.this);
        dialog.setView(join_view);
        dialog.setTitle("회원가입");
        dialog.setPositiveButton("가입", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                j_man = (RadioButton)join_view.findViewById(R.id.join_man);
                j_woman = (RadioButton)join_view.findViewById(R.id.join_woman);
                if(j_man.isChecked()) gender = "남자";
                else gender = "여자";
                ed_name = (EditText)join_view.findViewById(R.id.name_join);
                ed_age = (EditText)join_view.findViewById(R.id.age_join);
                ed_phone = (EditText)join_view.findViewById(R.id.phone_join);
                ed_id = (EditText)join_view.findViewById(R.id.id_join);
                ed_pw = (EditText)join_view.findViewById(R.id.pw_join);
                name = ed_name.getText().toString();
                age = ed_age.getText().toString();
                phone = ed_phone.getText().toString();
                id = ed_id.getText().toString();
                pw = ed_pw.getText().toString();

                DB_reservation db_reservation = new DB_reservation(getApplicationContext(),"inpyungschool_reservation.db",null,1 );
                db_reservation.insert("insert into member_list values ('"+id+"',"+pw+",'"+name+"',"+age+",'"+gender+"','"+phone+"');");
                Toast.makeText(LoginActivity.this,"회원가입이 완료되었습니다.",Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setNegativeButton("취소",null);
        dialog.show();

    }


}
