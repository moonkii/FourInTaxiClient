package com.noah.taxiclient.Public_Data;

import android.widget.Filter;

import java.util.ArrayList;

/**
 * Created by JinHa on 2017-09-22.
 */

public class FilterHelper extends Filter {
    // 검색 필터

    static ArrayList<String> currentList;
    static Fragment_Detail_Adapter adapter;


    public static FilterHelper newInstanse(ArrayList<String> currentList, Fragment_Detail_Adapter adapter){
        FilterHelper.adapter = adapter;
        FilterHelper.currentList = currentList;
        return new FilterHelper();
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults filterResults = new FilterResults();

        if(charSequence != null && charSequence.length() > 0){

            charSequence = charSequence.toString().toUpperCase();

            ArrayList<String> foundFilters = new ArrayList<>();
            String spacecraft;

            for(int i = 0; i<currentList.size(); i++){
                spacecraft = currentList.get(i);

                if(spacecraft.toUpperCase().contains(charSequence)){
                    foundFilters.add(spacecraft);
                }
            }
            filterResults.count = foundFilters.size();
            filterResults.values=foundFilters;
        }
        else{
            filterResults.count = currentList.size();
            filterResults.values = currentList;
        }

        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        adapter.setSpacecrafts((ArrayList<String>) filterResults.values);
        adapter.notifyDataSetChanged();
    }
}
