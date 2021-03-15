package com.example.cs492finalproject.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cs492finalproject.MakeupDataItem;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MakeupRepository {
    private static final String BASE_URL = "https://makeup.p.rapidapi.com";

    private MutableLiveData<List<MakeupDataItem>> searchResults;
    //loading status could go here

    //holds all preferences
    private String curQuery;
    private String curSearchType;
    private String curPriceGreater;
    private String curPriceLess;

    private MakeupService makeupService;

    public MakeupRepository(){
        this.searchResults = new MutableLiveData<>();
        this.searchResults.setValue(null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.makeupService = retrofit.create(MakeupService.class);
    }

    public LiveData<List<MakeupDataItem>> getSearchResults(){
        return this.searchResults;
    }

//    private boolean shouldExecuteSearch(String query, String type, String brand, String priceGreater, String priceLess){
//
//    }


}
