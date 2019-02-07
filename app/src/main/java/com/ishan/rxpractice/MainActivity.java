package com.ishan.rxpractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ishan.rxpractice.network.APIClient;
import com.ishan.rxpractice.network.APIInterface;

import io.reactivex.CompletableObserver;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    APIInterface apiInterface;
    Single<String> singleValue = Single.just("Ishan");
    Maybe<String> maybeValue = Maybe.just("Ishan");
    Maybe<String> maybeEmptyValue = Maybe.empty();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        maybeCheck();
    }

    //Count Operation
    public void checkCount(){
        apiInterface.getDonutLovers()
                .count()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(Long count) {
                        Log.d(TAG,"Count "+count);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }

    //Single Observer check without API Call
    public void singelObserverCheck(){
                singleValue
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        Log.d(TAG,s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }

    //Get Single User
    public void getUser(){
        apiInterface.getUser()
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<JsonObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(JsonObject jsonObject) {
                        Log.d(TAG, "Get User: "+ new Gson().toJson(jsonObject));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }

    //Observer
    public void getDonutLovers(){
        apiInterface.getDonutLovers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonArray>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(JsonArray jsonElements) {
                        Log.d(TAG,"Donut List "+new Gson().toJson(jsonElements));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //Check Maybe. This support either emit or not emit
    public void maybeCheck(){

        maybeValue.subscribe(new MaybeObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String s) {
                Log.d(TAG, "Maybe: Success"+s);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                //call on empty directly
                Log.d(TAG, "Maybe: complete");
            }
        });
    }

    //Completable observer will not provide any values. It Just they whether it completed or Error
    public void getCompletion(){
        apiInterface.getUserCompletion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "Just completed");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    //Filter and the Flat map 
    public void checkFilterAndFlatMap(){
        apiInterface.getDonutLovers()
                .flatMap(new Function<JsonArray, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<JsonElement> apply(JsonArray jsonElements) throws Exception {
                        return Observable.fromIterable(jsonElements);
                    }
                })
                .filter(new Predicate<Object>() {
                    @Override
                    public boolean test(Object o) throws Exception {
                        Log.d(TAG, "test: "+((JsonObject)o).get("name").getAsString().equals("ishan"));
                        return ((JsonObject)o).get("name").getAsString().equals("ishan");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Log.d(TAG, "accept: "+((JsonObject)o).get("name"));
                    }
                });


    }
}
