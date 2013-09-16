package com.example.canteenapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.canteenapp.model.LunchMenuItem;

import java.util.ArrayList;

public class WeekListActivity extends ListActivity {

    private LunchMenuAdapter lunchMenuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lunchMenuAdapter = new LunchMenuAdapter();
        setListAdapter(lunchMenuAdapter);

        //TODO: get this value sent to the activity (using intents)
        String canteen = "Rotvoll";
        lunchMenuAdapter.downloadLunchCourses(canteen);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        //TODO: open activity to show menu item details
        //NB: remember to add that activity to the manifest xml file
    }

    private class LunchMenuAdapter extends BaseAdapter
    {
        private ArrayList<LunchMenuItem> lunches = new ArrayList<LunchMenuItem>();

        @Override
        public int getCount() {
            return lunches.size();
        }

        @Override
        public Object getItem(int i) {
            return lunches.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = getLayoutInflater().inflate(android.R.layout.two_line_list_item, null);
            }

            LunchMenuItem lunchMenuItem = lunches.get(i);

            TextView weekdayName = (TextView)view.findViewById(android.R.id.text1);
            weekdayName.setText(lunchMenuItem.getWeekDayName());

            TextView courseName = (TextView)view.findViewById(android.R.id.text2);
            courseName.setText(lunchMenuItem.getCourseName());

            return view;
        }

        public void downloadLunchCourses(String canteen) {
            //TODO: download list using REST API
            lunches.add(new LunchMenuItem("Monday", "Chicken"));
            lunches.add(new LunchMenuItem("Tuesday", "Pasta"));
            lunches.add(new LunchMenuItem("Wednesday", "Fish"));
            lunches.add(new LunchMenuItem("Thursday", "Salmon"));
            lunches.add(new LunchMenuItem("Friday", "Beef"));

            //Notifies the view (observer) that the adapter state has changed
            notifyDataSetInvalidated();
        }
    }
}
