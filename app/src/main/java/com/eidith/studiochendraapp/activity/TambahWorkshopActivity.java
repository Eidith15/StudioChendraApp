package com.eidith.studiochendraapp.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.eidith.studiochendraapp.R;
import com.eidith.studiochendraapp.api.APIRequestData;
import com.eidith.studiochendraapp.api.RetrofitServer;
import com.eidith.studiochendraapp.model.WorkshopModel;

import java.io.File;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahWorkshopActivity extends AppCompatActivity {


    private EditText etJudulWorkshop, etDeskripsiWorkshop;
    private Button btnTambahWorkshop, btnDatePicker, btnSelectVideo, btnSelectImage;
    private String judul_workshop, deskripsi_workshop, gambar_workshop, video_workshop, tanggal_workshop;
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
    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_workshop);

        setTitle("Tambah Data Workshop");

        verifyStoragePermissions(TambahWorkshopActivity.this);

        // Assign Variable
        etJudulWorkshop = findViewById(R.id.inputJudulWorkshop);
        etDeskripsiWorkshop = findViewById(R.id.inputDeskripsiWorkshop);
        btnDatePicker = findViewById(R.id.btnDatePicker);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnSelectVideo = findViewById(R.id.btnSelectVideo);
        btnTambahWorkshop = findViewById(R.id.btnTambahWorkshop);

        //Set Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.setCancelable(false);

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int selectedmonth = month+1;
                String date = year+"-"+selectedmonth+"-"+dayOfMonth;
                btnDatePicker.setText(date);
                btnDatePicker.setError(null);
            }
        };

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();

            }
        });



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
                gambar_workshop = btnSelectImage.getText().toString();
                video_workshop = btnSelectVideo.getText().toString();
                tanggal_workshop = btnDatePicker.getText().toString();

                //Checking Null
                if (judul_workshop.trim().equals("")){
                    etJudulWorkshop.setError("Harap Masukan Judul");
                } else if (deskripsi_workshop.trim().equals("")){
                    etDeskripsiWorkshop.setError("Harap Masukan Deskripsi");
                } else if (tanggal_workshop.trim().equals("Pilih Tanggal")){
                    btnDatePicker.setError("Harap Masukan Tanggal");
                } else if (gambar_workshop.trim().equals("Tambah Gambar")){
                    btnSelectImage.setError("Harap Pilih Gambar");
                } else if (video_workshop.trim().equals("Tambah Video")){
                    btnSelectVideo.setError("Harap Pilih Video");
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TambahWorkshopActivity.this);
                    builder.setTitle("Tambah Data Workshop");
                    builder.setMessage("Yakin Menambah data?");
                    builder.setIcon(R.drawable.ic_launcher_background);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            UploadData();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
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
                btnSelectImage.setText(mediaPathImage);
                btnSelectImage.setError(null);
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
                btnSelectVideo.setText(mediaPathVideo);
                btnSelectVideo.setError(null);
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
        RequestBody tanggal = RequestBody.create(MediaType.parse("text/plain"), btnDatePicker.getText().toString());
        RequestBody gambar = RequestBody.create(MediaType.parse("image/*"), fileImage);
        MultipartBody.Part imagepart = MultipartBody.Part.createFormData("gambar_workshop", fileImage.getName(), gambar);
        RequestBody video = RequestBody.create(MediaType.parse("video/*"), fileVideo);
        MultipartBody.Part videopart = MultipartBody.Part.createFormData("video_workshop", fileVideo.getName(), video);

        //Execute createData to json method
        APIRequestData ardData = RetrofitServer.connectRetrofit().create(APIRequestData.class);
        Call<WorkshopModel> createData = ardData.CreateData(judul, deskripsi, tanggal, imagepart, videopart);

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

    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                TambahWorkshopActivity.this,
                dateSetListener,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }


}