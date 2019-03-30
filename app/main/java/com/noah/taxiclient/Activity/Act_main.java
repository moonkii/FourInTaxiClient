package com.noah.taxiclient.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.Bundle;

import android.support.annotation.NonNull;

import android.support.annotation.ColorInt;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.noah.taxiclient.Fragment.Frag_adapter;
import com.noah.taxiclient.MyFirebaseMessagingService;
import com.noah.taxiclient.R;
import com.noah.taxiclient.utills.Item_response_driver;

/**
 * Created by Noah on 2017-08-15.
 */

public class Act_main extends AppCompatActivity {

    /*#####################################
    *          전역 변수
    * #####################################*/

    String TAG="###Act_main"; //로그 변수

    //뷰
    Frag_adapter frag_adapter; //프래그먼트 어댑터
    ViewPager viewPager; //뷰페이저
    TabLayout tabLayout; //탭 레이아웃



    /*#####################################
    *          온크리에이트
    * #####################################*/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //====================== 액션바 커스텀 ======================
        try{
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.actionbar_custom);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        //==========================================================
        setContentView(R.layout.act_main);
        makeViewOfViewPagerWithTab();

//        if(checkGPSService()){
//            //탭(뷰페이저) 레이아웃 뷰 생성
//            makeViewOfViewPagerWithTab();
//        }


    }


    /*#####################################
    *          뷰 생성 메소드
    * #####################################*/
    private void makeViewOfViewPagerWithTab(){

        frag_adapter = new Frag_adapter(getSupportFragmentManager(),Act_main.this);//프래그먼트 어댑터 생성
        viewPager = (ViewPager) findViewById(R.id.main_pager); //뷰페이저 설정
        viewPager.setAdapter(frag_adapter); //뷰페이저와 프래그먼트 연결
        tabLayout = (TabLayout) findViewById(R.id.main_tabs); //탭레이아웃 생성
        tabLayout.setupWithViewPager(viewPager); //탭레이아웃과 뷰페이저 연결

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                frag_adapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }




    public boolean checkGPSService() {
        LocationManager manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            // GPS OFF 일때 Dialog 표시

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            alertDialogBuilder.setTitle("위치 서비스(GPS)를 설정해주세요")
                    .setCancelable(false)
                    .setPositiveButton("확인",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 확인 버튼

                                    // GPS설정 화면으로 이동
                                    Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                                    startActivity(intent);
                                    
                                    dialog.dismiss();

                                }
                            });
            AlertDialog alert = alertDialogBuilder.create();

            alert.show();

            return false;


        } else {
            return true;
        }


    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if(checkGPSService()){
//            //탭(뷰페이저) 레이아웃 뷰 생성
//            makeViewOfViewPagerWithTab();
//        }
//
//    }

    @Override
    protected void onPause() {
        super.onPause();

    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if(hasFocus) {
//            checkGPSService();
//        }
//
//
//    }
}//Act_main end
