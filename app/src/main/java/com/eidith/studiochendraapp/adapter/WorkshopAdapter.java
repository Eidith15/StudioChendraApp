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
import com.eidith.studiochendraapp.api.RetrofitServer;
import com.eidith.studiochendraapp.model.WorkshopModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WorkshopAdapter extends RecyclerView.Adapter<WorkshopAdapter.ViewHolder>{

    private Context context;
    private List<WorkshopModel> listWorkshop;
    private OnItemClickListener mOnItemClickListener;

    public WorkshopAdapter(Context context, List<WorkshopModel> listWorkshop, OnItemClickListener mOnItemClickListener) {
        //Assign default value
        this.context = context;
        this.listWorkshop = listWorkshop;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @NotNull
    @Override
    public WorkshopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
        holder.tvId.setText(String.valueOf(workshopModel.getId_workshop()));
        holder.tvJudul.setText(workshopModel.getJudul_workshop());
        Glide.with(holder.itemView.getContext())
                .load(RetrofitServer.imageURL + listWorkshop.get(position).getGambar_workshop())
                .apply(new RequestOptions().override(1280, 720))
                .into(holder.imgGambar);
        holder.tvTanggal.setText(String.valueOf(workshopModel.getTanggal_workshop()));

    }

    @Override
    public int getItemCount() {
        return listWorkshop.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvId, tvJudul;
        ImageView imgGambar;
        TextView tvTanggal;

        OnItemClickListener onItemClickListener;

        public ViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            //Assign value
            tvId = itemView.findViewById(R.id.textIdWorkshop);
            tvJudul = itemView.findViewById(R.id.textJudulWorkshop);
            imgGambar = itemView.findViewById(R.id.imgGambarWorkshop);
            tvTanggal = itemView.findViewById(R.id.tvTanggalWorkshop);
            this.onItemClickListener = onItemClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.OnItemClick(getAdapterPosition());
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

}
