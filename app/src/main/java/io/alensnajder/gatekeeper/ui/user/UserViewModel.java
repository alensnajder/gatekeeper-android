package io.alensnajder.gatekeeper.ui.user;

import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import io.alensnajder.gatekeeper.data.model.User;
import io.alensnajder.gatekeeper.data.repository.UserRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class UserViewModel extends ViewModel {

    private UserRepository userRepository;
    private CompositeDisposable disposable;

    @Inject
    public UserViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.disposable = new CompositeDisposable();

        fetchUsers();
    }

    public void fetchUsers() {
        disposable.add(userRepository.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<User>>() {
                    @Override
                    public void onSuccess(List<User> userList) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));
    }
}
