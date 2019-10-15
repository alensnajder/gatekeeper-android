package io.alensnajder.gatekeeper.ui.auth.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
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
import io.alensnajder.gatekeeper.ui.auth.AuthActivity;
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
                loginViewModel.login(etEmail.getText().toString(), etPassword.getText().toString());
                break;
            case R.id.tvSignUp:
                Navigation.findNavController(getView()).navigate(R.id.signUpFragment);
                break;
        }
    }
}
