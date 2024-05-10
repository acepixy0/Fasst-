package com.example.movieratingservice.firebase;

public interface DataCallback {
    void onSuccess(String data);
    void onFailure(String error);
}
