package io.alensnajder.gatekeeper.ui.auth.signup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import io.alensnajder.gatekeeper.R;

public class SignUpFragment extends DaggerFragment implements View.OnClickListener {

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
        Button btSignUp = rootView.findViewById(R.id.btSignUp);
        btSignUp.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        signUpViewModel = ViewModelProviders.of(this, viewModelFactory).get(SignUpViewModel.class);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btSignUp:
                break;
        }
    }
}
