package io.alensnajder.gatekeeper.ui.gate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.alensnajder.gatekeeper.R;
import io.alensnajder.gatekeeper.data.model.Gate;

public class GateAdapter extends RecyclerView.Adapter<GateAdapter.ViewHolder> {

    private List<Gate> gates;
    private final OnLongItemClickListener listener;

    public GateAdapter(OnLongItemClickListener listener) {
        this.gates = new ArrayList<>();
        this.listener = listener;
    }

    public void setGates(List<Gate> gates) {
        this.gates = gates;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gates, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(gates.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return gates.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvGateName;

        public ViewHolder(View itemView) {
            super(itemView);
            tvGateName = itemView.findViewById(R.id.tvGateName);
        }

        public void bind(final Gate gate, final OnLongItemClickListener listener) {
            tvGateName.setText(gate.getName());
            itemView.setOnLongClickListener(v -> {
                listener.onLongItemClick(gate);
                return true;
            });
        }
    }

    public interface OnLongItemClickListener {
        void onLongItemClick(Gate gate);
    }
}
