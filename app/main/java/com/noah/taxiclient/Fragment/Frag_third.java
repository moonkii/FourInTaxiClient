package com.noah.taxiclient.Fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.noah.taxiclient.Activity.Act_Mcourse;
import com.noah.taxiclient.Activity.Act_Mrecord;
import com.noah.taxiclient.Dialog.D_Language;
import com.noah.taxiclient.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Frag_third extends Fragment {
    /*#####################################
    *           전역 변수
    * #####################################*/
    //뷰변수
    Button btn_record;
    Button btn_lang;
//    Button btn_driver;
    Button btn_course;

    TextView username;

    SharedPreferences pref_user; // 사용자 이름
    SharedPreferences pref_lang; // 언어
    int language_num = 0;

    /*#####################################
    *          프래그먼트 생성자
    * #####################################*/
    public Frag_third() {
        // Required empty public constructor
    }


    /*#####################################
    *          프래그먼트 객체
    * #####################################*/
    public static Frag_third newInstance() {
        Bundle args = new Bundle();

        Frag_third frag_third = new Frag_third();
        frag_third.setArguments(args);
        return frag_third;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref_user = getActivity().getSharedPreferences("User", MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag_thrid, container, false);

        btn_record = (Button) view.findViewById(R.id.third_record);
        btn_lang = (Button) view.findViewById(R.id.third_lang);
//        btn_driver = (Button) view.findViewById(R.id.third_driver);
        username = (TextView) view.findViewById(R.id.third_myName);
        username.setText(pref_user.getString("name", "사용자 님"));
        btn_course = (Button) view.findViewById(R.id.third_course);

        pref_lang = getActivity().getSharedPreferences("lang", MODE_PRIVATE);
        int language_num = pref_lang.getInt("country", 0);

        if (language_num == 0) {
            btn_record.setText(getResources().getString(R.string.korea_recordTitle));
            btn_lang.setText(getResources().getString(R.string.korean_language));
//            btn_driver.setText(getResources().getString(R.string.korea_mydriver));
            btn_course.setText(getResources().getString(R.string.korea_mycource));
        } else if (language_num == 1) {
            btn_record.setText(getResources().getString(R.string.english_recordTitle));
            btn_lang.setText(getResources().getString(R.string.english_language));
//            btn_driver.setText(getResources().getString(R.string.english_mydriver));
            btn_course.setText(getResources().getString(R.string.english_mycource));
        } else if (language_num == 2) {
            btn_record.setText(getResources().getString(R.string.chinese_recordTitle));
            btn_lang.setText(getResources().getString(R.string.chinese_language));
//            btn_driver.setText(getResources().getString(R.string.chinese_mydriver));
            btn_course.setText(getResources().getString(R.string.chinese_mycource));
        } else if (language_num == 3) {
            btn_record.setText(getResources().getString(R.string.japanese_recordTitle));
            btn_lang.setText(getResources().getString(R.string.japanese_language));
//            btn_driver.setText(getResources().getString(R.string.japanese_mydriver));
            btn_course.setText(getResources().getString(R.string.japanese_mycource));
        } else if (language_num == 4) {
            btn_record.setText(getResources().getString(R.string.french_recordTitle));
            btn_lang.setText(getResources().getString(R.string.french_language));
//            btn_driver.setText(getResources().getString(R.string.french_mydriver));
            btn_course.setText(getResources().getString(R.string.french_mycource));
        }


        // Inflate the layout for this fragment
        return view;


    }

    @Override
    public void onStart() {
        super.onStart();

        btn_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                D_Language langDialog = new D_Language(getActivity(), new D_Language.ClickOkButtonListener() {
                    @Override
                    public void setLanguage() {
                        //목록 언어 및 뷰페이저타이틀 변경

                        language_num = pref_lang.getInt("country", 0);


                        switch (language_num) {

                            case 0:
                                //한국어
                                Frag_adapter.firstTitle = getResources().getString(R.string.korean_first_tab);
                                Frag_adapter.secondTitle = getResources().getString(R.string.korean_second_tab);
                                Frag_adapter.thridTitle = getResources().getString(R.string.korean_third_tab);
                                break;

                            case 1:
                                //영어
                                Frag_adapter.firstTitle = getResources().getString(R.string.english_first_tab);
                                Frag_adapter.secondTitle = getResources().getString(R.string.english_second_tab);
                                Frag_adapter.thridTitle = getResources().getString(R.string.english_third_tab);
                                break;

                            case 2:
                                //중국어
                                Frag_adapter.firstTitle = getResources().getString(R.string.chinese_first_tab);
                                Frag_adapter.secondTitle = getResources().getString(R.string.chinese_second_tab);
                                Frag_adapter.thridTitle = getResources().getString(R.string.chinese_third_tab);
                                break;

                            case 3:
                                //일본어
                                Frag_adapter.firstTitle = getResources().getString(R.string.japanese_first_tab);
                                Frag_adapter.secondTitle = getResources().getString(R.string.japanese_second_tab);
                                Frag_adapter.thridTitle = getResources().getString(R.string.japanese_third_tab);
                                break;

                            case 4:
                                //프랑스어
                                Frag_adapter.firstTitle = getResources().getString(R.string.french_first_tab);
                                Frag_adapter.secondTitle = getResources().getString(R.string.french_second_tab);
                                Frag_adapter.thridTitle = getResources().getString(R.string.french_third_tab);
                                break;
                        }


                        if (language_num == 0) {
                            btn_record.setText(getResources().getString(R.string.korea_recordTitle));
                            btn_lang.setText(getResources().getString(R.string.korean_language));
//                            btn_driver.setText(getResources().getString(R.string.korea_mydriver));
                            btn_course.setText(getResources().getString(R.string.korea_mycource));
                        } else if (language_num == 1) {
                            btn_record.setText(getResources().getString(R.string.english_recordTitle));
                            btn_lang.setText(getResources().getString(R.string.english_language));
//                            btn_driver.setText(getResources().getString(R.string.english_mydriver));
                            btn_course.setText(getResources().getString(R.string.english_mycource));
                        } else if (language_num == 2) {
                            btn_record.setText(getResources().getString(R.string.chinese_recordTitle));
                            btn_lang.setText(getResources().getString(R.string.chinese_language));
//                            btn_driver.setText(getResources().getString(R.string.chinese_mydriver));
                            btn_course.setText(getResources().getString(R.string.chinese_mycource));
                        } else if (language_num == 3) {
                            btn_record.setText(getResources().getString(R.string.japanese_recordTitle));
                            btn_lang.setText(getResources().getString(R.string.japanese_language));
//                            btn_driver.setText(getResources().getString(R.string.japanese_mydriver));
                            btn_course.setText(getResources().getString(R.string.japanese_mycource));
                        } else if (language_num == 4) {
                            btn_record.setText(getResources().getString(R.string.french_recordTitle));
                            btn_lang.setText(getResources().getString(R.string.french_language));
//                            btn_driver.setText(getResources().getString(R.string.french_mydriver));
                            btn_course.setText(getResources().getString(R.string.french_mycource));
                        }


                    }
                });
                langDialog.setCancelable(false);
                langDialog.show();

            }
        });

        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), Act_Mrecord.class);
                startActivity(intent);

            }
        });


        btn_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Act_Mcourse.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        /*if(isVisibleToUser){
            pref = getActivity().getSharedPreferences("lang",MODE_PRIVATE);
            language_num = pref.getInt("country",0);

            switch (language_num){

                case 0:
                    //한국어
                    Frag_adapter.firstTitle = getResources().getString(R.string.korean_first_tab);
                    Frag_adapter.secondTitle = getResources().getString(R.string.korean_second_tab);
                    Frag_adapter. thridTitle = getResources().getString(R.string.korean_third_tab);
                    break;

                case 1:
                    //영어
                    Frag_adapter.firstTitle = getResources().getString(R.string.english_first_tab);
                    Frag_adapter.secondTitle = getResources().getString(R.string.english_second_tab);
                    Frag_adapter.thridTitle = getResources().getString(R.string.english_third_tab);
                    break;

                case 2:
                    //중국어
                    Frag_adapter.firstTitle = getResources().getString(R.string.chinese_first_tab);
                    Frag_adapter.secondTitle = getResources().getString(R.string.chinese_second_tab);
                    Frag_adapter.thridTitle = getResources().getString(R.string.chinese_third_tab);
                    break;

                case 3:
                    //일본어
                    Frag_adapter.firstTitle = getResources().getString(R.string.japanese_first_tab);
                    Frag_adapter.secondTitle = getResources().getString(R.string.japanese_second_tab);
                    Frag_adapter.thridTitle = getResources().getString(R.string.japanese_third_tab);
                    break;

                case 4:
                    //프랑스어
                    Frag_adapter.firstTitle = getResources().getString(R.string.french_first_tab);
                    Frag_adapter.secondTitle = getResources().getString(R.string.french_second_tab);
                    Frag_adapter.thridTitle = getResources().getString(R.string.french_third_tab);
                    break;
            }
            //여기다가 뷰변수들 언어 변경
        }*/
    }

}
