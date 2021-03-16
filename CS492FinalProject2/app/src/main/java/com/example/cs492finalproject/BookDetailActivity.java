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

            bookNameTV.setText(book.title);
            bookAuthorTV.setText(book.auth.get(0));
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


//    private void shareBook() {
//        if (this.book != null) {
//            String shareText = getString(
//                    R.string.share_repo_text,
//                    this.repo.fullName,
//                    this.repo.htmlUrl
//            );
//            Intent intent = new Intent(Intent.ACTION_SEND);
//            intent.putExtra(Intent.EXTRA_TEXT, shareText);
//            intent.setType("text/plain");
//
//            Intent chooserIntent = Intent.createChooser(intent, null);
//            startActivity(chooserIntent);
//        }
//    }
}
