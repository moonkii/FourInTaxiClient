package com.noah.taxiclient.Public_Data;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.noah.taxiclient.Activity.Act_Splash;
import com.noah.taxiclient.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class SightseeingActivity extends AppCompatActivity {

    // 리스트
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Realm_Data_Model> myDataset;
    ArrayList<Fragment_Detail_Item> list_si = new ArrayList<>();

    TextView btn_all,btn_travel,btn_cultural_heritage,btn_traditional_market,btn_festival,btn_etc;
    int language_num = 0; // 사용자가 선택한 언어를 담고 있는 변수( 0 - korean / 1 - english / 2 - chinese / 3 - japanese / 4 - french )
    String language = "";
    String area = "", area_s = "";
    int area_p = 0;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //====================== 액션바 커스텀 ======================
        try{
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.actionbar_custom);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        //==========================================================
        setContentView(R.layout.activity_sightseeing);

        Realm.init(SightseeingActivity.this);
        realm = Realm.getDefaultInstance();

        Intent getData = getIntent();
        area =getData.getStringExtra("area");
        area_p = getData.getIntExtra("area_p",0);

        btn_all = (TextView) findViewById(R.id.btn_all); // 모두 보기
        btn_travel = (TextView) findViewById(R.id.btn_travel); // 여행지
        btn_cultural_heritage = (TextView) findViewById(R.id.btn_cultural_heritage); // 문화유산
        btn_traditional_market = (TextView) findViewById(R.id.btn_traditional_market); // 전통시장
        btn_festival = (TextView) findViewById(R.id.btn_festival); // 축제
        btn_etc = (TextView) findViewById(R.id.btn_etc); // 기타

        SharedPreferences pref = getSharedPreferences("lang",MODE_PRIVATE);
        language_num = pref.getInt("country",0);

        // 언어 설정
        if(language_num == 0){
            btn_all.setText(R.string.korean_all);
            btn_travel.setText(R.string.korean_travel);
            btn_cultural_heritage.setText(R.string.korean_cultural_heritage);
            btn_traditional_market.setText(R.string.korean_traditional_market);
            btn_festival.setText(R.string.korean_festival);
            btn_etc.setText(R.string.korean_etc);
            language = "korean";
        }
        else if(language_num == 1){ // 영어로 변경 필요
            btn_all.setText(R.string.english_all);
            btn_travel.setText(R.string.english_travel);
            btn_cultural_heritage.setText(R.string.english_cultural_heritage);
            btn_traditional_market.setText(R.string.english_traditional_market);
            btn_festival.setText(R.string.english_festival);
            btn_etc.setText(R.string.english_etc);
            language = "english";
        }
        else if(language_num == 2){ // 중국어로 변경 필요
            btn_all.setText(R.string.chinese_all);
            btn_travel.setText(R.string.chinese_travel);
            btn_cultural_heritage.setText(R.string.chinese_cultural_heritage);
            btn_traditional_market.setText(R.string.chinese_traditional_market);
            btn_festival.setText(R.string.chinese_festival);
            btn_etc.setText(R.string.chinese_etc);
            language = "chinese";
        }
        else if(language_num == 3){ // 일본어로 변경 필요
            btn_all.setText(R.string.japanese_all);
            btn_travel.setText(R.string.japanese_travel);
            btn_cultural_heritage.setText(R.string.japanese_cultural_heritage);
            btn_traditional_market.setText(R.string.japanese_traditional_market);
            btn_festival.setText(R.string.japanese_festival);
            btn_etc.setText(R.string.japanese_etc);
            language = "japanese";
        }
        else if(language_num == 4){ // 프랑스어로 변경 필요
            btn_all.setText(R.string.french_all);
            btn_travel.setText(R.string.french_travel);
            btn_cultural_heritage.setText(R.string.french_cultural_heritage);
            btn_traditional_market.setText(R.string.french_traditional_market);
            btn_festival.setText(R.string.french_festival);
            btn_etc.setText(R.string.french_etc);
            language = "french";
        }
        else { // 예외 발생 시 한국어로 설정
            btn_all.setText(R.string.korean_all);
            btn_travel.setText(R.string.korean_travel);
            btn_cultural_heritage.setText(R.string.korean_cultural_heritage);
            btn_traditional_market.setText(R.string.korean_traditional_market);
            btn_festival.setText(R.string.korean_festival);
            btn_etc.setText(R.string.korean_etc);
            language = "korean";
        }

        if(area_p == 1){
            area_s = "강원도";
        }
        else if(area_p == 2){
            area_s = "춘천시";
        }
        else if(area_p == 3){
            area_s = "강릉시";
        }
        else if(area_p == 4){
            area_s = "원주시";
        }
        else if(area_p == 5){
            area_s = "동해시";
        }
        else if(area_p == 6){
            area_s = "태백시";
        }
        else if(area_p == 7){
            area_s = "속초시";
        }
        else if(area_p == 8){
            area_s = "삼척시";
        }
        else if(area_p == 9){
            area_s = "홍천군";
        }
        else if(area_p == 10){
            area_s = "횡성군";
        }
        else if(area_p == 11){
            area_s = "영월군";
        }
        else if(area_p == 12){
            area_s = "평창군";
        }
        else if(area_p == 13){
            area_s = "정선군";
        }
        else if(area_p == 14){
            area_s = "철원군";
        }
        else if(area_p == 15){
            area_s = "화천군";
        }
        else  if(area_p == 16){
            area_s = "양구군";
        }
        else if(area_p == 17){
            area_s = "인제군";
        }
        else if(area_p == 18){
            area_s = "고성군";
        }
        else if(area_p == 19){
            area_s = "양양군";
        }

        btn_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDataset.clear();
                //myDataset = getData_sightseeing(""); // 데이터 읽어오기
                RealmResults<Realm_Data_Model> results;

                if(area_s.equals("강원도")){

                    results = realm.where(Realm_Data_Model.class)
                            .distinct(language) //언어별 컬럼
                            .where()
                            .equalTo("type", "관광") // 관광
                            .findAll();

                    int size=results.size();
                    Log.e("Realm_results_size", String.valueOf(size));
                }
                else {
                    results = realm.where(Realm_Data_Model.class)
                            .equalTo("type", "관광") // 관광
                            .findAll()
                            .where()
                            .distinct(language)
                            .where()
                            .equalTo("area", area_s) // 지역별
                            .findAll();

                    int size=results.size();
                    Log.e("Realm_results_size", String.valueOf(size));
                }

                ArrayList<Realm_Data_Model> list = new ArrayList<>();
                for(Realm_Data_Model d : results){
                    list.add(d);
                }
                myDataset = list;
                Log.e("Realm", String.valueOf(list));
                mRecyclerView.setAdapter(new Fragment_Detail_Adapter(SightseeingActivity.this, myDataset,language_num,area_p,area));
            }
        });

        btn_travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDataset.clear();
                //myDataset = getData_sightseeing(); // 데이터 읽어오기
                RealmResults<Realm_Data_Model> results;

                if(area_s.equals("강원도")){

                    results = realm.where(Realm_Data_Model.class)
                            .distinct(language) //언어별 컬럼
                            .where()
                            .equalTo("type", "관광") // 관광
                            .findAll()
                            .where()
                            .not()
                            .beginGroup()
                                .equalTo("classification", "전통시장")
                                .or()
                                .equalTo("classification", "문화재")
                                .or()
                                .equalTo("classification", "봄")
                                .or()
                                .equalTo("classification", "여름")
                                .or()
                                .equalTo("classification", "가을")
                                .or()
                                .equalTo("classification", "겨울")
                                .or()
                                .equalTo("classification", "쇼핑")
                            .endGroup()
                            .findAll();

                    int size=results.size();
                    Log.e("Realm_results_size", String.valueOf(size));
                }
                else {
                    results = realm.where(Realm_Data_Model.class)
                            .equalTo("type", "관광") // 관광
                            .findAll()
                            .where()
                            .distinct(language)
                            .where()
                            .equalTo("area", area_s) // 지역별
                            .findAll()
                            .where()
                            .not()
                            .beginGroup()
                                .equalTo("classification", "전통시장")
                                .or()
                                .equalTo("classification", "문화재")
                                .or()
                                .equalTo("classification", "봄")
                                .or()
                                .equalTo("classification", "여름")
                                .or()
                                .equalTo("classification", "가을")
                                .or()
                                .equalTo("classification", "겨울")
                                .or()
                                .equalTo("classification", "쇼핑")
                            .endGroup()
                            .findAll();

                    int size=results.size();
                    Log.e("Realm_results_size", String.valueOf(size));
                }

                ArrayList<Realm_Data_Model> list = new ArrayList<>();
                for(Realm_Data_Model d : results){
                    list.add(d);
                }
                myDataset = list;
                mRecyclerView.setAdapter(new Fragment_Detail_Adapter(SightseeingActivity.this, myDataset,language_num,area_p,area));
            }
        });

        btn_cultural_heritage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDataset.clear();
                //myDataset = getData_sightseeing(); // 데이터 읽어오기
                RealmResults<Realm_Data_Model> results;

                if(area_s.equals("강원도")){

                    results = realm.where(Realm_Data_Model.class)
                            .distinct(language) //언어별 컬럼
                            .where()
                            .equalTo("type", "관광") // 관광
                            .findAll()
                            .where()
                            .equalTo("classification", "문화재") // 문화재
                            .findAll();

                    int size=results.size();
                    Log.e("Realm_results_size", String.valueOf(size));
                }
                else {
                    results = realm.where(Realm_Data_Model.class)
                            .equalTo("type", "관광") // 관광
                            .findAll()
                            .where()
                            .distinct(language)
                            .where()
                            .equalTo("area", area_s) // 지역별
                            .findAll()
                            .where()
                            .equalTo("classification", "문화재") // 문화재
                            .findAll();

                    int size=results.size();
                    Log.e("Realm_results_size", String.valueOf(size));
                }

                ArrayList<Realm_Data_Model> list = new ArrayList<>();
                for(Realm_Data_Model d : results){
                    list.add(d);
                }
                myDataset = list;
                mRecyclerView.setAdapter(new Fragment_Detail_Adapter(SightseeingActivity.this, myDataset,language_num,area_p,area));
            }
        });

        btn_traditional_market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDataset.clear();
                //myDataset = getData_sightseeing(); // 데이터 읽어오기
                RealmResults<Realm_Data_Model> results;

                if(area_s.equals("강원도")){

                    results = realm.where(Realm_Data_Model.class)
                            .distinct(language) //언어별 컬럼
                            .where()
                            .equalTo("type", "관광") // 관광
                            .findAll()
                            .where()
                            .equalTo("classification", "전통시장") // 전통시장
                            .findAll();

                    int size=results.size();
                    Log.e("Realm_results_size", String.valueOf(size));
                }
                else {
                    results = realm.where(Realm_Data_Model.class)
                            .equalTo("type", "관광") // 관광
                            .findAll()
                            .where()
                            .distinct(language)
                            .where()
                            .equalTo("area", area_s) // 지역별
                            .findAll()
                            .where()
                            .equalTo("classification", "전통시장") // 전통시장
                            .findAll();

                    int size=results.size();
                    Log.e("Realm_results_size", String.valueOf(size));
                }

                ArrayList<Realm_Data_Model> list = new ArrayList<>();
                for(Realm_Data_Model d : results){
                    list.add(d);
                }
                myDataset = list;
                mRecyclerView.setAdapter(new Fragment_Detail_Adapter(SightseeingActivity.this, myDataset,language_num,area_p,area));
            }
        });

        btn_festival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDataset.clear();
                //myDataset = getData_sightseeing(); // 데이터 읽어오기
                RealmResults<Realm_Data_Model> results;

                if(area_s.equals("강원도")){

                    results = realm.where(Realm_Data_Model.class)
                            .distinct(language) //언어별 컬럼
                            .where()
                            .equalTo("type", "관광") // 관광
                            .findAll()
                            .where()
                            .beginGroup()
                                .equalTo("classification", "봄")
                                .or()
                                .equalTo("classification", "여름")
                                .or()
                                .equalTo("classification", "가을")
                                .or()
                                .equalTo("classification", "겨울")
                            .endGroup()
                            .findAll();

                    int size=results.size();
                    Log.e("Realm_results_size", String.valueOf(size));
                }
                else {
                    results = realm.where(Realm_Data_Model.class)
                            .equalTo("type", "관광") // 관광
                            .findAll()
                            .where()
                            .distinct(language)
                            .where()
                            .equalTo("area", area_s) // 지역별
                            .findAll()
                            .where()
                            .beginGroup()
                                .equalTo("classification", "봄")
                                .or()
                                .equalTo("classification", "여름")
                                .or()
                                .equalTo("classification", "가을")
                                .or()
                                .equalTo("classification", "겨울")
                            .endGroup()
                            .findAll();

                    int size=results.size();
                    Log.e("Realm_results_size", String.valueOf(size));
                }

                ArrayList<Realm_Data_Model> list = new ArrayList<>();
                for(Realm_Data_Model d : results){
                    list.add(d);
                }
                myDataset = list;
                mRecyclerView.setAdapter(new Fragment_Detail_Adapter(SightseeingActivity.this, myDataset,language_num,area_p,area));
            }
        });

        btn_etc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDataset.clear();
                //myDataset = getData_sightseeing(); // 데이터 읽어오기
                RealmResults<Realm_Data_Model> results;

                if(area_s.equals("강원도")){

                    results = realm.where(Realm_Data_Model.class)
                            .distinct(language) //언어별 컬럼
                            .where()
                            .equalTo("type", "관광") // 관광
                            .findAll()
                            .where()
                            .equalTo("classification", "쇼핑") // 쇼핑
                            .findAll();

                    int size=results.size();
                    Log.e("Realm_results_size", String.valueOf(size));
                }
                else {
                    results = realm.where(Realm_Data_Model.class)
                            .equalTo("type", "관광") // 관광
                            .findAll()
                            .where()
                            .distinct(language)
                            .where()
                            .equalTo("area", area_s) // 지역별
                            .findAll()
                            .where()
                            .equalTo("classification", "쇼핑") // 쇼핑
                            .findAll();

                    int size=results.size();
                    Log.e("Realm_results_size", String.valueOf(size));
                }

                ArrayList<Realm_Data_Model> list = new ArrayList<>();
                for(Realm_Data_Model d : results){
                    list.add(d);
                }
                myDataset = list;
                mRecyclerView.setAdapter(new Fragment_Detail_Adapter(SightseeingActivity.this, myDataset,language_num,area_p,area));
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        myDataset = new ArrayList<>();
        mAdapter = new Fragment_Detail_Adapter(this, myDataset,language_num,area_p,area);
        //mAdapter.setRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

        Log.e("Realm_area",area_s);
        Log.e("Realm_lang", language);
        //myDataset = getData_sightseeing(area_s);
        RealmResults<Realm_Data_Model> results;

        if(area_s.equals("강원도")){

            results = realm.where(Realm_Data_Model.class)
                    .distinct(language) //언어별 컬럼
                    .where()
                    .equalTo("type", "관광") // 관광
                    .findAll();

            /*results = realm.where(Realm_Data_Model.class)
                    .equalTo("type", "관광") // 관광
                    .findAll();*/

            int size=results.size();
            Log.e("Realm_results_size", String.valueOf(size));
        }
        else {
            results = realm.where(Realm_Data_Model.class)
                        .equalTo("type", "관광") // 관광
                        .findAll()
                        .where()
                        .distinct(language)
                        .where()
                        .equalTo("area", area_s) // 지역별
                        .findAll();

            int size=results.size();
            Log.e("Realm_results_size", String.valueOf(size));
        }

        ArrayList<Realm_Data_Model> list = new ArrayList<>();
        for(Realm_Data_Model d : results){
            list.add(d);
        }
        myDataset = list;
        Log.e("Realm", String.valueOf(list));
        mRecyclerView.setAdapter(new Fragment_Detail_Adapter(this, myDataset,language_num,area_p,area));
    }

    public ArrayList<Fragment_Detail_Item> getData_sightseeing(String AREA) { // 관광

        Cursor c;

        String language = ""; // 사용자가 선택한 언어로 데이터 값 저장
        String content_id,name,type,area,address,korean,english,chinese,japanese,french;
        // 이름, 지역, 주소
        list_si.clear();

        Act_Splash.db = Act_Splash.helper_Si.getReadableDatabase();
        if(AREA.equals("")){
            c = Act_Splash.db.rawQuery("SELECT content_id, type, area, korean, english, chinese, japanese, french FROM Sightseeing;",null);
        }
        else {
            c = Act_Splash.db.rawQuery("SELECT content_id, type, area, korean, english, chinese, japanese, french FROM Sightseeing WHERE area='" + AREA + "';", null);
        }

        if(c != null) {
            while (c.moveToNext()) {
                content_id = c.getString(0);
                type = c.getString(1);
                area = c.getString(2);
                korean = c.getString(3);
                english = c.getString(4);
                chinese = c.getString(5);
                japanese = c.getString(6);
                french = c.getString(7);

                // 프리퍼런스에 저장된 언어 선택을 기준으로 변경 필요

                if(language_num == 0){
                    language = korean;
                    type = getString(R.string.korean_sightseeing);
                }
                else if(language_num == 1){
                    language = english;
                    type = getString(R.string.english_sightseeing);
                }
                else if(language_num == 2){
                    language = chinese;
                    type = getString(R.string.chinese_sightseeing);
                }
                else if(language_num == 3){
                    language = japanese;
                    type = getString(R.string.japanese_sightseeing);
                }
                else if(language_num == 4){
                    language = french;
                    type = getString(R.string.french_sightseeing);
                }
                else{
                    Log.e("Language","언어 선택 오류 발생");
                }

                try {
                    JSONObject jo = new JSONObject(language);
                    address = jo.getString("address");
                    name = jo.getString("subject");

                    list_si.add(new Fragment_Detail_Item(content_id,name,type,area,address));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            c.close();
        }
        return list_si;
    }
}
