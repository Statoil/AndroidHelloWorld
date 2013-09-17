package com.statoil.veryverysimpleapp;

import android.app.Activity;
import android.os.Bundle;


/**
 * Created by asbjorn on 9/17/13.
 */
public class BasicActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic);
    }
}
