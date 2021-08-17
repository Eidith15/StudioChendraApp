package com.eidith.studiochendraapp.activity.portofolio;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eidith.studiochendraapp.R;
import com.eidith.studiochendraapp.api.APIClient;
import com.github.chrisbanes.photoview.PhotoView;


public class DetailPortofolioActivity extends AppCompatActivity {

    private PhotoView ivDetailPortofolio;
    private TextView tvDetailJudulPortofolio, tvDetailDeskripsiPortofolio;
    private LinearLayout llDetailPortofolio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_portofolio);

        //Assign Variable
        llDetailPortofolio = findViewById(R.id.llDetailPortofolio);
        ivDetailPortofolio = findViewById(R.id.ivDetailPortofolio);
        tvDetailJudulPortofolio = findViewById(R.id.tvDetailJudulPortofolio);
        tvDetailDeskripsiPortofolio = findViewById(R.id.tvDetailDeskripsiPortofolio);

        //Get data from extras
        String judulFoto = getIntent().getExtras().getString("Judul Foto");
        String deskripsiFoto = getIntent().getExtras().getString("Deskripsi Foto");
        String gambarFoto = getIntent().getExtras().getString("Gambar Foto");

        //Set data to view
        tvDetailJudulPortofolio.setText(judulFoto);
        tvDetailDeskripsiPortofolio.setText(deskripsiFoto);


        //Glide Image to image view
        Glide.with(DetailPortofolioActivity.this)
                .load(APIClient.imageURL + gambarFoto)
                .apply(new RequestOptions().override(800, 400))
                .into(ivDetailPortofolio);

        llDetailPortofolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvDetailJudulPortofolio.getMaxLines() == 2 && tvDetailDeskripsiPortofolio.getMaxLines() == 3) {
                    tvDetailJudulPortofolio.setMaxLines(Integer.MAX_VALUE);
                    tvDetailDeskripsiPortofolio.setMaxLines(Integer.MAX_VALUE);
                } else {
                    tvDetailJudulPortofolio.setMaxLines(2);
                    tvDetailDeskripsiPortofolio.setMaxLines(3);
                }

            }
        });


    }
}