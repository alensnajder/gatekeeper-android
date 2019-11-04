package io.alensnajder.gatekeeper.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import io.alensnajder.gatekeeper.data.model.User;
import io.alensnajder.gatekeeper.data.repository.UserRepository;
import io.alensnajder.gatekeeper.utils.AccountUtils;
import io.alensnajder.gatekeeper.utils.HostUtils;
import io.alensnajder.gatekeeper.vo.LiveHolder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel {

    private AccountUtils accountUtils;
    private HostUtils hostUtils;
    private UserRepository userRepository;

    private CompositeDisposable disposable;
    private final MutableLiveData<LiveHolder> userLive = new MutableLiveData<>();

    @Inject
    public MainViewModel(AccountUtils accountUtils, HostUtils hostUtils, UserRepository userRepository) {
        this.accountUtils = accountUtils;
        this.hostUtils = hostUtils;
        this.userRepository = userRepository;
        disposable = new CompositeDisposable();
    }

    public MutableLiveData<LiveHolder> getUserLive() {
        return userLive;
    }

    public boolean isReadyToRun() {
        if (accountUtils.isLoggedIn() && accountUtils.isActive() && hostUtils.isHostSet()) {
            hostUtils.setHost();
            return true;
        }

        return false;
    }

    public void logout() {
        accountUtils.handleLogout();
    }

    public void fetchLoggedInUser() {
        int userId = accountUtils.getAccountId();

        if (userId > 0) {
            disposable.add(userRepository.getUser(userId)
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
    }
}
