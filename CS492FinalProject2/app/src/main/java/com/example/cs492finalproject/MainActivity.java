package com.example.cs492finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.cs492finalproject.utils.BookUtils;
import com.example.cs492finalproject.utils.NetworkUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
        private ProgressBar loadingIndicatorPB;
        private static final String TAG = MainActivity.class.getSimpleName();
        private BookAdapter bookAdapter;
        private TextView errorMessageTV;
        private RecyclerView searchResultsRV;
        private EditText searchBoxET;

        private BookViewModel bookViewModel;

        private SharedPreferences sharedPreferences;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            this.searchBoxET = findViewById(R.id.et_search_box);
            this.searchResultsRV = findViewById(R.id.rv_search_results);
            this.loadingIndicatorPB = findViewById(R.id.pb_loading_indicator);
            this.errorMessageTV = findViewById(R.id.tv_error_message);

            this.searchResultsRV.setLayoutManager(new LinearLayoutManager(this));
            this.searchResultsRV.setHasFixedSize(true);

            this.bookAdapter = new BookAdapter();
            this.searchResultsRV.setAdapter(bookAdapter);

            this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            this.sharedPreferences.registerOnSharedPreferenceChangeListener(this);

            this.bookViewModel = new ViewModelProvider(this).get(BookViewModel.class);
            this.bookViewModel.getSearchResults().observe(
                    this,
                    new Observer<List<BookDataItem>>() {
                        @Override
                        public void onChanged(List<BookDataItem> bookDataItems) {
                            bookAdapter.updateSearchResults(bookDataItems);
                        }
                    }
            );

            //where loading stuff for view model would go

            Button searchButton = (Button)findViewById(R.id.btn_search);
            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String searchQuery = searchBoxET.getText().toString();
                    if (!TextUtils.isEmpty(searchQuery)) {
                        //TODO: UNCOMMENT THIS TO GET NORMAL SEARCH
                        //doMakeupSearch(searchQuery);

                        //search type
                        String searchType = sharedPreferences.getString(
                                getString(R.string.pref_search_type_key),
                                getString(R.string.pref_search_type_default)
                        );

                        bookViewModel.loadSearchResults(searchQuery, searchType);
                    }
                }
            });
        }


    @Override
    protected void onDestroy() {
        this.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();
    }

    private void doMakeupSearch(String query) {
            String url = BookUtils.buildBookSearchURL(query);
            new BookSearchTask().execute(url);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if(TextUtils.equals(key, getString(R.string.pref_search_type_key))){
                Log.d(TAG, "shared preference changed, key: " + key + ", value: " + sharedPreferences.getString(key, ""));
            }
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu){
            getMenuInflater().inflate(R.menu.activity_main, menu);
            return true;
        }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public class BookSearchTask extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingIndicatorPB.setVisibility(View.VISIBLE);
            }

            @Override
            protected String doInBackground(String... params) {
                String url = params[0];
                Log.d(TAG, "searching with this URL: " + url);

                String results = null;
                try {
                    results = NetworkUtils.doHttpGet(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "query results: " + results);
                return results;
            }

            @Override
            protected void onPostExecute(String results) {
                loadingIndicatorPB.setVisibility(View.INVISIBLE);
                if (results != null) {
                    Log.d(TAG, "querying the results: " + results);
                    ArrayList<BookDataItem> searchResultsList = BookUtils.parseBookSearchResults(results);
                    bookAdapter.updateSearchResults(searchResultsList);
                    searchResultsRV.setVisibility(View.VISIBLE);
                    errorMessageTV.setVisibility(View.INVISIBLE);
                } else {
                    searchResultsRV.setVisibility(View.INVISIBLE);
                    errorMessageTV.setVisibility(View.VISIBLE);
                }
            }
        }


    }