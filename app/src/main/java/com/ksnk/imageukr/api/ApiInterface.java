package com.ksnk.imageukr.api;

import com.ksnk.imageukr.data.Image;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiInterface {

    @GET("callories-59b6b-default-rtdb-export.json")
    Call <List<Image>> getDataFromAPI();

}
