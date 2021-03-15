package com.example.cs492finalproject.utils;

import android.net.Uri;

import com.example.cs492finalproject.BookDataItem;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BookUtils {
    private final static String GITHUB_SEARCH_BASE_URL = "https://openlibrary.org/search.json";
    private final static String GITHUB_SEARCH_QUERY_PARAM = "q";


    private static class BookSearchResults {
//        @SerializedName("total_count")
//        public int totalCount;
//
//        @SerializedName("incomplete_results")
//        public boolean incompleteResults;

        public ArrayList<BookDataItem> docs;
    }
    public static String buildBookSearchURL(String query) {
        return Uri.parse(GITHUB_SEARCH_BASE_URL).buildUpon()
                .appendQueryParameter(GITHUB_SEARCH_QUERY_PARAM, query)
                .build()
                .toString();
    }

    public static ArrayList<BookDataItem> parseBookSearchResults(String json) {
        Gson gson = new Gson();
        BookSearchResults results = gson.fromJson(json, BookSearchResults.class);
        return results != null ? results.docs : null;
    }
}

