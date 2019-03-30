package com.noah.taxiclient.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.noah.taxiclient.R;

import java.util.ArrayList;

/**
 * Created by Noah on 2017-09-15.
 */

public class Act_Mrecord extends Activity {

    ListView recordListView;
    Act_recordAdapter adapter;
    ArrayList<Act_recorditem> dataArray;

    TextView record_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_record);

        record_title = (TextView) findViewById(R.id.record_title);
        SharedPreferences pref = getSharedPreferences("lang",MODE_PRIVATE);
        int language_num = pref.getInt("country",0);

        if(language_num == 0){
            record_title.setText(R.string.korea_recordTitle);
        }
        else if(language_num == 1){
            record_title.setText(R.string.english_recordTitle);
        }
        else if(language_num == 2){
            record_title.setText(R.string.chinese_recordTitle);
        }
        else if(language_num == 3){
            record_title.setText(R.string.japanese_recordTitle);
        }
        else if(language_num == 4){
            record_title.setText(R.string.french_recordTitle);
        }

        dataArray = new ArrayList<>();
        dataArray.add(new Act_recorditem("2017. 09. 17","출발 13:09 경기도 감자밭","도착 15:10 경기도 호텔","테스트 기사님","경기 33 바 1234 | 소나타","01012345678"));
        recordListView = (ListView) findViewById(R.id.record_list);
        adapter = new Act_recordAdapter(this,R.layout.act_record_item,dataArray);
        recordListView.setAdapter(adapter);

    }



}


class Act_recorditem{

    String date;
    String start;
    String end;
    String driverName;
    String carNumAndCategory;
    String callnum;

    public Act_recorditem(String date, String start, String end, String driverName, String carNumAndCategory, String callnum) {
        this.date = date;
        this.start = start;
        this.end = end;
        this.driverName = driverName;
        this.carNumAndCategory = carNumAndCategory;
        this.callnum = callnum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getCarNumAndCategory() {
        return carNumAndCategory;
    }

    public void setCarNumAndCategory(String carNumAndCategory) {
        this.carNumAndCategory = carNumAndCategory;
    }

    public String getCallnum() {
        return callnum;
    }

    public void setCallnum(String callnum) {
        this.callnum = callnum;
    }
}


class Act_recordAdapter extends ArrayAdapter<Act_recorditem> {


    ArrayList<Act_recorditem> arraylist;
    Context c;


    public Act_recordAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Act_recorditem> objects) {
        super(context, resource, objects);
        this.c=context;
        this.arraylist=objects;
    }

    @Override
    public int getCount() {
        return arraylist.size() ;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView==null){
            //인플레이터 생성
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //인플레이터로 리스트뷰를 실제 객체로 만들어줌.
            convertView = inflater.inflate(R.layout.act_record_item,parent,false);
        }

        TextView date= (TextView) convertView.findViewById(R.id.record_item_date);
        TextView start= (TextView) convertView.findViewById(R.id.record_item_start);
        TextView end = (TextView) convertView.findViewById(R.id.record_item_end);
        TextView driverName = (TextView) convertView.findViewById(R.id.record_driverName);
        TextView carinfo= (TextView) convertView.findViewById(R.id.record_carnum);
        ImageView call =(ImageView) convertView.findViewById(R.id.record_call);


        date.setText(arraylist.get(position).getDate());
        start.setText(arraylist.get(position).getStart());
        end.setText(arraylist.get(position).getEnd());
        driverName.setText(arraylist.get(position).getDriverName());
        carinfo.setText(arraylist.get(position).getCarNumAndCategory());

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c, position+"번째전화", Toast.LENGTH_SHORT).show();
            }
        });


        return convertView;
    }


}