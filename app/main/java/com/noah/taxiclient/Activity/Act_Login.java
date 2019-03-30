package com.noah.taxiclient.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.noah.taxiclient.R;
import com.noah.taxiclient.utills.Item_signup;
import com.noah.taxiclient.utills.item_response;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Noah on 2017-09-17.
 */

public class Act_Login extends Activity {

    //프리퍼런스
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    //로그인 버튼
    ImageView btn_google;
    ImageView btn_naver;
    ImageView btn_kakao;
    ImageView btn_face;

    Button btn_login;

    //입력받는 것들
    EditText userName;

    //FCM
    String token; //토큰값
    String checkStr; //입력받은 이름
    String sendToServerStr; //서버로 보낼 값
    Gson gson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        
        //프리퍼런스 호출
        pref = getSharedPreferences("User",MODE_PRIVATE);
        editor=pref.edit();

        token =  FirebaseInstanceId.getInstance().getToken();

        gson= new Gson();

        btn_login = (Button) findViewById(R.id.login);
        btn_google= (ImageView) findViewById(R.id.google);
        btn_naver = (ImageView) findViewById(R.id.naver);
        btn_kakao = (ImageView) findViewById(R.id.kakao);
        btn_face = (ImageView) findViewById(R.id.face);
        userName = (EditText) findViewById(R.id.login_name);


        //기존 이름 있는지 확인
        if(pref.getString("name",null)!=null){
            userName.setText(pref.getString("name",null));
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLogin();
            }
        });

        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLogin();
            }
        });

        btn_naver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLogin();
            }
        });

        btn_kakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLogin();
            }
        });

        btn_face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLogin();
            }
        });

    }
    
    
    //로그인 
    private void goLogin(){

        if(nameInputCheck()){

            if(pref.getString("name",null)!=null){
                startActivity(new Intent(Act_Login.this,Act_main.class));
                Act_Login.this.finish();
            }else{
                //이름 저장
                editor.putString("name",checkStr);
                editor.commit();

                Item_signup item_signup = new Item_signup(null,null,token,null,checkStr,"2");
                sendToServerStr=gson.toJson(item_signup);
                Register task = new Register();
                task.execute();
            }



        }

    }

    //이름 입력했는지 체크 메소드
    private boolean nameInputCheck(){
        checkStr = userName.getText().toString();

        if(!checkStr.equals("")){

            return true;
        }else{
            Toast.makeText(this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    class Register extends AsyncTask<String, Void, String> {
        ProgressDialog loading;
        Response response;
        String get;
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            loading = ProgressDialog.show(Act_Login.this, "로그인 중입니다.", null, true, true); //서버접속하기 전에 로딩 띄우기
            Log.i("Register_Activity","백그라운드 시작");
        }

        @Override
        protected void onPostExecute(String s) { //서버접속했다면 로딩끄기
            super.onPostExecute(s);
            Log.i("register_response",s);
            Log.v("##백그라운드 끝!!!","ㅇㅇㅇㅇ"+s);
            loading.dismiss();
            item_response result =  gson.fromJson(s,item_response.class);
            Log.v("##백그라운드 끝222!!!","ㅇㅇㅇㅇ"+result);

            Log.v("##백그라운드 끝333!!!","ㅇㅇㅇㅇ"+result.getResponse());
            if(result.getResponse()!=null &&result.getResponse().equals("1")){
                startActivity(new Intent(Act_Login.this,Act_main.class));
                editor.putString("token",token);
                Act_Login.this.finish();
                Log.v("##백그라운드 끝444!!!","로그인성공");
            }else{
                Toast.makeText(Act_Login.this, "인터넷상태를 확인해주세요.", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected String doInBackground(String... params) {
            //이부분에서 data를 key로  메시지를 보내준다.
            OkHttpClient client = new OkHttpClient();
            RequestBody body = new FormBody.Builder()
                    .add("data", sendToServerStr)
                    .build();

            //request
            Request request = new Request.Builder()
                    .url("http://teampinky.vps.phps.kr/API/register_driver.php")
                    .post(body)
                    .build();

            try {
                response = client.newCall(request).execute();
                get = response.body().string();

            } catch (IOException error) {
                error.printStackTrace();
            }

            return get;
        }
    }



}//class end
