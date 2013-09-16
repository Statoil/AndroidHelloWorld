package com.example.canteenapp.task;

import android.os.AsyncTask;

import com.example.canteenapp.model.LunchMenuItem;

import java.util.ArrayList;

/**
 * Created by asbjorn on 9/16/13.
 */
public class WeekMenuFetcher extends AsyncTask<Void, ArrayList<LunchMenuItem>, ArrayList<LunchMenuItem>> {
    public static final String TAG = WeekMenuFetcher.class.getSimpleName();
    public static final String url = "http://canteen-app.herokuapp.com/canteens/";

    WeekMenuFetcher() {

    }

    private final String getUrl(String canteenName) {
        return url+canteenName;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<LunchMenuItem> doInBackground(Void... voids) {
        return null;
    }


}
