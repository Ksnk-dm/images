package com.ksnk.imageukr.api;

import com.ksnk.imageukr.data.Image;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiInterface {

    @GET("images.ksnk")
    Call <List<Image>> getDataFromAPI();

}
