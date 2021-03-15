package com.example.cs492finalproject;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class BookDataItem implements Serializable {
    @SerializedName("title")
    public String title;

    @SerializedName("author_name")
    public ArrayList<String> auth;
}

