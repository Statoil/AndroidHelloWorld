package com.example.canteenapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canteenapp.model.CanteenItem;
import com.example.canteenapp.model.LunchMenuItem;
import com.example.canteenapp.task.WeekMenuFetcher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WeekListActivity extends ListActivity {

    private LunchMenuAdapter lunchMenuAdapter;
    public static final String TAG = WeekListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if(intent==null) {
            Log.e(TAG, "Intent was null. Something is wrong!");
            Toast.makeText(getApplicationContext(),
                    "Something horribly happened..",
                    Toast.LENGTH_LONG).show();
            return;
        }

        CanteenItem canteenItem = (CanteenItem) intent.getSerializableExtra(MainActivity.CANTEEN_ITEM);

        lunchMenuAdapter = new LunchMenuAdapter();
        setListAdapter(lunchMenuAdapter);

        // TODO: Start a new AsyncTask to download the courses for
        // this particular "canteen"
        WeekMenuFetcher weekMenuFetcher = new WeekMenuFetcher(lunchMenuAdapter, this);
        weekMenuFetcher.execute(canteenItem.getId());
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Toast.makeText(WeekListActivity.this, "Hi there", Toast.LENGTH_LONG).show();
    }

    public class LunchMenuAdapter extends BaseAdapter
    {
        private ArrayList<LunchMenuItem> lunches = new ArrayList<LunchMenuItem>();

        @Override
        public int getCount() {
            return lunches.size();
        }

        @Override
        public LunchMenuItem getItem(int i) {
            return lunches.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        public List<LunchMenuItem> getList() {
            return lunches;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                // If the View item in our ListView has not yet been created,
                // we need to "Inflate" it first.
                view = getLayoutInflater().inflate(android.R.layout.two_line_list_item, null);
            }

            LunchMenuItem lunchMenuItem = lunches.get(i);

            TextView weekdayName = (TextView)view.findViewById(android.R.id.text1);
            weekdayName.setText(lunchMenuItem.getWeekDayName());

            TextView courseName = (TextView)view.findViewById(android.R.id.text2);
            courseName.setText(lunchMenuItem.getCourseName());

            return view;
        }
    }
}
