package com.eidith.studiochendraapp.activity.artikel;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.eidith.studiochendraapp.R;
import com.eidith.studiochendraapp.activity.MainActivity;
import com.eidith.studiochendraapp.activity.akun.AkunActivity;
import com.eidith.studiochendraapp.activity.layanan.LayananActivity;
import com.eidith.studiochendraapp.activity.login.LoginActivity;
import com.eidith.studiochendraapp.activity.portofolio.PortofolioActivity;
import com.eidith.studiochendraapp.activity.workshop.WorkshopActivity;
import com.eidith.studiochendraapp.adapter.ArtikelAdapter;
import com.eidith.studiochendraapp.api.APIRequestData;
import com.eidith.studiochendraapp.api.APIClient;
import com.eidith.studiochendraapp.model.ArtikelModel;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArtikelActivity extends AppCompatActivity implements ArtikelAdapter.OnItemClickListener {

    private SwipeRefreshLayout refreshArtikel;
    private RecyclerView rvArtikel;
    private ProgressBar pbarArtikel;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<ArtikelModel> listArtikel = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artikel);

        setTitle("Artikel");

        //Assign Variable
        refreshArtikel = findViewById(R.id.refreshArtikel);
        rvArtikel = findViewById(R.id.rvArtikel);
        pbarArtikel = findViewById(R.id.pbarArtikel);

        layoutManager = new GridLayoutManager(this,2, GridLayoutManager.VERTICAL, false);
        rvArtikel.setLayoutManager(layoutManager);

        //Set refresh swipe to get data
        refreshArtikel.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshArtikel.setRefreshing(true);
                retrieveData();
                refreshArtikel.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveData();
    }

    public void retrieveData(){
        pbarArtikel.setVisibility(View.VISIBLE);

        //Conncet to server to parse Json and get data
        APIRequestData ardData = APIClient.connectRetrofit().create(APIRequestData.class);
        Call<ArtikelModel> tampilData = ardData.RetrieveDataArtikel();

        tampilData.enqueue(new Callback<ArtikelModel>() {
            @Override
            public void onResponse(Call<ArtikelModel> call, Response<ArtikelModel> response) {
                //Set data to Adapter
                listArtikel = response.body().getData_artikel();

                adapter = new ArtikelAdapter(ArtikelActivity.this, listArtikel, ArtikelActivity.this::OnItemClickArtikel);
                rvArtikel.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                pbarArtikel.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ArtikelModel> call, Throwable t) {
                Toast.makeText(ArtikelActivity.this, "Gagal Menghubungi Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                pbarArtikel.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public void OnItemClickArtikel(int position) {
        //Send data to DetailArtikel Activity
        Intent intent = new Intent(this, DetailArtikelActivity.class);
        intent.putExtra("Id Artikel", listArtikel.get(position).getId_artikel());
        intent.putExtra("Judul Artikel", listArtikel.get(position).getJudul_artikel());
        intent.putExtra("Deskripsi Artikel", listArtikel.get(position).getDeskripsi_artikel());
        intent.putExtra("Gambar Artikel", listArtikel.get(position).getGambar_artikel());
        intent.putExtra("Video Artikel", listArtikel.get(position).getVideo_artikel());
        intent.putExtra("Tanggal Artikel", listArtikel.get(position).getTanggal_artikel());
        startActivity(intent);
    }
}
