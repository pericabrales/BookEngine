package com.example.cs492finalproject.data;

import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cs492finalproject.BookDataItem;
import com.example.cs492finalproject.utils.BookUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookRepository {
    private static final String TAG = BookRepository.class.getSimpleName();

    private static final String BASE_URL = "https://openlibrary.org/search.json/";

    private MutableLiveData<List<BookDataItem>> searchResults;
    private MutableLiveData<LoadingStatus> loadingStatus;

    //holds all preferences
    private String curQuery;
    private String curSearchType;

    private BookService bookService;

    public BookRepository(){
        this.searchResults = new MutableLiveData<>();
        this.searchResults.setValue(null);

        this.loadingStatus = new MutableLiveData<>();
        this.loadingStatus.setValue(LoadingStatus.SUCCESS);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.bookService = retrofit.create(BookService.class);
    }

    public LiveData<List<BookDataItem>> getSearchResults(){
        return this.searchResults;
    }

    public LiveData<LoadingStatus> getLoadingStatus(){
        return this.loadingStatus;
    }

    private boolean shouldExecuteSearch(String query, String searchType){
        return !TextUtils.equals(query, this.curQuery)
                || !TextUtils.equals(searchType, this.curSearchType)
                || this.getLoadingStatus().getValue() == LoadingStatus.ERROR;
    }

    public void loadSearchResults(String query, String searchType){
        if(this.shouldExecuteSearch(query, searchType)){
            this.curQuery = query;
            this.curSearchType = searchType;

            //in case of empty for some reason
            if(searchType.equals("")){
                this.executeSearch(query, "General Search");
            }
            else{
                this.executeSearch(query, searchType);
            }
        }
        else{
            Log.d(TAG, "using cached search results for this query: " + query);
        }
    }

    private void executeSearch(String query, String searchType){
        Call<BookResponse> response;

        if(searchType.equals("General Search")) {
            Log.d(TAG, "search type is general search");
            //call the function for general search
            response = this.bookService.searchBooksGeneral(query);
        }
        else if(searchType.equals("Search by Title")) {
            Log.d(TAG, "search type is search by title");
            //call the function for title search
            response = this.bookService.searchBooksTitle(query);
        }
        else{
            Log.d(TAG, "search type is search by author");
            //call the function for author search
            response = this.bookService.searchBooksAuthor(query);
        }

        this.searchResults.setValue(null);
        this.loadingStatus.setValue(LoadingStatus.LOADING);

        response.enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                if(response.code() == 200){
                    searchResults.setValue(response.body().bookList);
                    loadingStatus.setValue(LoadingStatus.SUCCESS);
                }
                else{
                    loadingStatus.setValue(LoadingStatus.ERROR);
                }
            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                t.printStackTrace();
                loadingStatus.setValue(LoadingStatus.ERROR);
            }
        });
    }


}
