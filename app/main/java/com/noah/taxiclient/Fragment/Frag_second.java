package com.noah.taxiclient.Fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.noah.taxiclient.Public_Data.FoodActivity;
import com.noah.taxiclient.Public_Data.SightseeingActivity;
import com.noah.taxiclient.Public_Data.StayActivity;
import com.noah.taxiclient.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Frag_second extends Fragment {

    // 변수
    SearchView sv_search;
    //EditText edit_search;
    ImageView btn_search;

    Spinner spinner;
    Button btn_sightseeing, btn_stay, btn_food;
    // 사용자 언어 선택 정보
    int language_num = 0; // 사용자가 선택한 언어를 담고 있는 변수( 0 - korean / 1 - english / 2 - chinese / 3 - japanese / 4 - french )
    String area = "지역"; // 지역 선택
    int area_p = 0; // 0 - 지역


    SharedPreferences pref;
    SharedPreferences.Editor editor;

    /*#####################################
    *          프래그먼트 생성자
    * #####################################*/
    public Frag_second() {
        // Required empty public constructor
    }


    /*#####################################
    *          프래그먼트 객체
    * #####################################*/
    public static Frag_second newInstance() {
        Bundle args = new Bundle();

        Frag_second frag_second = new Frag_second();
        frag_second.setArguments(args);
        return frag_second;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);


        if(isVisibleToUser){
            if(getActivity()!=null){
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

            }
            }

        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View tab_public_data = inflater.inflate(R.layout.fragment_frag_second, container, false);
        sv_search = (SearchView) tab_public_data.findViewById(R.id.sv_search);
        spinner = (Spinner) tab_public_data.findViewById(R.id.spi_area);
        //btn_search = (ImageView) tab_public_data.findViewById(R.id.btn_search);
        btn_sightseeing = (Button) tab_public_data.findViewById(R.id.btn_sightseeing);
        btn_stay = (Button) tab_public_data.findViewById(R.id.btn_stay);
        btn_food = (Button) tab_public_data.findViewById(R.id.btn_food);

        SharedPreferences pref = getActivity().getSharedPreferences("lang",MODE_PRIVATE);
        language_num = pref.getInt("country",0);

        // 관광명소, 숙박, 음식점 탭 언어 설정
        if(language_num == 0){
            btn_sightseeing.setText(R.string.korean_sightseeing);
            btn_stay.setText(R.string.korean_stay);
            btn_food.setText(R.string.korean_food);
            ArrayAdapter<String> spinnerCountArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.city));
            spinner.setAdapter(spinnerCountArrayAdapter);
        }
        else if(language_num == 1){
            btn_sightseeing.setText(R.string.english_sightseeing);
            btn_stay.setText(R.string.english_stay);
            btn_food.setText(R.string.english_food_tab);
            ArrayAdapter<String> spinnerCountArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.city_e));
            spinner.setAdapter(spinnerCountArrayAdapter);
        }
        else if(language_num == 2){
            btn_sightseeing.setText(R.string.chinese_sightseeing);
            btn_stay.setText(R.string.chinese_stay);
            btn_food.setText(R.string.chinese_food_tab);
            ArrayAdapter<String> spinnerCountArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.city_c));
            spinner.setAdapter(spinnerCountArrayAdapter);
        }
        else if(language_num == 3){
            btn_sightseeing.setText(R.string.japanese_sightseeing);
            btn_stay.setText(R.string.japanese_stay);
            btn_food.setText(R.string.japanese_food);
            ArrayAdapter<String> spinnerCountArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.city_j));
            spinner.setAdapter(spinnerCountArrayAdapter);
        }
        else if(language_num == 4){
            btn_sightseeing.setText(R.string.french_sightseeing);
            btn_stay.setText(R.string.french_stay);
            btn_food.setText(R.string.french_food);
            ArrayAdapter<String> spinnerCountArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.city_f));
            spinner.setAdapter(spinnerCountArrayAdapter);
        }
        else { // 예외 발생 시 한국어로 설정
            btn_sightseeing.setText(R.string.korean_sightseeing);
            btn_stay.setText(R.string.korean_stay);
            btn_food.setText(R.string.korean_food);
            ArrayAdapter<String> spinnerCountArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.city));
            spinner.setAdapter(spinnerCountArrayAdapter);
        }

        /*btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Fragment_Detail.class);
                getActivity().startActivity(i);
            }
        });*/

        //스피너
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("position","position = "+position+parent.getItemAtPosition(position));
                area_p = position;
                area = String.valueOf(parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_sightseeing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!area.equals("지역") && !area.equals("Area") && !area.equals("地区") && !area.equals("地域") && !area.equals("Région")){
                    Intent si = new Intent(getActivity(), SightseeingActivity.class);
                    si.putExtra("area",area);
                    si.putExtra("area_p",area_p);
                    startActivity(si);
                }
                else{
                    ToastMessage(language_num);
                }
            }
        });

        btn_stay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!area.equals("지역") && !area.equals("Area") && !area.equals("地区") && !area.equals("地域") && !area.equals("Région")){
                    Intent st = new Intent(getActivity(), StayActivity.class);
                    st.putExtra("area",area);
                    st.putExtra("area_p",area_p);
                    startActivity(st);}
                else {
                    ToastMessage(language_num);
                }
            }
        });

        btn_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!area.equals("지역") && !area.equals("Area") && !area.equals("地区") && !area.equals("地域") && !area.equals("Région")){
                    Intent f = new Intent(getActivity(), FoodActivity.class);
                    f.putExtra("area", area);
                    f.putExtra("area_p",area_p);
                    startActivity(f);
                }else {
                    ToastMessage(language_num);
                }
            }
        });

        return tab_public_data;
    }
    public void ToastMessage(int language_num) {
        if (language_num == 0) {
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.korean_select), Toast.LENGTH_SHORT).show();
        }
        else if(language_num == 1){
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.english_select), Toast.LENGTH_SHORT).show();
        }
        else if(language_num == 2){
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.chinese_select), Toast.LENGTH_SHORT).show();
        }
        else if(language_num == 3){
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.japanese_select), Toast.LENGTH_SHORT).show();
        }
        else if(language_num == 4){
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.french_select), Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.korean_select), Toast.LENGTH_SHORT).show();
        }
    }
}
