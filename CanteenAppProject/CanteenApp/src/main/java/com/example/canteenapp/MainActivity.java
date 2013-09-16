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

import java.util.ArrayList;
import java.util.logging.Logger;

public class MainActivity extends ListActivity {

    private CanteenListAdapter canteenListAdapter;
    public static final String TAG = MainActivity.class.getSimpleName();

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
        canteenListAdapter.downloadCanteens();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        System.out.println("Item clicked: " + position);

        //TODO: Send the selected canteen id(name) to the intent
        Intent weekListIntent = new Intent(MainActivity.this, WeekListActivity.class);
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
        ArrayList<String> canteens = new ArrayList<String>();

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

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, null);
            }

            TextView titleView = (TextView)view.findViewById(android.R.id.text1);
            titleView.setText(canteens.get(i));

            return view;
        }

        public void downloadCanteens() {
            //TODO: download list using REST API
            canteens.add("Rotvoll");
            canteens.add("Stavanger");

            //Notifies the view (observer) that the adapter state has changed
            notifyDataSetChanged();
        }
    }
}
