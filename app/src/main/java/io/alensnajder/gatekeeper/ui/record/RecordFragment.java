package io.alensnajder.gatekeeper.ui.record;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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
import io.alensnajder.gatekeeper.data.model.Record;
import io.alensnajder.gatekeeper.vo.LiveHolder;

public class RecordFragment extends DaggerFragment {

    private RecyclerView rvRecords;
    private RecordAdapter recordAdapter;
    private ProgressBar progressBar;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private RecordViewModel recordViewModel;

    public static RecordFragment newInstance() {
        return new RecordFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_record, container, false);
        rvRecords = rootView.findViewById(R.id.rvRecords);
        progressBar = rootView.findViewById(R.id.progress_bar);

        recordAdapter = new RecordAdapter();
        rvRecords.setAdapter(recordAdapter);
        rvRecords.setLayoutManager(new LinearLayoutManager(getContext()));
        rvRecords.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recordViewModel = ViewModelProviders.of(this, viewModelFactory).get(RecordViewModel.class);

        fetchRecords();
        onRecords();

    }

    private void fetchRecords() {
        rvRecords.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        recordViewModel.fetchRecords();
    }

    private void onRecords() {
        recordViewModel.getRecordsLive().observe(this, new Observer<LiveHolder>() {
            @Override
            public void onChanged(LiveHolder recordsHolder) {
                progressBar.setVisibility(View.GONE);
                rvRecords.setVisibility(View.VISIBLE);
                switch (recordsHolder.status) {
                    case SUCCESS:
                        recordAdapter.setRecords((List<Record>) recordsHolder.data);
                        break;
                    case ERROR:
                        Snackbar.make(getView(), recordsHolder.errorMessage, Snackbar.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }

}
