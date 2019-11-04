package io.alensnajder.gatekeeper.ui.auth.signup;

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

public class SignUpViewModel extends ViewModel {

    private UserRepository userRepository;

    private CompositeDisposable disposable;
    private final MutableLiveData<LiveHolder> registration = new MutableLiveData<>();

    @Inject
    public SignUpViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.disposable = new CompositeDisposable();
    }

    public MutableLiveData<LiveHolder> getRegistration() {
        return registration;
    }

    public void signUp(String email,
                       String firstName,
                       String lastName,
                       String password,
                       String confirmPassword) {
        disposable.add(userRepository.createUser(email, firstName, lastName, password, confirmPassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<User>() {
                    @Override
                    public void onSuccess(User user) {
                        registration.setValue(LiveHolder.success(user));
                    }

                    @Override
                    public void onError(Throwable e) {
                        registration.setValue(LiveHolder.error(e.getMessage()));
                    }
                }));
    }
}
