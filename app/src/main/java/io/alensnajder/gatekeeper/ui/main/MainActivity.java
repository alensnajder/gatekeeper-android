package io.alensnajder.gatekeeper.ui.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import io.alensnajder.gatekeeper.R;
import io.alensnajder.gatekeeper.data.AppPreferences;
import io.alensnajder.gatekeeper.ui.auth.AuthActivity;

public class MainActivity extends DaggerAppCompatActivity {

    @Inject
    AppPreferences appPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        isRefreshToken();
    }

    private void isRefreshToken() {
        String refreshToken = appPreferences.getRefreshToken();

        if (refreshToken == null) {
            Intent intent = new Intent(this, AuthActivity.class);
            startActivity(intent);

            finish();
        }
    }
}
