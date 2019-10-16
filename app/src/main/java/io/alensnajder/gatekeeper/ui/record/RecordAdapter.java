package io.alensnajder.gatekeeper.ui.record;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.alensnajder.gatekeeper.R;
import io.alensnajder.gatekeeper.data.model.Record;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecordViewHolder> {

    private List<Record> records;

    public RecordAdapter() {
        this.records = new ArrayList<>();
    }

    public void setRecords(List<Record> gates) {
        this.records = gates;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_records, parent, false);
        RecordViewHolder viewHolder = new RecordViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        Record record = records.get(position);
        holder.tvGateName.setText(record.getGate().getName());
        holder.tvUserName.setText(record.getUser().getFullName());
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public static class RecordViewHolder extends RecyclerView.ViewHolder {

        TextView tvGateName;
        TextView tvUserName;
        TextView tvDate;

        public RecordViewHolder(View itemView) {
            super(itemView);
            tvGateName = itemView.findViewById(R.id.tvGateName);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
