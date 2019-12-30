package com.example.abeiiii.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Intent i = getIntent();
        String web = i.getStringExtra("url");
        String title=i.getStringExtra("title");
        if(web!=null)
        {
            getSupportActionBar().setTitle(title);
            WebView simpleWebView=new WebView(this);
            setContentView(simpleWebView);
            simpleWebView.loadUrl(web);
        }
    }
}
