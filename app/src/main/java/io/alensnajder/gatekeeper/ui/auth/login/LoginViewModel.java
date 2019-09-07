package io.alensnajder.gatekeeper.ui.auth.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import io.alensnajder.gatekeeper.data.AppPreferences;
import io.alensnajder.gatekeeper.data.model.Auth;
import io.alensnajder.gatekeeper.data.repository.AuthRepository;
import io.alensnajder.gatekeeper.vo.LiveHolder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends ViewModel {

    private AuthRepository authRepository;
    private AppPreferences appPreferences;

    private CompositeDisposable disposable;
    private final MutableLiveData<LiveHolder> authentication = new MutableLiveData<>();

    @Inject
    public LoginViewModel(AuthRepository authRepository, AppPreferences appPreferences) {
        this.authRepository = authRepository;
        this.appPreferences = appPreferences;
        disposable = new CompositeDisposable();
    }

    public MutableLiveData<LiveHolder> getAuthentication() {
        return authentication;
    }

    public void login(String email, String password) {
        disposable.add(authRepository.getAccessTokenByPassword(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Auth>() {
                    @Override
                    public void onSuccess(Auth auth) {
                        appPreferences.setAccessToken(auth.getAccessToken());
                        appPreferences.setRefreshToken(auth.getRefreshToken());
                        authentication.setValue(LiveHolder.success(null));
                    }

                    @Override
                    public void onError(Throwable e) {
                        authentication.setValue(LiveHolder.error(e.getMessage()));
                    }
                }));

    }

}
