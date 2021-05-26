package com.ample.espirelife;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class MainActivityPage extends Activity {
    private WebView webView;
    public static ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar_2);
        progressBar.setVisibility(View.VISIBLE);

        webView = findViewById(R.id.webView1);
        webView.setWebViewClient(new WebViewClientImpl(this));

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        webView.loadUrl("https://app.espirelife.in");
    }

    public static class WebViewClientImpl extends WebViewClient {
        private final Activity mActivity;
        boolean overrideUrlLoading = false;

        public WebViewClientImpl(Activity activity) {
            this.mActivity = activity;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            if (url.startsWith("tel:")) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+91 9005234499"));
                mActivity.startActivity(intent);
                return overrideUrlLoading = true;
            } else if (url.contains("wa.me/")) {
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    mActivity.startActivity(intent);
                    return overrideUrlLoading = true;
                }
            } else {
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    webView.loadUrl(url);
                    return overrideUrlLoading = true;
                }
            }
            return overrideUrlLoading;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()) {
            this.webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}