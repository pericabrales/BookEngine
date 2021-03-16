package com.example.cs492finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cs492finalproject.BookDataItem;

public class BookDetailActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{
    public static final String EXTRA_BOOK_DETAIL = "BookDataItem";

    private static final String TAG = BookDetailActivity.class.getSimpleName();

    private Toast errorToast;
    private ReadingListViewModel viewModel;
    private boolean isBookmarked;

    private BookDataItem book;

    private SharedPreferences sharedPreferences;

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
            this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            this.sharedPreferences.registerOnSharedPreferenceChangeListener(this);

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
        switch(item.getItemId()){
            case R.id.action_share:
                Log.d(TAG, "action share");
                shareBook();
                return true;
            case R.id.action_search_web:
                searchBook();
                return true;
            case R.id.action_bookmark:
                toggleRepoBookmark(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    //intent for sharing to others
    private void shareBook() {
        Log.d(TAG, "in shareBook");
        if (this.book != null) {
            String shareText = getString(
                    R.string.share_book_text,
                    this.book.title,
                    this.book.auth.get(0)
            );
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, shareText);
            intent.setType("text/plain");

            Intent chooserIntent = Intent.createChooser(intent, null);
            startActivity(chooserIntent);
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


    //intent for google searching
    private void searchBook(){
        if(this.book != null){
            //String query = this.book.title + " by " + this.book.auth.get(0);
            //String baseUrl = "http://google.com/search?q=" + query;
            String query = "";
            String searchType = sharedPreferences.getString(
                    getString(R.string.pref_search_type_key),
                    getString(R.string.pref_search_type_default)
            );
            if(searchType.equals("")){
                query = this.book.title + " " + this.book.auth.get(0) + " " + this.book.publisher.get(0);
            }
            else{
                query = this.book.title + " " + this.book.auth.get(0);
            }

            //String query = this.book.title + " " + this.book.auth.get(0) + " " + this.book.publisher.get(0);
            Log.d(TAG, "query for url is " + query);
            String baseUrl = "http://amazon.com/s?k=" + query + "&ref=nb_sb_noss";
            Log.d(TAG, "full url is " + baseUrl);

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(baseUrl));

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setPackage("com.android.chrome");

            try{
                startActivity(intent);
            }catch(ActivityNotFoundException e){
                if(this.errorToast != null){
                    this.errorToast.cancel();
                }
                this.errorToast = Toast.makeText(
                        this,
                        getString(R.string.search_action_error),
                        Toast.LENGTH_LONG
                );
                this.errorToast.show();
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }
}
