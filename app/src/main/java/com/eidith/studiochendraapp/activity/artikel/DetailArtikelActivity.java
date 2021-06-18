package com.eidith.studiochendraapp.activity.artikel;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eidith.studiochendraapp.R;
import com.eidith.studiochendraapp.api.APIClient;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

public class DetailArtikelActivity extends AppCompatActivity {

    private PlayerView vvDetailArtikel;
    private TextView tvDetailTanggalArtikel, tvDetailJudulArtikel, tvDetailDeskripsiArtikel;
    private ImageView ivDetailArtikel;

    private ImageButton btnFullScreen;
    private SimpleExoPlayer player;
    boolean fullscreen = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_artikel);

        setTitle("Artikel Detail");

        //Assign Variable
        vvDetailArtikel = findViewById(R.id.vvDetailArtikel);
        tvDetailTanggalArtikel = findViewById(R.id.tvDetailTanggalArtikel);
        tvDetailJudulArtikel = findViewById(R.id.tvDetailJudulArtikel);
        tvDetailDeskripsiArtikel = findViewById(R.id.tvDetailDeskripsiArtikel);
        ivDetailArtikel = findViewById(R.id.ivDetailArtikel);
        btnFullScreen = findViewById(R.id.btnFullscreen);

        //Get data from extras
        String judulArtikel = getIntent().getExtras().getString("Judul Artikel");
        String deskripsiArtikel = getIntent().getExtras().getString("Deskripsi Artikel");
        String gambarArtikel = getIntent().getExtras().getString("Gambar Artikel");
        String videoArtikel = getIntent().getExtras().getString("Video Artikel");
        String tanggalArtikel = getIntent().getExtras().getString("Tanggal Artikel");

        //Set data to view
        tvDetailJudulArtikel.setText(judulArtikel);
        tvDetailDeskripsiArtikel.setText(deskripsiArtikel);
        tvDetailTanggalArtikel.setText(tanggalArtikel);

        //Glide Image to image view
        Glide.with(DetailArtikelActivity.this)
                .load(APIClient.imageURL+gambarArtikel)
                .apply(new RequestOptions().override(800, 400))
                .into(ivDetailArtikel);

        //Create exo Player fof playing video
        MediaItem mediaItem = MediaItem.fromUri(APIClient.videoURL+videoArtikel);
        player = new SimpleExoPlayer.Builder(this).build();

        vvDetailArtikel.setShowBuffering(PlayerView.SHOW_BUFFERING_ALWAYS);
        vvDetailArtikel.setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING);
        vvDetailArtikel.setPlayer(player);
        vvDetailArtikel.setKeepScreenOn(true);
        player.setMediaItem(mediaItem);
        player.prepare();
        vvDetailArtikel.hideController();
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
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) vvDetailArtikel.getLayoutParams();
                    params.width = params.MATCH_PARENT;
                    params.height = (int) ( 200 * getApplicationContext().getResources().getDisplayMetrics().density);
                    vvDetailArtikel.setLayoutParams(params);
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
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) vvDetailArtikel.getLayoutParams();
                    params.width = params.MATCH_PARENT;
                    params.height = params.MATCH_PARENT;
                    vvDetailArtikel.setLayoutParams(params);
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
