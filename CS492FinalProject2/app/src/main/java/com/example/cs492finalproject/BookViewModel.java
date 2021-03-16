package com.example.cs492finalproject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.cs492finalproject.data.BookRepository;
import com.example.cs492finalproject.data.LoadingStatus;

import java.util.ArrayList;
import java.util.List;

public class BookViewModel extends ViewModel {
    private LiveData<List<BookDataItem>> searchResults;
    private BookRepository repository;
    private LiveData<LoadingStatus> loadingStatus;

    public BookViewModel(){
        this.repository = new BookRepository();
        this.searchResults = this.repository.getSearchResults();
        this.loadingStatus = this.repository.getLoadingStatus();
    }

    public void loadSearchResults(String query, String searchType){
        this.repository.loadSearchResults(query, searchType);
    }

    public LiveData<List<BookDataItem>> getSearchResults(){
        return this.searchResults;
    }

    public LiveData<LoadingStatus> getLoadingStatus(){
        return this.loadingStatus;
    }

}
