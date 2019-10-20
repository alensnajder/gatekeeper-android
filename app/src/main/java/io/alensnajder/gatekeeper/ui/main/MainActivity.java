package io.alensnajder.gatekeeper.ui.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import io.alensnajder.gatekeeper.R;
import io.alensnajder.gatekeeper.data.AppPreferences;
import io.alensnajder.gatekeeper.network.HostInterceptor;
import io.alensnajder.gatekeeper.ui.auth.AuthActivity;

public class MainActivity extends DaggerAppCompatActivity
        implements SharedPreferences.OnSharedPreferenceChangeListener, NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    @Inject
    AppPreferences appPreferences;
    @Inject
    HostInterceptor hostInterceptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_gate, R.id.nav_record, R.id.nav_user)
                .setDrawerLayout(drawerLayout)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(this);

        isRefreshToken();

        String host = appPreferences.getHost();
        if (host != null) {
            hostInterceptor.setHost(host);
        }
    }

    private void isRefreshToken() {
        String refreshToken = appPreferences.getRefreshToken();

        if (refreshToken == null) {
            Intent intent = new Intent(this, AuthActivity.class);
            startActivity(intent);

            finish();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("PREF_KEY_REFRESH_TOKEN")) {
            String refreshToken = appPreferences.getRefreshToken();

            if (refreshToken == null) {
                Intent intent = new Intent(this, AuthActivity.class);
                startActivity(intent);

                finish();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        menuItem.setChecked(true);
        drawerLayout.closeDrawer(GravityCompat.START);

        switch (menuItem.getItemId()) {
            case R.id.nav_logout:
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                dialogBuilder.setTitle("Logout");
                dialogBuilder.setMessage("Are you sure you want to logout?");

                dialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        appPreferences.setAccessToken(null);
                        appPreferences.setRefreshToken(null);
                        Intent intent = new Intent(getApplicationContext(), AuthActivity.class);
                        startActivity(intent);

                        finish();
                        dialog.cancel();
                    }
                });

                dialogBuilder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                dialogBuilder.show();
                break;
            default:
                navController.navigate(menuItem.getItemId());
                break;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
