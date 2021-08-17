package com.eidith.studiochendraapp.activity.workshop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.eidith.studiochendraapp.R;
import com.eidith.studiochendraapp.adapter.RecyclerViewAdapterWorkshop;
import com.eidith.studiochendraapp.api.APIClient;
import com.eidith.studiochendraapp.api.APIRequestData;
import com.eidith.studiochendraapp.model.WorkshopModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkshopActivity extends AppCompatActivity implements RecyclerViewAdapterWorkshop.OnItemClickListener {

    private static final String LOG_TAG = "WorkshopActivity";

    private RecyclerView rvWorkshop;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<WorkshopModel> listWorkshop = new ArrayList<>();
    private SwipeRefreshLayout refreshWorkshop;
    private ProgressBar pbarWorkshop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop);

        setTitle("Workshop");

        //Assign Variable
        rvWorkshop = findViewById(R.id.rvWorkshop);
        refreshWorkshop = findViewById(R.id.refreshWorkshop);
        pbarWorkshop = findViewById(R.id.pbarWorkshop);

        //Set Recycler view Layout
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvWorkshop.setLayoutManager(layoutManager);

        //Set refresh swipe to get data
        refreshWorkshop.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshWorkshop.setRefreshing(true);
                Log.v(LOG_TAG, "Start Retrieve Data");
                retrieveData();
                Log.v(LOG_TAG, "Finish Retrieve Data");
                refreshWorkshop.setRefreshing(false);
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
        pbarWorkshop.setVisibility(View.VISIBLE);

        //Conncet to server to parse Json and get data with gson
        APIRequestData ardData = APIClient.connectRetrofitGson().create(APIRequestData.class);
        Call<WorkshopModel> tampilData = ardData.RetrieveDataWorkshop();

        //Conncet to server to parse Json and get data with moshi
//        APIRequestData ardData = APIClient.connectRetrofitMoshi().create(APIRequestData.class);
//        Call<WorkshopModel> tampilData = ardData.RetrieveDataWorkshop();

        tampilData.enqueue(new Callback<WorkshopModel>() {
            @Override
            public void onResponse(Call<WorkshopModel> call, Response<WorkshopModel> response) {
                //Set data to Adapter
                listWorkshop = response.body().getData_workshop();

                adapter = new RecyclerViewAdapterWorkshop(WorkshopActivity.this, listWorkshop, WorkshopActivity.this::OnItemClickWorkshop);
                rvWorkshop.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                pbarWorkshop.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<WorkshopModel> call, Throwable t) {
                Toast.makeText(WorkshopActivity.this, "Gagal Menghubungi Server : " + t.getMessage(), Toast.LENGTH_SHORT).show();

                pbarWorkshop.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void OnItemClickWorkshop(int position) {
        //Send data to DetailWorkshop Activity
        Intent intent = new Intent(WorkshopActivity.this, DetailWorkshopActivity.class);
        intent.putExtra("Id Workshop", listWorkshop.get(position).getId_workshop());
        intent.putExtra("Judul Workshop", listWorkshop.get(position).getJudul_workshop());
        intent.putExtra("Deskripsi Workshop", listWorkshop.get(position).getDeskripsi_workshop());
        intent.putExtra("Gambar Workshop", listWorkshop.get(position).getGambar_workshop());
        intent.putExtra("Video Workshop", listWorkshop.get(position).getVideo_workshop());
        intent.putExtra("Tanggal Workshop", listWorkshop.get(position).getTanggal_workshop());
        startActivity(intent);
    }



}
