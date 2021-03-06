package com.noah.taxiclient.utills;

/**
 * Created by YH on 2017-09-11.
 */

public class Item_signup {

    private String id;
    private String pw;
    private String token;
    private String phone;
    private String name;

    private String mode ;
    private String flag = "signup";

    public Item_signup(String id, String pw, String token, String phone, String name,String mode) {
        this.id = id;
        this.pw = pw;
        this.token = token;
        this.phone = phone;
        this.name = name;
        this.mode = mode;
    }

    public Item_signup() {

    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getId() {
        return id;

    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
       return pw;
     }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
