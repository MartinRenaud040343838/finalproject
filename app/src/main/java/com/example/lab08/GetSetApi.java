package com.example.lab08;

import com.google.gson.annotations.SerializedName;

public class GetSetApi {


    //Variable names below match the JSON attribs on NASA website
    //If NASA website ever changes, we need to change this

    private String date;
    private String url;
    private String hdurl;


    public GetSetApi(String date, String url, String hdurl) {
        this.date = date;
        this.url = url;
        this.hdurl = hdurl;

    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public String getHDUrl() {
        return hdurl;
    }



}
