package com.example.boardgames.Classes;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.util.Arrays;

public class HttpService {
    Integer sucessResp[] = {200,201,202,203,205};
    OkHttpClient client;
    MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public HttpService() {
        this.client = new OkHttpClient();
    }

    public boolean postMethod(String URL, String payload) {
        RequestBody body = RequestBody.create(payload, JSON);
        Request request = new Request.Builder()
                .url(URL)
                .post(body)
                .build();

        Response response = null;

        try {
            response = client.newCall(request).execute();

            if(Arrays.asList(sucessResp).contains(response.code())) {
                return true;
            }
        } catch (Exception e){
            return false;
        }
        return false;
    }


}
