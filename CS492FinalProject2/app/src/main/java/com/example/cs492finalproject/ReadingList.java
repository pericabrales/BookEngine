package com.example.cs492finalproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReadingList extends AppCompatActivity implements BookAdapter.OnSearchResultClickListener {
    private ReadingListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_list);

        RecyclerView bookmarkedReposRV = findViewById(R.id.rv_bookmarked_repos);
        bookmarkedReposRV.setLayoutManager(new LinearLayoutManager(this));
        bookmarkedReposRV.setHasFixedSize(true);

        BookAdapter adapter = new BookAdapter(this);
        bookmarkedReposRV.setAdapter(adapter);

        this.viewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(ReadingListViewModel.class);

        this.viewModel.getAllReadingList().observe(
                this,
                new Observer<List<BookDataItem>>() {
                    @Override
                    public void onChanged(List<BookDataItem> readingList) {
                        adapter.updateSearchResults(readingList);
                    }
                }
        );
    }

    @Override
    public void onSearchResultClicked(BookDataItem repo) {
        Intent intent = new Intent(this, BookDetailActivity.class);
        intent.putExtra(BookDetailActivity.EXTRA_BOOK_DETAIL, repo);
        startActivity(intent);
    }
}