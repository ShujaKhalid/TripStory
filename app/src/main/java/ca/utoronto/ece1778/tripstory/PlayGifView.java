package ca.utoronto.ece1778.tripstory;

/**
 * Created by sssk9797 on 27/11/2016.
 */
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

public class PlayGifView extends View{

    private static final int DEFAULT_MOVIEW_DURATION = 1000;

    private int mMovieResourceId;
    private Movie mMovie;

    private long mMovieStart = 0;
    private int mCurrentAnimationTime = 0;

    @SuppressLint("NewApi")
    public PlayGifView(Context context, AttributeSet attrs) {
        super(context, attrs);

        /**
         * Starting from HONEYCOMB have to turn off HardWare acceleration to draw
         * Movie on Canvas.
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    public void setImageResource(int mvId){
        this.mMovieResourceId = mvId;
        System.out.println("mvID inside PlayGIfView = " + mvId);
        mMovie = Movie.decodeStream(getResources().openRawResource(mMovieResourceId));
        requestLayout();
    }

    /*
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(mMovie != null){
            setMeasuredDimension(mMovie.width()*30, mMovie.height()*10);
        }else{
            //setMeasuredDimension(getSuggestedMinimumWidth(), getSuggestedMinimumHeight());
            setMeasuredDimension(10000, 10000);
        }
    }
    */


    @Override
    protected void onDraw(Canvas canvas) {
        if (mMovie != null){
            updateAnimtionTime();
            drawGif(canvas);
            invalidate();
        }else{
            drawGif(canvas);
        }
    }

    private void updateAnimtionTime() {
        long now = android.os.SystemClock.uptimeMillis();

        if (mMovieStart == 0) {
            mMovieStart = now;
        }
        int dur = mMovie.duration();
        if (dur == 0) {
            dur = DEFAULT_MOVIEW_DURATION;
        }
        mCurrentAnimationTime = (int) ((now - mMovieStart) % dur);
    }

    private void drawGif(Canvas canvas) {
        mMovie.setTime(mCurrentAnimationTime);

        float screenwidth = 350;
        float screenheight = 550;
        float gifwidth = 80;
        float gifheight = 80;
        float scaleWidth = (float) ((screenwidth / (1f*gifwidth)));//add 1f does the trick
        float scaleHeight = (float) ((screenheight / (1f*gifheight)));
        canvas.scale(scaleWidth, scaleHeight);


        //canvas.scale((float)this.getWidth() / (float)mMovie.width(),(float)this.getHeight() / (float)mMovie.height());
        mMovie.draw(canvas, 0, 0);
        canvas.restore();
    }

}
