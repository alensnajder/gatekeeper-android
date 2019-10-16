package io.alensnajder.gatekeeper.ui.record;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import io.alensnajder.gatekeeper.data.model.Record;
import io.alensnajder.gatekeeper.data.repository.RecordRepository;
import io.alensnajder.gatekeeper.vo.LiveHolder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class RecordViewModel extends ViewModel {

    private RecordRepository recordRepository;

    private CompositeDisposable disposable;
    private final MutableLiveData<LiveHolder> recordsLive = new MutableLiveData<>();


    @Inject
    public RecordViewModel(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
        this.disposable = new CompositeDisposable();
    }

    public MutableLiveData<LiveHolder> getRecordsLive() {
        return recordsLive;
    }

    public void fetchRecords() {
        disposable.add(recordRepository.getRecords()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Record>>() {
                    @Override
                    public void onSuccess(List<Record> records) {
                        recordsLive.setValue(LiveHolder.success(records));
                    }

                    @Override
                    public void onError(Throwable e) {
                        recordsLive.setValue(LiveHolder.error(e.getMessage()));
                    }
                }));
    }
}
