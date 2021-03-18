package com.eidith.studiochendraapp.controller;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eidith.studiochendraapp.R;
import com.eidith.studiochendraapp.adapter.AdapterData;
import com.eidith.studiochendraapp.api.APIRequestData;
import com.eidith.studiochendraapp.api.RetrofitServer;
import com.eidith.studiochendraapp.model.WorkshopModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkshopActivity extends AppCompatActivity {

    private RecyclerView rvData;
    private RecyclerView.Adapter adData;
    private RecyclerView.LayoutManager lmData;
    private List<WorkshopModel> listData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop);

        rvData = findViewById(R.id.rvWorkshop);
        lmData = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(lmData);
        retrieveData();
    }

    public void retrieveData(){
        APIRequestData ardData = RetrofitServer.konekRetrofit().create(APIRequestData.class);
        Call<WorkshopModel> tampilData = ardData.ardRetrieveData();

        tampilData.enqueue(new Callback<WorkshopModel>() {
            @Override
            public void onResponse(Call<WorkshopModel> call, Response<WorkshopModel> response) {
                int kode = response.body().getCode();
                String pesan = response.body().getMessage();
                Toast.makeText(WorkshopActivity.this, "Kode 1 : "+kode+" Pesan : "+pesan, Toast.LENGTH_SHORT).show();

                listData = response.body().getData_workshop();

                adData = new AdapterData(WorkshopActivity.this, listData);
                rvData.setAdapter(adData);
                adData.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<WorkshopModel> call, Throwable t) {
                Toast.makeText(WorkshopActivity.this, "Gagal Menghubungi Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
