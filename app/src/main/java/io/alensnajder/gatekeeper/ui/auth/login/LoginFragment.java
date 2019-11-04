package io.alensnajder.gatekeeper.ui.auth.login;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import io.alensnajder.gatekeeper.R;
import io.alensnajder.gatekeeper.ui.main.MainActivity;

public class LoginFragment extends DaggerFragment implements View.OnClickListener {

    private EditText etEmail;
    private EditText etPassword;
    private Button btLogin;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private LoginViewModel loginViewModel;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.title_login);
        etEmail = rootView.findViewById(R.id.etEmail);
        etPassword = rootView.findViewById(R.id.etPassword);
        btLogin = rootView.findViewById(R.id.btLogin);
        btLogin.setOnClickListener(this);
        TextView tvSignUp = rootView.findViewById(R.id.tvSignUp);
        tvSignUp.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loginViewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel.class);

        onAuthentication();
    }

    private void onAuthentication() {
        loginViewModel.getAuthentication().observe(this, authHolder -> {
            btLogin.setEnabled(true);
            switch (authHolder.status) {
                case SUCCESS:
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                    break;
                case ERROR:
                    Snackbar.make(getView(), authHolder.errorMessage, Snackbar.LENGTH_LONG).show();
                    break;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btLogin:
                if (loginViewModel.isReadyToRun()) {
                    btLogin.setEnabled(false);
                    loginViewModel.login(etEmail.getText().toString(), etPassword.getText().toString());
                } else {
                    Snackbar.make(getView(), R.string.error_host_not_set_login, Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.tvSignUp:
                if (loginViewModel.isReadyToRun()) {
                    Navigation.findNavController(getView()).navigate(R.id.signUpFragment);
                } else {
                    Snackbar.make(getView(), R.string.error_host_not_set_signup, Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_login, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_host:
                showHostDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showHostDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.dialog_host_title);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_host, (ViewGroup) getView(), false);
        final EditText etHost = view.findViewById(R.id.etHost);

        String host = loginViewModel.getHost();

        if (host != null) {
            etHost.setText(host);
        }

        builder.setView(view);

        builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
            String newHost = etHost.getText().toString();
            loginViewModel.setHost(newHost);
            dialog.cancel();
        });

        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
