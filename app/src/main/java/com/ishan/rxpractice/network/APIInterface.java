package com.ishan.rxpractice.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface APIInterface {
    @GET("/donuts")
    Observable<JsonArray> getDonutLovers();

    @GET("/burger")
    Observable<JsonArray> getBurgerLovers();

    @GET("/user")
    Single<JsonObject> getUser();

    @GET("/donuts")
    Maybe<JsonArray> getDonutLoversMaybe();

    @GET("/user")
    Completable getUserCompletion();
}
