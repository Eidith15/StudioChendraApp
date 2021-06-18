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
import com.eidith.studiochendraapp.activity.layanan.LayananActivity;
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

    private DrawerLayout drawableLayoutArtikel;
    private SwipeRefreshLayout refreshArtikel;
    private RecyclerView rvArtikel;
    private ProgressBar pbarArtikel;
    private NavigationView navViewArtikel;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<ArtikelModel> listArtikel = new ArrayList<>();
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artikel);

        setTitle("Artikel");

        //Assign Variable
        drawableLayoutArtikel = findViewById(R.id.drawableLayoutArtikel);
        refreshArtikel = findViewById(R.id.refreshArtikel);
        rvArtikel = findViewById(R.id.rvArtikel);
        pbarArtikel = findViewById(R.id.pbarArtikel);
        navViewArtikel = findViewById(R.id.navViewArtikel);

        layoutManager = new GridLayoutManager(this,2, GridLayoutManager.VERTICAL, false);
        rvArtikel.setLayoutManager(layoutManager);

        //Set Menu on Action bar
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawableLayoutArtikel, R.string.open, R.string.close);

        //Navigation view menu listener
        navViewArtikel.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homeUser:
                        onBackPressed();
                        return true;

                    case R.id.akunUser:
                        Toast.makeText(ArtikelActivity.this, "Ini ke Akun", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.workshop:
                        Intent intentWorkshop = new Intent(ArtikelActivity.this, WorkshopActivity.class);
                        startActivity(intentWorkshop);
                        finish();
                        return true;

                    case R.id.artikelFotografi:
                        drawableLayoutArtikel.closeDrawers();
                        return true;

                    case R.id.layananJasaFotografi:
                        Intent intentLayanan = new Intent(ArtikelActivity.this, LayananActivity.class);
                        startActivity(intentLayanan);
                        finish();
                        return true;

                    case R.id.fotoPortofolio:
                        Intent intentPortofolio = new Intent(ArtikelActivity.this, PortofolioActivity.class);
                        startActivity(intentPortofolio);
                        finish();
                        return true;

                    case R.id.registrasiOrder:
                        Toast.makeText(ArtikelActivity.this, "Ini Ke Registrasi Order", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.customerService:
                        Toast.makeText(ArtikelActivity.this, "Ini Ke Respond Customer", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.logoutUser:
                        Toast.makeText(ArtikelActivity.this, "Ini Ke Logout", Toast.LENGTH_SHORT).show();
                        return true;

                }
                return false;
            }
        });

        //Set menu
        drawableLayoutArtikel.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    //On item selected Menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveData();
        drawableLayoutArtikel.closeDrawers();
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
