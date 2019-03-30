package com.noah.taxiclient.Public_Data;

import com.noah.taxiclient.Activity.Act_Splash;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by JinHa on 2017-09-22.
 */

public class Search_list {
    // 검색 리스트

    Act_Splash act_splash;

    String name = "";
    int language_num = 0;
    // ( 0 - korean / 1 - english / 2 - chinese / 3 - japanese / 4 - french )

    ArrayList<String> data_li;
    //ArrayList<ArrayList<String>> data_li;
    JSONArray ja;

    /*public ArrayList<ArrayList<String>> Json() throws JSONException {
        data_li = new ArrayList();

        ArrayList<String> data_si = helper_Si.select(language_num);
        ArrayList<String> data_st = helper_St.select(language_num);
        ArrayList<String> data_f = helper_F.select(language_num);

        data_li.add(data_si);
        data_li.add(data_st);
        data_li.add(data_f);

        return data_li;
    }*/

    public ArrayList<String> Json() throws JSONException {
        data_li = new ArrayList();

        ArrayList<String> data_si = act_splash.helper_Si.select(language_num);
        ArrayList<String> data_st = act_splash.helper_St.select(language_num);
        ArrayList<String> data_f = act_splash.helper_F.select(language_num);

        /*data_li.add(data_si);
        data_li.add(data_st);
        data_li.add(data_f);
        */
        return data_li;
    }

}