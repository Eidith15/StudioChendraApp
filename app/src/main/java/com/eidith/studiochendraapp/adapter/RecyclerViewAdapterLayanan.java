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
import com.eidith.studiochendraapp.model.LayananModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RecyclerViewAdapterLayanan extends RecyclerView.Adapter<RecyclerViewAdapterLayanan.ViewHolder> {

    private final Context context;
    private final List<LayananModel> listLayanan;
    private final RecyclerViewAdapterLayanan.OnItemClickListener mOnItemClickListener;

    public RecyclerViewAdapterLayanan(Context context, List<LayananModel> listLayanan, RecyclerViewAdapterLayanan.OnItemClickListener mOnItemClickListener) {
        //Assign default value
        this.context = context;
        this.listLayanan = listLayanan;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @NotNull
    @Override
    public RecyclerViewAdapterLayanan.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //set item xml to view holder recycler view
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layanan, parent, false);
        RecyclerViewAdapterLayanan.ViewHolder holder = new ViewHolder(layout, mOnItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterLayanan.ViewHolder holder, int position) {
        //get item data position
        LayananModel layananModel = listLayanan.get(position);

        //set item data to view
        holder.tvIdLayanan.setText(String.valueOf(layananModel.getId_layanan()));
        Glide.with(holder.itemView.getContext())
                .load(APIClient.imageURL + listLayanan.get(position).getGambar_layanan())
                .apply(new RequestOptions().override(1280, 500))
                .into(holder.imgGambarLayanan);

    }

    @Override
    public int getItemCount() {
        return listLayanan.size();
    }

    public interface OnItemClickListener {
        void OnItemClickLayanan(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvIdLayanan;
        ImageView imgGambarLayanan;

        RecyclerViewAdapterLayanan.OnItemClickListener onItemClickListener;

        public ViewHolder(@NonNull View itemView, RecyclerViewAdapterLayanan.OnItemClickListener onItemClickListener) {
            super(itemView);
            //Assign value
            tvIdLayanan = itemView.findViewById(R.id.textIdLayanan);
            imgGambarLayanan = itemView.findViewById(R.id.imgGambarLayanan);
            this.onItemClickListener = onItemClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.OnItemClickLayanan(getAdapterPosition());
        }
    }

}
