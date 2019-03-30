package com.noah.taxiclient.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.noah.taxiclient.Activity.Act_main;
import com.noah.taxiclient.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Noah on 2017-09-15.
 */

public class D_Language extends Dialog {

    /*#####################################
    *           전역 변수
    * #####################################*/
    Spinner spinner_lang;
    ArrayAdapter<String> arrayAdapter;
    TextView ok;
    TextView cancel;
    Context c;
    String langs[];

    SharedPreferences pref;
    SharedPreferences.Editor editor;


    ClickOkButtonListener clickOkListener;

    public D_Language(@NonNull Context context, ClickOkButtonListener click) {
        super(context);
        this.c=context;
        this.clickOkListener = click;
    }

    public D_Language(@NonNull Context context) {
        super(context);
        this.c=context;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = c.getSharedPreferences("lang",MODE_PRIVATE);
        editor = pref.edit();

        //다이얼로그 뷰 설정
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
        setContentView(R.layout.dialog_lang);

        langs=c.getResources().getStringArray(R.array.korea_langlist);
        spinner_lang = (Spinner) findViewById(R.id.dialog_lan_spinner);
        arrayAdapter = new ArrayAdapter(c,android.R.layout.simple_list_item_1,langs);
        spinner_lang.setAdapter(arrayAdapter);
        ok = (TextView) findViewById(R.id.dialog_lan_ok);


        spinner_lang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position){

                    case 0 :
                        editor.putInt("country",0); // 한국어
                        editor.commit();
                        break;

                    case 1 :
                        editor.putInt("country",1); // 영어
                        editor.commit();
                        break;

                    case 2 :
                        editor.putInt("country",2); // 중국어
                        editor.commit();
                        break;

                    case 3 :
                        editor.putInt("country",3); // 일본어
                        editor.commit();
                        break;

                    case 4 :
                        editor.putInt("country",4); // 프랑스어
                        editor.commit();
                        break;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOkListener.setLanguage();
                D_Language.this.dismiss();
            }
        });
    }


    public interface ClickOkButtonListener{
        void setLanguage();
    }

    //back key 막기


    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            Toast.makeText(c, "언어를 선택해주세요.", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onKeyDown(keyCode, event);


    }
}
