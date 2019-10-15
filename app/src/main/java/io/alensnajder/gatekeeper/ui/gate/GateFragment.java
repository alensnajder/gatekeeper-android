package io.alensnajder.gatekeeper.ui.gate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
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
import io.alensnajder.gatekeeper.vo.LiveHolder;

public class GateFragment extends DaggerFragment {

    private RecyclerView rvGates;
    private GateAdapter gateAdapter;

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

        gateAdapter = new GateAdapter();
        rvGates.setAdapter(gateAdapter);
        rvGates.setLayoutManager(new LinearLayoutManager(getContext()));
        rvGates.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        gateViewModel = ViewModelProviders.of(this, viewModelFactory).get(GateViewModel.class);
        gateViewModel.fetchGates();

        onGates();
    }

    private void onGates() {
        gateViewModel.getGatesLive().observe(this, new Observer<LiveHolder>() {
            @Override
            public void onChanged(LiveHolder gatesHolder) {
                switch (gatesHolder.status) {
                    case SUCCESS:
                        gateAdapter.setGates((List<Gate>) gatesHolder.data);
                        break;
                    case ERROR:
                        Snackbar.make(getView(), gatesHolder.errorMessage, Snackbar.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }

}
