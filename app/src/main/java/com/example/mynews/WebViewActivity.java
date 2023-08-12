package com.example.mynews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Intent intent = getIntent();
        String news_url = intent.getStringExtra("NEWS_URL");

        webView = this.findViewById(R.id.news_web_view);
        backButton = this.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(WebViewActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });

        WebSettings ws = webView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.setWebViewClient( new MyWebViewClient() );
        webView.loadUrl(news_url);
    }
}