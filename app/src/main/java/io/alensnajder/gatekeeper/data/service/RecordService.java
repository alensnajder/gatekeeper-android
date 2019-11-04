package io.alensnajder.gatekeeper.data.service;

import java.util.List;

import io.alensnajder.gatekeeper.data.model.Gate;
import io.alensnajder.gatekeeper.data.model.Record;
import io.alensnajder.gatekeeper.data.model.request.CreateRecordRequest;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RecordService {
    @GET("/v1/records")
    Single<List<Record>> getRecords();

    @POST("/v1/records")
    Single<Record> createRecord(@Body CreateRecordRequest request);
}
