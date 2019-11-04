package io.alensnajder.gatekeeper.ui.gate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import io.alensnajder.gatekeeper.R;
import io.alensnajder.gatekeeper.data.model.Gate;

public class GateFragment extends DaggerFragment implements GateAdapter.OnLongItemClickListener {

    private RecyclerView rvGates;
    private GateAdapter gateAdapter;
    private ProgressBar progressBar;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private GateViewModel gateViewModel;

    public static GateFragment newInstance() {
        return new GateFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gate, container, false);
        rvGates = rootView.findViewById(R.id.rvGates);
        progressBar = rootView.findViewById(R.id.progress_bar);

        gateAdapter = new GateAdapter(this);
        rvGates.setAdapter(gateAdapter);
        rvGates.setLayoutManager(new LinearLayoutManager(getContext()));
        rvGates.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        gateViewModel = ViewModelProviders.of(this, viewModelFactory).get(GateViewModel.class);

        fetchGates();
        onGates();
        onCreateRecord();
    }

    private void fetchGates() {
        rvGates.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        gateViewModel.fetchGates();
    }

    private void onGates() {
        gateViewModel.getGatesLive().observe(this, gatesHolder -> {
            progressBar.setVisibility(View.GONE);
            rvGates.setVisibility(View.VISIBLE);
            switch (gatesHolder.status) {
                case SUCCESS:
                    gateAdapter.setGates((List<Gate>) gatesHolder.data);
                    break;
                case ERROR:
                    Snackbar.make(getView(), gatesHolder.errorMessage, Snackbar.LENGTH_LONG).show();
                    break;
            }
        });
    }

    private void onCreateRecord() {
        gateViewModel.getCreateRecordLive().observe(this, createRecordHolder -> {
            switch (createRecordHolder.status) {
                case SUCCESS:
                    Snackbar.make(getView(), "Success", Snackbar.LENGTH_LONG).show();
                    break;
                case ERROR:
                    Snackbar.make(getView(), createRecordHolder.errorMessage, Snackbar.LENGTH_LONG).show();
                    break;
            }
        });
    }

    @Override
    public void onLongItemClick(Gate gate) {
        gateViewModel.createRecord(gate.getId());
    }
}
