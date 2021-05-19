package com.eidith.studiochendraapp.activity;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.eidith.studiochendraapp.R;
import com.eidith.studiochendraapp.adapter.WorkshopAdapter;
import com.eidith.studiochendraapp.api.APIRequestData;
import com.eidith.studiochendraapp.api.RetrofitServer;
import com.eidith.studiochendraapp.model.WorkshopModel;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkshopActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<WorkshopModel> listData = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop);

        recyclerView = findViewById(R.id.rvWorkshop);
        swipeRefreshLayout = findViewById(R.id.refreshWorkshop);
        progressBar = findViewById(R.id.pbarWorkshop);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        drawerLayout = findViewById(R.id.drawableLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.akunAdmin:
                        Toast.makeText(WorkshopActivity.this, "Ini ke Akun", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.tambahWorkshop:
                        Intent intent = new Intent(WorkshopActivity.this, TambahWorkshopActivity.class);
                        startActivity(intent);
                        return true;

                    case R.id.tambahArtikelFotografi:
                        Toast.makeText(WorkshopActivity.this, "Ini Ke Tambah Artikel", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.tambahLayananJasaFotografi:
                        Toast.makeText(WorkshopActivity.this, "Ini Ke Tambah Layanan", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.tambahFotoPortofolio:
                        Toast.makeText(WorkshopActivity.this, "Ini Ke Tambah Foto", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.registrasiOrderAdmin:
                        Toast.makeText(WorkshopActivity.this, "Ini Ke Registrasi Order", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.respondCustomer:
                        Toast.makeText(WorkshopActivity.this, "Ini Ke Respond Customer", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.logout:
                        Toast.makeText(WorkshopActivity.this, "Ini Ke Logout", Toast.LENGTH_SHORT).show();
                        return true;

                }
                return false;
            }
        });

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                retrieveData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

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
    }

    public void retrieveData(){
        progressBar.setVisibility(View.VISIBLE);

        APIRequestData ardData = RetrofitServer.connectRetrofit().create(APIRequestData.class);
        Call<WorkshopModel> tampilData = ardData.ardRetrieveData();

        tampilData.enqueue(new Callback<WorkshopModel>() {
            @Override
            public void onResponse(Call<WorkshopModel> call, Response<WorkshopModel> response) {
                int kode = response.body().getCode();
                String pesan = response.body().getMessage();
                Toast.makeText(WorkshopActivity.this, "Kode 1 : "+kode+" Pesan : "+pesan, Toast.LENGTH_SHORT).show();

                listData = response.body().getData_workshop();

                adapter = new WorkshopAdapter(WorkshopActivity.this, listData);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<WorkshopModel> call, Throwable t) {
                Toast.makeText(WorkshopActivity.this, "Gagal Menghubungi Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();

                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

}
