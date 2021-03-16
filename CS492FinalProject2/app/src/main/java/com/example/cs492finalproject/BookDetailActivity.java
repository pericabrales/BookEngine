package com.example.cs492finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

    private BookDataItem book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_share:
                shareBook();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //intent for sharing to others
    private void shareBook() {
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
}
