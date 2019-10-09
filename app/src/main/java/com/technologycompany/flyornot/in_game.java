package com.technologycompany.flyornot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ProgressBar;
import android.widget.TextView;

public class in_game extends AppCompatActivity implements GestureDetector.OnGestureListener {

    ProgressBar pb;
    int counter = 0;
    private int best_score = 0;
    TextView score;

    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);

        gestureDetector = new GestureDetector(this,this);

        score = findViewById(R.id.score);


      /*fly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fly();
            }
        });

        cant_fly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cant_fly();
            }
        }); */
    }




//  Gestures Started

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float v, float v1) {

        if(e1.getY() - e2.getY() > 50){
            score.setText("UP");
            return true;
        }

        if(e2.getY() - e1.getY() > 50){
            score.setText("DOWN");
            return true;
        }

        return false;
    }

//    Gestures Ended



  /* public void prog(){

        pb = (ProgressBar) findViewById(R.id.progress);

       Timer t = new Timer();
       TimerTask tt = new TimerTask() {
           @Override
           public void run() {
               counter++;
               pb.setProgress(counter);
           }
       };
       t.schedule(tt, 0 , 30);
    }

    public void fly(){
           prog();
    }

    public void cant_fly(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }*/

}
