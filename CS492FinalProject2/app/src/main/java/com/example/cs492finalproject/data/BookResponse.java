package com.example.cs492finalproject.data;

import com.example.cs492finalproject.BookDataItem;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BookResponse {
    @SerializedName("docs")
    public ArrayList<BookDataItem> bookList;
}
