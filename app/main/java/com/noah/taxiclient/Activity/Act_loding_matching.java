package com.noah.taxiclient.Activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.noah.taxiclient.MyFirebaseMessagingService;
import com.noah.taxiclient.R;
import com.noah.taxiclient.utills.Item_response_driver;

/**
 * Created by YH on 2017-09-18.
 */

public class Act_loding_matching extends Activity {

    ImageView imageView;
    TextView msg;
    Button cancel;

    int language_num = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_matching);
        imageView = (ImageView) findViewById(R.id.imageView2);
        cancel = (Button)findViewById(R.id.button2);
        msg= (TextView) findViewById(R.id.textView3);
        //이 화면에서 운전자의 승낙 요청을 기다린다.
        //MyFirebaseMessagingService에서 DRIVER_OK로 날린 브로드캐스트를 감지하는 리시버와 값이 온다면 실행할 코드를 담은 리시버를 등록한다.

        SharedPreferences pref = getSharedPreferences("lang",MODE_PRIVATE);
        language_num = pref.getInt("country",0);

        if(language_num == 0)
        {
            msg.setText(R.string.korean_matching);
            cancel.setText(R.string.korean_call_cancel);
            Glide.with(this).load(R.drawable.korean).override(500,500).into(imageView);
        }
        else if(language_num == 1) {
            msg.setText(R.string.english_matching);
            cancel.setText(R.string.english_call_cancel);
            Glide.with(this).load(R.drawable.english).override(500,500).into(imageView);
        }
        else if(language_num == 2) {
            msg.setText(R.string.chinese_matching);
            cancel.setText(R.string.chinese_call_cancel);
            Glide.with(this).load(R.drawable.chinese).override(500,500).into(imageView);
        }
        else if(language_num == 3){
            msg.setText(R.string.japanese_matching);
            cancel.setText(R.string.japanese_call_cancel);
            Glide.with(this).load(R.drawable.japanese).override(500,500).into(imageView);
        }
        else if(language_num == 4){
            msg.setText(R.string.french_matching);
            cancel.setText(R.string.french_call_cancel);
            Glide.with(this).load(R.drawable.french2).override(500,500).into(imageView);
        }
        registerReceiver(myReceiver, new IntentFilter(MyFirebaseMessagingService.DRIVER_OK));

        // 이 화면에서만 운전자의 응답 요구를 받을 수 있다.
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }

    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String get_msg =  intent.getStringExtra("msg");
            Gson gson = new Gson();
            Item_response_driver item =  gson.fromJson(get_msg,Item_response_driver.class);

            if(item.getFlag().trim().equals("driver_ok")) { //운전자가 승낙 응답을 기다린다. flag를 변경해줘야한다.
                String car_num = item.getCar_num();
                String name = item.getName();
                String x = item.getX(); //위도
                String y = item.getY();// 경도
                String destination = item.getDestination(); //목적지
                String start_address = item.getStart_address(); //출발지
                String get_token = item.getToken(); //토큰
                //매칭된 화면으로 넘어간다.
                // 그전에 운전자의 정보를 DB에 저장한다
                // 이유는 내가 탑승한 운전자 번호를 알기 위해서이다.
                //Realm 에 넣는다.
                //Toast.makeText(context, "택시 기사님과 연결되었습니다. ", Toast.LENGTH_SHORT).show();
                ToastMessage_driver(language_num);

                if(language_num == 0)
                {
                    msg.setText(getString(R.string.korean_taxi_number)+" : "+car_num+"\n"+getString(R.string.korean_taxi_come));
                }
                else if(language_num == 1) {
                    msg.setText(getString(R.string.english_taxi_number)+" : "+car_num+"\n"+getString(R.string.english_taxi_come));
                }
                else if(language_num == 2) {
                    msg.setText(getString(R.string.chinese_taxi_number)+" : "+car_num+"\n"+getString(R.string.chinese_taxi_come));
                }
                else if(language_num == 3){
                    msg.setText(getString(R.string.japanese_taxi_number)+" : "+car_num+"\n"+getString(R.string.japanese_taxi_come));
                }
                else if(language_num == 4){
                    msg.setText(getString(R.string.french_taxi_number)+" : "+car_num+"\n"+getString(R.string.french_taxi_come));
                }

            }else if(item.getFlag().trim().equals("get_client")) { //매칭완료
                //화면 꺼줌
                //Toast.makeText(context, "탑승 완료!", Toast.LENGTH_SHORT).show();
                ToastMessge_ok(language_num);
                /*Intent i = new Intent(Act_loding_matching.this,Act_ending.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();*/

                moveTaskToBack(true);
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }
    };

    public void ToastMessage_driver(int language_num) {
        if (language_num == 0) {
            Toast.makeText(Act_loding_matching.this, getResources().getString(R.string.korean_driver), Toast.LENGTH_SHORT).show();
        }
        else if(language_num == 1){
            Toast.makeText(Act_loding_matching.this, getResources().getString(R.string.english_driver), Toast.LENGTH_SHORT).show();
        }
        else if(language_num == 2){
            Toast.makeText(Act_loding_matching.this, getResources().getString(R.string.chinese_driver), Toast.LENGTH_SHORT).show();
        }
        else if(language_num == 3){
            Toast.makeText(Act_loding_matching.this, getResources().getString(R.string.japanese_driver), Toast.LENGTH_SHORT).show();
        }
        else if(language_num == 4){
            Toast.makeText(Act_loding_matching.this, getResources().getString(R.string.french_driver), Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(Act_loding_matching.this, getResources().getString(R.string.korean_driver), Toast.LENGTH_SHORT).show();
        }
    }
    public void ToastMessge_ok(int language_num){
        if (language_num == 0) {
            Toast.makeText(Act_loding_matching.this, getResources().getString(R.string.korean_call_ok), Toast.LENGTH_SHORT).show();
        }
        else if(language_num == 1){
            Toast.makeText(Act_loding_matching.this, getResources().getString(R.string.english_call_ok), Toast.LENGTH_SHORT).show();
        }
        else if(language_num == 2){
            Toast.makeText(Act_loding_matching.this, getResources().getString(R.string.chinese_call_ok), Toast.LENGTH_SHORT).show();
        }
        else if(language_num == 3){
            Toast.makeText(Act_loding_matching.this, getResources().getString(R.string.japanese_call_ok), Toast.LENGTH_SHORT).show();
        }
        else if(language_num == 4){
            Toast.makeText(Act_loding_matching.this, getResources().getString(R.string.french_call_ok), Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(Act_loding_matching.this, getResources().getString(R.string.korean_call_ok), Toast.LENGTH_SHORT).show();
        }
    }
}
