package com.eidith.studiochendraapp.activity.portofolio;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.eidith.studiochendraapp.R;
import com.eidith.studiochendraapp.adapter.RecyclerViewAdapterPortofolio;
import com.eidith.studiochendraapp.api.APIClient;
import com.eidith.studiochendraapp.api.APIRequestData;
import com.eidith.studiochendraapp.model.PortofolioModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PortofolioActivity extends AppCompatActivity implements RecyclerViewAdapterPortofolio.OnItemClickListener {

    private static final String LOG_TAG = "PortofolioActivity";

    private SwipeRefreshLayout refreshPortofolio;
    private RecyclerView rvPortofolio;
    private ProgressBar pbarPotrofolio;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<PortofolioModel> listPortofolio = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portofolio);

        setTitle("Portofolio Gallery");

        //Assign variable
        refreshPortofolio = findViewById(R.id.refreshPortofolio);
        rvPortofolio = findViewById(R.id.rvPortofolio);
        pbarPotrofolio = findViewById(R.id.pbarPotrofolio);

        layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        rvPortofolio.setLayoutManager(layoutManager);

        //Set refresh swipe to get data
        refreshPortofolio.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshPortofolio.setRefreshing(true);
                Log.v(LOG_TAG, "Start Retrieve Data");
                retrieveData();
                Log.v(LOG_TAG, "Finish Retrieve Data");
                refreshPortofolio.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(LOG_TAG, "Start Retrieve Data");
        retrieveData();
        Log.v(LOG_TAG, "Finish Retrieve Data");
    }

    public void retrieveData() {
        pbarPotrofolio.setVisibility(View.VISIBLE);

        //Conncet to server to parse Json and get data with gson
        APIRequestData ardData = APIClient.connectRetrofitGson().create(APIRequestData.class);
        Call<PortofolioModel> tampilData = ardData.RetrieveDataPortofolio();

        //Conncet to server to parse Json and get data with moshi
//        APIRequestData ardData = APIClient.connectRetrofitMoshi().create(APIRequestData.class);
//        Call<PortofolioModel> tampilData = ardData.RetrieveDataPortofolio();

        tampilData.enqueue(new Callback<PortofolioModel>() {
            @Override
            public void onResponse(Call<PortofolioModel> call, Response<PortofolioModel> response) {
                //Set data to Adapter
                listPortofolio = response.body().getData_portofolio();

                adapter = new RecyclerViewAdapterPortofolio(PortofolioActivity.this, listPortofolio, PortofolioActivity.this::OnItemClickPortofolio);
                rvPortofolio.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                pbarPotrofolio.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<PortofolioModel> call, Throwable t) {
                Toast.makeText(PortofolioActivity.this, "Gagal Menghubungi Server : " + t.getMessage(), Toast.LENGTH_SHORT).show();

                pbarPotrofolio.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void OnItemClickPortofolio(int position) {
        //Send data to DetailArtikel Activity
        Intent intent = new Intent(PortofolioActivity.this, DetailPortofolioActivity.class);
        intent.putExtra("Id Foto", listPortofolio.get(position).getId_portofolio());
        intent.putExtra("Judul Foto", listPortofolio.get(position).getJudul_portofolio());
        intent.putExtra("Deskripsi Foto", listPortofolio.get(position).getDeskripsi_foto());
        intent.putExtra("Gambar Foto", listPortofolio.get(position).getGambar_foto());
        startActivity(intent);
    }
}