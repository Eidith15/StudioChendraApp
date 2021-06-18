package com.eidith.studiochendraapp.activity.layanan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eidith.studiochendraapp.R;
import com.eidith.studiochendraapp.api.APIClient;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

public class DetailLayananActivity extends AppCompatActivity {

    private PlayerView vvDetailLayanan;
    private TextView tvDetailTanggalLayanan, tvDetailJudulLayanan, tvDetailDeskripsiLayanan;
    private ImageView ivDetailLayanan;
    private Button btnRegistrasiLayanan;

    private ImageButton btnFullScreen;
    private SimpleExoPlayer player;
    boolean fullscreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_layanan);

        setTitle("Layanan Jasa Fotografi Detail");

        //Assign Variable
        vvDetailLayanan = findViewById(R.id.vvDetailLayanan);
        tvDetailTanggalLayanan = findViewById(R.id.tvDetailTanggalLayanan);
        tvDetailJudulLayanan = findViewById(R.id.tvDetailJudulLayanan);
        tvDetailDeskripsiLayanan = findViewById(R.id.tvDetailDeskripsiLayanan);
        ivDetailLayanan = findViewById(R.id.ivDetailLayanan);
        btnRegistrasiLayanan = findViewById(R.id.btnRegistrasiLayanan);
        btnFullScreen = findViewById(R.id.btnFullscreen);

        //Get data from extras
        String judulLayanan = getIntent().getExtras().getString("Judul Layanan");
        String deskripsiLayanan = getIntent().getExtras().getString("Deskripsi Layanan");
        String gambarLayanan = getIntent().getExtras().getString("Gambar Layanan");
        String videoLayanan = getIntent().getExtras().getString("Video Layanan");
        String tanggalLayanan = getIntent().getExtras().getString("Tanggal Layanan");

        //Set data to view
        tvDetailJudulLayanan.setText(judulLayanan);
        tvDetailDeskripsiLayanan.setText(deskripsiLayanan);
        tvDetailTanggalLayanan.setText(tanggalLayanan);

        //Glide Image to image view
        Glide.with(DetailLayananActivity.this)
                .load(APIClient.imageURL+gambarLayanan)
                .apply(new RequestOptions().override(800, 400))
                .into(ivDetailLayanan);

        //Create exo Player fof playing video
        MediaItem mediaItem = MediaItem.fromUri(APIClient.videoURL+videoLayanan);
        player = new SimpleExoPlayer.Builder(this).build();

        vvDetailLayanan.setShowBuffering(PlayerView.SHOW_BUFFERING_ALWAYS);
        vvDetailLayanan.setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING);
        vvDetailLayanan.setPlayer(player);
        vvDetailLayanan.setKeepScreenOn(true);
        player.setMediaItem(mediaItem);
        player.prepare();
        vvDetailLayanan.hideController();
        player.setPlayWhenReady(true);

        btnFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check
                if(fullscreen) {
                    //set exit full screen on click
                    btnFullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen));
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    if(getSupportActionBar() != null){
                        getSupportActionBar().show();
                    }
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) vvDetailLayanan.getLayoutParams();
                    params.width = params.MATCH_PARENT;
                    params.height = (int) ( 200 * getApplicationContext().getResources().getDisplayMetrics().density);
                    vvDetailLayanan.setLayoutParams(params);
                    fullscreen = false;
                }else{
                    //Set full screen on click
                    btnFullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen_exit));
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                            |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                    if(getSupportActionBar() != null){
                        getSupportActionBar().hide();
                    }
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) vvDetailLayanan.getLayoutParams();
                    params.width = params.MATCH_PARENT;
                    params.height = params.MATCH_PARENT;
                    vvDetailLayanan.setLayoutParams(params);
                    fullscreen = true;
                }

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        player.stop();
    }
}