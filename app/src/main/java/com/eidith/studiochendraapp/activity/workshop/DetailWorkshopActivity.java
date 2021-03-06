package com.eidith.studiochendraapp.activity.workshop;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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

public class DetailWorkshopActivity extends AppCompatActivity {

    boolean fullscreen = false;
    private PlayerView vvDetailWorkshop;
    private TextView tvDetailJudulWorkshop, tvDetailDeskripsiWorkshop, tvDetailTanggalWorkshop;
    private ImageView ivDetailWorkshop;
    private ImageButton btnFullScreen;
    private SimpleExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_workshop);

        setTitle("Workshop Detail");

        //Assign Variable
        vvDetailWorkshop = findViewById(R.id.vvDetailWorkshop);
        tvDetailJudulWorkshop = findViewById(R.id.tvDetailJudulWorkshop);
        tvDetailDeskripsiWorkshop = findViewById(R.id.tvDetailDeskripsiWorkshop);
        ivDetailWorkshop = findViewById(R.id.ivDetailWorkshop);
        btnFullScreen = findViewById(R.id.btnFullscreen);
        tvDetailTanggalWorkshop = findViewById(R.id.tvDetailTanggalWorkshop);

        //Get data from extras
        String judulWorkshop = getIntent().getExtras().getString("Judul Workshop");
        String deskripsiWorkshop = getIntent().getExtras().getString("Deskripsi Workshop");
        String gambarWorkshop = getIntent().getExtras().getString("Gambar Workshop");
        String videWorkshop = getIntent().getExtras().getString("Video Workshop");
        String tanggalWorkshop = getIntent().getExtras().getString("Tanggal Workshop");

        //Set data to view
        tvDetailJudulWorkshop.setText(judulWorkshop);
        tvDetailDeskripsiWorkshop.setText(deskripsiWorkshop);
        tvDetailTanggalWorkshop.setText(tanggalWorkshop);

        //Glide Image to image view
        Glide.with(DetailWorkshopActivity.this)
                .load(APIClient.imageURL + gambarWorkshop)
                .apply(new RequestOptions().override(800, 400))
                .into(ivDetailWorkshop);

        //Create exo Player fof playing video
        MediaItem mediaItem = MediaItem.fromUri(APIClient.videoURL + videWorkshop);
        player = new SimpleExoPlayer.Builder(this).build();

        vvDetailWorkshop.setShowBuffering(PlayerView.SHOW_BUFFERING_ALWAYS);
        vvDetailWorkshop.setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING);
        vvDetailWorkshop.setPlayer(player);
        vvDetailWorkshop.setKeepScreenOn(true);
        player.setMediaItem(mediaItem);
        player.prepare();
        vvDetailWorkshop.hideController();
        player.setPlayWhenReady(true);

        btnFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check
                if (fullscreen) {
                    //set exit full screen on click
                    btnFullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen));
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().show();
                    }
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) vvDetailWorkshop.getLayoutParams();
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    params.height = (int) (200 * getApplicationContext().getResources().getDisplayMetrics().density);
                    vvDetailWorkshop.setLayoutParams(params);
                    fullscreen = false;
                } else {
                    //Set full screen on click
                    btnFullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen_exit));
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().hide();
                    }
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) vvDetailWorkshop.getLayoutParams();
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    vvDetailWorkshop.setLayoutParams(params);
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
