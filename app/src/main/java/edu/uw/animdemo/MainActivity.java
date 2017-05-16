package edu.uw.animdemo;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main";

    private DrawingView view;

    private GestureDetectorCompat mDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = (DrawingView)findViewById(R.id.drawingView);

        mDetector = new GestureDetectorCompat(this, new MyGestureListener());

        ValueAnimator animation = ValueAnimator.ofFloat(0f, 1f);
        animation.setDuration(1000);
        animation.start();


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.e(TAG, event.toString());
        mDetector.onTouchEvent(event);
        int action = MotionEventCompat.getActionMasked(event);
        float x = event.getX();
        float y = event.getY();
        switch(action) {
            case (MotionEvent.ACTION_DOWN): //put finger down
                //e.g., move ball
                view.ball.cx = x;
                view.ball.cy = y;

                ObjectAnimator animX = ObjectAnimator.ofFloat(view.ball, "x", x);
                ObjectAnimator animY = ObjectAnimator.ofFloat(view.ball, "y", y);
                animX.setDuration(1000);
                animY.setDuration(1500);
                AnimatorSet animSetXY = new AnimatorSet();
                animSetXY.playTogether(animX, animY);
                animSetXY.start();

                return true;
            case (MotionEvent.ACTION_MOVE): //move finger
                //e.g., move ball
                view.ball.cx = x;
                view.ball.cy = y;
                return true;
            case (MotionEvent.ACTION_UP): //lift finger up
            case (MotionEvent.ACTION_CANCEL): //aborted gesture
            case (MotionEvent.ACTION_OUTSIDE): //outside bounds
            default:
                return super.onTouchEvent(event);
        }
    }

    public class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.v(TAG, "Fling!");
            view.ball.dx = -1*velocityX*.02f;
            view.ball.dy = -1*velocityY*.02f;

            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

    /** Menus **/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.menu_pulse:
                //make the ball change size!
//                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view.ball, "radius", 100f, 200f);
//                objectAnimator.setDuration(1000);
//                objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
//                objectAnimator.setRepeatMode(ObjectAnimator.REVERSE);
//                objectAnimator.start();

                ObjectAnimator anim = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.animations);
                anim.setTarget(view.ball);
                anim.start();

                return true;
            case R.id.menu_button:
                startActivity(new Intent(MainActivity.this, ButtonActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
