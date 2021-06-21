package com.eidith.studiochendraapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eidith.studiochendraapp.R;
import com.eidith.studiochendraapp.model.RegistrasiOrderModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RegistrasiOrderAdapter extends RecyclerView.Adapter<RegistrasiOrderAdapter.ViewHolder> {

    private Context context;
    private List<RegistrasiOrderModel> listRegistrasiOrder;
    private RegistrasiOrderAdapter.OnItemClickListener mOnItemClickListener;

    public RegistrasiOrderAdapter(Context context, List<RegistrasiOrderModel> listRegistrasiOrder, OnItemClickListener mOnItemClickListener) {
        this.context = context;
        this.listRegistrasiOrder = listRegistrasiOrder;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public RegistrasiOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_registrasi_order, parent, false);
        RegistrasiOrderAdapter.ViewHolder holder = new RegistrasiOrderAdapter.ViewHolder(layout, mOnItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RegistrasiOrderAdapter.ViewHolder holder, int position) {
        RegistrasiOrderModel registrasiOrderModel = listRegistrasiOrder.get(position);

        holder.tvRegistrasiId.setText(String.valueOf(registrasiOrderModel.getId_registrasi()));
        holder.tvRegistrasiNama.setText(registrasiOrderModel.getNama_user());
        holder.tvRegistrasiJudul.setText(registrasiOrderModel.getJudul_layanan());
    }


    @Override
    public int getItemCount() {
        return listRegistrasiOrder.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvRegistrasiId, tvRegistrasiNama, tvRegistrasiJudul;
        ImageView ivRegistrasiPic;

        RegistrasiOrderAdapter.OnItemClickListener onItemClickListener;

        public ViewHolder(@NonNull View itemView, RegistrasiOrderAdapter.OnItemClickListener onItemClickListener) {
            super(itemView);
            //Assign value
            tvRegistrasiId = itemView.findViewById(R.id.tvRegistrasiId);
            tvRegistrasiNama = itemView.findViewById(R.id.tvRegistrasiNama);
            tvRegistrasiJudul = itemView.findViewById(R.id.tvRegistrasiJudul);
            ivRegistrasiPic = itemView.findViewById(R.id.ivRegistrasiPic);

            this.onItemClickListener = onItemClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.OnItemClickWorkshop(getAdapterPosition());
        }
    }

    public interface OnItemClickListener {
        void OnItemClickWorkshop(int position);
    }
}
