package com.example.movieratingservice;

public interface DataCallback {
    void onSuccess(String data);
    void onFailure(String error);
}
