package com.eidith.studiochendraapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eidith.studiochendraapp.R;
import com.eidith.studiochendraapp.api.APIClient;
import com.eidith.studiochendraapp.model.LayananModel;

import java.util.List;

public class SpinnerAdapterLayanan extends ArrayAdapter<LayananModel> {

    private final LayoutInflater layoutInflater;
    private final List<LayananModel> listLayanan;

    public SpinnerAdapterLayanan(Context context, int resource, List<LayananModel> listLayanan) {
        super(context, resource, listLayanan);
        this.layoutInflater = LayoutInflater.from(context);
        this.listLayanan = listLayanan;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = layoutInflater.inflate(R.layout.dropdown_layanan, null, true);
        LayananModel layananModel = listLayanan.get(position);
        TextView tvIdLayanan = rowView.findViewById(R.id.tvDropdownIdLayanan);
        ImageView imgLayanan = rowView.findViewById(R.id.ivDropdownLayanan);
        tvIdLayanan.setText(String.valueOf(layananModel.getId_layanan()));
        Glide.with(rowView.getContext())
                .load(APIClient.imageURL + layananModel.getGambar_layanan())
                .apply(new RequestOptions().override(1280, 500))
                .into(imgLayanan);
        return rowView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = layoutInflater.inflate(R.layout.dropdown_layanan, parent, false);
        LayananModel layananModel = listLayanan.get(position);
        TextView tvIdLayanan = convertView.findViewById(R.id.tvDropdownIdLayanan);
        ImageView imgLayanan = convertView.findViewById(R.id.ivDropdownLayanan);
        tvIdLayanan.setText(String.valueOf(layananModel.getId_layanan()));
        Glide.with(convertView.getContext())
                .load(APIClient.imageURL + layananModel.getGambar_layanan())
                .apply(new RequestOptions().override(1280, 500))
                .into(imgLayanan);
        return convertView;
    }
}
