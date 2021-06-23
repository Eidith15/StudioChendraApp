package com.eidith.studiochendraapp.activity.layanan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.eidith.studiochendraapp.R;
import com.eidith.studiochendraapp.activity.MainActivity;
import com.eidith.studiochendraapp.activity.akun.AkunActivity;
import com.eidith.studiochendraapp.activity.login.LoginActivity;
import com.eidith.studiochendraapp.activity.portofolio.PortofolioActivity;
import com.eidith.studiochendraapp.activity.artikel.ArtikelActivity;
import com.eidith.studiochendraapp.activity.workshop.WorkshopActivity;
import com.eidith.studiochendraapp.adapter.LayananAdapter;
import com.eidith.studiochendraapp.api.APIRequestData;
import com.eidith.studiochendraapp.api.APIClient;
import com.eidith.studiochendraapp.model.LayananModel;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LayananActivity extends AppCompatActivity implements LayananAdapter.OnItemClickListener{

    private SwipeRefreshLayout refreshLayanan;
    private RecyclerView rvLayanan;
    private ProgressBar pbarLayanan;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<LayananModel> listLayanan = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layanan);

        setTitle("Layanan Jasa Fotografi");

        refreshLayanan = findViewById(R.id.refreshLayanan);
        rvLayanan = findViewById(R.id.rvLayanan);
        pbarLayanan = findViewById(R.id.pbarLayanan);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvLayanan.setLayoutManager(layoutManager);

        //Set refresh swipe to get data
        refreshLayanan.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayanan.setRefreshing(true);
                retrieveData();
                refreshLayanan.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveData();
    }

    public void retrieveData(){
        pbarLayanan.setVisibility(View.VISIBLE);

        //Conncet to server to parse Json and get data
        APIRequestData ardData = APIClient.connectRetrofit().create(APIRequestData.class);
        Call<LayananModel> tampilData = ardData.RetrieveDataLayanan();

        tampilData.enqueue(new Callback<LayananModel>() {
            @Override
            public void onResponse(Call<LayananModel> call, Response<LayananModel> response) {
                //Set data to Adapter
                listLayanan = response.body().getData_layanan();

                adapter = new LayananAdapter(LayananActivity.this, listLayanan, LayananActivity.this::OnItemClickLayanan);
                rvLayanan.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                pbarLayanan.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<LayananModel> call, Throwable t) {
                Toast.makeText(LayananActivity.this, "Gagal Menghubungi Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();

                pbarLayanan.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public void OnItemClickLayanan(int position) {
        //Send data to DetailArtikel Activity
        Intent intent = new Intent(LayananActivity.this, DetailLayananActivity.class);
        intent.putExtra("Id Layanan", listLayanan.get(position).getId_layanan());
        intent.putExtra("Judul Layanan", listLayanan.get(position).getJudul_layanan());
        intent.putExtra("Deskripsi Layanan", listLayanan.get(position).getDeskripsi_layanan());
        intent.putExtra("Gambar Layanan", listLayanan.get(position).getGambar_layanan());
        intent.putExtra("Video Layanan", listLayanan.get(position).getVideo_layanan());
        intent.putExtra("Tanggal Layanan", listLayanan.get(position).getTanggal_layanan());
        startActivity(intent);
    }

}