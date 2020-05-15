package com.example.lab_3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class BrowserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        String url = this.getIntent().getDataString();
        WebView webView= findViewById(R.id.webView);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(url);
    }
}
