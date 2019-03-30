package com.noah.taxiclient.Public_Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.noah.taxiclient.Activity.Act_Splash;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by JinHa on 2017-09-19.
 */

public class MySQLiteOpenHelper_Stay extends SQLiteOpenHelper {

    public MySQLiteOpenHelper_Stay(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql= "CREATE TABLE Stay (content_id TEXT PRIMARY KEY,type TEXT,classification TEXT,area TEXT,korean TEXT,english TEXT,chinese TEXT,japanese TEXT,french TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Stay";
        db.execSQL(sql);

        onCreate(db); // 테이블을 지웠으므로 다시 테이블을 만들어주는 과정
    }

    public void insert(String type,String classification,String area,String content_id,String korean, String english, String chinese, String japanese,String french) {

        Act_Splash.db = Act_Splash.helper_St.getWritableDatabase(); // db 객체를 얻어온다. 쓰기 가능

        ContentValues values = new ContentValues();

        values.put("type",type);
        values.put("classification",classification);
        values.put("area",area);
        values.put("content_id",content_id);
        values.put("korean", korean);
        values.put("english", english);
        values.put("chinese", chinese);
        values.put("japanese", japanese);
        values.put("french",french);

        Act_Splash.db.execSQL("INSERT OR REPLACE INTO Stay (type,classification,area,content_id,korean,english,chinese,japanese,french) VALUES ('" + type + "','" + classification + "','" +area+ "','" +content_id +"','" +korean + "','" + english + "','" + chinese + "','" + japanese+ "','" + french+ "')");

        //System.out.println("type : "+type+", classification : "+classification+", area : " + area +", content_id : " + content_id + ", korean : " + korean + ", english : " + english + ", chinese : " + chinese+", japanese : "+japanese+", french : "+french);
    }

    public ArrayList<String> select(int language_num) throws JSONException {

        ArrayList<String> list = new  ArrayList<String>();

        Act_Splash.db = Act_Splash.helper_St.getReadableDatabase(); // db객체를 얻어온다. 읽기 전용

        Cursor c = Act_Splash.db.query("Stay",null,null,null,null,null,null,null);

        while (c.moveToNext()) {

            String type=c.getString(c.getColumnIndex("type"));
            String classification=c.getString(c.getColumnIndex("classification"));
            String area=c.getString(c.getColumnIndex("area"));
            String content_id=c.getString(c.getColumnIndex("content_id"));
            String korean = c.getString(c.getColumnIndex("korean"));
            String english = c.getString(c.getColumnIndex("english"));
            String chinese = c.getString(c.getColumnIndex("chinese"));
            String japanese = c.getString(c.getColumnIndex("japanese"));
            String french = c.getString(c.getColumnIndex("french"));

            if(language_num == 0){
                JSONObject jo = new JSONObject(korean);
                String name =jo.getString("subject");
                list.add(name);
            }
            else if(language_num == 1){
                JSONObject jo = new JSONObject(english);
                String name =jo.getString("subject");
                list.add(name);
            }
            else if(language_num == 2){
                JSONObject jo = new JSONObject(chinese);
                String name =jo.getString("subject");
                list.add(name);
            }
            else if(language_num == 3){
                JSONObject jo = new JSONObject(japanese);
                String name =jo.getString("subject");
                list.add(name);
            }
            else if(language_num == 4){
                JSONObject jo = new JSONObject(french);
                String name =jo.getString("subject");
                list.add(name);
            }

            //Act_Splash.db.rawQuery("SELECT * FROM Stay", null);
            //System.out.println("type : "+type+", classification : "+classification+", area : " + area +", content_id : " + content_id + ", korean : " + korean + ", english : " + english + ", chinese : " + chinese+", japanese : "+japanese+", french : "+french);
        }

        c.close();
        return list;
    }
    public String select_area(int language_num, String areas) {

        String a = null;

        Act_Splash.helper_St.getReadableDatabase(); // db객체를 얻어온다. 읽기 전용

        Cursor c = Act_Splash.db.rawQuery("SELECT * FROM Stay WHERE area='"+areas+"';", null);

        if(c.moveToFirst())
        {

            String type=c.getString(c.getColumnIndex("type"));
            String classification=c.getString(c.getColumnIndex("classification"));
            String area=c.getString(c.getColumnIndex("area"));
            String content_id=c.getString(c.getColumnIndex("content_id"));
            String korean = c.getString(c.getColumnIndex("korean"));
            String english = c.getString(c.getColumnIndex("english"));
            String chinese = c.getString(c.getColumnIndex("chinese"));
            String japanese = c.getString(c.getColumnIndex("japanese"));
            String french = c.getString(c.getColumnIndex("french"));

            /*JSONObject obj = new JSONObject();
            try {
                obj.put("type",type);
                obj.put("classification",classification);
                obj.put("area",area);
                obj.put("content_id",content_id);
                obj.put("korean",korean);
                obj.put("english",english);
                obj.put("chinese",chinese);
                obj.put("japanese",japanese);
                obj.put("french",french);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            a = obj.toString();*/

            if(language_num == 0){
                a = korean;
            }
            else if(language_num == 1){
                a = english;
            }
            else if(language_num == 2){
                a = chinese;
            }
            else if(language_num == 3){
                a = japanese;
            }
            else if(language_num == 4){
                a = french;
            }
            else {
                a = korean;
            }

            //System.out.println("type : "+type+", classification : "+classification+", area : " + area +", content_id : " + content_id + ", korean : " + korean + ", english : " + english + ", chinese : " + chinese+", japanese : "+japanese+", french : "+french);
        }
        c.close();

        return a;
    }

    public String select_content_id(int language_num, String id) {

        String i = null;

        Act_Splash.helper_St.getReadableDatabase(); // db객체를 얻어온다. 읽기 전용

        Cursor c = Act_Splash.db.rawQuery("SELECT * FROM Stay WHERE content_id='"+id+"';", null);

        if(c.moveToFirst())
        {

            String type=c.getString(c.getColumnIndex("type"));
            String classification=c.getString(c.getColumnIndex("classification"));
            String area=c.getString(c.getColumnIndex("area"));
            String content_id=c.getString(c.getColumnIndex("content_id"));
            String korean = c.getString(c.getColumnIndex("korean"));
            String english = c.getString(c.getColumnIndex("english"));
            String chinese = c.getString(c.getColumnIndex("chinese"));
            String japanese = c.getString(c.getColumnIndex("japanese"));
            String french = c.getString(c.getColumnIndex("french"));

            /*JSONObject obj = new JSONObject();
            try {
                obj.put("type",type);
                obj.put("classification",classification);
                obj.put("area",area);
                obj.put("content_id",content_id);
                obj.put("korean",korean);
                obj.put("english",english);
                obj.put("chinese",chinese);
                obj.put("japanese",japanese);
                obj.put("french",french);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            i = obj.toString();*/

            if(language_num == 0){
                i = korean;
            }
            else if(language_num == 1){
                i = english;
            }
            else if(language_num == 2){
                i = chinese;
            }
            else if(language_num == 3){
                i = japanese;
            }
            else if(language_num == 4){
                i = french;
            }
            else {
                i = korean;
            }

            //System.out.println("type : "+type+", classification : "+classification+", area : " + area +", content_id : " + content_id + ", korean : " + korean + ", english : " + english + ", chinese : " + chinese+", japanese : "+japanese+", french : "+french);
        }
        c.close();

        return i;
    }
}
