package com.noah.taxiclient.Public_Data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.noah.taxiclient.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by JinHa on 2017-09-15.
 */

public class Fragment_Detail_Adapter extends RecyclerView.Adapter<Fragment_Detail_Adapter.ItemViewHolder> implements Filterable{

    Context context;
    // ArrayList<Fragment_Detail_Item> mDataset;
    ArrayList<Realm_Data_Model> mDataset;
    // 리스트 검색
    ArrayList<String> spacecrafts;
    ArrayList<String> currentList;

    int language_num = 0;
    String type,area;
    int area_p = 0;

    TextToSpeech tts;

    String language, name = "", address ="",type_s="", area_s="";

    static HashMap<Integer,String> ttsHash; //key: position , value: checkbox boolean

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView cardView;
        public TextView mNameView,mTypeView,mAreaView,mAddressView;
        public ImageView btn_speaker;



        public ItemViewHolder(View view) {
            super(view);

            cardView = (CardView) view.findViewById(R.id.cardView);
            mNameView = (TextView)view.findViewById(R.id.tv_name);
            mTypeView = (TextView)view.findViewById(R.id.tv_type);
            mAreaView = (TextView)view.findViewById(R.id.tv_area);
            mAddressView = (TextView)view.findViewById(R.id.tv_address);
            btn_speaker = (ImageView) view.findViewById(R.id.btn_speaker);
            btn_speaker.setTag(this);


        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    //public Fragment_Detail_Adapter(Context context,ArrayList<Fragment_Detail_Item> myDataset, ArrayList<String> nameDataset) {
    public Fragment_Detail_Adapter(Context context,ArrayList<Realm_Data_Model> myDataset, int language_num, int area_p, String area){ // 언어별, 관광 / 숙박 / 음식, 지역별
        this.context = context;
        mDataset = myDataset;
        this.language_num = language_num;
        this.area_p = area_p;
        this.area = area;
        ttsHash = new HashMap<>();

        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, 15, AudioManager.FLAG_SHOW_UI);

        for(int i =0; i<myDataset.size() ; i++){

            String n="";
            try {
                JSONObject jo = new JSONObject(myDataset.get(i).getKorean());
                n = jo.getString("subject");
                ttsHash.put(i,n);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public Fragment_Detail_Adapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_frag_detail, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ItemViewHolder vh = new ItemViewHolder(v);
        return vh;


    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        final Realm_Data_Model item = mDataset.get(position);


        type = item.getType();

        if(language_num == 0){

            if(type.equals("관광")) {
                type_s=context.getResources().getString(R.string.korean_sightseeing);
            }
            else if(type.equals("숙박")){
                type_s=context.getResources().getString(R.string.korean_stay);
            }
            else if(type.equals("음식")){
                type_s=context.getResources().getString(R.string.korean_food);
            }

            try {
                JSONObject jo = new JSONObject(item.getKorean());
                address = jo.getString("address");
                name = jo.getString("subject");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if(language_num == 1){

            if(type.equals("관광")) {
                type_s=context.getResources().getString(R.string.english_sightseeing);
            }
            else if(type.equals("숙박")){
                type_s=context.getResources().getString(R.string.english_stay);
            }
            else if(type.equals("음식")){
                type_s=context.getResources().getString(R.string.english_food);
            }

            try {
                JSONObject jo = new JSONObject(item.getEnglish());
                address = jo.getString("address");
                name = jo.getString("subject");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if(language_num == 2){

            if(type.equals("관광")) {
                type_s=context.getResources().getString(R.string.chinese_sightseeing);
            }
            else if(type.equals("숙박")){
                type_s=context.getResources().getString(R.string.chinese_stay);
            }
            else if(type.equals("음식")){
                type_s=context.getResources().getString(R.string.chinese_food);
            }

            try {
                JSONObject jo = new JSONObject(item.getChinese());
                address = jo.getString("address");
                name = jo.getString("subject");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if(language_num == 3){

            if(type.equals("관광")) {
                type_s=context.getResources().getString(R.string.japanese_sightseeing);
            }
            else if(type.equals("숙박")){
                type_s=context.getResources().getString(R.string.japanese_stay);
            }
            else if(type.equals("음식")){
                type_s=context.getResources().getString(R.string.japanese_food);
            }

            try {
                JSONObject jo = new JSONObject(item.getJapanese());
                address = jo.getString("address");
                name = jo.getString("subject");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if(language_num == 4){

            if(type.equals("관광")) {
                type_s=context.getResources().getString(R.string.french_sightseeing);
            }
            else if(type.equals("숙박")){
                type_s=context.getResources().getString(R.string.french_stay);
            }
            else if(type.equals("음식")){
                type_s=context.getResources().getString(R.string.french_food);
            }

            try {
                JSONObject jo = new JSONObject(item.getFrench());
                address = jo.getString("address");
                name = jo.getString("subject");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {

            if(type.equals("관광")) {
                type_s=context.getResources().getString(R.string.korean_sightseeing);
            }
            else if(type.equals("숙박")){
                type_s=context.getResources().getString(R.string.korean_stay);
            }
            else if(type.equals("음식")){
                type_s=context.getResources().getString(R.string.korean_food);
            }

            try {
                JSONObject jo = new JSONObject(item.getKorean());
                address = jo.getString("address");
                name = jo.getString("subject");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        holder.mNameView.setText(name);
        holder.mTypeView.setText(type_s);
        holder.mAreaView.setText(area);
        holder.mAddressView.setText(address);

        if(ttsHash.containsKey(position)){
            holder.btn_speaker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            tts.speak(ttsHash.get(position)+"으로 가주세요",TextToSpeech.QUEUE_FLUSH,null);
                        }
                    });
                }
            });
        }




        final String finalName = name;
        final String finalAddress = address;
        final String finalType_s = type_s;

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, finalName, Toast.LENGTH_SHORT).show();

                SharedPreferences share = context.getSharedPreferences("set", MODE_PRIVATE);
                SharedPreferences.Editor editor = share.edit();
                editor.putBoolean("my_course",false); // 내 코스에서 상세 내용을 확인했는가?  맞을 때 true / 아닐 때 false
                editor.apply();

                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("content_id", item.getContent_id());
                i.putExtra("name", finalName);
                i.putExtra("type", finalType_s);
                i.putExtra("area", area);
                i.putExtra("address", finalAddress);
                i.putExtra("korea",ttsHash.get(position));
                context.startActivity(i);
            }
        });
    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    //-----------------------------------------------------------------
    // 리스트 검색 기능
    @Override
    public Filter getFilter() {
        return FilterHelper.newInstanse(currentList,this);
    }

    public void setSpacecrafts(ArrayList<String> filteredSpacecrafts){
        this.spacecrafts = filteredSpacecrafts;
    }

    //-----------------------------------------------------------------
    // 리스트 페이징 기능에 사용 될 예정
    public void setProgressMore(final boolean isProgress){
        if(isProgress){
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    mDataset.add(null);
                    notifyItemInserted(mDataset.size() - 1);
                }
            });
        } else {
            mDataset.remove(mDataset.size() - 1);
            notifyItemRemoved(mDataset.size());
        }
    }


    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        public ProgressViewHolder(View v){
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        }
    }

    public void addAll(List<Realm_Data_Model> lst){
        mDataset.clear();
        mDataset.addAll(lst);
        notifyDataSetChanged();
    }

    public void addItemMore(List<Realm_Data_Model> lst){
        mDataset.addAll(lst);
        notifyItemRangeChanged(0,mDataset.size());
    }
    // -----------------------------------------------------------------
}
