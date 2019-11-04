package io.alensnajder.gatekeeper.data.repository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.alensnajder.gatekeeper.data.model.Gate;
import io.alensnajder.gatekeeper.data.service.GateService;
import io.reactivex.Single;

@Singleton
public class GateRepository {

    private GateService gateService;

    @Inject
    public GateRepository(GateService gateService) {
        this.gateService = gateService;
    }

    public Single<List<Gate>> getGates() {
        return gateService.getGates();
    }
}
