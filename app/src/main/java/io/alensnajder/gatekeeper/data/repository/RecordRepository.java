package io.alensnajder.gatekeeper.data.repository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.alensnajder.gatekeeper.data.model.Record;
import io.alensnajder.gatekeeper.data.service.RecordService;
import io.reactivex.Single;

@Singleton
public class RecordRepository {

    private RecordService recordService;

    @Inject
    public RecordRepository(RecordService recordService) {
        this.recordService = recordService;
    }

    public Single<List<Record>> getRecords() {
        return recordService.getRecords();
    }
}
