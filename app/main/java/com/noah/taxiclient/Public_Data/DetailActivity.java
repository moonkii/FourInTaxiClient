package com.noah.taxiclient.Public_Data;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.noah.taxiclient.Activity.Act_main;
import com.noah.taxiclient.Activity.Act_set_destination;
import com.noah.taxiclient.Data.MyCourseData;
import com.noah.taxiclient.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback{

    LinearLayout li_address, li_content,li_contact, li_homepage, li_content_s, li_differ_s, li_guide, li_charges, li_noofrooms, li_time, li_holiday, li_parking, li_parking_fee, li_attraction, li_btn;
    TextView tv_address,content,tv_contact,tv_homepage,tv_content_s,tv_differ_s,tv_guide,tv_charges,tv_noofrooms,tv_time, tv_holiday, tv_parking, tv_parking_fee, tv_attraction;
    TextView la_address,la_content,la_contact, la_homepage, la_content_s, la_differ_s, la_guide, la_charges, la_noofrooms, la_time, la_holiday, la_parking, la_parking_fee, la_attraction;
    // 도로명 주소, 핵심내용, 전화번호, 홈페이지, 소개, 차별적특징, 관광가이드, 시설사용요금, 전체객실수, 이용시간, 휴무일, 주차시설, 주차요금, 주변명소
    Button my_course,set_up;
    String data="";
    int language_num = 0; //기본 한국어로 설정
    // ( 0 - korean / 1 - english / 2 - chinese / 3 - japanese / 4 - french )
    String language, name,address, lat, lng, area, content_id, summary, contact, homepage, content_s, guide, charges, noofrooms, parking, parking_fee, attraction, time, holiday, differ_s, type,korea;
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
        setContentView(R.layout.activity_detail);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        li_address = (LinearLayout) findViewById(R.id.li_address);
        li_content = (LinearLayout) findViewById(R.id.li_content);
        li_contact = (LinearLayout) findViewById(R.id.li_contact);
        li_homepage = (LinearLayout) findViewById(R.id.li_homepage);
        li_content_s = (LinearLayout) findViewById(R.id.li_intro);
        li_differ_s = (LinearLayout) findViewById(R.id.li_characteristics);
        li_guide = (LinearLayout) findViewById(R.id.li_guide);
        li_charges = (LinearLayout) findViewById(R.id.li_charges);
        li_noofrooms = (LinearLayout) findViewById(R.id.li_noofrooms);
        li_time = (LinearLayout) findViewById(R.id.li_time);
        li_holiday = (LinearLayout) findViewById(R.id.li_holiday);
        li_parking = (LinearLayout) findViewById(R.id.li_parking);
        li_parking_fee = (LinearLayout) findViewById(R.id.li_fee);
        li_attraction = (LinearLayout) findViewById(R.id.li_attraction);
        li_btn = (LinearLayout) findViewById(R.id.li_btn);

        tv_address = (TextView) findViewById(R.id.tv_address); // 도로명 주소
        tv_contact = (TextView) findViewById(R.id.tv_phone_number); // 전화번호
        tv_homepage = (TextView) findViewById(R.id.tv_homepage); // 홈페이지
        tv_content_s = (TextView) findViewById(R.id.tv_intro); // 소개
        tv_differ_s = (TextView) findViewById(R.id.tv_characteristics); // 차별적특징
        tv_guide = (TextView) findViewById(R.id.tv_guide); // 관광가이드
        tv_charges = (TextView) findViewById(R.id.tv_charges); // 시설사용요금
        tv_noofrooms = (TextView) findViewById(R.id.tv_noofrooms); // 전체객실수
        tv_time = (TextView) findViewById(R.id.tv_time); // 이용시간
        tv_holiday = (TextView) findViewById(R.id.tv_holiday); // 휴무일
        tv_parking = (TextView) findViewById(R.id.tv_parking); // 주차시설
        tv_parking_fee = (TextView) findViewById(R.id.tv_fee); // 주차요금
        tv_attraction = (TextView) findViewById(R.id.tv_attraction); // 주변명소
        content = (TextView) findViewById(R.id.tv_content); // 핵심내용
        //content.setMovementMethod(new ScrollingMovementMethod());

        la_address = (TextView) findViewById(R.id.la_address); // 도로명 주소
        la_contact = (TextView) findViewById(R.id.la_contact); // 전화번호
        la_homepage = (TextView) findViewById(R.id.la_homepage); // 홈페이지
        la_content_s = (TextView) findViewById(R.id.la_intro); // 소개
        la_differ_s = (TextView) findViewById(R.id.la_characteristics); // 차별적특징
        la_guide = (TextView) findViewById(R.id.la_guide); // 관광가이드
        la_charges = (TextView) findViewById(R.id.la_charges); // 시설사용요금
        la_noofrooms = (TextView) findViewById(R.id.la_noofrooms); // 전체객실수
        la_time = (TextView) findViewById(R.id.la_time); // 이용시간
        la_holiday = (TextView) findViewById(R.id.la_holiday); // 휴무일
        la_parking = (TextView) findViewById(R.id.la_parking); // 주차시설
        la_parking_fee = (TextView) findViewById(R.id.la_fee); // 주차요금
        la_attraction = (TextView) findViewById(R.id.la_attraction); // 주변명소
        la_content = (TextView) findViewById(R.id.la_content); // 핵심내용

        my_course = (Button) findViewById(R.id.btn_my_course);
        set_up = (Button) findViewById(R.id.btn_set_up);

        FragmentManager fragmentManager = getFragmentManager();
        final MapFragment mapFragment = (MapFragment)fragmentManager
                .findFragmentById(R.id.detail_map);
        mapFragment.getMapAsync(this);

        Intent getData = getIntent();
        content_id = getData.getStringExtra("content_id"); // 분류 번호
        name = getData.getStringExtra("name"); // 이름
        type = getData.getStringExtra("type"); // 숙박, 관광, 음식 종류
        area = getData.getStringExtra("area"); // 지역
        address = getData.getStringExtra("address"); // 주소
        korea = getData.getStringExtra("korea");

        Log.e("Detail",content_id);
        Log.e("Detail",name);
        Log.e("Detail",type);
        Log.e("Detail",area);
        Log.e("Detail",address);

        SharedPreferences share = getSharedPreferences("set", MODE_PRIVATE);
        boolean check = share.getBoolean("my_course",false);
        if(check){ // 내 코스에서 상세 내용 확인
            li_btn.setVisibility(View.GONE);
        }
        else{
            li_btn.setVisibility(View.VISIBLE);
        }

        SharedPreferences pref = getSharedPreferences("lang",MODE_PRIVATE);
        language_num = pref.getInt("country",0);

        // 언어 설정
        if(language_num == 0){
            la_address.setText(R.string.korean_address);// 도로명 주소
            la_contact.setText(R.string.korean_contact); // 전화번호
            la_homepage.setText(R.string.korean_homepage); // 홈페이지
            la_content_s.setText(R.string.korean_content_s); // 소개
            la_differ_s.setText(R.string.korean_differ_s); // 차별적특징
            la_guide.setText(R.string.korean_guide); // 관광가이드
            la_charges.setText(R.string.korean_charges); // 시설사용요금
            la_noofrooms.setText(R.string.korean_noofrooms); // 전체객실수
            la_time.setText(R.string.korean_time); // 이용시간
            la_holiday.setText(R.string.korean_holiday); // 휴무일
            la_parking.setText(R.string.korean_parking); // 주차시설
            la_parking_fee.setText(R.string.korean_parking_fee); // 주차요금
            la_attraction.setText(R.string.korean_attraction); // 주변명소
            la_content.setText(R.string.korean_summary); // 핵심내용
            my_course.setText(R.string.korean_my_add_course);
            set_up.setText(R.string.korean_destination_setting);
            language = "korean";
        }
        else if(language_num == 1){
            la_address.setText(R.string.english_address);// 도로명 주소
            la_contact.setText(R.string.english_contact); // 전화번호
            la_homepage.setText(R.string.english_homepage); // 홈페이지
            la_content_s.setText(R.string.english_content_s); // 소개
            la_differ_s.setText(R.string.english_differ_s); // 차별적특징
            la_guide.setText(R.string.english_guide); // 관광가이드
            la_charges.setText(R.string.english_charges); // 시설사용요금
            la_noofrooms.setText(R.string.english_noofrooms); // 전체객실수
            la_time.setText(R.string.english_time); // 이용시간
            la_holiday.setText(R.string.english_holiday); // 휴무일
            la_parking.setText(R.string.english_parking); // 주차시설
            la_parking_fee.setText(R.string.english_parking_fee); // 주차요금
            la_attraction.setText(R.string.english_attraction); // 주변명소
            la_content.setText(R.string.english_summary); // 핵심내용
            my_course.setText(R.string.english_my_add_course);
            set_up.setText(R.string.english_destination_setting);
            language = "english";
        }
        else if(language_num == 2){
            la_address.setText(R.string.chinese_address);// 도로명 주소
            la_contact.setText(R.string.chinese_contact); // 전화번호
            la_homepage.setText(R.string.chinese_homepage); // 홈페이지
            la_content_s.setText(R.string.chinese_content_s); // 소개
            la_differ_s.setText(R.string.chinese_differ_s); // 차별적특징
            la_guide.setText(R.string.chinese_guide); // 관광가이드
            la_charges.setText(R.string.chinese_charges); // 시설사용요금
            la_noofrooms.setText(R.string.chinese_noofrooms); // 전체객실수
            la_time.setText(R.string.chinese_time); // 이용시간
            la_holiday.setText(R.string.chinese_holiday); // 휴무일
            la_parking.setText(R.string.chinese_parking); // 주차시설
            la_parking_fee.setText(R.string.chinese_parking_fee); // 주차요금
            la_attraction.setText(R.string.chinese_attraction); // 주변명소
            la_content.setText(R.string.chinese_summary); // 핵심내용
            my_course.setText(R.string.chinese_my_add_course);
            set_up.setText(R.string.chinese_destination_setting);
            language = "chinese";
        }
        else if(language_num == 3){
            la_address.setText(R.string.japanese_address);// 도로명 주소
            la_contact.setText(R.string.japanese_contact); // 전화번호
            la_homepage.setText(R.string.japanese_homepage); // 홈페이지
            la_content_s.setText(R.string.japanese_content_s); // 소개
            la_differ_s.setText(R.string.japanese_differ_s); // 차별적특징
            la_guide.setText(R.string.japanese_guide); // 관광가이드
            la_charges.setText(R.string.japanese_charges); // 시설사용요금
            la_noofrooms.setText(R.string.japanese_noofrooms); // 전체객실수
            la_time.setText(R.string.japanese_time); // 이용시간
            la_holiday.setText(R.string.japanese_holiday); // 휴무일
            la_parking.setText(R.string.japanese_parking); // 주차시설
            la_parking_fee.setText(R.string.japanese_parking_fee); // 주차요금
            la_attraction.setText(R.string.japanese_attraction); // 주변명소
            la_content.setText(R.string.japanese_summary); // 핵심내용
            my_course.setText(R.string.japanese_my_add_course);
            set_up.setText(R.string.japanese_destination_setting);
            language = "japanese";
        }
        else if(language_num == 4){
            la_address.setText(R.string.french_address);// 도로명 주소
            la_contact.setText(R.string.french_contact); // 전화번호
            la_homepage.setText(R.string.french_homepage); // 홈페이지
            la_content_s.setText(R.string.french_content_s); // 소개
            la_differ_s.setText(R.string.french_differ_s); // 차별적특징
            la_guide.setText(R.string.french_guide); // 관광가이드
            la_charges.setText(R.string.french_charges); // 시설사용요금
            la_noofrooms.setText(R.string.french_noofrooms); // 전체객실수
            la_time.setText(R.string.french_time); // 이용시간
            la_holiday.setText(R.string.french_holiday); // 휴무일
            la_parking.setText(R.string.french_parking); // 주차시설
            la_parking_fee.setText(R.string.french_parking_fee); // 주차요금
            la_attraction.setText(R.string.french_attraction); // 주변명소
            la_content.setText(R.string.french_summary); // 핵심내용
            my_course.setText(R.string.french_my_add_course);
            set_up.setText(R.string.french_destination_setting);
            language = "french";
        }
        else { // 예외 발생 시 한국어로 설정
            la_address.setText(R.string.korean_address);// 도로명 주소
            la_contact.setText(R.string.korean_contact); // 전화번호
            la_homepage.setText(R.string.korean_homepage); // 홈페이지
            la_content_s.setText(R.string.korean_content_s); // 소개
            la_differ_s.setText(R.string.korean_differ_s); // 차별적특징
            la_guide.setText(R.string.korean_guide); // 관광가이드
            la_charges.setText(R.string.korean_charges); // 시설사용요금
            la_noofrooms.setText(R.string.korean_noofrooms); // 전체객실수
            la_time.setText(R.string.korean_time); // 이용시간
            la_holiday.setText(R.string.korean_holiday); // 휴무일
            la_parking.setText(R.string.korean_parking); // 주차시설
            la_parking_fee.setText(R.string.korean_parking_fee); // 주차요금
            la_attraction.setText(R.string.korean_attraction); // 주변명소
            la_content.setText(R.string.korean_summary); // 핵심내용
            my_course.setText(R.string.korean_my_add_course);
            set_up.setText(R.string.korean_destination_setting);
            language = "korean";
        }

        if(type.equals(getString(R.string.korean_stay)) || type.equals(getString(R.string.english_stay)) || type.equals(getString(R.string.chinese_stay))
                || type.equals(getString(R.string.japanese_stay)) || type.equals(getString(R.string.french_stay))){
        //if(type.equals("숙박")){
            //data=Act_Splash.helper_St.select_content_id(language_num,content_id);

            RealmResults<Realm_Data_Model> results = realm.where(Realm_Data_Model.class)
                    .distinct(language) //언어별 컬럼
                    .where()
                    .equalTo("content_id", content_id)
                    .findAll();

            int size = results.size();
            Log.e("Realm_size", String.valueOf(size));

            ArrayList<Realm_Data_Model> list = new ArrayList<>();
            list.clear();
            for(Realm_Data_Model i : results){
                list.add(i);
            }

            try {
                Realm_Data_Model item = list.get(0);
                if(language_num == 0){
                    data = item.getKorean();
                }
                else if(language_num == 1){
                    data = item.getEnglish();
                }
                else if(language_num == 2){
                    data = item.getChinese();
                }
                else if(language_num == 3){
                    data = item.getJapanese();
                }
                else if(language_num == 4){
                    data = item.getFrench();
                }
                else { // 예외 발생 시 한국어로 설정
                    data = item.getKorean();
                }

                JSONObject jo = new JSONObject(data);
                summary = jo.getString("summary");
                lat = jo.getString("lat");
                lng = jo.getString("lng");
                contact = jo.getString("contact");
                homepage = jo.getString("homepage");
                content_s = jo.getString("content");
                guide = jo.getString("guide");
                charges = jo.getString("charges");
                noofrooms = jo.getString("noofrooms");
                parking = jo.getString("parking");
                parking_fee = jo.getString("parking_fee");
                attraction = jo.getString("attraction");

                if(parking.equals("") || parking_fee.equals("")){
                    content.setText(summary);
                    tv_contact.setText(contact);
                    tv_homepage.setText(homepage);
                    tv_content_s.setText(content_s);
                    tv_guide.setText(guide);
                    tv_address.setText(address);
                    tv_charges.setText(charges);
                    tv_noofrooms.setText(noofrooms);
                    li_parking.setVisibility(View.GONE);
                    li_parking_fee.setVisibility(View.GONE);
                    tv_attraction.setText(attraction);
                    li_differ_s.setVisibility(View.GONE);
                    li_time.setVisibility(View.GONE);
                    li_holiday.setVisibility(View.GONE);

                    /*content.setText(
                            "도로명주소 - " + address + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "핵심내용 - " + summary + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "전화번호 - " + contact + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "홈페이지 - " + homepage + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "소개 - " + content_s + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "관광가이드 - " + guide + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "시설사용요금 - " + charges + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "전체객실수 - " + noofrooms + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "주변명소 - " + attraction
                    );*/
                }
                else {
                    content.setText(summary);
                    tv_contact.setText(contact);
                    tv_homepage.setText(homepage);
                    tv_content_s.setText(content_s);
                    tv_guide.setText(guide);
                    tv_address.setText(address);
                    tv_charges.setText(charges);
                    tv_noofrooms.setText(noofrooms);
                    tv_parking.setText(parking);
                    tv_parking_fee.setText(parking_fee);
                    tv_attraction.setText(attraction);
                    li_differ_s.setVisibility(View.GONE);
                    li_time.setVisibility(View.GONE);
                    li_holiday.setVisibility(View.GONE);

                    /*content.setText(
                            "도로명주소 - " + address + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "핵심내용 - " + summary + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "전화번호 - " + contact + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "홈페이지 - " + homepage + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "소개 - " + content_s + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "관광가이드 - " + guide + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "시설사용요금 - " + charges + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "전체객실수 - " + noofrooms + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "주차시설 - " + parking + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "주차요금 - " + parking_fee + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "주변명소 - " + attraction
                    );*/
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals(getString(R.string.korean_food)) || type.equals(getString(R.string.english_food)) || type.equals(getString(R.string.chinese_food)) ||
                type.equals(getString(R.string.japanese_food)) || type.equals(getString(R.string.french_food))){
        //else if(type.equals("음식")){

            //data=Act_Splash.helper_F.select_content_id(language_num,content_id);

            RealmResults<Realm_Data_Model> results = realm.where(Realm_Data_Model.class)
                    .distinct(language) //언어별 컬럼
                    .where()
                    .equalTo("content_id", content_id)
                    .findAll();

            int size = results.size();
            Log.e("Realm_size", String.valueOf(size));

            ArrayList<Realm_Data_Model> list = new ArrayList<>();
            list.clear();
            for(Realm_Data_Model i : results){
                list.add(i);
            }

            try {
                Realm_Data_Model item = list.get(0);
                if(language_num == 0){
                    data = item.getKorean();
                }
                else if(language_num == 1){
                    data = item.getEnglish();
                }
                else if(language_num == 2){
                    data = item.getChinese();
                }
                else if(language_num == 3){
                    data = item.getJapanese();
                }
                else if(language_num == 4){
                    data = item.getFrench();
                }
                else { // 예외 발생 시 한국어로 설정
                    data = item.getKorean();
                }

                JSONObject jo = new JSONObject(data);
                summary = jo.getString("summary");
                lat = jo.getString("lat");
                lng = jo.getString("lng");
                contact = jo.getString("contact");
                homepage = jo.getString("homepage");
                content_s = jo.getString("content");
                time = jo.getString("time_s");
                holiday = jo.getString("holiday");
                parking = jo.getString("parking");
                parking_fee = jo.getString("parking_fee");
                attraction = jo.getString("attraction");

                if(parking.equals("")|| parking_fee.equals("")){
                    content.setText(summary);
                    tv_contact.setText(contact);
                    tv_homepage.setText(homepage);
                    tv_content_s.setText(content_s);
                    li_guide.setVisibility(View.GONE);
                    tv_address.setText(address);
                    li_charges.setVisibility(View.GONE);
                    li_noofrooms.setVisibility(View.GONE);
                    li_parking.setVisibility(View.GONE);
                    li_parking_fee.setVisibility(View.GONE);
                    tv_attraction.setText(attraction);
                    li_differ_s.setVisibility(View.GONE);
                    tv_time.setText(time);
                    tv_holiday.setText(holiday);

                    /*content.setText(
                            "도로명주소 - "+address+"\n"+
                            "--------------------------------------------------------------------------------------\n" +
                            "핵심내용 - "+summary+"\n"+
                            "--------------------------------------------------------------------------------------\n" +
                            "전화번호 - "+contact+"\n"+
                            "--------------------------------------------------------------------------------------\n" +
                            "홈페이지 - "+homepage+"\n"+
                            "--------------------------------------------------------------------------------------\n" +
                            "소개 - "+content_s+"\n"+
                            "--------------------------------------------------------------------------------------\n" +
                            "이용시간 - "+time+"\n"+
                            "--------------------------------------------------------------------------------------\n" +
                            "휴무일 - "+holiday+"\n"+
                            "--------------------------------------------------------------------------------------\n" +
                            "주변명소 - "+attraction
                    );*/
                }
                else {
                    content.setText(summary);
                    tv_contact.setText(contact);
                    tv_homepage.setText(homepage);
                    tv_content_s.setText(content_s);
                    li_guide.setVisibility(View.GONE);
                    tv_address.setText(address);
                    li_charges.setVisibility(View.GONE);
                    li_noofrooms.setVisibility(View.GONE);
                    tv_parking.setText(parking);
                    tv_parking_fee.setText(parking_fee);
                    tv_attraction.setText(attraction);
                    li_differ_s.setVisibility(View.GONE);
                    tv_time.setText(time);
                    tv_holiday.setText(holiday);

                    /*content.setText(
                            "도로명주소 - " + address + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "핵심내용 - " + summary + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "전화번호 - " + contact + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "홈페이지 - " + homepage + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "소개 - " + content_s + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "이용시간 - " + time + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "휴무일 - " + holiday + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "주차시설 - " + parking + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "주차요금 - " + parking_fee + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "주변명소 - " + attraction
                    );*/
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals(getString(R.string.korean_sightseeing)) || type.equals(getString(R.string.english_sightseeing)) || type.equals(getString(R.string.chinese_sightseeing)) ||
                type.equals(getString(R.string.japanese_sightseeing)) || type.equals(getString(R.string.french_sightseeing))){
       //else if(type.equals("관광")){

            //data=Act_Splash.helper_Si.select_content_id(language_num,content_id);

            RealmResults<Realm_Data_Model> results = realm.where(Realm_Data_Model.class)
                    .distinct(language) //언어별 컬럼
                    .where()
                    .equalTo("content_id", content_id)
                    .findAll();

            int size = results.size();
            Log.e("Realm_size", String.valueOf(size));

            ArrayList<Realm_Data_Model> list = new ArrayList<>();
            list.clear();
            for(Realm_Data_Model i : results){
                list.add(i);
            }

            try {
                Realm_Data_Model item = list.get(0);
                if(language_num == 0){
                    data = item.getKorean();
                }
                else if(language_num == 1){
                    data = item.getEnglish();
                }
                else if(language_num == 2){
                    data = item.getChinese();
                }
                else if(language_num == 3){
                    data = item.getJapanese();
                }
                else if(language_num == 4){
                    data = item.getFrench();
                }
                else { // 예외 발생 시 한국어로 설정
                    data = item.getKorean();
                }

                JSONObject jo = new JSONObject(data);
                summary = jo.getString("summary");
                lat = jo.getString("lat");
                lng = jo.getString("lng");
                contact = jo.getString("contact");
                homepage = jo.getString("homepage");
                content_s = jo.getString("content");
                differ_s = jo.getString("differ_s");
                guide = jo.getString("guide");
                //String program = jo.getString("program");
                //String period = jo.getString("period");
                //String place = jo.getString("place");
                time = jo.getString("time");
                //String organizer = jo.getString("organizer");
                parking = jo.getString("parking");
                parking_fee = jo.getString("parking_fee");
                attraction = jo.getString("attraction");

                /*content.setText(
                        "분류 번호 - "+content_id+"\n"+
                        "대표제목 - "+name+"\n"+
                        "종류 - "+type+"\n"+
                        "지역 - "+area+"\n"+
                        "도로명주소 - "+address+"\n"+
                        "핵심내용 - "+summary+"\n"+
                        "위도 - "+lat+"\n"+
                        "경도 - "+lng+"\n"+
                        "전화번호 - "+contact+"\n"+
                        "홈페이지 - "+homepage+"\n"+
                        "소개 - "+content_s+"\n"+
                        "차별적특징 - "+differ_s+"\n"+
                        "관광가이드 - "+guide+"\n"+
                        "프로그램 - "+program+"\n"+
                        "행사기간 - "+period+"\n"+
                        "행사장소 - "+place+"\n"+
                        "이용시간 - "+time+"\n"+
                        "주최주관 - "+organizer+"\n"+
                        "주차시설 - "+parking+"\n"+
                        "주차요금 - "+parking_fee+"\n"+
                        "주변명소 - "+attraction
                );*/

                if(parking.equals("")|| parking_fee.equals("")){
                    content.setText(summary);
                    tv_contact.setText(contact);
                    tv_homepage.setText(homepage);
                    tv_content_s.setText(content_s);
                    tv_guide.setText(guide);
                    tv_address.setText(address);
                    li_charges.setVisibility(View.GONE);
                    li_noofrooms.setVisibility(View.GONE);
                    li_parking.setVisibility(View.GONE);
                    li_parking_fee.setVisibility(View.GONE);
                    tv_attraction.setText(attraction);
                    tv_differ_s.setText(differ_s);
                    tv_time.setText(time);
                    li_holiday.setVisibility(View.GONE);
                    /*content.setText(
                            "도로명주소 - " + address + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "핵심내용 - " + summary + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "전화번호 - " + contact + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "홈페이지 - " + homepage + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "소개 - " + content_s + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "차별적특징 - " + differ_s + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "관광가이드 - " + guide + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "이용시간 - " + time + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "주변명소 - " + attraction
                    );*/
                }
                else {
                    content.setText(summary);
                    tv_contact.setText(contact);
                    tv_homepage.setText(homepage);
                    tv_content_s.setText(content_s);
                    tv_guide.setText(guide);
                    tv_address.setText(address);
                    li_charges.setVisibility(View.GONE);
                    li_noofrooms.setVisibility(View.GONE);
                    tv_parking.setText(parking);
                    tv_parking_fee.setText(parking_fee);
                    tv_attraction.setText(attraction);
                    tv_differ_s.setText(differ_s);
                    tv_time.setText(time);
                    li_holiday.setVisibility(View.GONE);

                    /*content.setText(
                            "도로명주소 - " + address + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "핵심내용 - " + summary + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "전화번호 - " + contact + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "홈페이지 - " + homepage + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "소개 - " + content_s + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "차별적특징 - " + differ_s + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "관광가이드 - " + guide + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "이용시간 - " + time + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "주차시설 - " + parking + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "주차요금 - " + parking_fee + "\n" +
                            "--------------------------------------------------------------------------------------\n" +
                            "주변명소 - " + attraction
                    );*/
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
            Log.e("DetailActivity","null 값 발생");
        }

        my_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("summary",summary);
                    jsonObject.put("contact",contact);
                    jsonObject.put("homepage",homepage);
                    jsonObject.put("content_s",content_s);
                    jsonObject.put("differ_s",differ_s);
                    jsonObject.put("guide",guide);
                    jsonObject.put("time",time);
                    jsonObject.put("holiday",holiday);
                    jsonObject.put("parking",parking);
                    jsonObject.put("parking_fee",parking_fee);
                    jsonObject.put("attraction",attraction);
                    jsonObject.put("charges",charges);
                    jsonObject.put("noofrooms",noofrooms);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        MyCourseData myCourseData = realm.createObject(MyCourseData.class);
                        myCourseData.setAddress(address);
                        myCourseData.setLng(Double.parseDouble(lng));
                        myCourseData.setLat(Double.parseDouble(lat));
                        myCourseData.setLocal(area);
                        myCourseData.setName(name);
                        myCourseData.setLang(language);
                        myCourseData.setContent_id(content_id);
                        myCourseData.setType(type);
                        myCourseData.setKoreaData(korea);
                    }
                });

                MyCourseToastMessage(language_num);
            }
        });
        set_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent intent = new Intent();
                intent.putExtra("name",name);
                intent.putExtra("address",address);
                intent.putExtra("content_id",content_id);
                setResult(0,intent);
                finish();*/

                Intent s = new Intent(DetailActivity.this, Act_set_destination.class);
                s.putExtra("name",name);
                s.putExtra("address",address);
                s.putExtra("content_id",content_id);
                startActivity(s);

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng POSITION = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(POSITION);
        markerOptions.title(name);
        markerOptions.snippet(address);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.end_pindpi));
        googleMap.addMarker(markerOptions);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(POSITION));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }
    public void MyCourseToastMessage(int language_num) {
        if (language_num == 0) {
            Toast.makeText(DetailActivity.this, getResources().getString(R.string.korean_toast_my_course), Toast.LENGTH_SHORT).show();
        }
        else if(language_num == 1){
            Toast.makeText(DetailActivity.this, getResources().getString(R.string.english_toast_my_course), Toast.LENGTH_SHORT).show();
        }
        else if(language_num == 2){
            Toast.makeText(DetailActivity.this, getResources().getString(R.string.chinese_toast_my_course), Toast.LENGTH_SHORT).show();
        }
        else if(language_num == 3){
            Toast.makeText(DetailActivity.this, getResources().getString(R.string.japanese_toast_my_course), Toast.LENGTH_SHORT).show();
        }
        else if(language_num == 4){
            Toast.makeText(DetailActivity.this, getResources().getString(R.string.french_toast_my_course), Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(DetailActivity.this, getResources().getString(R.string.korean_toast_my_course), Toast.LENGTH_SHORT).show();
        }
    }
}
