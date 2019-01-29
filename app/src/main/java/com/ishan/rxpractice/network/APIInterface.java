package com.ishan.rxpractice.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface APIInterface {
    @GET("/donuts")
    Observable<JsonArray> getDonutLovers();

    @GET("burger")
    Observable<JsonArray> getBurgerLovers();
}
