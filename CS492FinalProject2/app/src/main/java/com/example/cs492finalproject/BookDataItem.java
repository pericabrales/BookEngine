package com.example.cs492finalproject;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import java.util.ArrayList;

@Entity(tableName = "readingList")
public class BookDataItem implements Serializable {
    @SerializedName("title")
    @PrimaryKey
    @NonNull
    public String title;

    //everything past title is an array

    @Ignore
    @SerializedName("publisher")
    public ArrayList<String> publisher;

    @Ignore
    @SerializedName("subject")
    @NonNull
    public ArrayList<String> subject;

    @Ignore
    @SerializedName("language")
    @NonNull
    public ArrayList<String> language;

    @Ignore
    @SerializedName("publish_date")
    @NonNull
    public ArrayList<String> publishDate;

    @Ignore
    @SerializedName("author_name")
    @NonNull
    public ArrayList<String> auth;
}

