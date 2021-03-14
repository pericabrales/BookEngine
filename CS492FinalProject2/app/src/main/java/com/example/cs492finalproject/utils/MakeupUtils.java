package com.example.cs492finalproject.utils;

import android.net.Uri;

import com.example.cs492finalproject.MakeupDataItem;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MakeupUtils {
    private final static String MAKEUP_SEARCH_BASE_URL = "https://makeup.p.rapidapi.com/products.json";
    private final static String MAKEUP_SEARCH_QUERY_PARAM = "q";
    private final static String MAKEUP_BRAND_PARAM = "brand";
    private final static String MAKEUP_SEARCH_SORT_VALUE = "covergirl";

    private static class GitHubSearchResults {
        public boolean incompleteResults;

        public ArrayList<MakeupDataItem> items;
    }

    public static String buildMakeupSearchURL(String query) {
        return Uri.parse(MAKEUP_SEARCH_BASE_URL).buildUpon()
                .appendQueryParameter(MAKEUP_SEARCH_QUERY_PARAM, query)
                .appendQueryParameter(MAKEUP_BRAND_PARAM, MAKEUP_SEARCH_SORT_VALUE)
                .build()
                .toString();
    }

    public static ArrayList<MakeupDataItem> parseMakeupSearchResults(String json) {
        Gson gson = new Gson();
        GitHubSearchResults results = gson.fromJson(json, GitHubSearchResults.class);
        return results != null ? results.items : null;
    }
}
