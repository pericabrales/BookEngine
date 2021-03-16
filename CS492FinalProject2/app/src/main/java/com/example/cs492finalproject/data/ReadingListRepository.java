package com.example.cs492finalproject.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.cs492finalproject.BookDataItem;

import java.util.List;

public class ReadingListRepository {
    private ReadingListDao dao;

    public ReadingListRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        this.dao = db.readingListDao();
    }

    public void insertReadingList(BookDataItem repo) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(repo);
            }
        });
    }

    public void deleteReadingList(BookDataItem repo) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.delete(repo);
            }
        });
    }

    public LiveData<List<BookDataItem>> getAllReadingList() {
        return this.dao.getAllReadingList();
    }

    public LiveData<BookDataItem> getAllReadingListByName(String fullName) {
        return this.dao.getAllReadingListByName(fullName);
    }
}
