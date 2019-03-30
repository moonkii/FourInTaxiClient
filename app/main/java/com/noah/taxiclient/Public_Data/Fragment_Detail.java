package com.noah.taxiclient.Public_Data;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.noah.taxiclient.Activity.Act_Splash;
import com.noah.taxiclient.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by JinHa on 2017-09-15.
 */

public class Fragment_Detail extends AppCompatActivity{

    // 리스트
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Fragment_Detail_Item> myDataset;
    ArrayList<Fragment_Detail_Item> list_si = new ArrayList<>();
    ArrayList<Fragment_Detail_Item> list_st = new ArrayList<>();
    ArrayList<Fragment_Detail_Item> list_f = new ArrayList<>();

    //검색
    Search_list search_list;
    SearchView sv_search;
    //EditText edit_search;
    //ImageView btn_search;

    // 지역, 분류
    Spinner spinner;
    TextView sightseeing, stay, food;

    // 사용자 언어 선택 정보
    int language_num = 0; // 사용자가 선택한 언어를 담고 있는 변수( 0 - korean / 1 - english / 2 - chinese / 3 - japanese / 4 - french )

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail);
        sv_search = (SearchView) findViewById(R.id.sv_search);
        //btn_search = (ImageView) findViewById(R.id.btn_search);

        spinner = (Spinner) findViewById(R.id.spi_area);
        sightseeing = (TextView) findViewById(R.id.tv_sight);
        stay = (TextView) findViewById(R.id.tv_stay);
        food = (TextView) findViewById(R.id.tv_food);

        search_list = new Search_list();

        // 관광명소, 숙박, 음식점 탭 언어 설정
        if(language_num == 0){
            sightseeing.setText(R.string.korean_sightseeing_tab);
            stay.setText(R.string.korean_stay);
            food.setText(R.string.korean_food_tab);
        }
        else if(language_num == 1){
            sightseeing.setText(R.string.english_sightseeing_tab);
            stay.setText(R.string.english_stay);
            food.setText(R.string.english_food_tab);
        }
        else if(language_num == 2){
            sightseeing.setText(R.string.chinese_sightseeing_tab);
            stay.setText(R.string.chinese_stay);
            food.setText(R.string.chinese_food_tab);
        }
        else if(language_num == 3){
            sightseeing.setText(R.string.japanese_sightseeing_tab);
            stay.setText(R.string.japanese_stay);
            food.setText(R.string.japanese_food_tab);
        }
        else if(language_num == 4){
            sightseeing.setText(R.string.french_sightseeing_tab);
            stay.setText(R.string.french_stay);
            food.setText(R.string.french_food_tab);
        }
        else { // 예외 발생 시 한국어로 설정
            sightseeing.setText(R.string.korean_sightseeing_tab);
            stay.setText(R.string.korean_stay);
            food.setText(R.string.korean_food_tab);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        myDataset = new ArrayList<>();
        //mAdapter = new Fragment_Detail_Adapter(this, myDataset);
        //mAdapter.setRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

        myDataset = getData_sightseeing();
        //mRecyclerView.setAdapter(new Fragment_Detail_Adapter(this, myDataset));

        //목적지 검색 버튼
        /*btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/


        //스피너
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("position","position = "+position+parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sightseeing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*myDataset.clear();
                myDataset = getData_sightseeing();
                mRecyclerView.setAdapter(new Fragment_Detail_Adapter(Fragment_Detail.this, myDataset));*/
            }
        });

        stay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*myDataset.clear();
                myDataset = getData_stay();
                mRecyclerView.setAdapter(new Fragment_Detail_Adapter(Fragment_Detail.this, myDataset));*/
            }
        });

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*myDataset.clear();
                myDataset = getData_food();
                mRecyclerView.setAdapter(new Fragment_Detail_Adapter(Fragment_Detail.this, myDataset));*/
            }
        });
    }

    /*@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment

            View tab_public_data = inflater.inflate(R.layout.fragment_detail, container, false);
            edit_search = (EditText) tab_public_data.findViewById(R.id.edit_search);
            btn_search = (ImageView) tab_public_data.findViewById(R.id.btn_search);

            spinner = (Spinner) tab_public_data.findViewById(R.id.spi_area);
            sightseeing = (TextView) tab_public_data.findViewById(R.id.tv_sight);
            stay = (TextView) tab_public_data.findViewById(R.id.tv_stay);
            food = (TextView) tab_public_data.findViewById(R.id.tv_food);

            mRecyclerView = (RecyclerView) tab_public_data.findViewById(R.id.rv);
            mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
            mRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);

            // specify an adapter (see also next example)
            myDataset = new ArrayList<>();
            mAdapter = new Fragment_Detail_Adapter(getActivity(), myDataset);
            mRecyclerView.setAdapter(mAdapter);

            myDataset = showList();
            mRecyclerView.setAdapter(new Fragment_Detail_Adapter(getActivity(), myDataset));

            btn_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            return tab_public_data;
        }*/
    public ArrayList<Fragment_Detail_Item> getData_sightseeing() { // 관광

        String language = ""; // 사용자가 선택한 언어로 데이터 값 저장
        String content_id,name,type,area,address,korean,english,chinese,japanese,french;
        // 이름, 지역, 주소
        list_si.clear();

        Act_Splash.db = Act_Splash.helper_Si.getReadableDatabase();
        Cursor c = Act_Splash.db.rawQuery("SELECT content_id, type, area, korean, english, chinese, japanese, french FROM Sightseeing;",null);
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

    public ArrayList<Fragment_Detail_Item> getData_stay() { // 숙박

        String language = ""; // 사용자가 선택한 언어로 데이터 값 저장
        String content_id,name,type,area,address,korean,english,chinese,japanese,french;
        // 이름, 지역, 주소
        list_st.clear();

        Act_Splash.db = Act_Splash.helper_St.getReadableDatabase();
        Cursor c = Act_Splash.db.rawQuery("SELECT content_id, type, area, korean, english, chinese, japanese, french FROM Stay;",null);
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
                    type = getString(R.string.korean_stay);
                }
                else if(language_num == 1){
                    language = english;
                    type = getString(R.string.english_stay);
                }
                else if(language_num == 2){
                    language = chinese;
                    type = getString(R.string.chinese_stay);
                }
                else if(language_num == 3){
                    language = japanese;
                    type = getString(R.string.japanese_stay);
                }
                else if(language_num == 4){
                    language = french;
                    type = getString(R.string.japanese_stay);
                }
                else{
                    Log.e("Language","언어 선택 오류 발생");
                }

                try {
                    JSONObject jo = new JSONObject(language);
                    address = jo.getString("address");
                    name = jo.getString("subject");

                    list_st.add(new Fragment_Detail_Item(content_id,name,type,area,address));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            c.close();
        }

        return list_st;
    }

    public ArrayList<Fragment_Detail_Item> getData_food() { // 음식

        String language = ""; // 사용자가 선택한 언어로 데이터 값 저장
        String content_id,name,type,area,address,korean,english,chinese,japanese,french;
        // 이름, 지역, 주소
        list_f.clear();

        Act_Splash.db = Act_Splash.helper_F.getReadableDatabase();
        Cursor c = Act_Splash.db.rawQuery("SELECT content_id, type, area, korean, english, chinese, japanese, french FROM Food;",null);
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
                    type = getString(R.string.korean_food);
                }
                else if(language_num == 1){
                    language = english;
                    type = getString(R.string.english_food);
                }
                else if(language_num == 2){
                    language = chinese;
                    type = getString(R.string.chinese_food);
                }
                else if(language_num == 3){
                    language = japanese;
                    type = getString(R.string.japanese_food);
                }
                else if(language_num == 4){
                    language = french;
                    type = getString(R.string.french_food);
                }
                else{
                    Log.e("Language","언어 선택 오류 발생");
                }

                try {
                    JSONObject jo = new JSONObject(language);
                    address = jo.getString("address");
                    name = jo.getString("subject");

                    list_f.add(new Fragment_Detail_Item(content_id,name,type,area,address));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            c.close();
        }

        return list_f;
    }
}
