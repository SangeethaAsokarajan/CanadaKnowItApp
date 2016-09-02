package sangeetha.canadaknowitapp.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import sangeetha.canadaknowitapp.R;
import sangeetha.canadaknowitapp.adapter.RecyclerViewAdapter;
import sangeetha.canadaknowitapp.dataModel.DataCanada;
import sangeetha.canadaknowitapp.divider.MyItemDecoration;

/**
 * Created by Sangeetha on 8/30/2016.
 */

public class HomeActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<DataCanada> rowItemList;
    private ActionBar actionBar;
    private final String url = "https://dl.dropboxusercontent.com/u/746330/facts.json";
    private String title="";
    private static final String TAG = "CanadaKnowItHomePage";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        actionBar = getSupportActionBar();

        /* Initialize recyclerview for display the list of information */
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        /* Initialize SwipeRefreshLayout to refresh the data in the recyclerview on a swipe gesture */
        mSwipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);

        //* Add MyItemDecoration for making divider  in the recycler view
        mRecyclerView.addItemDecoration(new MyItemDecoration());

        /*Downloading data from below url*/
        new AsyncHttpTask().execute(url);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });
    }

    /* This inner class is used to parse the JSON Data from the URL provided above */
    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            actionBar.setTitle(title);
        }
        @Override
        protected Integer doInBackground(String... params) {

            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;
            try {
                /* forming the java.net.URL object */
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                /* for Get request */
                urlConnection.setRequestMethod("GET");
                int statusCode = urlConnection.getResponseCode();
                /* 200 represents HTTP OK */
                if (statusCode ==  200) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }
                    parseResult(response.toString());
                    result = 1; // Successful fetch of data
                }else{
                    result = 0; //"Failed to fetch data!";
                }
            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
            return result; //"Failed to fetch data!";
        }
        @Override
        protected void onPostExecute(Integer result) {

            /* Download complete. Lets update UI */
            if (result == 1) {
                adapter = new RecyclerViewAdapter(HomeActivity.this, rowItemList);
                mRecyclerView.setAdapter(adapter);
                actionBar.setTitle(title);
            } else {
                Log.e(TAG, "Failed to fetch data!");
            }
        }
    }

    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            title=response.optString("title");
            JSONArray posts = response.optJSONArray("rows");
            /*Initialize array if null*/
            if (null == rowItemList) {
                rowItemList = new ArrayList<DataCanada>();
            }
            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                DataCanada item = new DataCanada();
                item.setTitle(post.optString("title").trim());
                item.setDescription(post.optString("description").trim());
                item.setThumbnail(post.optString("imageHref").trim());
                rowItemList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
 /* This method handles the refresh functionality to refresh the recycler view*/
    private void refreshItems(){

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                new AsyncHttpTask().execute(url);
            }
        });
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
