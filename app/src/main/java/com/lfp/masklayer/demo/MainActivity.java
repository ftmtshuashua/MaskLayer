package com.lfp.masklayer.demo;

import android.os.Bundle;
import android.view.View;


import com.lfp.masklayer.demo.test.Layer1;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.view_Button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        new Layer1(this).show();
    }
}
