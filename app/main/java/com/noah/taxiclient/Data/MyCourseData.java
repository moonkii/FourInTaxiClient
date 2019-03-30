package com.noah.taxiclient.Data;

import io.realm.RealmObject;

/**
 * Created by Noah on 2017-09-26.
 */

public class MyCourseData extends RealmObject {


    String content_id;
    String name;
    String local; //지역명
    String address; //주소
    double lat;
    double lng;
    String lang; //언어
    String type;
    String data;
    String koreaData;

    public String getKoreaData() {
        return koreaData;
    }

    public void setKoreaData(String koreaData) {
        this.koreaData = koreaData;
    }

    public String getContent_id() {
        return content_id;
    }

    public void setContent_id(String content_id) {
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

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getType(){
        return type;
    }
    public void setType(String type){
        this.type = type;
    }
    /*public void setData(String data){
        this.data = data;
    }
    public String getData(){
        return data;
    }*/

}
