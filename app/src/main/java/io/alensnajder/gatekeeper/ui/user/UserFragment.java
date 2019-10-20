package io.alensnajder.gatekeeper.ui.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import io.alensnajder.gatekeeper.R;
import io.alensnajder.gatekeeper.data.model.User;
import io.alensnajder.gatekeeper.vo.LiveHolder;

public class UserFragment extends DaggerFragment implements UserAdapter.OnItemClickListener {

    private RecyclerView rvUsers;
    private UserAdapter userAdapter;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private UserViewModel userViewModel;

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user, container, false);
        rvUsers = rootView.findViewById(R.id.rvUsers);

        userAdapter = new UserAdapter(this);
        rvUsers.setAdapter(userAdapter);
        rvUsers.setLayoutManager(new LinearLayoutManager(getContext()));
        rvUsers.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel.class);
        userViewModel.fetchUsers();

        onUsers();
    }

    private void onUsers() {
        userViewModel.getUsersLive().observe(this, new Observer<LiveHolder>() {
            @Override
            public void onChanged(LiveHolder usersHolder) {
                switch (usersHolder.status) {
                    case SUCCESS:
                        userAdapter.setUsers((List<User>) usersHolder.data);
                        break;
                    case ERROR:
                        Snackbar.make(getView(), usersHolder.errorMessage, Snackbar.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }

    @Override
    public void onItemClick(User user) {
        Navigation.findNavController(getView()).navigate(R.id.userDetailFragment);
    }
}
