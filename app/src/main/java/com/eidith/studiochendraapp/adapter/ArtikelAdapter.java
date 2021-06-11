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
import com.eidith.studiochendraapp.model.ArtikelModel;
import com.eidith.studiochendraapp.model.WorkshopModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ArtikelAdapter extends RecyclerView.Adapter<ArtikelAdapter.ViewHolder>{

    private Context context;
    private List<ArtikelModel> listArtikel;
    private OnItemClickListener mOnItemClickListener;

    public ArtikelAdapter(Context context, List<ArtikelModel> listArtikel, OnItemClickListener mOnItemClickListener) {
        //Assign default value
        this.context = context;
        this.listArtikel = listArtikel;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @NotNull
    @Override
    public ArtikelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //set item xml to view holder recycler view
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artikel, parent, false);
        ViewHolder holder = new ViewHolder(layout, mOnItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get item data position
        ArtikelModel artikelModel = listArtikel.get(position);

        //set item data to view
        holder.tvId.setText(String.valueOf(artikelModel.getId_artikel()));
        holder.tvJudul.setText(artikelModel.getJudul_artikel());
        Glide.with(holder.itemView.getContext())
                .load(RetrofitServer.imageURL + listArtikel.get(position).getGambar_artikel())
                .apply(new RequestOptions().override(800, 400))
                .into(holder.imgGambar);
        holder.tvTanggal.setText(String.valueOf(artikelModel.getTanggal_artikel()));

    }

    @Override
    public int getItemCount() {
        return listArtikel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvId, tvJudul;
        ImageView imgGambar;
        TextView tvTanggal;

        OnItemClickListener onItemClickListener;

        public ViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            //Assign value
            tvId = itemView.findViewById(R.id.textIdArtikel);
            tvJudul = itemView.findViewById(R.id.textJudulArtikel);
            imgGambar = itemView.findViewById(R.id.imgGambarArtikel);
            tvTanggal = itemView.findViewById(R.id.tvTanggalArtikel);
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
