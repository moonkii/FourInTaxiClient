package com.noah.taxiclient.Public_Data;

/**
 * Created by JinHa on 2017-09-15.
 */

public class Fragment_Detail_Item {
    String name,type, area, address,content_id;

    public void setName(String name){
        this.name = name;
    }
    public void setType(String type){
        this.type = type;
    }
    public void setArea(String area){
        this.area = area;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public void setContent_id(String content_id) {this.content_id = content_id;}


    public String getName(){
        return this.name;
    }
    public String getType(){
        return this.type;
    }
    public String getArea(){
        return this.area;
    }
    public String getAddress(){
        return this.address;
    }
    public String getContent_id() {return this.content_id;}

    public Fragment_Detail_Item(String content_id,String name, String type, String area, String address){
        this.content_id = content_id;
        this.name=name;
        this.type=type;
        this.area=area;
        this.address=address;
    }

    public Fragment_Detail_Item(String type){
        this.type = type;
    }
}
