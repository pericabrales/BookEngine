package com.example.cs492finalproject;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cs492finalproject.data.ReadingListRepository;

import java.util.List;

public class ReadingListViewModel extends AndroidViewModel {
    private ReadingListRepository repository;

    public ReadingListViewModel(Application application) {
        super(application);
        this.repository = new ReadingListRepository(application);
    }

    public void insertReadingList(BookDataItem repo) {
        this.repository.insertReadingList(repo);
    }

    public void deleteReadingList(BookDataItem repo) {
        this.repository.deleteReadingList(repo);
    }

    public LiveData<List<BookDataItem>> getAllReadingList() {
        return this.repository.getAllReadingList();
    }

    public LiveData <BookDataItem> getAllReadingListByName(String title) {
        return this.repository.getAllReadingListByName(title);
    }

}
