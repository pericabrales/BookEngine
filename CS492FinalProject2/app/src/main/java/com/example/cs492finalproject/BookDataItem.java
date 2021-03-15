package com.example.cs492finalproject;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BookDataItem implements Serializable {
    @SerializedName("title")
    public String title;
}

