package com.example.cs492finalproject;

import androidx.appcompat.app.AppCompatActivity;

import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.cs492finalproject.utils.MakeupUtils;
import com.example.cs492finalproject.utils.NetworkUtils;

import java.io.IOException;
import java.util.ArrayList;

    public class MainActivity extends AppCompatActivity implements MakeupAdapter.OnMakeupItemClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

        private ProgressBar loadingIndicatorPB;
        private static final String TAG = MainActivity.class.getSimpleName();
        private MakeupAdapter makeupAdapter;
        private TextView errorMessageTV;

        private SharedPreferences sharedPreferences;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            RecyclerView makeupListRV = findViewById(R.id.rv_makeup_list);
            makeupListRV.setLayoutManager(new LinearLayoutManager(this));
            makeupListRV.setHasFixedSize(true);
//            this.loadingIndicatorPB = findViewById(R.id.pb_loading_indicator);
//            this.errorMessageTV = findViewById(R.id.tv_error_message);

            this.makeupAdapter = new MakeupAdapter(this);
            makeupListRV.setAdapter(makeupAdapter);

            this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            this.sharedPreferences.registerOnSharedPreferenceChangeListener(this);

//            doMakeupSearch(searchQuery);
        }

        private void doMakeupSearch(String query) {
            String url = MakeupUtils.buildMakeupSearchURL(query);
            new MakeupSearchTask().execute(url);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            //need view model for this
        }


        public class MakeupSearchTask extends AsyncTask<String, Void, String> {
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
                    errorMessageTV.setVisibility(View.INVISIBLE);
                    ArrayList<MakeupDataItem> searchResultsList = MakeupUtils.parseOpenWeatherSearchResults(results);
                    makeupAdapter.updateForecastData(searchResultsList);
                } else {
                    errorMessageTV.setVisibility(View.VISIBLE);
                }
            }
        }

        @Override
        public void onMakeupItemClicked(MakeupDataItem makeupData) {
            Intent intent = new Intent(this, MakeupDetailActivity.class);
            intent.putExtra(MakeupDetailActivity.DETAILED_WEATHER_FORECAST, makeupData);
            startActivity(intent);
        }

    }
}