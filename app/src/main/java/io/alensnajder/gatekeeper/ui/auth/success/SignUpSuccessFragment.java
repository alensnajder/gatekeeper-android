package io.alensnajder.gatekeeper.ui.auth.success;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import dagger.android.support.DaggerFragment;
import io.alensnajder.gatekeeper.R;

public class SignUpSuccessFragment extends DaggerFragment implements View.OnClickListener {

    public static SignUpSuccessFragment newInstance() {
        return new SignUpSuccessFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_up_success, container, false);
        Button btToLogin = rootView.findViewById(R.id.btToLogin);
        btToLogin.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btToLogin:
                Navigation.findNavController(getView()).navigate(SignUpSuccessFragmentDirections.actionSignUpSuccessFragmentToLoginFragment());
                break;
        }
    }
}
