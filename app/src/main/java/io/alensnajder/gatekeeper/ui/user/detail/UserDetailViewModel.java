package io.alensnajder.gatekeeper.ui.user.detail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import io.alensnajder.gatekeeper.data.model.User;
import io.alensnajder.gatekeeper.data.repository.UserRepository;
import io.alensnajder.gatekeeper.vo.LiveHolder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class UserDetailViewModel extends ViewModel {

    private UserRepository userRepository;

    private CompositeDisposable disposable;
    private final MutableLiveData<LiveHolder> userLive = new MutableLiveData<>();

    @Inject
    public UserDetailViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
        disposable = new CompositeDisposable();
    }

    public MutableLiveData<LiveHolder> getUserLive() {
        return userLive;
    }

    public void fetchUser(int id) {
        disposable.add(userRepository.getUser(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<User>() {
                    @Override
                    public void onSuccess(User user) {
                        userLive.setValue(LiveHolder.success(user));
                    }

                    @Override
                    public void onError(Throwable e) {
                        userLive.setValue(LiveHolder.error(e.getMessage()));
                    }
                }));
    }

    public void removeUser(int id) {
        disposable.add(userRepository.removeUser(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        int i = 1 + 1;
                    }

                    @Override
                    public void onError(Throwable e) {
                        int i = 1 + 1;
                    }
                }));
    }
}
