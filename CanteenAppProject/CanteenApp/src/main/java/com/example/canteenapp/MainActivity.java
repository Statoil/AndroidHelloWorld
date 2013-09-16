package com.example.canteenapp;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.canteenapp.model.CanteenItem;
import com.example.canteenapp.task.CanteensFetcher;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MainActivity extends ListActivity {

    private CanteenListAdapter canteenListAdapter;
    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String CANTEEN_ITEM = "canteenItem";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "Creating MainActivity view");
        View headerView = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, null);
        TextView headerText = (TextView)headerView.findViewById(android.R.id.text1);
        headerText.setText("Canteens");
        getListView().addHeaderView(headerView, null, false);

        canteenListAdapter = new CanteenListAdapter();
        setListAdapter(canteenListAdapter);
        CanteensFetcher canteenFetcher = new CanteensFetcher(canteenListAdapter);
        canteenFetcher.execute();

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        System.out.println("Item clicked: " + position);
        CanteenItem canteenItem = canteenListAdapter.getList().get(position);

        // We construct a "messaging" object called "Intent" here.
        // This intent tells Android to start the 'WeekListActivity'
        Intent weekListIntent = new Intent(MainActivity.this, WeekListActivity.class);

        // We also pass the 'CanteenItem' object the user selected
        // from the list.
        weekListIntent.putExtra(CANTEEN_ITEM, canteenItem);

        // Here we tell Android to execute our Intent / message
        startActivity(weekListIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public class CanteenListAdapter extends BaseAdapter
    {
        ArrayList<CanteenItem> canteens = new ArrayList<CanteenItem>();

        @Override
        public int getCount() {
            return canteens.size();
        }

        @Override
        public Object getItem(int i) {
            return canteens.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        public List<CanteenItem> getList() {
            return canteens;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, null);
            }

            TextView titleView = (TextView)view.findViewById(android.R.id.text1);
            CanteenItem canteenItem = canteens.get(i);
            titleView.setText(canteenItem.getName());

            return view;
        }
    }
}
