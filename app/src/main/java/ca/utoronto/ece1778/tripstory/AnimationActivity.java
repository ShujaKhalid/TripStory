package ca.utoronto.ece1778.tripstory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

/**
 * Created by sssk9797 on 27/11/2016.
 */
public class AnimationActivity extends Activity {
    public Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String currentframe = getIntent().getStringExtra("frame");
        setContentView(R.layout.activity_animation);

        if ( currentframe.equals("shark") ) {

            final PlayGifView gifView = (PlayGifView) findViewById(R.id.viewGif);
            gifView.setImageResource(R.raw.sharky);
            Animation fadein = new AlphaAnimation(0.f, 1.f);
            Animation fadeout = new AlphaAnimation(1.f, 0.f);
            gifView.setVisibility(View.VISIBLE);
            fadein.setDuration(1500);
            gifView.startAnimation(fadeout);
            //fadeout.setDuration(1500);
            //gifView.startAnimation(fadeout);

            startVibrate(gifView);

            gifView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    stopVibrate(findViewById(R.id.viewGif));
                    //gifView.destroyDrawingCache();
                    gifView.setVisibility(View.GONE);
                    finish();
                }
            }, 10000);

        } else if ( currentframe.equals("intro") ) {

            final PlayGifView gifView = (PlayGifView) findViewById(R.id.viewGif);
            gifView.setImageResource(R.raw.intro);
            Animation fadein = new AlphaAnimation(0.f, 1.f);
            Animation fadeout = new AlphaAnimation(1.f, 0.f);
            gifView.setVisibility(View.VISIBLE);
            fadein.setDuration(1500);
            gifView.startAnimation(fadeout);
            fadeout.setDuration(1500);
            gifView.startAnimation(fadeout);

            //startVibrate(gifView);

            gifView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //stopVibrate(findViewById(R.id.viewGif));
                    //gifView.destroyDrawingCache();
                    gifView.setVisibility(View.GONE);
                    finish();
                }
            }, 3700);

        }

    }


    public void startVibrate(View v) {
        long pattern[] = { 0, 100, 200, 300, 400 };
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(pattern, 2);

    }

    public void stopVibrate(View v) {
        vibrator.cancel();
    }

}
