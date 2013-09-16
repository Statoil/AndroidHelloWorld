package com.example.canteenapp.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.example.canteenapp.MainActivity;
import com.example.canteenapp.model.CanteenItem;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asbjorn on 9/16/13.
 *
 * This AsyncTask downloads the JSON data from the backend server. Note that since this class
 * extends AsyncTask we will run in a background thread.
 */
public class CanteensFetcher extends AsyncTask<Void, ArrayList<CanteenItem>, ArrayList<CanteenItem>> {
    public static final String TAG = CanteensFetcher.class.getSimpleName();
    public static final String url = "http://canteen-app.herokuapp.com/canteens";
    private final List<CanteenItem> canteens;
    private final MainActivity.CanteenListAdapter adapter;
    private final Activity activity;
    private ProgressDialog progressDialog;

    public CanteensFetcher(MainActivity.CanteenListAdapter canteenListAdapter, Activity activity) {
        canteens = canteenListAdapter.getList();
        adapter = canteenListAdapter;
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(activity, "Loading..", "Loading canteens", true);
    }

    @Override
    protected ArrayList<CanteenItem> doInBackground(Void... args) {
        ArrayList<CanteenItem> items = new ArrayList<CanteenItem>();
        String jsonFeed = readJSONFeed(url);
        try {
            final JSONArray jsonArray = new JSONArray(jsonFeed);
            final int arraySize = jsonArray.length();
            for(int i=0;i<arraySize;i++) {
                JSONObject canteenObj = jsonArray.getJSONObject(i);
                String canteenId = canteenObj.getString("id");
                String canteenName = canteenObj.getString("name");
                String canteenLink = canteenObj.getString("link");

                CanteenItem canteenItem = new CanteenItem(canteenId, canteenName, canteenLink);
                items.add(canteenItem);
            }

        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage());
        }

        return items;
    }

    protected String readJSONFeed(String URL) {
        Log.d(TAG, "Trying to connect to: "+URL);
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL);
        try {
            HttpResponse response = httpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                inputStream.close();
            } else {
                Log.d("JSON", "Failed to download file");
            }
        } catch (Exception e) {
            Log.d("readJSONFeed", e.getLocalizedMessage());
        }
        return stringBuilder.toString();
    }

    @Override
    protected void onPostExecute(ArrayList<CanteenItem> items) {
        super.onPostExecute(items);
        Log.d(TAG, "Downloaded "+items.size()+" canteens.");
        canteens.clear();
        canteens.addAll(items);

        adapter.notifyDataSetChanged();

        if(progressDialog!=null) {
            progressDialog.dismiss();
        }
    }

}
