package com.noah.taxiclient.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.noah.taxiclient.Data.MyCourseData;
import com.noah.taxiclient.Public_Data.DetailActivity;
import com.noah.taxiclient.R;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Noah on 2017-09-26.
 */

public class Act_Mcourse extends Activity {


    ArrayList<Act_Mcourse_item> arrayList;
    Act_Mcourse_Adapter adapter;
    ListView listView;
    Realm realm;
    RealmResults<MyCourseData> data;

    TextView mycourse_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mycourse);

        //realm.init(this);
        realm = Realm.getDefaultInstance();

        arrayList = new ArrayList<>();

        data = realm.where(MyCourseData.class).findAll();
        int size = data.size();

        Log.e("MyCourse", String.valueOf(size));

        for(MyCourseData d : data){
            arrayList.add(new Act_Mcourse_item(d.getLang(),d.getName(),d.getLocal(),d.getAddress(),d.getType(),d.getContent_id(),d.getKoreaData()));
        }



        AudioManager am = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, 15, AudioManager.FLAG_SHOW_UI);



        mycourse_title = (TextView) findViewById(R.id.mycourse_title);
        SharedPreferences pref = getSharedPreferences("lang",MODE_PRIVATE);
        int language_num = pref.getInt("country",0);

        if(language_num == 0){
           mycourse_title.setText(R.string.korea_mycource);
        }
        else if(language_num == 1){
            mycourse_title.setText(R.string.english_mycource);
        }
        else if(language_num == 2){
            mycourse_title.setText(R.string.chinese_mycource);
        }
        else if(language_num == 3){
            mycourse_title.setText(R.string.japanese_mycource);
        }
        else if(language_num == 4){
            mycourse_title.setText(R.string.french_mycource);
        }

        listView = (ListView) findViewById(R.id.course_list);
        adapter = new Act_Mcourse_Adapter(Act_Mcourse.this,R.layout.act_mycourse_item,arrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Act_Mcourse_item data = arrayList.get(position);
                Log.e("MyCourse",data.getLang() +"/" + data.getName() + "/" + data.getLocal() +"/"+ data.getAddress()+"/"+data.getType());

                SharedPreferences share = getSharedPreferences("set", MODE_PRIVATE);
                SharedPreferences.Editor editor = share.edit();
                editor.putBoolean("my_course",true); // 내 코스에서 상세 내용을 확인했는가?  맞을 때 true / 아닐 때 false
                editor.apply();

                Intent i = new Intent(Act_Mcourse.this, DetailActivity.class);
                i.putExtra("content_id", data.getContent_id());
                i.putExtra("name", data.getName());
                i.putExtra("type", data.getType());
                i.putExtra("area", data.getLocal());
                i.putExtra("address", data.getAddress());
                startActivity(i);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Act_Mcourse_item data_i = arrayList.get(position);
                Log.e("MyCourse",data_i.getLang() +"/" + data_i.getName() + "/" + data_i.getLocal() +"/"+ data_i.getAddress()+"/"+data_i.getType());

                String language = "", ok = "", cancel = "";

                if(data_i.getLang().equals("korean")){
                    language = getResources().getString(R.string.korean_delete_msg);
                    ok = getResources().getString(R.string.korea_ok);
                    cancel = getResources().getString(R.string.korea_cancel);
                }
                else if(data_i.getLang().equals("english")){
                    language = getResources().getString(R.string.english_delete_msg);
                    ok = getResources().getString(R.string.english_ok);
                    cancel = getResources().getString(R.string.english_cancel);
                }
                else if(data_i.getLang().equals("chinese")){
                    language = getResources().getString(R.string.chinese_delete_msg);
                    ok = getResources().getString(R.string.chinese_ok);
                    cancel = getResources().getString(R.string.chinese_cancel);
                }
                else if(data_i.getLang().equals("japanese")){
                    language = getResources().getString(R.string.japanese_delete_msg);
                    ok = getResources().getString(R.string.japanese_ok);
                    cancel = getResources().getString(R.string.japanese_cancel);
                }
                else if(data_i.getLang().equals("french")){
                    language = getResources().getString(R.string.french_delete_msg);
                    ok = getResources().getString(R.string.french_ok);
                    cancel = getResources().getString(R.string.french_cancel);
                }

                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Act_Mcourse.this);
                alertDialogBuilder.setMessage(language);
                alertDialogBuilder.setPositiveButton(ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                arrayList.remove(position);
                                adapter.notifyDataSetChanged();
                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        MyCourseData myCourseData = data.get(position);
                                        myCourseData.deleteFromRealm();
                                    }
                                });

                                Toast.makeText(Act_Mcourse.this,getResources().getString(R.string.korean_delete),Toast.LENGTH_SHORT).show();
                            }
                        });

                alertDialogBuilder.setNegativeButton(cancel
                        ,new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return true;
            }
        });
    }
}



class Act_Mcourse_item{

    private String name;
    private String local;
    private String address;
    private String lang; //언어
    private String data;
    private String type;
    private String content_id;
    private String korea;

    public String getKorea() {
        return korea;
    }

    public void setKorea(String korea) {
        this.korea = korea;
    }

    public Act_Mcourse_item(String lang, String name, String local, String address, String type, String content_id,String k) {
        this.name = name;
        this.local = local;
        this.address = address;
        this.lang = lang;
        this.type = type;
        this.content_id = content_id;
        this.korea=k;
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

    public String getLang(){
        return lang;
    }
    public void setLang(String lang){
        this.lang = lang;
    }

    public String getType(){
        return type;
    }
    public void setType(String type){
        this.type = type;
    }

    public void setContent_id(String content_id){
        this.content_id = content_id;
    }
    public String getContent_id(){
        return content_id;
    }

    /*public String getData(){
        return data;
    }
    public void setData(String data){
        this.data = data;
    }*/
}

class Act_Mcourse_Adapter extends ArrayAdapter<Act_Mcourse_item> {

    ArrayList<Act_Mcourse_item> arraylist;
    Context c;
    TextToSpeech tts;

    public Act_Mcourse_Adapter(Context context, int resource,ArrayList<Act_Mcourse_item> arraylist) {
        super(context, resource);
        this.arraylist = arraylist;
        this.c=context;

    }

    @Override
    public int getCount() {
        return this.arraylist.size();
    }

    @Nullable
    @Override
    public Act_Mcourse_item getItem(int position) {
        return this.arraylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v =convertView;


        if(v==null){
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=inflater.inflate(R.layout.act_mycourse_item,parent,false);
        }

        TextView name = (TextView) v.findViewById(R.id.mycourse_name);
        TextView local = (TextView) v.findViewById(R.id.mycourse_local);
        TextView add = (TextView) v.findViewById(R.id.mycourse_address);
        ImageView speaker = (ImageView) v.findViewById(R.id.btn_speaker);

        name.setText(arraylist.get(position).getName());
        local.setText(arraylist.get(position).getLocal());
        add.setText(arraylist.get(position).getAddress());

        speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tts = new TextToSpeech(c, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        tts.speak(arraylist.get(position).getKorea()+"으로 가주세요.",TextToSpeech.QUEUE_FLUSH,null);

                    }
                });

            }
        });


        return v;

    }


}
