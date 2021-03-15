package com.example.cs492finalproject;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class BookDataItem implements Serializable {
    @SerializedName("title")
    public String title;

    //everything past title is an array

    @SerializedName("publisher")
    public ArrayList<String> publisher;

    @SerializedName("subject")
    public ArrayList<String> subject;

    @SerializedName("language")
    public ArrayList<String> language;

    @SerializedName("author_name")
    public ArrayList<String> authorNames;

    @SerializedName("publish_date")
    public ArrayList<String> publishDate;


}

