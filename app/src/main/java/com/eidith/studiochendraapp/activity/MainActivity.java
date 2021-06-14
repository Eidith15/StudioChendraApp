package com.eidith.studiochendraapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.eidith.studiochendraapp.activity.artikel.ArtikelActivity;
import com.eidith.studiochendraapp.activity.artikel.DetailArtikelActivity;
import com.eidith.studiochendraapp.activity.layanan.DetailLayananActivity;
import com.eidith.studiochendraapp.activity.layanan.LayananActivity;
import com.eidith.studiochendraapp.activity.portofolio.DetailPortofolioActivity;
import com.eidith.studiochendraapp.activity.portofolio.PortofolioActivity;
import com.eidith.studiochendraapp.activity.workshop.DetailWorkshopActivity;
import com.eidith.studiochendraapp.activity.workshop.WorkshopActivity;
import com.eidith.studiochendraapp.adapter.ArtikelAdapter;
import com.eidith.studiochendraapp.adapter.LayananAdapter;
import com.eidith.studiochendraapp.adapter.PortofolioAdapter;
import com.eidith.studiochendraapp.adapter.WorkshopAdapter;
import com.eidith.studiochendraapp.api.APIRequestData;
import com.eidith.studiochendraapp.api.RetrofitServer;
import com.eidith.studiochendraapp.model.ArtikelModel;
import com.eidith.studiochendraapp.model.LayananModel;
import com.eidith.studiochendraapp.model.PortofolioModel;
import com.eidith.studiochendraapp.model.WorkshopModel;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements
        WorkshopAdapter.OnItemClickListener,
        ArtikelAdapter.OnItemClickListener,
        LayananAdapter.OnItemClickListener,
        PortofolioAdapter.OnItemClickListener{

    private DrawerLayout drawableLayoutMain;
    private SwipeRefreshLayout refreshMain;
    private RecyclerView rvMainWorkshop, rvMainArtikel, rvMainLayanan, rvMainPortofolio;
    private ProgressBar pbarMain;
    private NavigationView navViewMain;

    private RecyclerView.Adapter adapterWorkshop;
    private RecyclerView.Adapter adapterArtikel;
    private RecyclerView.Adapter adapterLayanan;
    private RecyclerView.Adapter adapterPortofolio;
    private RecyclerView.LayoutManager layoutManagerWorkshop;
    private RecyclerView.LayoutManager layoutManagerArtikel;
    private RecyclerView.LayoutManager layoutManagerLayanan;
    private RecyclerView.LayoutManager layoutManagerPortofolio;
    private List<WorkshopModel> listWorkshop = new ArrayList<>();
    private List<ArtikelModel> listArtikel = new ArrayList<>();
    private List<LayananModel> listLayanan = new ArrayList<>();
    private List<PortofolioModel> listPortofolio = new ArrayList<>();

    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Home");

        //Assign Variable
        drawableLayoutMain = findViewById(R.id.drawableLayoutMain);
        refreshMain = findViewById(R.id.refreshMain);
        rvMainWorkshop = findViewById(R.id.rvMainWorkshop);
        rvMainArtikel = findViewById(R.id.rvMainArtikel);
        rvMainLayanan = findViewById(R.id.rvMainLayanan);
        rvMainPortofolio = findViewById(R.id.rvMainPortofolio);
        pbarMain = findViewById(R.id.pbarMain);
        navViewMain = findViewById(R.id.navViewMain);

        layoutManagerWorkshop = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManagerArtikel = new GridLayoutManager(this,2, GridLayoutManager.VERTICAL, false);
        layoutManagerLayanan = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManagerPortofolio = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL, false);


        rvMainWorkshop.setLayoutManager(layoutManagerWorkshop);
        rvMainArtikel.setLayoutManager(layoutManagerArtikel);
        rvMainLayanan.setLayoutManager(layoutManagerLayanan);
        rvMainPortofolio.setLayoutManager(layoutManagerPortofolio);

        //Set Menu on Action bar
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawableLayoutMain, R.string.open, R.string.close);

        //Navigation view menu listener
        navViewMain.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homeUser:
                        drawableLayoutMain.closeDrawers();
                        return true;

                    case R.id.akunUser:
                        Toast.makeText(MainActivity.this, "Ini ke Akun", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.workshop:
                        Intent intentWorkshop = new Intent(MainActivity.this, WorkshopActivity.class);
                        startActivity(intentWorkshop);
                        return true;

                    case R.id.artikelFotografi:
                        Intent intentArtikel = new Intent(MainActivity.this, ArtikelActivity.class);
                        startActivity(intentArtikel);
                        return true;

                    case R.id.layananJasaFotografi:
                        Intent intentLayanan = new Intent(MainActivity.this, LayananActivity.class);
                        startActivity(intentLayanan);
                        return true;

                    case R.id.fotoPortofolio:
                        Intent intentPortofolio = new Intent(MainActivity.this, PortofolioActivity.class);
                        startActivity(intentPortofolio);
                        return true;

                    case R.id.registrasiOrder:
                        Toast.makeText(MainActivity.this, "Ini Ke Registrasi Order", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.customerService:
                        Toast.makeText(MainActivity.this, "Ini Ke Respond Customer", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.logout:
                        Toast.makeText(MainActivity.this, "Ini Ke Logout", Toast.LENGTH_SHORT).show();
                        return true;

                }
                return false;
            }
        });

        //Set menu
        drawableLayoutMain.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Set refresh swipe to get data
        refreshMain.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshMain.setRefreshing(true);
                retrieveData();
                refreshMain.setRefreshing(false);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveData();
        drawableLayoutMain.closeDrawers();
    }

    //On item selected Menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void retrieveData(){
        pbarMain.setVisibility(View.VISIBLE);

        //Conncet to server to parse Json and get data
        APIRequestData ardData = RetrofitServer.connectRetrofit().create(APIRequestData.class);

        Call<WorkshopModel> tampilDataWorkshop = ardData.RetrieveDataWorkshop();
        Call<ArtikelModel> tampilDataArtikel = ardData.RetrieveDataArtikel();
        Call<LayananModel> tampilDataLayanan = ardData.RetrieveDataLayanan();
        Call<PortofolioModel> tampilDataPortofolio = ardData.RetrieveDataPortofolio();

        tampilDataWorkshop.enqueue(new Callback<WorkshopModel>() {
            @Override
            public void onResponse(Call<WorkshopModel> call, Response<WorkshopModel> response) {
                //Set data to Adapter
                listWorkshop = response.body().getData_workshop();
                listWorkshop.subList(2, listWorkshop.size()).clear();

                adapterWorkshop = new WorkshopAdapter(MainActivity.this, listWorkshop, MainActivity.this::OnItemClickWorkshop);
                rvMainWorkshop.setAdapter(adapterWorkshop);
                adapterWorkshop.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<WorkshopModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal Menghubungi Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();

                pbarMain.setVisibility(View.GONE);
            }
        });

        tampilDataArtikel.enqueue(new Callback<ArtikelModel>() {
            @Override
            public void onResponse(Call<ArtikelModel> call, Response<ArtikelModel> response) {
                //Set data to Adapter
                listArtikel = response.body().getData_artikel();
                listArtikel.subList(4, listArtikel.size()).clear();

                adapterArtikel = new ArtikelAdapter(MainActivity.this, listArtikel, MainActivity.this::OnItemClickArtikel);
                rvMainArtikel.setAdapter(adapterArtikel);
                adapterArtikel.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArtikelModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal Menghubungi Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();

                pbarMain.setVisibility(View.GONE);
            }
        });

        tampilDataLayanan.enqueue(new Callback<LayananModel>() {
            @Override
            public void onResponse(Call<LayananModel> call, Response<LayananModel> response) {
                //Set data to Adapter
                listLayanan = response.body().getData_layanan();
                listLayanan.subList(2, listLayanan.size()).clear();

                adapterLayanan = new LayananAdapter(MainActivity.this, listLayanan, MainActivity.this::OnItemClickLayanan);
                rvMainLayanan.setAdapter(adapterLayanan);
                adapterLayanan.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<LayananModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal Menghubungi Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();

                pbarMain.setVisibility(View.GONE);
            }
        });

        tampilDataPortofolio.enqueue(new Callback<PortofolioModel>() {
            @Override
            public void onResponse(Call<PortofolioModel> call, Response<PortofolioModel> response) {
                //Set data to Adapter
                listPortofolio = response.body().getData_portofolio();
                listPortofolio.subList(4, listPortofolio.size()).clear();

                adapterPortofolio = new PortofolioAdapter(MainActivity.this, listPortofolio, MainActivity.this::OnItemClickPortofolio);
                rvMainPortofolio.setAdapter(adapterPortofolio);
                adapterPortofolio.notifyDataSetChanged();

                pbarMain.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<PortofolioModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal Menghubungi Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();

                pbarMain.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public void OnItemClickWorkshop(int position) {
        //Send data to DetailWorkshop Activity
        Intent intent = new Intent(this, DetailWorkshopActivity.class);
        intent.putExtra("Id Workshop", listWorkshop.get(position).getId_workshop());
        intent.putExtra("Judul Workshop", listWorkshop.get(position).getJudul_workshop());
        intent.putExtra("Deskripsi Workshop", listWorkshop.get(position).getDeskripsi_workshop());
        intent.putExtra("Gambar Workshop", listWorkshop.get(position).getGambar_workshop());
        intent.putExtra("Video Workshop", listWorkshop.get(position).getVideo_workshop());
        intent.putExtra("Tanggal Workshop", listWorkshop.get(position).getTanggal_workshop());
        startActivity(intent);
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

    @Override
    public void OnItemClickLayanan(int position) {
        //Send data to DetailArtikel Activity
        Intent intent = new Intent(this, DetailLayananActivity.class);
        intent.putExtra("Id Layanan", listLayanan.get(position).getId_layanan());
        intent.putExtra("Judul Layanan", listLayanan.get(position).getJudul_layanan());
        intent.putExtra("Deskripsi Layanan", listLayanan.get(position).getDeskripsi_layanan());
        intent.putExtra("Gambar Layanan", listLayanan.get(position).getGambar_layanan());
        intent.putExtra("Video Layanan", listLayanan.get(position).getVideo_layanan());
        intent.putExtra("Tanggal Layanan", listLayanan.get(position).getTanggal_layanan());
        startActivity(intent);
    }

    @Override
    public void OnItemClickPortofolio(int position) {
        //Send data to DetailArtikel Activity
        Intent intent = new Intent(this, DetailPortofolioActivity.class);
        intent.putExtra("Id Foto", listPortofolio.get(position).getId_portofolio());
        intent.putExtra("Judul Foto", listPortofolio.get(position).getJudul_portofolio());
        intent.putExtra("Deskripsi Foto", listPortofolio.get(position).getDeskripsi_foto());
        intent.putExtra("Gambar Foto", listPortofolio.get(position).getGambar_foto());
        startActivity(intent);
    }
}