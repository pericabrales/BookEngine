package com.example.cs492finalproject;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MakeupDataItem implements Serializable {
    @SerializedName("brand")
    public String brand;

    @SerializedName("name")
    public String product_name;
}

