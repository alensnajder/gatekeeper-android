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

public class GateAdapter extends RecyclerView.Adapter<GateAdapter.GateViewHolder> {

    private List<Gate> gates;

    public GateAdapter() {
        this.gates = new ArrayList<>();
    }

    public void setGates(List<Gate> gates) {
        this.gates = gates;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gates, parent, false);
        GateViewHolder viewHolder = new GateViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GateViewHolder holder, int position) {
        Gate gate = gates.get(position);
        holder.tvGateName.setText(gate.getName());
        holder.tvGateGpio.setText("GPIO: " + gate.getGpioPin());
    }

    @Override
    public int getItemCount() {
        return gates.size();
    }

    public static class GateViewHolder extends RecyclerView.ViewHolder {

        TextView tvGateName;
        TextView tvGateGpio;

        public GateViewHolder(View itemView) {
            super(itemView);
            tvGateName = itemView.findViewById(R.id.tvGateName);
            tvGateGpio = itemView.findViewById(R.id.tvGateGpio);
        }
    }
}
