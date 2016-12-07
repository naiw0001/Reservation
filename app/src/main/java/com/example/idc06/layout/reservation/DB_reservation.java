package com.example.idc06.layout.reservation;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by idc06 on 2016-11-30.
 */

public class DB_reservation extends SQLiteOpenHelper {

    public DB_reservation(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { // 처음 table 생성
        db.execSQL("CREATE TABLE reservation (_idx INTEGER PRIMARY KEY AUTOINCREMENT, _time TEXT, _room TEXT, _poeple INTEGER, _limit INTEGER, _nowtime TEXT, _id TEXT);");
        db.execSQL("CREATE TABLE member (_id TEXT PRIMARY KEY, _password INTEGER, _name TEXT, _age INTEGER, _gender TEXT, _phone TEXT);");
        db.execSQL("CREATE TABLE list (_idx INTEGER PRIMARY KEY AUTOINCREMENT, _time TEXT, _room TEXT, _poeple INTEGER, _limit INTEGER, _nowtime TEXT, _id TEXT ,_password INTEGER, _name TEXT,_age INTEGER, _gender TEXT, _phone TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE reservation");
        db.execSQL("DROP TABLE member");
        onCreate(db);
    }

    public String select_join(){ // 관리자
        SQLiteDatabase db = getWritableDatabase();
        String list ="";
        Cursor cursor = db.rawQuery("select * from reservation INNER JOIN member ON reservation._id=member._id",null);

       while(cursor.moveToNext()){
           list += "번호 : "
                   +cursor.getInt(0)
                   +", 시간 : "
                   +cursor.getString(1)
                   +", 방번호 : "
                   +cursor.getString(2)
                   +", 사람 수 : "
                   +cursor.getInt(3)
                   +", 제한 시간 : "
                   +cursor.getInt(4)
                   +", 예약시 시간 : "
                   +cursor.getString(5)
                   +"\n 아이디 : "
                   +cursor.getString(6)
                   +", 비밀번호 : "
                   +cursor.getInt(8)
                   +", 이름 : "
                   +cursor.getString(9)
                   +", 나이 : "
                   +cursor.getInt(10)
                   +"살 , 성별 : "
                   +cursor.getString(11)
                   +", 전화번호"
                   +cursor.getString(12)
                   +"\n\n";

           db.execSQL("insert into list values ("+cursor.getString(0)+",'"+cursor.getString(1)+"','"+cursor.getString(2)+"',"+cursor.getInt(3)+","+cursor.getInt(4)+",'"+cursor.getString(5)+"','"+cursor.getString(6)+"',"+cursor.getInt(8)+",'"+cursor.getString(9)+"',"+cursor.getInt(10)+",'"+cursor.getString(11)+"','"+cursor.getString(12)+"');");
       }
        Log.d("asdasdasd",list);
        return list;
    }
    public String reservation_list(){
        SQLiteDatabase db = getWritableDatabase();
        String list ="";
        Cursor cursor = db.rawQuery("select * from reservation INNER JOIN member ON reservation._id=member._id",null);

        while(cursor.moveToNext()) {
            list += "번호 : "
                    + cursor.getInt(0)
                    + ", 시간 : "
                    + cursor.getString(1)
                    + ", 방번호 : "
                    + cursor.getString(2)
                    + ", 사람 수 : "
                    + cursor.getInt(3)
                    + ", 제한 시간 : "
                    + cursor.getInt(4)
                    + ", 예약시 시간 : "
                    + cursor.getString(5)
                    + "\n 아이디 : "
                    + cursor.getString(6)
                    + ", 비밀번호 : "
                    + cursor.getInt(8)
                    + ", 이름 : "
                    + cursor.getString(9)
                    + ", 나이 : "
                    + cursor.getInt(10)
                    + "살 , 성별 : "
                    + cursor.getString(11)
                    + ", 전화번호"
                    + cursor.getString(12)
                    + "\n\n";
        }
        return list;
    }


    public void insert(String query){ // 삽입
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    public void delete(String query){ // 삭제
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    public String printname(String id){
        SQLiteDatabase db = getWritableDatabase();
        String name="";
        Cursor cursor = db.rawQuery("select * from member where _id = '"+id+"'",null);
        while(cursor.moveToNext()) {
            name += cursor.getString(2);
        }
        return name;
    }

    public String printwhere(String name){ // 조건을 이용한 출력
        SQLiteDatabase db = getWritableDatabase();
        String reservation = "";
        Cursor cursor = db.rawQuery("select * from list where _name = '"+name+"' ",null);
            while(cursor.moveToNext()){
                reservation += "번호 : "
                        +cursor.getInt(0)
                        +", 이름 : "
                        +cursor.getString(8)
                        +"님, 시간 : "
                        +cursor.getString(1)
                        +"\n방번호 : "
                        +cursor.getString(2)
                        +", 사람 수 : "
                        +cursor.getInt(3)
                        +", 제한 시간 : "
                        +cursor.getInt(4)
                        +"시간\n예약시 시간 : "
                        +cursor.getString(5)
                        +"\n";

            }

        return reservation;
    }
    public String login(String id,int pw){ //아이디 비번 출력
        SQLiteDatabase db = getWritableDatabase();
        String db_id = "";
        int db_pw = 0;
        Cursor cursor = db.rawQuery("select * from member where _id = '"+id+"' OR _password = "+pw+" " , null);
        while(cursor.moveToNext()){
            db_id = cursor.getString(0);
            db_pw = cursor.getInt(1);
        }
        Log.d("id",db_id);
        Log.d("pw",String.valueOf(db_pw));

        if(db_id.equals(id) && db_pw == pw){
            if(db_id.equals("admin")){
                return "admin";
            }
            return "Ok";
        } else return "Fail";


    }

    public String adminPrint(){ // 관리자 출력 내용
        SQLiteDatabase db = getWritableDatabase();
        String reservation = "";
        Cursor cursor = db.rawQuery("select * from reservation_list",null);
        while(cursor.moveToNext()){
            reservation +="번호 : "
                    + cursor.getInt(0)
                    +", 이름 : "
                    + cursor.getString(1)
                    + ", 나이 : "
                    +cursor.getInt(2)
                    + ", 성별 : "
                    +cursor.getString(3)
                    +"\n예약시간 : "
                    +cursor.getString(4)
                    +"\n방번호 : "
                    +cursor.getString(5)
                    +"사용 인원 : "
                    +cursor.getInt(6)
                    +"명"
                    + "\n사용 시간 : "
                    +cursor.getString(7)
                    +"\n예약시 시간 : "
                    +cursor.getString(8)
                    +"\n"
                    +"\n";
        }
        return reservation;
    }

    public String member_print(){ // 멤버 출력
        SQLiteDatabase db = getWritableDatabase();
        String list = "";
        Cursor cursor = db.rawQuery("select * from member where not _id = 'admin' ",null);
        while(cursor.moveToNext()){
            list += "id : "
                    +cursor.getString(0)
                    +", password : "
                    +cursor.getInt(1)
                    +", 이름 : "
                    +cursor.getString(2)
                    +", 나이 : "
                    +cursor.getInt(3)
                    +", 성별 : "
                    +cursor.getString(4)
                    +", 연락처 : "
                    +cursor.getString(5)
                    +"\n"
                    +"\n";

        }
        return list;
    }


}
