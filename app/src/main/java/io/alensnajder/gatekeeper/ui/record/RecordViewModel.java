package io.alensnajder.gatekeeper.ui.record;

import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import io.alensnajder.gatekeeper.data.model.Record;
import io.alensnajder.gatekeeper.data.repository.RecordRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class RecordViewModel extends ViewModel {

    private RecordRepository recordRepository;

    private CompositeDisposable disposable;

    @Inject
    public RecordViewModel(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
        this.disposable = new CompositeDisposable();

        disposable.add(recordRepository.getRecords()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Record>>() {
                    @Override
                    public void onSuccess(List<Record> records) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));
    }
}
