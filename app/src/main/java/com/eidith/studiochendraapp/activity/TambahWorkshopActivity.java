package com.eidith.studiochendraapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eidith.studiochendraapp.R;
import com.eidith.studiochendraapp.api.APIRequestData;
import com.eidith.studiochendraapp.api.RetrofitServer;
import com.eidith.studiochendraapp.model.WorkshopModel;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahWorkshopActivity extends AppCompatActivity {


    private EditText etJudulWorkshop, etDeskripsiWorkshop;
    private Button btnSelectImage, btnSelectVideo, btnTambahWorkshop;
    private TextView tvInputImage, tvInputVideo;
    private String judul_workshop, deskripsi_workshop, gambar_workshop, video_workshop;
    private String mediaPathImage;
    private String mediaPathVideo;
    private ProgressDialog progressDialog;
    private int progress = 0;
    private static final int IMG_REQUEST_CODE = 0;
    private static final int VID_REQUEST_CODE = 1;
    private static final int REQUEST_EXTERNAL_STORAGE = 2;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_workshop);

        setTitle("Tambah Data Workshop");

        verifyStoragePermissions(TambahWorkshopActivity.this);

        // Assign Variable
        etJudulWorkshop = findViewById(R.id.inputJudulWorkshop);
        etDeskripsiWorkshop = findViewById(R.id.inputDeskripsiWorkshop);
        tvInputImage = findViewById(R.id.tvTambahGambar);
        tvInputVideo = findViewById(R.id.tvTambahVideo);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnSelectVideo = findViewById(R.id.btnSelectVideo);
        btnTambahWorkshop = findViewById(R.id.btnTambahWorkshop);

        //Set Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.setCancelable(false);

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Select Image
                Intent galleryIntent = new Intent (Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, IMG_REQUEST_CODE);
            }
        });

        btnSelectVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Select Video
                Intent galleryIntent = new Intent (Intent.ACTION_PICK,
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, VID_REQUEST_CODE);
            }
        });

        btnTambahWorkshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get Data input
                judul_workshop = etJudulWorkshop.getText().toString();
                deskripsi_workshop = etDeskripsiWorkshop.getText().toString();
                gambar_workshop = tvInputImage.getText().toString();
                video_workshop = tvInputVideo.getText().toString();

                //Checking Null
                if (judul_workshop.trim().equals("")){
                    etJudulWorkshop.setError("Harap Masukan Judul");
                } else if (deskripsi_workshop.trim().equals("")){
                    etDeskripsiWorkshop.setError("Harap Masukan Deskripsi");
                } else if (gambar_workshop.trim().equals("Tambah Gambar")){
                    tvInputImage.setError("Harap Pilih Gambar");
                } else if (video_workshop.trim().equals("Tambah Video")){
                    tvInputVideo.setError("Harap Pilih Video");
                } else {
                    UploadData();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            //Select Image from cursor and get path
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPathImage = cursor.getString(columnIndex);
                tvInputImage.setText(mediaPathImage);
                tvInputImage.setError(null);
                cursor.close();

                //Select Video from cursor and get path
            } else if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
                Uri selectedVideo = data.getData();
                String[] filePathColumn = {MediaStore.Video.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedVideo, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPathVideo = cursor.getString(columnIndex);
                tvInputVideo.setText(mediaPathVideo);
                tvInputVideo.setError(null);
                cursor.close();
            } else {
                Toast.makeText(this, "Harap pilih Image/Video", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Ada Kesalahan", Toast.LENGTH_LONG).show();
        }
    }

    private void UploadData(){
        progressDialog.show();

        //Set path image to file type
        File fileImage = new File(mediaPathImage);
        File fileVideo = new File(mediaPathVideo);

        //Set to Parser Json using Request Body and Multipart
        RequestBody judul = RequestBody.create(MediaType.parse("text/plain"), etJudulWorkshop.getText().toString());
        RequestBody deskripsi = RequestBody.create(MediaType.parse("text/plain"), etDeskripsiWorkshop.getText().toString());
        RequestBody gambar = RequestBody.create(MediaType.parse("image/*"), fileImage);
        MultipartBody.Part imagepart = MultipartBody.Part.createFormData("gambar_workshop", fileImage.getName(), gambar);
        RequestBody video = RequestBody.create(MediaType.parse("video/*"), fileVideo);
        MultipartBody.Part videopart = MultipartBody.Part.createFormData("video_workshop", fileVideo.getName(), video);

        //Execute createData to json method
        APIRequestData ardData = RetrofitServer.connectRetrofit().create(APIRequestData.class);
        Call<WorkshopModel> createData = ardData.CreateData(judul, deskripsi, imagepart, videopart);

        createData.enqueue(new Callback<WorkshopModel>() {
            @Override
            public void onResponse(Call<WorkshopModel> call, Response<WorkshopModel> response) {
                int kode = response.body().getCode();
                String pesan = response.body().getMessage();
                Toast.makeText(TambahWorkshopActivity.this, "Kode : "+kode+" | Pesan : "+pesan, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                finish();
            }

            @Override
            public void onFailure(Call<WorkshopModel> call, Throwable t) {
                Toast.makeText(TambahWorkshopActivity.this, "Gagal Menghubungi Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Verify storage permission from user
    private void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}