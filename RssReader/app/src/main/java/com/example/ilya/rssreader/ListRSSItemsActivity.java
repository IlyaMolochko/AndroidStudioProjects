package com.example.ilya.rssreader;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by ilya on 1.12.17.
 */


public class ListRSSItemsActivity extends ListActivity {

    private ProgressDialog pDialog;
    private ArrayList<HashMap<String, String>> rssItemList = new ArrayList<HashMap<String, String>>();

    RSSParser rssParser = new RSSParser();
    Button btnAddSite;

    List<RSSItem> rssItems = new ArrayList<RSSItem>();

    private static String TAG_TITLE = "title";
    private static String TAG_LINK = "link";
    private static String TAG_DESRIPTION = "description";
    private static String TAG_PUB_DATE = "pubDate";
    private static String TAG_GUID = "guid";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rss_item_list);

        btnAddSite = (Button) findViewById(R.id.btnAddSite);
        btnAddSite.setVisibility(View.GONE);

        Intent intent = getIntent();
        Integer site_id = Integer.parseInt(intent.getStringExtra("id"));
        RSSDatabaseHandler rssDB = new RSSDatabaseHandler(getApplicationContext());
        WebSite site = rssDB.getSite(site_id);
        String rss_link = site.getRSSLink();

        new loadRSSFeedItems().execute(rss_link);

        ListView lv = getListView();
        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent in = new Intent(getApplicationContext(), DisPlayWebPageActivity.class);
                String page_url = ((TextView) view.findViewById(R.id.page_url)).getText().toString();
                in.putExtra("page_url", page_url);
                startActivity(in);
            }
        });
    }

    class loadRSSFeedItems extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(
                    ListRSSItemsActivity.this);
            pDialog.setMessage(getString(R.string.loadingRecentArticles));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String rssUrl = args[0];
            rssItems = rssParser.getRSSFeedItems(rssUrl);

            for (RSSItem item : rssItems) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(TAG_TITLE, item.getTitle());
                map.put(TAG_LINK, item.getLink());
                map.put(TAG_PUB_DATE, item.getPubdate());
                String description = item.getDescription();
                if (description.length() > 100) {
                    description = description.substring(0, 97) + "...";
                }
                map.put(TAG_DESRIPTION, description);
                rssItemList.add(map);
            }

            runOnUiThread(new Runnable() {
                public void run() {
                    ListAdapter adapter = new SimpleAdapter(
                            ListRSSItemsActivity.this,
                            rssItemList, R.layout.rss_item_list_row,
                            new String[]{TAG_LINK, TAG_TITLE, TAG_PUB_DATE, TAG_DESRIPTION},
                            new int[]{R.id.page_url, R.id.title, R.id.pub_date, R.id.link});
                    setListAdapter(adapter);
                }
            });
            return null;
        }

        protected void onPostExecute(String args) {
            pDialog.dismiss();
        }
    }
}