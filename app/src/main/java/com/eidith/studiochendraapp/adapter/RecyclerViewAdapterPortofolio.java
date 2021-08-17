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
import com.eidith.studiochendraapp.model.PortofolioModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RecyclerViewAdapterPortofolio extends RecyclerView.Adapter<RecyclerViewAdapterPortofolio.ViewHolder> {

    private final Context context;
    private final List<PortofolioModel> listPortofolio;
    private final RecyclerViewAdapterPortofolio.OnItemClickListener mOnItemClickListener;

    public RecyclerViewAdapterPortofolio(Context context, List<PortofolioModel> listPortofolio, RecyclerViewAdapterPortofolio.OnItemClickListener mOnItemClickListener) {
        //Assign default value
        this.context = context;
        this.listPortofolio = listPortofolio;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @NotNull
    @Override
    public RecyclerViewAdapterPortofolio.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //set item xml to view holder recycler view
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_portofolio, parent, false);
        RecyclerViewAdapterPortofolio.ViewHolder holder = new RecyclerViewAdapterPortofolio.ViewHolder(layout, mOnItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterPortofolio.ViewHolder holder, int position) {
        //get item data position
        PortofolioModel portofolioModel = listPortofolio.get(position);

        //set item data to view
        holder.tvIdPortofolio.setText(String.valueOf(portofolioModel.getId_portofolio()));
        Glide.with(holder.itemView.getContext())
                .load(APIClient.imageURL + listPortofolio.get(position).getGambar_foto())
                .apply(new RequestOptions().override(1280, 720))
                .into(holder.imgGambarPortofolio);

    }

    @Override
    public int getItemCount() {
        return listPortofolio.size();
    }

    public interface OnItemClickListener {
        void OnItemClickPortofolio(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvIdPortofolio;
        ImageView imgGambarPortofolio;

        RecyclerViewAdapterPortofolio.OnItemClickListener onItemClickListener;

        public ViewHolder(@NonNull View itemView, RecyclerViewAdapterPortofolio.OnItemClickListener onItemClickListener) {
            super(itemView);
            //Assign value
            tvIdPortofolio = itemView.findViewById(R.id.textIdPortofolio);
            imgGambarPortofolio = itemView.findViewById(R.id.imgGambarPortofolio);
            this.onItemClickListener = onItemClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.OnItemClickPortofolio(getAdapterPosition());
        }
    }

}
