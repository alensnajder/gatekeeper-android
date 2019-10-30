package io.alensnajder.gatekeeper.ui.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import io.alensnajder.gatekeeper.R;
import io.alensnajder.gatekeeper.data.model.User;
import io.alensnajder.gatekeeper.ui.auth.AuthActivity;
import io.alensnajder.gatekeeper.vo.LiveHolder;

public class MainActivity extends DaggerAppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    private TextView tvFullName;
    private TextView tvEmail;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);

        if (!mainViewModel.isReadyToRun()) {
            navigateToLogin();
        }

        setupUi();

        mainViewModel.fetchLoggedInUser();
        onUser();
    }

    private void onUser() {
        mainViewModel.getUserLive().observe(this, new Observer<LiveHolder>() {
            @Override
            public void onChanged(LiveHolder userHolder) {
                switch (userHolder.status) {
                    case SUCCESS:
                        setupNavigationHeader((User) userHolder.data);
                        break;
                    case ERROR:
                        Snackbar.make(findViewById(android.R.id.content), userHolder.errorMessage, Snackbar.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }

    private void setupUi() {
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

        View navigationViewHeader = navigationView.getHeaderView(0);
        tvFullName = navigationViewHeader.findViewById(R.id.tvFullName);
        tvEmail = navigationViewHeader.findViewById(R.id.tvEmail);
    }

    private void setupNavigationHeader(User user) {
        tvFullName.setText(user.getFullName());
        tvEmail.setText(user.getEmail());
    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);

        finish();
    }

    private void showLogoutDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle(R.string.dialog_logout_title);
        dialogBuilder.setMessage(R.string.dialog_logout_body);

        dialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mainViewModel.logout();
                navigateToLogin();
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
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        menuItem.setChecked(true);
        drawerLayout.closeDrawer(GravityCompat.START);

        switch (menuItem.getItemId()) {
            case R.id.nav_logout:
                showLogoutDialog();
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
