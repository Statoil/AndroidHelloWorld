package com.example.canteenapp.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.example.canteenapp.WeekListActivity;
import com.example.canteenapp.model.CanteenItem;
import com.example.canteenapp.model.LunchMenuItem;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asbjorn on 9/16/13.
 */
public class WeekMenuFetcher extends AsyncTask<String, ArrayList<LunchMenuItem>, ArrayList<LunchMenuItem>> {
    public static final String TAG = WeekMenuFetcher.class.getSimpleName();
    public static final String url = "http://canteen-app.herokuapp.com/canteens/";
    private final WeekListActivity.LunchMenuAdapter adapter;
    private final List<LunchMenuItem> lunches;
    private final Activity activity;
    private ProgressDialog progressDialog;

    public static final String MONDAY = "monday";
    public static final String THURSDAY = "thursday";
    public static final String WEDNESDAY = "wednesday";
    public static final String TUESDAY = "tuesday";
    public static final String FRIDAY = "friday";

    public WeekMenuFetcher(WeekListActivity.LunchMenuAdapter adapter, Activity activity) {
        this.adapter = adapter;
        this.lunches = adapter.getList();
        this.activity = activity;
    }

    private final String getUrl(String canteenName) {
        return url+canteenName;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(activity, "Loading..", "Loading menu", true);
    }


    @Override
    protected ArrayList<LunchMenuItem> doInBackground(String... canteens) {
        if(canteens.length==0) {
            Log.e(TAG, "No 'canteen id' forwarded to "+WeekMenuFetcher.class.getSimpleName());
            return new ArrayList<LunchMenuItem>();
        }
        ArrayList<LunchMenuItem> items = new ArrayList<LunchMenuItem>();
        final String expandedUrl = getUrl(canteens[0]);
        String jsonFeed = readJSONFeed(expandedUrl);

        try {
            final JSONObject jsonObj = new JSONObject(jsonFeed);

            items.add(createLunchMenuItem(jsonObj, MONDAY));
            items.add(createLunchMenuItem(jsonObj, THURSDAY));
            items.add(createLunchMenuItem(jsonObj, WEDNESDAY));
            items.add(createLunchMenuItem(jsonObj, TUESDAY));
            items.add(createLunchMenuItem(jsonObj, FRIDAY));

        } catch (JSONException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }

        return items;
    }


    public String getLunchForWeekday(final JSONObject jsonObj, String day) throws JSONException {
        final JSONObject weekdayObj = jsonObj.getJSONObject(day);
        if(weekdayObj==null) {
            Log.e(TAG, "Could not find 'lunch' for day: "+day);
            return "";
        }

        JSONArray jsonArray = weekdayObj.getJSONArray("lunch");
        final int size = jsonArray.length();
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<size;i++) {
            JSONObject lunchItem = jsonArray.getJSONObject(i);
            String lunchItemName = lunchItem.getString("name");
            sb.append(lunchItemName+" ");
        }

        return sb.toString();
    }

    public LunchMenuItem createLunchMenuItem(JSONObject jsonObj, String day) throws JSONException {
        String lunch = getLunchForWeekday(jsonObj, day);
        return new LunchMenuItem(day, lunch);
    }


    protected String readJSONFeed(String URL) {
        Log.d(TAG, "Trying to connect to: " + URL);
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
    protected void onPostExecute(ArrayList<LunchMenuItem> lunchMenuItems) {
        Log.d(TAG, "Downloaded "+lunchMenuItems.size()+" lunch menu items");

        lunches.clear();
        lunches.addAll(lunchMenuItems);
        adapter.notifyDataSetChanged();

        if(progressDialog!=null) {
            progressDialog.dismiss();
        }
    }
}
