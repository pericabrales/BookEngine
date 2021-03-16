package com.example.cs492finalproject.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.cs492finalproject.BookDataItem;

import java.util.List;

@Dao
public interface ReadingListDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(BookDataItem repo);

    @Delete
    void delete(BookDataItem repo);

    @Query("SELECT * FROM readingList")
    LiveData<List<BookDataItem>> getAllReadingList();

    @Query("SELECT * FROM readingList WHERE title = :title LIMIT 1 ")
    LiveData<BookDataItem> getAllReadingListByName(String title);
}
