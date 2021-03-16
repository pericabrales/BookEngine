package com.example.cs492finalproject;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WebActivity extends AppCompatActivity{
    private static final String TAG = WebActivity.class.getSimpleName();

    public static final String EXTRA_WEB_INFO = "WebActivity.info";

    private BookDataItem book;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WebView webView = (WebView) findViewById(R.id.action_webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        String splitPath[] = this.book.ISBN.get(0).split(",");
        String bookIsbn = splitPath[0];

        String url = "https://openlibrary.org/" + bookIsbn + "/" + this.book.title;
        webView.loadUrl(url);
    }
}
