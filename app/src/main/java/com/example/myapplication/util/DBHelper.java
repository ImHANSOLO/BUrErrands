package com.example.myapplication.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    public DBHelper(Context context){
        super(context,"db_thisApp",null,1);
        db = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists user(" +
                "_id integer primary key autoincrement,"+
                "name text,"+
                "password text," +
                "sex text," +
                "email text," +
                "phone text," +
                "address text," +
                "headImageBase64 text" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user");
        onCreate(db);
    }

    public boolean add(String name, String password){
        boolean judge2 = true;
        ArrayList<User> userData = this.getData();
        for (int i = 0; i < userData.size(); i++){
            User user = userData.get(i);
            if (name.equals(user.getName())){
                judge2 = false;
                break;
            }              //遍历user id，当输入的用户名已存在则false
        }
        if (judge2){
            db.execSQL("insert into user(name,password) values(?,?)", new Object[]{name,password});
        }
        return judge2;
    }

    public void delete(String name,String password){
        db.execSQL("delete from user where name = and password =" + name + password);
    }

    public void update(String name,String password){
        db.execSQL("update user set password = ?",new Object[]{password});
    }

    public void updateUsername(String name){
        db.execSQL("update user set name = ?", new Object[]{name});       //创建一个更新用户信息的DB方法
    }

    public void updateSex(String sex){
        db.execSQL("update user set sex = ?", new Object[]{sex});
    }

    public void updateEmail(String email){
        db.execSQL("update user set email = ?", new Object[]{email});
    }

    public void updatePhone(String phone){
        db.execSQL("update user set phone = ?", new Object[]{phone});
    }

    public void updateAddress(String address){
        db.execSQL("update user set address = ?", new Object[]{address});
    }

    public void updateHeadImage(String headImageBase64){
        db.execSQL("update user set headImageBase64 = ?",new Object[]{headImageBase64});
    }

    public ArrayList<User> getData(){
        //从数据库获取数据到list中
        ArrayList<User> userList = new ArrayList<User>();             //队列，以User结构建立队列，也就是建立User队列
        Cursor cursor = db.query("user",null,null,
                                null,
                                null,
                                null,
                                "name desc");
        while (cursor.moveToNext()){                     //因为value must be>0，所以不能使用moveToFirst，否则程序会崩溃：
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex("password"));
            @SuppressLint("Range") String sex = cursor.getString(cursor.getColumnIndex("sex"));
            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("email"));
            @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex("phone"));
            @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex("address"));
            @SuppressLint("Range") String userHead = cursor.getString(cursor.getColumnIndex("headImageBase64"));
            userList.add(new User(name,password,sex,email,phone,address,userHead));  //读出以上数据，全部放入user队列
        }
        cursor.close();
        return userList;
    }
}
