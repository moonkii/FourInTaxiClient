package com.noah.taxiclient.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.noah.taxiclient.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Noah on 2017-04-18.
 */

public class Frag_adapter extends FragmentPagerAdapter {//프래그먼트 어댑터 클래스 시작

    /*#####################################
    *               변수생성
    * #####################################*/
    int page_number=3;

    public static String firstTitle="택시콜";
    public static String secondTitle="장소 검색";
    public static String thridTitle="나의 택시";

    Context context;

    /*#####################################
     *          프래그먼트 어댑터 생성자
     * #####################################*/
    public Frag_adapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }


    /*#####################################
    *          프래그먼트 페이지 호출
    * #####################################*/
    @Override
    public Fragment getItem(int position) {//getItem start


        //페이지 호출
        switch (position){//페이지 호출 switch start

            case 0 :
                return Frag_first.newInstance();

            case 1 :
                return Frag_second.newInstance();

            case 2 :
                return Frag_third.newInstance();

        }//페이지 호출 switch end

        return null;
    }//getItem end


    /*#####################################
    *         프래그먼트 카운트 메소드
    * #####################################*/
    @Override
    public int getCount() {
        return page_number;
    }


    /*#####################################
    *         프래그먼트 제목 지정 메소드
    * #####################################*/

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {//프래그먼트 제목 지정 메소드 시작

        switch (position){ //프래그먼트 제목 지정 switch 시작
            case 0 :
                language();
                return firstTitle;

            case 1 :
                language();
                return secondTitle;

            case 2 :
                language();
                return thridTitle;

        }//프래그먼트 제목 지정 switch 끝

        return null;//프래그먼트 제목 지정 메소드 리턴

    }//프래그먼트 제목 지정 메소드 끝

    public void language(){
        SharedPreferences pref = context.getSharedPreferences("lang",MODE_PRIVATE);
        int language_num = pref.getInt("country",0);

        if(language_num == 0){
            firstTitle = context.getResources().getString(R.string.korean_first_tab);
            secondTitle = context.getResources().getString(R.string.korean_second_tab);
            thridTitle = context.getResources().getString(R.string.korean_third_tab);
        }
        else if(language_num == 1){
            firstTitle = context.getResources().getString(R.string.english_first_tab);
            secondTitle = context.getResources().getString(R.string.english_second_tab);
            thridTitle = context.getResources().getString(R.string.english_third_tab);
        }
        else if(language_num == 2){
            firstTitle = context.getResources().getString(R.string.chinese_first_tab);
            secondTitle = context.getResources().getString(R.string.chinese_second_tab);
            thridTitle = context.getResources().getString(R.string.chinese_third_tab);
        }
        else if(language_num == 3){
            firstTitle = context.getResources().getString(R.string.japanese_first_tab);
            secondTitle = context.getResources().getString(R.string.japanese_second_tab);
            thridTitle = context.getResources().getString(R.string.japanese_third_tab);
        }
        else if(language_num == 4){
            firstTitle = context.getResources().getString(R.string.french_first_tab);
            secondTitle = context.getResources().getString(R.string.french_second_tab);
            thridTitle = context.getResources().getString(R.string.french_third_tab);
        }
    }


}//프래그먼트 어댑터 클래스 끝
