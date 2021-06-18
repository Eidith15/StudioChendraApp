package com.eidith.studiochendraapp.activity.layanan;

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
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.eidith.studiochendraapp.R;
import com.eidith.studiochendraapp.api.APIRequestData;
import com.eidith.studiochendraapp.api.APIClient;
import com.eidith.studiochendraapp.model.LayananModel;

import java.io.File;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahLayananActivity extends AppCompatActivity {

    private static final int IMG_REQUEST_CODE = 0;
    private static final int VID_REQUEST_CODE = 1;
    private static final int REQUEST_EXTERNAL_STORAGE = 2;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private EditText etJudulLayanan, etDeskripsiLayanan;
    private Button btnDatePickerLayanan, btnSelectImageLayanan, btnSelectVideoLayanan, btnTambahLayanan;

    private ProgressDialog progressDialog;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    private String judul_layanan, deskripsi_layanan, gambar_layanan, video_layanan, tanggal_layanan;
    private String mediaPathImage;
    private String mediaPathVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_layanan);

        setTitle("Tambah Data Layanan");

        verifyStoragePermissions(TambahLayananActivity.this);

        //Assign variable
        etJudulLayanan = findViewById(R.id.etJudulLayanan);
        etDeskripsiLayanan = findViewById(R.id.etDeskripsiLayanan);
        btnDatePickerLayanan = findViewById(R.id.btnDatePickerLayanan);
        btnSelectImageLayanan = findViewById(R.id.btnSelectImageLayanan);
        btnSelectVideoLayanan = findViewById(R.id.btnSelectVideoLayanan);
        btnTambahLayanan = findViewById(R.id.btnTambahLayanan);

        //Set Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.setCancelable(false);

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int selectedmonth = month+1;
                String date = year+"-"+selectedmonth+"-"+dayOfMonth;
                btnDatePickerLayanan.setText(date);
                btnDatePickerLayanan.setError(null);
            }
        };

        btnDatePickerLayanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        btnSelectImageLayanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Select Image
                Intent galleryIntent = new Intent (Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, IMG_REQUEST_CODE);
            }
        });

        btnSelectVideoLayanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Select Video
                Intent galleryIntent = new Intent (Intent.ACTION_PICK,
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, VID_REQUEST_CODE);
            }
        });

        btnTambahLayanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get Data input
                judul_layanan = etJudulLayanan.getText().toString();
                deskripsi_layanan = etDeskripsiLayanan.getText().toString();
                gambar_layanan = btnSelectImageLayanan.getText().toString();
                video_layanan = btnSelectVideoLayanan.getText().toString();
                tanggal_layanan = btnDatePickerLayanan.getText().toString();

                //Checking Null
                if (judul_layanan.trim().equals("")){
                    etJudulLayanan.setError("Harap Masukan Judul");
                } else if (deskripsi_layanan.trim().equals("")){
                    etDeskripsiLayanan.setError("Harap Masukan Deskripsi");
                } else if (tanggal_layanan.trim().equals("Pilih Tanggal")){
                    btnDatePickerLayanan.setError("Harap Masukan Tanggal");
                } else if (gambar_layanan.trim().equals("Tambah Gambar")){
                    btnSelectImageLayanan.setError("Harap Pilih Gambar");
                } else if (video_layanan.trim().equals("Tambah Video")){
                    btnSelectVideoLayanan.setError("Harap Pilih Video");
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TambahLayananActivity.this);
                    builder.setTitle("Tambah Data Layanan");
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
                btnSelectImageLayanan.setText(mediaPathImage);
                btnSelectImageLayanan.setError(null);
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
                btnSelectVideoLayanan.setText(mediaPathVideo);
                btnSelectVideoLayanan.setError(null);
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
        RequestBody judul = RequestBody.create(MediaType.parse("text/plain"), etJudulLayanan.getText().toString());
        RequestBody deskripsi = RequestBody.create(MediaType.parse("text/plain"), etDeskripsiLayanan.getText().toString());
        RequestBody tanggal = RequestBody.create(MediaType.parse("text/plain"), btnDatePickerLayanan.getText().toString());
        RequestBody gambar = RequestBody.create(MediaType.parse("image/*"), fileImage);
        MultipartBody.Part imagepart = MultipartBody.Part.createFormData("gambar_layanan", fileImage.getName(), gambar);
        RequestBody video = RequestBody.create(MediaType.parse("video/*"), fileVideo);
        MultipartBody.Part videopart = MultipartBody.Part.createFormData("video_layanan", fileVideo.getName(), video);

        //Execute createData to json method
        APIRequestData ardData = APIClient.connectRetrofit().create(APIRequestData.class);
        Call<LayananModel> createData = ardData.CreateDataLayanan(judul, deskripsi, tanggal, imagepart, videopart);

        createData.enqueue(new Callback<LayananModel>() {
            @Override
            public void onResponse(Call<LayananModel> call, Response<LayananModel> response) {
                int kode = response.body().getCode();
                String pesan = response.body().getMessage();
                Toast.makeText(TambahLayananActivity.this, "Kode : "+kode+" | Pesan : "+pesan, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                finish();
            }

            @Override
            public void onFailure(Call<LayananModel> call, Throwable t) {
                Toast.makeText(TambahLayananActivity.this, "Gagal Menghubungi Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
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
                TambahLayananActivity.this,
                dateSetListener,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}