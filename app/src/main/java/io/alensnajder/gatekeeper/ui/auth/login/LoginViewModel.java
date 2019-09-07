package io.alensnajder.gatekeeper.ui.auth.login;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import io.alensnajder.gatekeeper.data.model.Auth;
import io.alensnajder.gatekeeper.data.repository.AuthRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends ViewModel {

    private AuthRepository authRepository;

    private CompositeDisposable disposable;

    @Inject
    public LoginViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
        disposable = new CompositeDisposable();
    }

    public void login(String email, String password) {
        disposable.add(authRepository.getAccessTokenByPassword(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Auth>() {
                    @Override
                    public void onSuccess(Auth auth) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));

    }

}
