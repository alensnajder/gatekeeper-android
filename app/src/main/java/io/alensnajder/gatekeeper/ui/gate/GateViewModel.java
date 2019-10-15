package io.alensnajder.gatekeeper.ui.gate;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import io.alensnajder.gatekeeper.data.model.Gate;
import io.alensnajder.gatekeeper.data.repository.GateRepository;
import io.alensnajder.gatekeeper.vo.LiveHolder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class GateViewModel extends ViewModel {

    private GateRepository gateRepository;

    private CompositeDisposable disposable;
    private final MutableLiveData<LiveHolder> gatesLive = new MutableLiveData<>();

    @Inject
    public GateViewModel(GateRepository gateRepository) {
        this.gateRepository = gateRepository;
        this.disposable = new CompositeDisposable();
    }

    public MutableLiveData<LiveHolder> getGatesLive() {
        return gatesLive;
    }

    public void fetchGates() {
        disposable.add(gateRepository.getGates()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Gate>>() {
                    @Override
                    public void onSuccess(List<Gate> gates) {
                        gatesLive.setValue(LiveHolder.success(gates));
                    }

                    @Override
                    public void onError(Throwable e) {
                        gatesLive.setValue(LiveHolder.error(e.getMessage()));
                    }
                }));
    }
}
