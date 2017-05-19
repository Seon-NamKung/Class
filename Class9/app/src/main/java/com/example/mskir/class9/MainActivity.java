package com.example.mskir.class9;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    WebView webView;
    EditText et;
    Animation anim;
    LinearLayout linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView)findViewById(R.id.webview);
        webView.addJavascriptInterface(new JavaScriptMethods(),"MyApp");
        et = (EditText)findViewById(R.id.et);
        final ProgressDialog dialog = new ProgressDialog(this);
        linear = (LinearLayout)findViewById(R.id.linear);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                dialog.setMessage("Loading....");
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                et.setText(url);
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if(newProgress >= 100) dialog.dismiss();
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                result.confirm();
                return super.onJsAlert(view, url, message, result);
            }
        });


        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setCacheMode(webSettings.LOAD_NO_CACHE);

        webView.loadUrl("https://www.google.com");
        anim = AnimationUtils.loadAnimation(this,R.anim.translat_top);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                linear.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,1,0,"즐겨찾기");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == 1){
            webView.loadUrl("file:///android_asset/www/index.html");
            linear.setAnimation(anim);
            anim.start();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v){
        if(v.getId() == R.id.btn2){
            webView.loadUrl(et.getText().toString());
        }
    }

    Handler myHandler = new Handler();
    public class JavaScriptMethods {

            @JavascriptInterface
            public void displayToast() {
                myHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                        dlg.setTitle("그림변경").setMessage("그림을 변경하시겠습니까?")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        webView.loadUrl("javascript:changeImage()");
                                    }
                                }).setNegativeButton("Cancel",null).show();
                    }
                });
            }
    }
}
