package com.noah.taxiclient.Fragment;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.noah.taxiclient.Data.MyCourseData;
import com.noah.taxiclient.R;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Noah on 2017-09-29.
 */

public class Frag_first_myCourse extends Fragment {

    ArrayList<Frag_first_myItem> arrayList;
    Frag_first_myAdapter adapter;
    ListView listView;
    Realm realm;
    RealmResults<MyCourseData> data;


    /*#####################################
    *          프래그먼트 객체
    * #####################################*/
    public static Frag_first_myCourse newInstance() {
        Bundle args = new Bundle();

        Frag_first_myCourse frag_first = new Frag_first_myCourse();
        frag_first.setArguments(args);
        return frag_first;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_first_my, container, false);

        realm =Realm.getDefaultInstance();
        arrayList = new ArrayList<>();
        data=realm.where(MyCourseData.class).findAll();

        for(MyCourseData d : data){
            arrayList.add(new Frag_first_myItem(d.getName(),d.getLocal(),d.getAddress()));
        }

        listView = (ListView) v.findViewById(R.id.frag_first_my_list);
        adapter = new Frag_first_myAdapter(getActivity(),R.layout.act_mycourse_item,arrayList);
        listView.setAdapter(adapter);

        return v;
    }


    @Override
    public void onStart() {
        super.onStart();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String name = arrayList.get(position).getName();
                String address= arrayList.get(position).getAddress();

                Intent intent = new Intent();
                intent.putExtra("name",name);
                intent.putExtra("address",address);
                getActivity().setResult(0,intent);
                getActivity().finish();

            }
        });
    }
}





class Frag_first_myItem{

    private String name;
    private String local;
    private String address;

    public Frag_first_myItem(String name, String local, String address) {
        this.name = name;
        this.local = local;
        this.address = address;
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
}

class Frag_first_myAdapter extends ArrayAdapter<Frag_first_myItem> {

    ArrayList<Frag_first_myItem> arraylist;
    Context c;

    public Frag_first_myAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<Frag_first_myItem> arraylist) {
        super(context, resource);
        this.arraylist = arraylist;
        this.c=context;
    }

    @Override
    public int getCount() {
        return arraylist.size();

    }

    @Nullable
    @Override
    public Frag_first_myItem getItem(int position) {
        return arraylist.get(position);
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
