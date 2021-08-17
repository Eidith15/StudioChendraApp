package com.eidith.studiochendraapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eidith.studiochendraapp.R;
import com.eidith.studiochendraapp.api.APIClient;
import com.eidith.studiochendraapp.model.WorkshopModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RecyclerViewAdapterWorkshop extends RecyclerView.Adapter<RecyclerViewAdapterWorkshop.ViewHolder> {

    private final Context context;
    private final List<WorkshopModel> listWorkshop;
    private final OnItemClickListener mOnItemClickListener;

    public RecyclerViewAdapterWorkshop(Context context, List<WorkshopModel> listWorkshop, OnItemClickListener mOnItemClickListener) {
        //Assign default value
        this.context = context;
        this.listWorkshop = listWorkshop;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @NotNull
    @Override
    public RecyclerViewAdapterWorkshop.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //set item xml to view holder recycler view
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_workshop, parent, false);
        ViewHolder holder = new ViewHolder(layout, mOnItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get item data position
        WorkshopModel workshopModel = listWorkshop.get(position);

        //set item data to view
        holder.tvIdWorkshop.setText(String.valueOf(workshopModel.getId_workshop()));
        holder.tvJudulWorkshop.setText(workshopModel.getJudul_workshop());
        Glide.with(holder.itemView.getContext())
                .load(APIClient.imageURL + listWorkshop.get(position).getGambar_workshop())
                .apply(new RequestOptions().override(1280, 500))
                .into(holder.imgGambarWorkshop);
        holder.tvTanggalWorkshop.setText(String.valueOf(workshopModel.getTanggal_workshop()));

    }

    @Override
    public int getItemCount() {
        return listWorkshop.size();
    }

    public interface OnItemClickListener {
        void OnItemClickWorkshop(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvIdWorkshop, tvJudulWorkshop;
        ImageView imgGambarWorkshop;
        TextView tvTanggalWorkshop;

        OnItemClickListener onItemClickListener;

        public ViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            //Assign value
            tvIdWorkshop = itemView.findViewById(R.id.textIdWorkshop);
            tvJudulWorkshop = itemView.findViewById(R.id.textJudulWorkshop);
            imgGambarWorkshop = itemView.findViewById(R.id.imgGambarWorkshop);
            tvTanggalWorkshop = itemView.findViewById(R.id.tvTanggalWorkshop);
            this.onItemClickListener = onItemClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.OnItemClickWorkshop(getAdapterPosition());
        }
    }

}
