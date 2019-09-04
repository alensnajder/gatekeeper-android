package io.alensnajder.gatekeeper.ui.auth;

import android.os.Bundle;

import dagger.android.support.DaggerAppCompatActivity;
import io.alensnajder.gatekeeper.R;

public class AuthActivity extends DaggerAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
    }
}
