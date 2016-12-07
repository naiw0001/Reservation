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
        db.execSQL("CREATE TABLE reservation_list(_idx INTEGER PRIMARY KEY AUTOINCREMENT, _name TEXT,_age INTEGER, _gender TEXT, _time TEXT, _room TEXT, _people INTEGER ,_limit INTEGER, _nowtime TEXT);");
        //번호,이름,나이,성별,예약시간,방번호,인원수,제한시간,예약 할 떄 시간
        db.execSQL("CREATE TABLE member_list(_id TEXT, _password INTEGER, _name TEXT, _age INTEGER, _gender TEXT, _phone TEXT)");
        //아이디,비번,이름,나이,성별,폰번
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE reservation_list");
        db.execSQL("DROP TABLE member_list");
        onCreate(db);
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
    public String print(){ // 출력
        SQLiteDatabase db = getWritableDatabase();
        String list = "";
        Cursor cursor = db.rawQuery("select * from reservation_list",null);
        while(cursor.moveToNext()){
            list +="번호 : "
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
                    +"\n";
        }
        return list;
    }

    public String printwhere(String name){ // 조건을 이용한 출력
        SQLiteDatabase db = getWritableDatabase();
        String reservation = "";
        Cursor cursor = db.rawQuery("select * from reservation_list where _name='"+name+"'",null);
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
                        +"\n";
        }

        return reservation;
    }
    public String login(String id,int pw){ //아이디 비번 출력
        SQLiteDatabase db = getWritableDatabase();
        String db_id = "";
        int db_pw = 0;
        Cursor cursor = db.rawQuery("select * from member_list where _id = '"+id+"' OR _password = "+pw+" " , null);
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
        Cursor cursor = db.rawQuery("select * from member_list where not _id = 'admin' ",null);
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

    public String member_name(String id){ // 멤버 아이디 출력
        SQLiteDatabase db = getWritableDatabase();
        String name="";
        Cursor cursor = db.rawQuery("select * from member_list where _id = '"+id+"' ",null);
        while(cursor.moveToNext()){
            name +=cursor.getString(2);
        }
        return name;
    }

    public String member_age(String id){ // 멤버 나이 출력
        SQLiteDatabase db = getWritableDatabase();
        String age="";
        Cursor cursor = db.rawQuery("select * from member_list where _id = '"+id+"' ",null);
        while(cursor.moveToNext()){
            age +=cursor.getString(3);
        }
        return age;
    }

    public String member_gender(String id){ // 멤버 성별 출력
        SQLiteDatabase db = getWritableDatabase();
        String gender="";
        Cursor cursor = db.rawQuery("select * from member_list where _id = '"+id+"' ",null);
        while(cursor.moveToNext()){
            gender +=cursor.getString(4);
        }
        return gender;
    }



}
