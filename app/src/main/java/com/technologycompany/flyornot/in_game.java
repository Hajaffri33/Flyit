package com.technologycompany.flyornot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class in_game extends AppCompatActivity implements GestureDetector.OnGestureListener {

    ArrayList<imageHandler> images = new ArrayList<>();
    imageHandler imgh;
    Random random = new Random();
    TextView score,textName;                                 //for displaying entities
    ImageView imageView;
    ProgressBar progressBar;
    CountDownTimer countDownTimer;
    int time, interval, progress = 0;
    int counter = 0;                             // for score purposes
    private GestureDetector gestureDetector;                 // for gestures

// -----------------------------------<> onCreate function <>---------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);

        gestureDetector = new GestureDetector(this,this);
        score = findViewById(R.id.score);
        textName  = findViewById(R.id.name);
        imageView = findViewById(R.id.image_viewer);
        progressBar = findViewById(R.id.progress);

        getImages();
        imageHandler imageHandler = getData();
        showData(imageHandler);

//                        ---------------<> Countdown Timer <>--------------

        countDownTimer = new CountDownTimer(3000, 10) {
            @Override
            public void onTick(long l) {

                progress++;
                progressBar.setProgress((int)progress*100/(3000/10));
            }

            @Override
            public void onFinish() {
                save();
                Intent intent = new Intent(in_game.this, game_end.class);
                startActivity(intent);
                finish();

            }
        };

        countDownTimer.start();

//                        ---------------<> Countdown End <>--------------

    } // onCreate end


// ------------------------------------<> Gestures Started <>---------------------------------------


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {}

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {}

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float v, float v1) {

        imageHandler imageHandler = imgh;
        String fly = imageHandler.getFly();

        if(e1.getY() - e2.getY() > 50){    // for up

           if(fly.equals("yes")){

                counter = counter + 1;
                countDownTimer.cancel();
                showData(getData());
                countDownTimer.start();
                return true;
            }
           else if(fly.equals("no")){

               countDownTimer.cancel();
               save();
               Intent intent = new Intent(in_game.this,game_end.class);
               startActivity(intent);
               finish();
               return true;
           }
        }
        if(e2.getY() - e1.getY() > 50){  // for down

            if(fly.equals("no")){

                counter = counter + 1;
                countDownTimer.cancel();
                showData(getData());
                countDownTimer.start();
                return true;
            }
            else if(fly.equals("yes")){

                countDownTimer.cancel();
                save();
                Intent intent = new Intent(in_game.this,game_end.class);
                startActivity(intent);
                finish();
                return true;
            }
        }

        return false;

    } //    Gestures Ended

// ----------------------------------------<> Show Data <>------------------------------------------

    public void showData(imageHandler imageHandler){

        imgh = imageHandler;
        progress = 0;
        score.setText(String.valueOf(counter));
        imageView.setImageDrawable(imageHandler.getUrl());
        textName.setText(imageHandler.getName());

    }

// -----------------------------------------<> Get Data <>------------------------------------------


    public imageHandler getData(){

        imageHandler imgHndlr = new imageHandler();
        int storeRandom = random.nextInt(10);
        imgHndlr.setName(images.get(storeRandom).getName());
        imgHndlr.setFly(images.get(storeRandom).getFly());
        imgHndlr.setUrl(images.get(storeRandom).getUrl());
        return imgHndlr;
    }

// --------------------------------------<> Getting Images <>---------------------------------------

    public void getImages(){

        images.add(new imageHandler("sparrow", "yes", getApplicationContext().getResources().getDrawable(R.drawable.img_sparrow)));

        images.add(new imageHandler("crow", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_crow)));

        images.add(new imageHandler("tiger", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_tiger)));

        images.add(new imageHandler("cow", "no", getApplicationContext().getResources().getDrawable(R.drawable.img_cow)));

        images.add(new imageHandler("aeroplane", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_aeroplane)));

        images.add(new imageHandler("roses", "no", getApplicationContext().getResources().getDrawable(R.drawable.img_roses)));

        images.add(new imageHandler("batman", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_batman)));

        images.add(new imageHandler("bee", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_bee)));

        images.add(new imageHandler("dog", "no", getApplicationContext().getResources().getDrawable(R.drawable.img_dog)));

        images.add(new imageHandler("pen", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_pen)));

    }

// ----------------------------------------<> Saving Score <>---------------------------------------

    public void save(){
        String score = String.valueOf(counter);
        SharedPreferences sharedPref = getSharedPreferences("MyData",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("score",score);
        editor.apply();
        editor.commit();

    }






}  // main function
