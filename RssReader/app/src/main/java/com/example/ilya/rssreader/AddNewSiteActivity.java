package com.example.ilya.rssreader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Created by ilya on 1.12.17.
 */

public class AddNewSiteActivity extends Activity {
    private Button btnSubmit;
    private Button btnCancel;
    private EditText txtUrl;
    private TextView lblMessage;

    private RSSParser rssParser = new RSSParser();
    private RSSFeed rssFeed;
    private ProgressDialog pDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_site);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        txtUrl = (EditText) findViewById(R.id.txtUrl);
        lblMessage = (TextView) findViewById(R.id.lblMessage);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String url = txtUrl.getText().toString();
                Log.d("URL Length", "" + url.length());
                if (url.length() > 0) {
                    lblMessage.setText("");
                    String urlPattern = "^http(s{0,1})://[a-zA-Z0-9_/\\-\\.]+\\.([A-Za-z/]{2,5})[a-zA-Z0-9_/\\&\\?\\=\\-\\.\\~\\%]*";
                    if (url.matches(urlPattern)) {
                        new loadRSSFeed().execute(url);
                    } else {
                        lblMessage.setText(R.string.incorrectUrl);
                    }
                } else {
                    lblMessage.setText(R.string.incorrectUrl);
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    class loadRSSFeed extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AddNewSiteActivity.this);
            pDialog.setMessage(getString(R.string.fetchingRss));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String url = args[0];
            rssFeed = rssParser.getRSSFeed(url);
            Log.d("rssFeed", " " + rssFeed);
            if (rssFeed != null) {
                Log.e("RSS URL",
                        rssFeed.getTitle() + "" + rssFeed.getLink() + ""
                                + rssFeed.getDescription() + ""
                                + rssFeed.getLanguage());
                RSSDatabaseHandler rssDb = new RSSDatabaseHandler(
                        getApplicationContext());
                WebSite site = new WebSite(rssFeed.getTitle(), rssFeed.getLink(), rssFeed.getRSSLink(),
                        rssFeed.getDescription());
                rssDb.addSite(site);
                Intent i = getIntent();
                setResult(100, i);
                finish();
            } else {
                runOnUiThread(new Runnable() {
                    public void run() {
                        lblMessage.setText(getString(R.string.rssUrlNotFound));
                    }
                });
            }
            return null;
        }

        protected void onPostExecute(String args) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    if (rssFeed != null) {

                    }

                }
            });

        }

    }
}