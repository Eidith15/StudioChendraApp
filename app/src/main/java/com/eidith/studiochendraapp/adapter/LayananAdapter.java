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
import com.eidith.studiochendraapp.model.LayananModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LayananAdapter extends  RecyclerView.Adapter<LayananAdapter.ViewHolder>{

    private Context context;
    private List<LayananModel> listLayanan;
    private LayananAdapter.OnItemClickListener mOnItemClickListener;

    public LayananAdapter(Context context, List<LayananModel> listLayanan, LayananAdapter.OnItemClickListener mOnItemClickListener) {
        //Assign default value
        this.context = context;
        this.listLayanan = listLayanan;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @NotNull
    @Override
    public LayananAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //set item xml to view holder recycler view
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layanan, parent, false);
        LayananAdapter.ViewHolder holder = new ViewHolder(layout, mOnItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LayananAdapter.ViewHolder holder, int position) {
        //get item data position
        LayananModel layananModel = listLayanan.get(position);

        //set item data to view
        holder.tvId.setText(String.valueOf(layananModel.getId_layanan()));
        Glide.with(holder.itemView.getContext())
                .load(RetrofitServer.imageURL + listLayanan.get(position).getGambar_layanan())
                .apply(new RequestOptions().override(800, 400))
                .into(holder.imgGambar);

    }

    @Override
    public int getItemCount() {
        return listLayanan.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvId;
        ImageView imgGambar;

        LayananAdapter.OnItemClickListener onItemClickListener;

        public ViewHolder(@NonNull View itemView, LayananAdapter.OnItemClickListener onItemClickListener) {
            super(itemView);
            //Assign value
            tvId = itemView.findViewById(R.id.textIdLayanan);
            imgGambar = itemView.findViewById(R.id.imgGambarLayanan);
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
