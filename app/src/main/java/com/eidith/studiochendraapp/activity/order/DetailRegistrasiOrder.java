package com.eidith.studiochendraapp.activity.order;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eidith.studiochendraapp.R;
import com.eidith.studiochendraapp.api.APIClient;

public class DetailRegistrasiOrder extends AppCompatActivity {

    private ImageView ivDetailRegistrasiLayanan;
    private TextView tvDetailIdRegistrasi, tvDetailRegistrasiJudulLayanan, tvDetailRegistrasiNama, tvDetailRegistrasiNoHp, tvDetailTanggalRegistrasi, tvDetailTanggalRegistrasiLayanan;
    private Button btnDetailRegistrasiCall;

    private String id_registrasi, id_layanan, id_user;
    private String tanggal_registrasi;
    private String judul_layanan, gambar_layanan, tanggal_layanan;
    private String nama_user, email_user, no_handphone_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_registrasi_order);

        setTitle("Detail Order");

        tvDetailIdRegistrasi = findViewById(R.id.tvDetailIdRegistrasi);
        ivDetailRegistrasiLayanan = findViewById(R.id.ivDetailRegistrasiLayanan);
        tvDetailTanggalRegistrasiLayanan = findViewById(R.id.tvDetailTanggalRegistrasiLayanan);
        tvDetailRegistrasiJudulLayanan = findViewById(R.id.tvDetailRegistrasiJudulLayanan);
        tvDetailRegistrasiNama = findViewById(R.id.tvDetailRegistrasiNama);
        tvDetailRegistrasiNoHp = findViewById(R.id.tvDetailRegistrasiNoHp);
        tvDetailTanggalRegistrasi = findViewById(R.id.tvDetailTanggalRegistrasi);
        btnDetailRegistrasiCall = findViewById(R.id.btnDetailRegistrasiCall);

        //Get data from extras
        id_registrasi = getIntent().getExtras().getString("Id Registrasi");
        tanggal_registrasi = getIntent().getExtras().getString("Tanggal Registrasi");

        id_layanan = getIntent().getExtras().getString("Id Registrasi");
        judul_layanan = getIntent().getExtras().getString("Judul Layanan");
        gambar_layanan = getIntent().getExtras().getString("Gambar Layanan");
        tanggal_layanan = getIntent().getExtras().getString("Tanggal Layanan");

        id_user = getIntent().getExtras().getString("Id User");
        nama_user = getIntent().getExtras().getString("Nama User");
        email_user = getIntent().getExtras().getString("Email User");
        no_handphone_user = getIntent().getExtras().getString("No Hp");

        tvDetailIdRegistrasi.setText(id_registrasi);
        Glide.with(DetailRegistrasiOrder.this)
                .load(APIClient.imageURL+gambar_layanan)
                .apply(new RequestOptions().override(800, 400))
                .into(ivDetailRegistrasiLayanan);
        tvDetailRegistrasiJudulLayanan.setText(judul_layanan);
        tvDetailTanggalRegistrasiLayanan.setText(tanggal_layanan);
        tvDetailRegistrasiNama.setText(nama_user);
        tvDetailRegistrasiNoHp.setText(no_handphone_user);
        tvDetailTanggalRegistrasi.setText(tanggal_registrasi);

    }
}
