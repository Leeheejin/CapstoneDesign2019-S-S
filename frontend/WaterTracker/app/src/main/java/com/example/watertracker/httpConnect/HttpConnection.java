package com.example.watertracker.httpConnect;

import com.example.watertracker.domain.Account;
import com.example.watertracker.domain.Cup;
import com.example.watertracker.domain.Water;
import com.google.gson.Gson;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpConnection {

    private final String url = "http://192.168.43.157:8080";
    private OkHttpClient client;
    private static HttpConnection instance = new HttpConnection();
    public static HttpConnection getInstance() {
        return instance;
    }

    private HttpConnection(){ this.client = new OkHttpClient(); }


    /** 웹 서버로 요청을 한다. */
    public void getUserInfo(Account account, Callback callback) {

        Gson gson = new Gson();
        String json = gson.toJson(account);

        Request request = new Request.Builder()
                .url(url+"/userinfo")
                .post(RequestBody.create(MediaType.parse("application/json"), json))
                .build();

        client.newCall(request).enqueue(callback);
    }

    public void confirm(Account account, Callback callback) {

        Gson gson = new Gson();
        String json = gson.toJson(account);

        Request request = new Request.Builder()
                .url(url+"/confirm")
                .post(RequestBody.create(MediaType.parse("application/json"), json))
                .build();

        client.newCall(request).enqueue(callback);
    }

    public void saveCup(Cup cup, Callback callback) {

        Gson gson = new Gson();
        String json = gson.toJson(cup);

        Request request = new Request.Builder()
                .url(url+"/savecup")
                .post(RequestBody.create(MediaType.parse("application/json"), json))
                .build();

        client.newCall(request).enqueue(callback);
    }

    public void changeCup(Cup cup, Callback callback) {

        Gson gson = new Gson();
        String json = gson.toJson(cup);

        Request request = new Request.Builder()
                .url(url+"/changecup")
                .post(RequestBody.create(MediaType.parse("application/json"), json))
                .build();

        client.newCall(request).enqueue(callback);
    }

    public void editCup(Cup cup, Callback callback) {

        Gson gson = new Gson();
        String json = gson.toJson(cup);

        Request request = new Request.Builder()
                .url(url+"/editcup")
                .post(RequestBody.create(MediaType.parse("application/json"), json))
                .build();

        client.newCall(request).enqueue(callback);
    }

    public void deleteCup(Cup cup) {

        Gson gson = new Gson();
        String json = gson.toJson(cup);

        Request request = new Request.Builder()
                .url(url+"/deletecup")
                .post(RequestBody.create(MediaType.parse("application/json"), json))
                .build();

        client.newCall(request);
    }

    public void cupInfo(Cup cup) {

        Gson gson = new Gson();
        String json = gson.toJson(cup);

        Request request = new Request.Builder()
                .url(url+"/cupInfo")
                .post(RequestBody.create(MediaType.parse("application/json"), json))
                .build();

        client.newCall(request);
    }

    public void drinkWater(Water water, Callback callback) {

        Gson gson = new Gson();
        String json = gson.toJson(water);

        Request request = new Request.Builder()
                .url(url+"/drink")
                .post(RequestBody.create(MediaType.parse("application/json"), json))
                .build();

        client.newCall(request).enqueue(callback);
    }
}