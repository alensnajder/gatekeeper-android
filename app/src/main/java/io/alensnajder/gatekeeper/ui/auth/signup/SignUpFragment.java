package io.alensnajder.gatekeeper.ui.auth.signup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import io.alensnajder.gatekeeper.R;

public class SignUpFragment extends DaggerFragment implements View.OnClickListener {

    private EditText etEmail;
    private EditText etFirstName;
    private EditText etLastName;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button btSignUp;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private SignUpViewModel signUpViewModel;

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        etEmail = rootView.findViewById(R.id.etEmail);
        etFirstName = rootView.findViewById(R.id.etFirstName);
        etLastName = rootView.findViewById(R.id.etLastName);
        etPassword = rootView.findViewById(R.id.etPassword);
        etConfirmPassword = rootView.findViewById(R.id.etConfirmPassword);
        btSignUp = rootView.findViewById(R.id.btSignUp);
        btSignUp.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        signUpViewModel = ViewModelProviders.of(this, viewModelFactory).get(SignUpViewModel.class);

        onRegistration();
    }

    private void onRegistration() {
        signUpViewModel.getRegistration().observe(this, registrationHolder -> {
            btSignUp.setEnabled(true);
            switch (registrationHolder.status) {
                case SUCCESS:
                    Navigation.findNavController(getView()).navigate(SignUpFragmentDirections.actionSignUpFragmentToSignUpSuccessFragment());
                    break;
                case ERROR:
                    Snackbar.make(getView(), registrationHolder.errorMessage, Snackbar.LENGTH_LONG).show();
                    break;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btSignUp:
                btSignUp.setEnabled(false);
                signUpViewModel.signUp(etEmail.getText().toString(), etFirstName.getText().toString(), etLastName.getText().toString(), etPassword.getText().toString(), etConfirmPassword.getText().toString());
                break;
        }
    }
}
