package io.alensnajder.gatekeeper.ui.auth.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import io.alensnajder.gatekeeper.R;
import io.alensnajder.gatekeeper.ui.main.MainActivity;
import io.alensnajder.gatekeeper.vo.LiveHolder;

public class LoginFragment extends DaggerFragment implements View.OnClickListener {

    private EditText etEmail;
    private EditText etPassword;

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
        Button btLogin = rootView.findViewById(R.id.btLogin);
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
        loginViewModel.getAuthentication().observe(this, new Observer<LiveHolder>() {
            @Override
            public void onChanged(LiveHolder authHolder) {
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
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btLogin:
                if (loginViewModel.isReadyToRun()) {
                    loginViewModel.login(etEmail.getText().toString(), etPassword.getText().toString());
                } else {
                    Snackbar.make(getView(), "Set host before sign up", Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.tvSignUp:
                if (loginViewModel.isReadyToRun()) {
                    Navigation.findNavController(getView()).navigate(R.id.signUpFragment);
                } else {
                    Snackbar.make(getView(), "Set host before sign up", Snackbar.LENGTH_LONG).show();
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

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String host = etHost.getText().toString();
                loginViewModel.setHost(host);
                dialog.cancel();
            }
        });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
