package io.alensnajder.gatekeeper.data.service;

import java.util.List;

import io.alensnajder.gatekeeper.data.model.Gate;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface GateService {
    @GET("/v1/gates")
    Single<List<Gate>> getGates();
}
