package com.example.cs492finalproject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.cs492finalproject.data.BookRepository;

import java.util.ArrayList;
import java.util.List;

public class BookViewModel extends ViewModel {
    private LiveData<List<BookDataItem>> searchResults;
    private BookRepository repository;

    public BookViewModel(){
        this.repository = new BookRepository();
        this.searchResults = this.repository.getSearchResults();

    }

    public void loadSearchResults(String query, String searchType){
        this.repository.loadSearchResults(query, searchType);
    }

    public LiveData<List<BookDataItem>> getSearchResults(){
        return this.searchResults;
    }


}
