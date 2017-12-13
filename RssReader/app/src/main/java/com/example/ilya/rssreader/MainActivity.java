package com.example.ilya.rssreader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity {

    private ProgressDialog pDialog;
    private ArrayList<HashMap<String, String>> rssFeedList;
    private Button btnAddSite;
    private String[] sqliteIds;

    public static String TAG_ID = "id";
    public static String TAG_TITLE = "title";
    public static String TAG_LINK = "link";

    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.site_list);

        btnAddSite = (Button) findViewById(R.id.btnAddSite);
        rssFeedList = new ArrayList<HashMap<String, String>>();
        new loadStoreSites().execute();

        listView = (ListView) findViewById(R.id.list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String sqlite_id = ((TextView) view.findViewById(R.id.sqlite_id)).getText().toString();
                Intent intent = new Intent(getApplicationContext(), ListRSSItemsActivity.class);
                intent.putExtra(TAG_ID, sqlite_id);
                startActivity(intent);
            }
        });


        btnAddSite.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddNewSiteActivity.class);
                startActivityForResult(i, 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId()==R.id.list) {
            menu.setHeaderTitle(R.string.delete);
            menu.add(Menu.NONE, 0, 0, R.string.deleteFeed);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        if(menuItemIndex == 0){
            RSSDatabaseHandler rssDb = new RSSDatabaseHandler(getApplicationContext());
            WebSite site = new WebSite();
            site.setId(Integer.parseInt(sqliteIds[info.position]));
            rssDb.deleteSite(site);
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }

        return true;
    }

    class loadStoreSites extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(
                    MainActivity.this);
            pDialog.setMessage("Loading websites ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        @Override
        protected String doInBackground(String... args) {
            runOnUiThread(new Runnable() {
                public void run() {
                    RSSDatabaseHandler rssDb = new RSSDatabaseHandler(
                            getApplicationContext());
                    List<WebSite> siteList = rssDb.getAllSites();
                    sqliteIds = new String[siteList.size()];

                    for (int i = 0; i < siteList.size(); i++) {
                        WebSite s = siteList.get(i);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(TAG_ID, s.getId().toString());
                        map.put(TAG_TITLE, s.getTitle());
                        map.put(TAG_LINK, s.getLink());
                        rssFeedList.add(map);
                        sqliteIds[i] = s.getId().toString();
                    }
                    ListAdapter adapter = new SimpleAdapter(
                            MainActivity.this,
                            rssFeedList, R.layout.site_list_row,
                            new String[] { TAG_ID, TAG_TITLE, TAG_LINK },
                            new int[] { R.id.sqlite_id, R.id.title, R.id.link });
                    listView.setAdapter(adapter);
                    registerForContextMenu(listView);
                }
            });
            return null;
        }

        protected void onPostExecute(String args) {
            pDialog.dismiss();
        }

    }
}