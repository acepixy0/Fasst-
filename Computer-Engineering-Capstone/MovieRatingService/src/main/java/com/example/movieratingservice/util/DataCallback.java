package com.example.movieratingservice.util;

import com.example.movieratingservice.model.Movies;

import java.util.List;

public interface DataCallback {
    void onSuccess(String data);
    void onFailure(String error);
}
