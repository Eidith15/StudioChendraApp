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
import com.eidith.studiochendraapp.model.ArtikelModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RecyclerViewAdapterArtikel extends RecyclerView.Adapter<RecyclerViewAdapterArtikel.ViewHolder> {

    private final Context context;
    private final List<ArtikelModel> listArtikel;
    private final OnItemClickListener mOnItemClickListener;

    public RecyclerViewAdapterArtikel(Context context, List<ArtikelModel> listArtikel, OnItemClickListener mOnItemClickListener) {
        //Assign default value
        this.context = context;
        this.listArtikel = listArtikel;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @NotNull
    @Override
    public RecyclerViewAdapterArtikel.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
        holder.tvIdArtikel.setText(String.valueOf(artikelModel.getId_artikel()));
        holder.tvJudulArtikel.setText(artikelModel.getJudul_artikel());
        Glide.with(holder.itemView.getContext())
                .load(APIClient.imageURL + listArtikel.get(position).getGambar_artikel())
                .apply(new RequestOptions().override(1280, 720))
                .into(holder.imgGambarArtikel);
        holder.tvTanggalArtikel.setText(String.valueOf(artikelModel.getTanggal_artikel()));

    }

    @Override
    public int getItemCount() {
        return listArtikel.size();
    }

    public interface OnItemClickListener {
        void OnItemClickArtikel(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvIdArtikel, tvJudulArtikel;
        ImageView imgGambarArtikel;
        TextView tvTanggalArtikel;

        OnItemClickListener onItemClickListener;

        public ViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            //Assign value
            tvIdArtikel = itemView.findViewById(R.id.textIdArtikel);
            tvJudulArtikel = itemView.findViewById(R.id.textJudulArtikel);
            imgGambarArtikel = itemView.findViewById(R.id.imgGambarArtikel);
            tvTanggalArtikel = itemView.findViewById(R.id.tvTanggalArtikel);
            this.onItemClickListener = onItemClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.OnItemClickArtikel(getAdapterPosition());
        }
    }

}
