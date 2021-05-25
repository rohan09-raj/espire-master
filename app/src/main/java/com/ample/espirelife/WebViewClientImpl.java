package com.ample.espirelife;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewClientImpl extends WebViewClient {
    private final Activity mActivity;
    boolean overrideUrlLoading = false;

    public WebViewClientImpl(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        if (url.startsWith("about:")) {
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
}

