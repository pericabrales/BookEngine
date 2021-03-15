package com.example.cs492finalproject.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cs492finalproject.BookDataItem;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookRepository {
    private static final String BASE_URL = "https://openlibrary.org/search.json";

    private MutableLiveData<List<BookDataItem>> searchResults;
    //loading status could go here

    //holds all preferences
    private String curQuery;
    private String curSearchType;
    private String curPriceGreater;
    private String curPriceLess;

    private BookService bookService;

    public BookRepository(){
        this.searchResults = new MutableLiveData<>();
        this.searchResults.setValue(null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.bookService = retrofit.create(BookService.class);
    }

    public LiveData<List<BookDataItem>> getSearchResults(){
        return this.searchResults;
    }

//    private boolean shouldExecuteSearch(String query, String type, String brand, String priceGreater, String priceLess){
//
//    }


}
