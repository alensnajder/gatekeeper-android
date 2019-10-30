package io.alensnajder.gatekeeper.ui.user.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import io.alensnajder.gatekeeper.R;
import io.alensnajder.gatekeeper.data.model.User;
import io.alensnajder.gatekeeper.vo.LiveHolder;

public class UserDetailFragment extends DaggerFragment implements View.OnClickListener {

    private TextView tvFullName;
    private TextView tvEmail;
    private TextView tvStatus;
    private TextView tvRole;
    private Button btStatusToggle;
    private ProgressBar progressBar;
    private ConstraintLayout content;

    private User user;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private UserDetailViewModel userDetailViewModel;

    public static UserDetailFragment newInstance() {
        return new UserDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_detail, container, false);
        setHasOptionsMenu(true);
        progressBar = rootView.findViewById(R.id.progress_bar);
        content = rootView.findViewById(R.id.content);
        tvFullName = rootView.findViewById(R.id.tvFullName);
        tvEmail = rootView.findViewById(R.id.tvEmail);
        tvStatus = rootView.findViewById(R.id.tvStatus);
        tvRole = rootView.findViewById(R.id.tvRole);
        btStatusToggle = rootView.findViewById(R.id.btStatusToggle);
        btStatusToggle.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userDetailViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserDetailViewModel.class);

        fetchUser();
        onUser();
        onUserStatus();
    }

    private void fetchUser() {
        content.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        UserDetailFragmentArgs args = UserDetailFragmentArgs.fromBundle(getArguments());
        userDetailViewModel.fetchUser(args.getUserId());
    }

    private void onUser() {
        userDetailViewModel.getUserLive().observe(this, new Observer<LiveHolder>() {
            @Override
            public void onChanged(LiveHolder userHolder) {
                progressBar.setVisibility(View.GONE);
                content.setVisibility(View.VISIBLE);
                switch (userHolder.status) {
                    case SUCCESS:
                        user = (User) userHolder.data;
                        tvFullName.setText(user.getFullName());
                        tvEmail.setText(user.getEmail());
                        tvStatus.setText((user.isActive()) ? R.string.label_active : R.string.label_inactive);
                        tvRole.setText((user.isAdmin()) ? R.string.label_admin : R.string.label_user);
                        btStatusToggle.setText((user.isActive()) ? R.string.button_deactivate : R.string.button_activate);
                        break;
                    case ERROR:
                        Snackbar.make(getView(), userHolder.errorMessage, Snackbar.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }

    private void onUserStatus() {
        userDetailViewModel.getUserStatusLive().observe(this, new Observer<LiveHolder>() {
            @Override
            public void onChanged(LiveHolder liveHolder) {
                switch (liveHolder.status) {
                    case SUCCESS:
                        user = (User) liveHolder.data;
                        tvStatus.setText((user.isActive()) ? R.string.label_active : R.string.label_inactive);
                        btStatusToggle.setText((user.isActive()) ? R.string.button_deactivate : R.string.button_activate);
                        break;
                    case ERROR:
                        Snackbar.make(getView(), liveHolder.errorMessage, Snackbar.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btStatusToggle:
                userDetailViewModel.updateUserStatus(user.getId(), !user.isActive());
                break;
        }
    }
}
