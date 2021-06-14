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
import com.eidith.studiochendraapp.model.PortofolioModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PortofolioAdapter extends RecyclerView.Adapter<PortofolioAdapter.ViewHolder>{

    private Context context;
    private List<PortofolioModel> listPortofolio;
    private PortofolioAdapter.OnItemClickListener mOnItemClickListener;

    public PortofolioAdapter(Context context, List<PortofolioModel> listPortofolio, PortofolioAdapter.OnItemClickListener mOnItemClickListener) {
        //Assign default value
        this.context = context;
        this.listPortofolio = listPortofolio;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @NotNull
    @Override
    public PortofolioAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //set item xml to view holder recycler view
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_portofolio, parent, false);
        PortofolioAdapter.ViewHolder holder = new PortofolioAdapter.ViewHolder(layout, mOnItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PortofolioAdapter.ViewHolder holder, int position) {
        //get item data position
        PortofolioModel portofolioModel = listPortofolio.get(position);

        //set item data to view
        holder.tvIdPortofolio.setText(String.valueOf(portofolioModel.getId_portofolio()));
        Glide.with(holder.itemView.getContext())
                .load(RetrofitServer.imageURL + listPortofolio.get(position).getGambar_foto())
                .apply(new RequestOptions().override(1280, 720))
                .into(holder.imgGambarPortofolio);

    }

    @Override
    public int getItemCount() {
        return listPortofolio.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvIdPortofolio;
        ImageView imgGambarPortofolio;

        PortofolioAdapter.OnItemClickListener onItemClickListener;

        public ViewHolder(@NonNull View itemView, PortofolioAdapter.OnItemClickListener onItemClickListener) {
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

    public interface OnItemClickListener {
        void OnItemClickPortofolio(int position);
    }

}
