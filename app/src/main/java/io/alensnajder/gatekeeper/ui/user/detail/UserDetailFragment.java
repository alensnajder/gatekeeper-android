package io.alensnajder.gatekeeper.ui.user.detail;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import io.alensnajder.gatekeeper.R;
import io.alensnajder.gatekeeper.data.model.User;
import io.alensnajder.gatekeeper.ui.main.MainActivity;
import io.alensnajder.gatekeeper.vo.LiveHolder;

public class UserDetailFragment extends DaggerFragment implements View.OnClickListener {

    private TextView tvFullName;
    private TextView tvEmail;
    private TextView tvStatus;
    private TextView tvRole;

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
        tvFullName = rootView.findViewById(R.id.tvFullName);
        tvEmail = rootView.findViewById(R.id.tvEmail);
        tvStatus = rootView.findViewById(R.id.tvStatus);
        tvRole = rootView.findViewById(R.id.tvRole);
        Button btStatusToggle = rootView.findViewById(R.id.btStatusToggle);
        btStatusToggle.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        UserDetailFragmentArgs args = UserDetailFragmentArgs.fromBundle(getArguments());
        userDetailViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserDetailViewModel.class);
        userDetailViewModel.fetchUser(args.getUserId());
        onUser();
    }

    private void onUser() {
        userDetailViewModel.getUserLive().observe(this, new Observer<LiveHolder>() {
            @Override
            public void onChanged(LiveHolder userHolder) {
                switch (userHolder.status) {
                    case SUCCESS:
                        user = (User) userHolder.data;
                        tvFullName.setText(user.getFullName());
                        tvEmail.setText(user.getEmail());
                        //tvStatus.setText((user.isActive()) ? "Active" : "Inactive");
                        break;
                    case ERROR:
                        Snackbar.make(getView(), userHolder.errorMessage, Snackbar.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_user_details, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_remove:
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                dialogBuilder.setTitle("Remove");
                dialogBuilder.setMessage("Are you sure you want to remove this item?");

                dialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userDetailViewModel.removeUser(user.getId());
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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btStatusToggle:
                break;
        }
    }
}
