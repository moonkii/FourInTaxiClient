package com.noah.taxiclient.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.noah.taxiclient.Fragment.Frag_first_find;
import com.noah.taxiclient.Fragment.Frag_first_myCourse;
import com.noah.taxiclient.R;

/**
 * Created by Noah on 2017-09-26.
 */

public class Act_arrive extends AppCompatActivity {


    //뷰
    Act_Arrive_Adapter frag_adapter; //프래그먼트 어댑터
    ViewPager viewPager; //뷰페이저
    TabLayout tabLayout; //탭 레이아웃

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


        setContentView(R.layout.first_arrive);

        frag_adapter = new Act_Arrive_Adapter(getSupportFragmentManager());//프래그먼트 어댑터 생성
        viewPager = (ViewPager) findViewById(R.id.first_find_pager); //뷰페이저 설정
        viewPager.setAdapter(frag_adapter); //뷰페이저와 프래그먼트 연결
        tabLayout = (TabLayout) findViewById(R.id.first_find_tap); //탭레이아웃 생성
        tabLayout.setupWithViewPager(viewPager); //탭레이아웃과 뷰페이저 연결

    }

}



class Act_Arrive_Adapter extends FragmentPagerAdapter {//프래그먼트 어댑터 클래스 시작

    /*#####################################
    *               변수생성
    * #####################################*/
    int page_number=2;

    String firstT="장소 검색";
    String secondT="내 코스";


    /*#####################################
        *          프래그먼트 어댑터 생성자
        * #####################################*/
    public Act_Arrive_Adapter(FragmentManager fm) {
        super(fm);
    }


    /*#####################################
    *          프래그먼트 페이지 호출
    * #####################################*/
    @Override
    public Fragment getItem(int position) {//getItem start


        //페이지 호출
        switch (position){//페이지 호출 switch start

            case 0 :
                return Frag_first_find.newInstance();

            case 1 :
                return Frag_first_myCourse.newInstance();

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
    public CharSequence getPageTitle(int position) {//프래그먼트 제목 지정 메소드 시작

        switch (position){ //프래그먼트 제목 지정 switch 시작
            case 0 :
                return firstT;

            case 1 :
                return secondT;


        }//프래그먼트 제목 지정 switch 끝

        return null;//프래그먼트 제목 지정 메소드 리턴

    }//프래그먼트 제목 지정 메소드 끝




}//프래그먼트 어댑터 클래스 끝

