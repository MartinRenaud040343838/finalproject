package com.example.lab08;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    String BASE_URL = "https://api.nasa.gov/planetary/";



    @GET("apod?api_key=jeADHwHp60mEX1frcE913FAnV5c2FyGrA3Hmcd95&date=2021-11-11")
    Call<List<GetSetApi>> getNasaImages();


}
