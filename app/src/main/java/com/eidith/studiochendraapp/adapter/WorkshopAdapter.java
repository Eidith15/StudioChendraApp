package com.eidith.studiochendraapp.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
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

public class WorkshopAdapter extends RecyclerView.Adapter<WorkshopAdapter.HolderData>{

    private Context context;
    private List<WorkshopModel> listWorkshop;

    public WorkshopAdapter(Context context, List<WorkshopModel> listWorkshop) {
        this.context = context;
        this.listWorkshop = listWorkshop;
    }

    @NotNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull WorkshopAdapter.HolderData holder, int position) {
        WorkshopModel workshopModel = listWorkshop.get(position);

        holder.tvId.setText(String.valueOf(workshopModel.getId_workshop()));
        holder.tvJudul.setText(workshopModel.getJudul_workshop());
        Glide.with(holder.itemView.getContext())
                .load(RetrofitServer.imagesURL + listWorkshop.get(position).getGambar_workshop())
                .apply(new RequestOptions().override(1000, 400))
                .into(holder.imgGambar);

//        holder.imgGambar.setImageDrawable(res);



    }

    @Override
    public int getItemCount() {
        return listWorkshop.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView tvId, tvJudul, tvDeskripsi, tvGambar, tvVideo;
        ImageView imgGambar;


        public HolderData(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.textId);
            tvJudul = itemView.findViewById(R.id.textJudul);
            imgGambar = itemView.findViewById(R.id.imgGambar);


        }
    }


}
