package io.alensnajder.gatekeeper.ui.main;

import android.os.Bundle;

import dagger.android.support.DaggerAppCompatActivity;
import io.alensnajder.gatekeeper.R;

public class MainActivity extends DaggerAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
