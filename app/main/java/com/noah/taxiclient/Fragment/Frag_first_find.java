package com.noah.taxiclient.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.noah.taxiclient.Public_Data.Realm_Data_Model;
import com.noah.taxiclient.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

import static android.R.attr.name;
import static android.R.attr.theme;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Noah on 2017-09-29.
 */

public class Frag_first_find extends Fragment {

    Button btn_sight;
    Button btn_stay;
    Button btn_food;
    ListView listView;
    Spinner spinner;
    Frag_first_find_Adapter adapter;
    ArrayList<Frag_first_findItem> arrayList;

    int langNum = 0;
    String language;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    String area = "", area_s = "";
    int area_p = 0; // 0 - 지역

    RealmResults<Realm_Data_Model> results;
    Realm realm;

    /*#####################################
    *          프래그먼트 객체
    * #####################################*/
    public static Frag_first_find newInstance() {
        Bundle args = new Bundle();

        Frag_first_find frag_first = new Frag_first_find();
        frag_first.setArguments(args);
        return frag_first;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_first_find, container, false);
        pref = getActivity().getSharedPreferences("lang", MODE_PRIVATE);
        langNum = pref.getInt("country", 0);

        realm = Realm.getDefaultInstance();

        btn_sight = (Button) v.findViewById(R.id.first_find_btn_sightseeing);
        btn_stay = (Button) v.findViewById(R.id.first_find_btn_stay);
        btn_food = (Button) v.findViewById(R.id.first_find_btn_food);

        listView = (ListView) v.findViewById(R.id.first_find_listView);
        spinner = (Spinner) v.findViewById(R.id.first_find_spi_area);


        // 관광명소, 숙박, 음식점 탭 언어 설정
        if (langNum == 0) {
            btn_sight.setText(R.string.korean_sightseeing);
            btn_stay.setText(R.string.korean_stay);
            btn_food.setText(R.string.korean_food);
            ArrayAdapter<String> spinnerCountArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.city));
            spinner.setAdapter(spinnerCountArrayAdapter);
            language = "korean";
        } else if (langNum == 1) {
            btn_sight.setText(R.string.english_sightseeing);
            btn_stay.setText(R.string.english_stay);
            btn_food.setText(R.string.english_food_tab);
            ArrayAdapter<String> spinnerCountArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.city_e));
            spinner.setAdapter(spinnerCountArrayAdapter);
            language = "english";
        } else if (langNum == 2) {
            btn_sight.setText(R.string.chinese_sightseeing);
            btn_stay.setText(R.string.chinese_stay);
            btn_food.setText(R.string.chinese_food_tab);
            ArrayAdapter<String> spinnerCountArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.city_c));
            spinner.setAdapter(spinnerCountArrayAdapter);
            language = "chinese";
        } else if (langNum == 3) {
            btn_sight.setText(R.string.japanese_sightseeing);
            btn_stay.setText(R.string.japanese_stay);
            btn_food.setText(R.string.japanese_food);
            ArrayAdapter<String> spinnerCountArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.city_j));
            spinner.setAdapter(spinnerCountArrayAdapter);
            language = "japanese";
        } else if (langNum == 4) {
            btn_sight.setText(R.string.french_sightseeing);
            btn_stay.setText(R.string.french_stay);
            btn_food.setText(R.string.french_food);
            ArrayAdapter<String> spinnerCountArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.city_f));
            spinner.setAdapter(spinnerCountArrayAdapter);
            language = "french";
        } else { // 예외 발생 시 한국어로 설정
            btn_sight.setText(R.string.korean_sightseeing);
            btn_stay.setText(R.string.korean_stay);
            btn_food.setText(R.string.korean_food);
            ArrayAdapter<String> spinnerCountArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.city));
            spinner.setAdapter(spinnerCountArrayAdapter);
            language = "korean";
        }


        return v;
    }


    @Override
    public void onStart() {
        super.onStart();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                area_p = position;
                area = String.valueOf(parent.getItemAtPosition(position));

                if (area_p == 1) {
                    area_s = "강원도";
                } else if (area_p == 2) {
                    area_s = "춘천시";
                } else if (area_p == 3) {
                    area_s = "강릉시";
                } else if (area_p == 4) {
                    area_s = "원주시";
                } else if (area_p == 5) {
                    area_s = "동해시";
                } else if (area_p == 6) {
                    area_s = "태백시";
                } else if (area_p == 7) {
                    area_s = "속초시";
                } else if (area_p == 8) {
                    area_s = "삼척시";
                } else if (area_p == 9) {
                    area_s = "홍천군";
                } else if (area_p == 10) {
                    area_s = "횡성군";
                } else if (area_p == 11) {
                    area_s = "영월군";
                } else if (area_p == 12) {
                    area_s = "평창군";
                } else if (area_p == 13) {
                    area_s = "정선군";
                } else if (area_p == 14) {
                    area_s = "철원군";
                } else if (area_p == 15) {
                    area_s = "화천군";
                } else if (area_p == 16) {
                    area_s = "양구군";
                } else if (area_p == 17) {
                    area_s = "인제군";
                } else if (area_p == 18) {
                    area_s = "고성군";
                } else if (area_p == 19) {
                    area_s = "양양군";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_sight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (area_p != 0) {
                    results = realm.where(Realm_Data_Model.class)
                            .distinct(language) //언어별 컬럼
                            .where()
                            .equalTo("type", "관광") // 관광
                            .findAll();
                    showList();
                } else {
                    ToastMessage(langNum);
                }


            }
        });


        btn_stay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area_p != 0) {
                    results = realm.where(Realm_Data_Model.class)
                            .distinct(language) //언어별 컬럼
                            .where()
                            .equalTo("type", "숙박") // 숙박
                            .findAll();
                    showList();
                } else {
                    ToastMessage(langNum);
                }
            }
        });

        btn_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area_p != 0) {
                    results = realm.where(Realm_Data_Model.class)
                            .distinct(language) //언어별 컬럼
                            .where()
                            .equalTo("type", "음식") // 음식
                            .findAll();

                    showList();
                } else {
                    ToastMessage(langNum);
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String name = arrayList.get(position).getName();
                String address= arrayList.get(position).getAddress();
                String content_id = arrayList.get(position).getContent_id();

                Intent intent = new Intent();
                intent.putExtra("name",name);
                intent.putExtra("address",address);
                intent.putExtra("content_id",content_id);
                getActivity().setResult(0,intent);
                getActivity().finish();
            }
        });

    }

    private void ToastMessage(int language_num) {
        if (language_num == 0) {
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.korean_select), Toast.LENGTH_SHORT).show();
        } else if (language_num == 1) {
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.english_select), Toast.LENGTH_SHORT).show();
        } else if (language_num == 2) {
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.chinese_select), Toast.LENGTH_SHORT).show();
        } else if (language_num == 3) {
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.japanese_select), Toast.LENGTH_SHORT).show();
        } else if (language_num == 4) {
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.french_select), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.korean_select), Toast.LENGTH_SHORT).show();
        }
    }

    private void showList(){
        arrayList = new ArrayList<>();


        for(Realm_Data_Model data : results){
            switch (langNum){
                case 0 :
                    try {
                        JSONObject jo = new JSONObject(data.getKorean());
                        String address = jo.getString("address");
                        String name = jo.getString("subject");
                        String content_id = jo.getString("control");
                        setDataToArray(name,address,content_id);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 1 :
                    try {
                        JSONObject jo = new JSONObject(data.getEnglish());
                        String address = jo.getString("address");
                        String name = jo.getString("subject");
                        String content_id = jo.getString("control");
                        setDataToArray(name,address,content_id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2 :
                    try {
                        JSONObject jo = new JSONObject(data.getChinese());
                        String address = jo.getString("address");
                        String name = jo.getString("subject");
                        String content_id = jo.getString("control");
                        setDataToArray(name,address,content_id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3 :
                    try {
                        JSONObject jo = new JSONObject(data.getJapanese());
                        String address = jo.getString("address");
                        String name = jo.getString("subject");
                        String content_id = jo.getString("control");
                        setDataToArray(name,address,content_id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 4 :
                    try {
                        JSONObject jo = new JSONObject(data.getFrench());
                        String address = jo.getString("address");
                        String name = jo.getString("subject");
                        String content_id = jo.getString("control");
                        setDataToArray(name,address,content_id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

            }
        }



        adapter = new Frag_first_find_Adapter(getActivity(),R.layout.act_mycourse_item,arrayList);
        listView.setAdapter(adapter);
    }

    private void setDataToArray(String name, String address,String content_id){
        arrayList.add(new Frag_first_findItem(name,area,address, content_id));
    }

}






class Frag_first_findItem{

    private String name;
    private String local;
    private String address;
    private String content_id;

    public Frag_first_findItem(String name, String local, String address, String content_id) {
        this.name = name;
        this.local = local;
        this.address = address;
        this.content_id = content_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContent_id(){
        return content_id;
    }
    public void setContent_id(String content_id){
        this.content_id = content_id;
    }
}




class Frag_first_find_Adapter extends ArrayAdapter<Frag_first_findItem> {

    ArrayList<Frag_first_findItem> arraylist;
    Context c;

    public Frag_first_find_Adapter(@NonNull Context context, @LayoutRes int resource, ArrayList<Frag_first_findItem> arraylist) {
        super(context, resource);
        this.arraylist = arraylist;
        this.c=context;
    }

    @Override
    public int getCount() {
        return arraylist.size();

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v =convertView;


        if(v==null){
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=inflater.inflate(R.layout.act_mycourse_item,parent,false);
        }

        ImageView speaker = (ImageView) v.findViewById(R.id.btn_speaker);
        TextView name = (TextView) v.findViewById(R.id.mycourse_name);
        TextView local = (TextView) v.findViewById(R.id.mycourse_local);
        TextView add = (TextView) v.findViewById(R.id.mycourse_address);

        if(speaker.getVisibility()==View.VISIBLE){
            speaker.setVisibility(View.GONE);
        }

        name.setText(arraylist.get(position).getName());
        local.setText(arraylist.get(position).getLocal());
        add.setText(arraylist.get(position).getAddress());




        return v;

    }
}


