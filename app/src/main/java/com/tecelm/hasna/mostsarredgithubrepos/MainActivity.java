package com.tecelm.hasna.mostsarredgithubrepos;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private static String url = "https://api.github.com/search/repositories?q=created:>2017-10-22&sort=stars&order=desc";

    RecyclerView mRecyclerView;
    List<Repository> repositories = new ArrayList<Repository>();
    android.support.v7.app.ActionBar actionbar;
    TextView textview;
    ActionBar.LayoutParams layoutparams;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        //Custom Title of ActionBar
        actionBarTitle();
        //get reference of our RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //get reference of our bottomNavigationVie
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        new GetRepos().execute();

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && bottomNavigationView.isShown()) {
                    //hide ButtomNavigationView when scroll up Recycler view
                    bottomNavigationView.setVisibility( View.GONE);
                } else if (dy < 0 ) {
                    //show ButtomNavigationView when scroll down Recycler view
                    bottomNavigationView.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                super.onScrollStateChanged(recyclerView, newState);
            }
        });

    }
    private class GetRepos extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray items = jsonObj.getJSONArray("items");
                    for (int i = 0; i < items.length(); i++){
                        JSONObject item = items.getJSONObject(i);
                        String name = item.getString("name");
                        String description = item.getString("description");
                        Long stargazers_count = item.getLong("stargazers_count");
                        JSONObject owner = item.getJSONObject("owner");
                        String owner_name = owner.getString("login");
                        String avatar_url = owner.getString("avatar_url");
                        Repository rep = new Repository(name, description, avatar_url,owner_name, stargazers_count);
                        repositories.add(rep);
                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            CustomerRepoAdapter adapter = new CustomerRepoAdapter(getApplicationContext(), repositories);
            mRecyclerView.setAdapter(adapter);
        }

    }
    @SuppressLint("ResourceType")
    private void actionBarTitle() {

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bleu)));

        actionbar = getSupportActionBar();

        textview = new TextView(getApplicationContext());

        layoutparams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);

        textview.setLayoutParams(layoutparams);

        textview.setText("Trending Repos");

        textview.setTextColor( Color.BLACK);

        textview.setGravity( Gravity.CENTER);

        textview.setTextSize(20);

        textview.setTypeface( Typeface.SANS_SERIF);

        actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        actionbar.setCustomView(textview);
    }

}

