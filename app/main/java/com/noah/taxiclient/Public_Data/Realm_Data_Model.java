package com.noah.taxiclient.Public_Data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by JinHa on 2017-09-24.
 */

public class Realm_Data_Model extends RealmObject {
    @PrimaryKey
    private String content_id;
    private String type;
    private String classification;
    private String area;
    private String korean;
    private String english;
    private String chinese;
    private String japanese;
    private String french;

    public String getContent_id() {
        return content_id;
    }
    public void setContent_id(String content_id){
        this.content_id = content_id;
    }


    public String getType(){
        return type;
    }
    public void setType(String type){
        this.type = type;
    }


    public String getClassification(){
        return classification;
    }
    public void setClassification(String classification){
        this.classification = classification;
    }


    public String getArea(){
        return area;
    }
    public void setArea(String area){
        this.area= area;
    }


    public String getKorean(){
        return korean;
    }
    public void setKorean(String korean){
        this.korean = korean;
    }


    public String getEnglish(){
        return english;
    }
    public void setEnglish(String english){
        this.english = english;
    }


    public String getChinese(){
        return chinese;
    }
    public void setChinese(String chinese){
        this.chinese = chinese;
    }


    public String getJapanese(){
        return japanese;
    }
    public void setJapanese(String japanese){
        this.japanese = japanese;
    }


    public String getFrench(){
        return french;
    }
    public void setFrench(String french){
        this.french = french;
    }
}
