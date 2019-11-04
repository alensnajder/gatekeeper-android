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

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {

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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_records, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(records.get(position));
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvGateName;
        TextView tvUserName;
        TextView tvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            tvGateName = itemView.findViewById(R.id.tvGateName);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvDate = itemView.findViewById(R.id.tvDate);
        }

        public void bind(final Record record) {
            tvGateName.setText(record.getGate().getName());
            tvUserName.setText(record.getUser().getFullName());
            tvDate.setText(record.getCreatedAtString());
        }
    }
}
