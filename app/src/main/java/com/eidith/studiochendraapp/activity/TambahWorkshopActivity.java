package com.eidith.studiochendraapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
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

public class TambahWorkshopActivity extends AppCompatActivity {

    private EditText etJudulWorkshop, etDeskripsiWorkshop;
    private Button btnSelectImage, btnSelectVideo, btnTambahWorkshop;
    private TextView tvInputImage, tvInputVideo;
    private String judul_workshop, deskripsi_workshop, gambar_workshop, video_workshop;
    String mediaPath;
    ProgressDialog progressDialog;
    private static final int IMG_REQUEST_CODE = 0;
    private static final int VID_REQUEST_CODE = 1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_workshop);

        setTitle("Tambah Data Workshop");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");

        etJudulWorkshop = findViewById(R.id.inputJudulWorkshop);
        etDeskripsiWorkshop = findViewById(R.id.inputDeskripsiWorkshop);
        tvInputImage = findViewById(R.id.tvTambahGambar);
        tvInputVideo = findViewById(R.id.tvTambahVideo);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnSelectVideo = findViewById(R.id.btnSelectVideo);
        btnTambahWorkshop = findViewById(R.id.btnTambahWorkshop);

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent (Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, IMG_REQUEST_CODE);
            }
        });

        btnSelectVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent (Intent.ACTION_PICK,
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, VID_REQUEST_CODE);
            }
        });

        btnTambahWorkshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                judul_workshop = etJudulWorkshop.getText().toString();
                deskripsi_workshop = etDeskripsiWorkshop.getText().toString();
                gambar_workshop = tvInputImage.getText().toString();
                video_workshop = tvInputVideo.getText().toString();

                if (judul_workshop.trim().equals("")){
                    etJudulWorkshop.setError("Harap Masukan Judul");
                } else if (deskripsi_workshop.trim().equals("")){
                    etDeskripsiWorkshop.setError("Harap Masukan Deskripsi");
                } else if (gambar_workshop.trim().equals("Tambah Gambar")){
                    tvInputImage.setError("Harap Pilih Gambar");
                } else if (video_workshop.trim().equals("Tambah Video")){
                    tvInputVideo.setError("Harap Pilih Video");
                } else {

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                tvInputImage.setText(mediaPath);
                tvInputImage.setError(null);
                cursor.close();
            } else if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
                Uri selectedVideo = data.getData();
                String[] filePathColumn = {MediaStore.Video.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedVideo, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                tvInputVideo.setText(mediaPath);
                tvInputVideo.setError(null);
                cursor.close();
            } else {
                Toast.makeText(this, "Harap pilih Image/Video", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Ada Kesalahan", Toast.LENGTH_LONG).show();
        }
    }

    private void uploadFile(){
        progressDialog.show();


    }
}