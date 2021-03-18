package com.eidith.studiochendraapp.adapter;

import android.content.Context;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringDef;
import androidx.recyclerview.widget.RecyclerView;

import com.eidith.studiochendraapp.R;
import com.eidith.studiochendraapp.model.WorkshopModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.eidith.studiochendraapp.R.drawable.gambar_workshop1;

public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData>{

    private Context ctx;
    private List<WorkshopModel> listWorkshop;

    public AdapterData(Context ctx, List<WorkshopModel> listWorkshop) {
        this.ctx = ctx;
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
    public void onBindViewHolder(@NonNull AdapterData.HolderData holder, int position) {
        WorkshopModel dm = listWorkshop.get(position);

        holder.tvId.setText(String.valueOf(dm.getId_workshop()));
        holder.tvJudul.setText(dm.getJudul_workshop());
//        holder.imgGambar.setImageResource();


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
