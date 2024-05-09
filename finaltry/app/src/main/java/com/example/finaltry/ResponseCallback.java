package com.example.finaltry;



public interface ResponseCallback {

    void onResponse(String response);

    void onError(Throwable throwable);
}
