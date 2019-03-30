package com.noah.taxiclient.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.noah.taxiclient.Dialog.D_Language;
import com.noah.taxiclient.Public_Data.MySQLiteOpenHelper_Food;
import com.noah.taxiclient.Public_Data.MySQLiteOpenHelper_Sightseeing;
import com.noah.taxiclient.Public_Data.MySQLiteOpenHelper_Stay;
import com.noah.taxiclient.Public_Data.Realm_Data_Model;
import com.noah.taxiclient.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Noah on 2017-09-01.
 */

public class Act_Splash extends Activity {

    Realm realm;

    //퍼미션 변수
    final int permissionRequestCodeForMap = 1000;

    public static SQLiteDatabase db;
    public static MySQLiteOpenHelper_Stay helper_St;
    public static MySQLiteOpenHelper_Sightseeing helper_Si;
    public static MySQLiteOpenHelper_Food helper_F;

    selectToDatabase_f select_f;
    selectToDatabase_si select_si;
    selectToDatabase_st select_st;

    String link_f;
    String link_si;
    String link_st;

    ProgressDialog dialog;

    SharedPreferences pref;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_splash);

        Stetho.initializeWithDefaults(this);
        Realm.init(this);
        realm = Realm.getDefaultInstance();


        pref = getSharedPreferences("set", MODE_PRIVATE);
        editor = pref.edit();


        helper_Si = new MySQLiteOpenHelper_Sightseeing(Act_Splash.this, "Sightseeing.db", null, 1);
        helper_F = new MySQLiteOpenHelper_Food(Act_Splash.this, "Food.db", null, 1);
        helper_St = new MySQLiteOpenHelper_Stay(Act_Splash.this, // 현재 화면의 context
                "Stay.db", // 파일명
                null, // 커서 팩토리
                1); // 버전 번호


        checkPermissions();
    }

    public void setLanguage() {

        D_Language langDialog = new D_Language(Act_Splash.this, new D_Language.ClickOkButtonListener() {
            @Override
            public void setLanguage() {


                dialog = new ProgressDialog(Act_Splash.this);
                dialog.setMessage("Language is Setting ...");
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);

                dialog.show();

                // 서버로부터 데이터 받기
                link_f = "http://teampinky.vps.phps.kr/public_data_select.php"; // 음식
                link_si = "http://teampinky.vps.phps.kr/public_data_sightseeing_select.php"; // 관광
                link_st = "http://teampinky.vps.phps.kr/public_data_stay_select.php"; // 숙박

                select_f = new selectToDatabase_f();
                select_f.execute(link_f);


            }
        });

        langDialog.setCancelable(false);
        langDialog.setCanceledOnTouchOutside(false);
        langDialog.show();

    }


    /*#####################################
     *      권한 체크 메소드
     * #####################################*/
    private void checkPermissions() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //API level 구분

            //마시멜로 이상인 경우

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //위치정보 권한
                ActivityCompat.requestPermissions(Act_Splash.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, permissionRequestCodeForMap);

            } else {
                //권한 모두 획득시


                if (!pref.getBoolean("init", false)) {
                    setLanguage();
                } else {

                    splashEnd();

                }


            }

        } else {
            //마시멜로 미만 버전 ( 권한 요청 따로 구분 x)

        }

    }

    //권한 요청 결과 받는 메소드
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case permissionRequestCodeForMap:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //폰 상태권한 허용
                    checkPermissions();

                } else {
                    //폰상태 권한 거절시
                    finish();
                    Toast.makeText(this, "권한을 허용하셔야 서비스 이용이 가능합니다.", Toast.LENGTH_SHORT).show();
                }

                break;

        }

    }


    private void splashEnd() {
        RealmResults<Realm_Data_Model> results = realm.where(Realm_Data_Model.class).findAll();
        Log.i("Realm", String.valueOf(results.size()));

        Handler handler = new Handler();

        int interval = 3000; //스플레쉬 화면 시간

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                editor.putBoolean("init", true);
                editor.commit();

                Intent intent = new Intent(Act_Splash.this, Act_Login.class);
                //인텐트로 이동
                startActivity(intent);
                Act_Splash.this.finish();
            }

        }, interval);
    }

    // 서버로부터 공공데이터 읽어오기
    private class selectToDatabase_f extends AsyncTask<String, Integer, String> { // 음식

        @Override
        protected String doInBackground(String... urls) {

            StringBuilder jsonHtml = new StringBuilder();
            try {
                // 연결 url 설정
                URL url = new URL(urls[0]);
                // 커넥션 객체 생성
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                // 연결되었으면.
                if (conn != null) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);
                    // 연결되었음 코드가 리턴되면.
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        for (; ; ) {
                            // 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
                            String line = br.readLine();
                            if (line == null) break;
                            // 저장된 텍스트 라인을 jsonHtml에 붙여넣음
                            jsonHtml.append(line).append("\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return jsonHtml.toString();
        }


        protected void onPostExecute(final String str) {


            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    JSONArray ja = null;
                    try {
                        ja = new JSONArray(str);


                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = null;

                            jo = ja.getJSONObject(i);
                            //Log.i("Array", String.valueOf(jo));
                            final String type = jo.getString("type");
                            final String classification = jo.getString("classification");
                            final String area = jo.getString("area");
                            final String content_id = jo.getString("content_id");
                            final String korean = jo.getString("korean");
                            final String english = jo.getString("english");
                            final String chinese = jo.getString("chinese");
                            final String japanese = jo.getString("japanese");
                            final String french = jo.getString("french");

                            if (json_check(korean, english, chinese, japanese, french)) {
                                Realm_Data_Model data = realm.createObject(Realm_Data_Model.class, content_id);
                                data.setType(type);
                                data.setClassification(classification);
                                data.setArea(area);
                                data.setKorean(korean);
                                data.setEnglish(english);
                                data.setChinese(chinese);
                                data.setJapanese(japanese);
                                data.setFrench(french);
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    select_si = new selectToDatabase_si();
                    select_si.execute(link_si);

                }
            });


        }

    }


    private class selectToDatabase_si extends AsyncTask<String, Integer, String> { // 관광

        @Override
        protected String doInBackground(String... urls) {

            StringBuilder jsonHtml = new StringBuilder();
            try {
                // 연결 url 설정
                URL url = new URL(urls[0]);
                // 커넥션 객체 생성
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                // 연결되었으면.
                if (conn != null) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);
                    // 연결되었음 코드가 리턴되면.
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        for (; ; ) {
                            // 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
                            String line = br.readLine();
                            if (line == null) break;
                            // 저장된 텍스트 라인을 jsonHtml에 붙여넣음
                            jsonHtml.append(line).append("\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return jsonHtml.toString();
        }


        protected void onPostExecute(final String str) {



            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {



                    try {

                        JSONArray ja = new JSONArray(str);

                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            //Log.i("Array", String.valueOf(jo));
                            final String type = jo.getString("type");
                            final String classification = jo.getString("classification");
                            final String area = jo.getString("area");
                            final String content_id = jo.getString("content_id");
                            final String korean = jo.getString("korean");
                            final String english = jo.getString("english");
                            final String chinese = jo.getString("chinese");
                            final String japanese = jo.getString("japanese");
                            final String french = jo.getString("french");

                            if (json_check(korean, english, chinese, japanese, french)) {
                                Realm_Data_Model data = realm.createObject(Realm_Data_Model.class, content_id);
                                data.setType(type);
                                data.setClassification(classification);
                                data.setArea(area);
                                data.setKorean(korean);
                                data.setEnglish(english);
                                data.setChinese(chinese);
                                data.setJapanese(japanese);
                                data.setFrench(french);
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    select_st = new selectToDatabase_st();
                    select_st.execute(link_st);
                }
            });



        }
    }

    private class selectToDatabase_st extends AsyncTask<String, Integer, String> { // 숙박

        @Override
        protected String doInBackground(String... urls) {

            StringBuilder jsonHtml = new StringBuilder();
            try {
                // 연결 url 설정
                URL url = new URL(urls[0]);
                // 커넥션 객체 생성
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                // 연결되었으면.
                if (conn != null) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);
                    // 연결되었음 코드가 리턴되면.
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        for (; ; ) {
                            // 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
                            String line = br.readLine();
                            if (line == null) break;
                            // 저장된 텍스트 라인을 jsonHtml에 붙여넣음
                            jsonHtml.append(line).append("\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return jsonHtml.toString();
        }


        protected void onPostExecute(final String str) {



            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {


                    try {

                        JSONArray ja = new JSONArray(str);

                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            //Log.i("Array", String.valueOf(jo));
                            final String type = jo.getString("type");
                            final String classification = jo.getString("classification");
                            final String area = jo.getString("area");
                            final String content_id = jo.getString("content_id");
                            final String korean = jo.getString("korean");
                            final String english = jo.getString("english");
                            final String chinese = jo.getString("chinese");
                            final String japanese = jo.getString("japanese");
                            final String french = jo.getString("french");

                            if(json_check(korean,english,chinese,japanese,french)) {
                                Realm_Data_Model data = realm.createObject(Realm_Data_Model.class, content_id);
                                data.setType(type);
                                data.setClassification(classification);
                                data.setArea(area);
                                data.setKorean(korean);
                                data.setEnglish(english);
                                data.setChinese(chinese);
                                data.setJapanese(japanese);
                                data.setFrench(french);
                            }

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    editor.putBoolean("init",true);
                    dialog.dismiss();
                    dialog = null;
                    splashEnd(); //액티비티 이동
                }
            });



        }

    }

    public boolean json_check(String k, String e, String c, String j, String f) {
        boolean check = true;

        JSONObject jk = null;
        try {
            jk = new JSONObject(k);
            String address_k = jk.getString("address");
            String name_k = jk.getString("subject");

        } catch (JSONException e1) {
            check = false;
        }

        JSONObject je = null;
        try {
            je = new JSONObject(e);
            String address_e = je.getString("address");
            String name_e = je.getString("subject");

        } catch (JSONException e1) {
            check = false;
        }

        JSONObject jc = null;
        try {
            jc = new JSONObject(c);
            String address_c = jc.getString("address");
            String name_c = jc.getString("subject");

        } catch (JSONException e1) {
            check = false;
        }

        JSONObject jj = null;
        try {
            jj = new JSONObject(j);
            String address_j = jj.getString("address");
            String name_j = jj.getString("subject");

        } catch (JSONException e1) {
            check = false;
        }

        JSONObject jf = null;
        try {
            jf = new JSONObject(f);
            String address_f = jf.getString("address");
            String name_f = jf.getString("subject");

        } catch (JSONException e1) {
            check = false;
        }

        return check;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}//Act_Splash class end
