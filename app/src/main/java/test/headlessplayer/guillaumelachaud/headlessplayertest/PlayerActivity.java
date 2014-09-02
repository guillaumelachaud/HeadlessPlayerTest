package test.headlessplayer.guillaumelachaud.headlessplayertest;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.io.IOException;


public class PlayerActivity extends Activity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {

    private boolean mIsHeadless = false;

    private final static String URL = "http://62.210.131.214/trailer.mp4";

    private MediaPlayer mMediaPlayer;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        mButton = (Button) findViewById(R.id.button);

        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);

        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnPreparedListener(this);
        try {
            mMediaPlayer.setDataSource(URL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.prepareAsync();


    }


    public void onSwitch(View view) {
        if(mIsHeadless){
            goSurface();
        } else {
            goHeadless();
        }
    }

    private void goHeadless(){
        mIsHeadless = true;
        mMediaPlayer.setDisplay(null);
        mButton.setText(R.string.goSurface);

    }

    private void goSurface(){
        mIsHeadless = false;
        mMediaPlayer.setDisplay(mSurfaceHolder);
        mButton.setText(R.string.goHeadless);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mMediaPlayer.setDisplay(mSurfaceHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }
}
