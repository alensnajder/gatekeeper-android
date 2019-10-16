package io.alensnajder.gatekeeper.data.service;

import java.util.List;

import io.alensnajder.gatekeeper.data.model.Gate;
import io.alensnajder.gatekeeper.data.model.Record;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface RecordService {
    @GET("/v1/records")
    Single<List<Record>> getRecords();
}
