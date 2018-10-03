 package com.viswanth.ocrtraslate;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


 public class TranslateActivity extends AppCompatActivity {

     WebView webView;
     int position;
     String text;
     private String[] languages = {"af","sq","am","ar","hy"
             ,"az","eu","be","bn","bs","bg",
             "ca","ceb","ny","zh-CN","co","hr","cs",
             "da","nl","en","eo","et","tl","fi",
             "fr","fy","gl","ka","de","el","gu",
             "ht","ha","haw","iw","hi","hmn","hu",
             "is","ig","id","ga","it","ja","jw",
             "kn","kk","km","ko","ku","ky","lo","la","lv",
             "lt","lb","mk","mg","ms","ml",
             "mt","mi","mr","mn","my","ne","no","ps",
             "fa","pl","pt","pa","ro","ru","sm","gd",
             "sr","st","sn","sd","si","sk","sl","so","es",
             "su","sw","sv","tg","ta","te","th","tr","uk",
             "ur","uz","vi","cy","xh","yi","yo","zu"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);

        position = getIntent().getExtras().getInt("lang");
        text = getIntent().getExtras().getString("text");

        webView = (WebView) findViewById(R.id.translateview);
        webView.setWebViewClient(new web());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://translate.google.co.in/#auto/"+languages[position]+"/"+text);



    }




    private class web extends WebViewClient{

        @Override
        public boolean shouldOverrideUrlLoading(WebView view,  String url) {

            view.loadUrl(url);
            return true;
        }
    }


}
