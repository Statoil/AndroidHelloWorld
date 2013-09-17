package com.statoil.veryverysimpleapp;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Attach a click listener for the 'click me' button
        Button clickmeBtn = (Button) findViewById(R.id.button1);
        clickmeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "clicked test button");
                Toast.makeText(
                        getApplicationContext(),
                        "You clicked me..",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Get a reference to the ImageView and animationBtn
        final ImageView statoilImg = (ImageView) findViewById(R.id.statoilImage);
        final Button animationBtn = (Button) findViewById(R.id.animationBtn);

        // Load the animation
        final Animation rotate = AnimationUtils.loadAnimation(
                getApplicationContext(),
                R.anim.rotate);

        // Attach a click listener for the Animation button.
        // Will start the animation defined in res/anim/rotate.xml
        animationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "starting animation");
                statoilImg.startAnimation(rotate);
                rotate.reset();
                rotate.start();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
