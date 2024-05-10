package com.example.movieratingservice;

import com.example.movieratingservice.models.MovieManager;

public class MovieGenre extends MovieManager {

    private String uuid;
    private String name;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public MovieGenre() {}

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "MovieGenre{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

}
