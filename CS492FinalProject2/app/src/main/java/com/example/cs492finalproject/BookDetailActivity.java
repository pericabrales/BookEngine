package com.example.cs492finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cs492finalproject.BookDataItem;

public class BookDetailActivity extends AppCompatActivity {
    public static final String EXTRA_BOOK_DETAIL = "BookDataItem";

    private static final String TAG = BookDetailActivity.class.getSimpleName();

    private Toast errorToast;
    private ReadingListViewModel viewModel;
    private boolean isBookmarked;

    private BookDataItem book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        this.isBookmarked = false;
        this.viewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(ReadingListViewModel.class);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_BOOK_DETAIL)) {
            this.book = (BookDataItem) intent.getSerializableExtra(EXTRA_BOOK_DETAIL);
            Log.d(TAG, "Got repo with name: " + book.title);

            TextView bookNameTV = findViewById(R.id.tv_book_name);
            TextView bookAuthorTV = findViewById(R.id.tv_book_author);
            TextView bookPublishTV = findViewById(R.id.tv_book_publish);
            TextView bookSubjectTV = findViewById(R.id.tv_book_subject);
            TextView bookLanguageTV = findViewById(R.id.tv_book_language);
            TextView bookDateTV = findViewById(R.id.tv_book_date);

            bookNameTV.setText(book.title);
            if(book.auth != null){
                bookAuthorTV.setText("Author: " + book.auth.get(0));

            }
            else{
                bookAuthorTV.setText("Author Not Specified");
            }
            if(book.publisher != null){
                bookPublishTV.setText("Publisher: " + book.publisher.get(0));

            }
            else{
                bookPublishTV.setText("Publisher Not Specified");
            }
            if(book.subject != null){
                bookSubjectTV.setText("Subject: " + book.subject.get(0));

            }
            else{
                bookSubjectTV.setText("Subject Not Specified");
            }
            if(book.language != null){
                bookLanguageTV.setText("Language: " + book.language.get(0));

            }
            else{
                bookLanguageTV.setText("Language Not Specified");
            }
            if(book.publishDate != null){
                bookDateTV.setText("Published in " + book.publishDate.get(0));
            }
            else{
                bookPublishTV.setText("Publish Date Not Specified");
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_detail, menu);
        this.viewModel.getAllReadingListByName(this.book.title).observe(
                this,
                new Observer<BookDataItem>() {
                    @Override
                    public void onChanged(BookDataItem repo) {
                        MenuItem menuItem = menu.findItem(R.id.action_bookmark);
                        if (repo == null) {
                            isBookmarked = false;
                            menuItem.setIcon(R.drawable.ic_reading_list_border);
                        } else {
                            isBookmarked = true;
                            menuItem.setIcon(R.drawable.ic_reading_list_checked);
                        }
                    }
                }
        );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_bookmark:
                toggleRepoBookmark(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void toggleRepoBookmark(MenuItem menuItem) {
        if (this.book != null) {
            this.isBookmarked = !this.isBookmarked;
            menuItem.setChecked(this.isBookmarked);
            if (this.isBookmarked) {
                menuItem.setIcon(R.drawable.ic_reading_list_checked);
                this.viewModel.insertReadingList(this.book);
            } else {
                menuItem.setIcon(R.drawable.ic_reading_list_border);
                this.viewModel.deleteReadingList(this.book);
            }
        }
    }

    /*private void shareRepo() {
        if (this.book != null) {
            String shareText = getString(
                    R.string.share_repo_text,
                    this.repo.fullName,
                    this.repo.htmlUrl
            );
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, shareText);
            intent.setType("text/plain");

            Intent chooserIntent = Intent.createChooser(intent, null);
            startActivity(chooserIntent);
        }
    }*/
}
