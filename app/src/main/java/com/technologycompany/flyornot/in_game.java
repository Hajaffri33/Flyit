package com.technologycompany.flyornot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
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
    TextView score,textName, count;                                 //for displaying entities
    ImageView imageView;
    ProgressBar progressBar;
    CountDownTimer countDownTimer, counterStart;
    public View viewMain, viewSec;
    int progress = 0;
    int counter = 0;                             // for score purposes
    int variable=4;
    private GestureDetector gestureDetector;                 // for gestures

// -----------------------------------<> onCreate function <>---------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);

        gestureDetector = new GestureDetector(this,this);
        score = findViewById(R.id.score);
        textName  = findViewById(R.id.name);
        count     = findViewById(R.id.countdown);
        imageView = findViewById(R.id.image_viewer);
        progressBar = findViewById(R.id.progress);
        viewMain = findViewById(R.id.viewGroupMain);
        viewSec  = findViewById(R.id.viewGroupSec);

        viewMain.setVisibility(View.GONE);
        viewSec.setVisibility(View.VISIBLE);

        counterStart = new CountDownTimer(3800,1000) {
            @Override
            public void onTick(long l) {

                variable--;
                if(variable >= 1 )
                {count.setText(String.valueOf(variable));}
                if(variable == 0)
                {count.setText("GO!");}
            }

            @Override
            public void onFinish() {

                viewMain.setVisibility(View.VISIBLE);
                viewSec.setVisibility(View.GONE);

                getImages();
                imageHandler imageHandler = getData();
                showData(imageHandler);

 //                        ---------------<> Countdown Timer <>--------------

                countDownTimer = new CountDownTimer(2000, 10) {
                    @Override
                    public void onTick(long l) {

                        progress++;
                        progressBar.setProgress((int)progress*100/(2000/10));
                    }

                    @Override
                    public void onFinish() {
                        saveHighScore();
                        save();
                        Intent intent = new Intent(in_game.this, game_end.class);
                        startActivity(intent);
                        finish();

                    }
                };
                countDownTimer.start();

//                        ---------------<> Countdown End <>--------------

            }
        }.start();







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
        String fly = imageHandler.getFly();;

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
                saveHighScore();
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
                saveHighScore();
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
        for(int i=0; i<=10; i++){
            Collections.shuffle(images);
        }
        int storeRandom = random.nextInt(60);
        imgHndlr.setName(images.get(storeRandom).getName());
        imgHndlr.setFly(images.get(storeRandom).getFly());
        imgHndlr.setUrl(images.get(storeRandom).getUrl());
        return imgHndlr;
    }

// ----------------------------------------<> Saving Score <>---------------------------------------

    public void save(){
        int score = counter;
        SharedPreferences sharedPref = getSharedPreferences("MyData",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("score",score);
        editor.apply();

    }

// ----------------------------------------<> Saving Score <>---------------------------------------

    public void saveHighScore(){

        int highscore;
        SharedPreferences sharedPref = getSharedPreferences("GameHighScore",MODE_PRIVATE);
        highscore = sharedPref.getInt("score", 0);

        if(highscore<counter){
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("score",counter);
            editor.apply();
        }

    }





// --------------------------------------<> Getting Images <>---------------------------------------

    public void getImages(){

        images.add(new imageHandler("Sparrow", "yes", getApplicationContext().getResources().getDrawable(R.drawable.img_sparrow)));

        images.add(new imageHandler("Crow", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_crow)));

        images.add(new imageHandler("Tiger", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_tiger)));

        images.add(new imageHandler("Cow", "no", getApplicationContext().getResources().getDrawable(R.drawable.img_cow)));

        images.add(new imageHandler("Aeroplane", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_aeroplane)));

        images.add(new imageHandler("Roses", "no", getApplicationContext().getResources().getDrawable(R.drawable.img_roses)));

        images.add(new imageHandler("Batman", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_batman)));

        images.add(new imageHandler("Bee", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_bee)));

        images.add(new imageHandler("Dog", "no", getApplicationContext().getResources().getDrawable(R.drawable.img_dog)));

        images.add(new imageHandler("Pen", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_pen)));

        images.add(new imageHandler("Alien", "no", getApplicationContext().getResources().getDrawable(R.drawable.img_alien)));

        images.add(new imageHandler("Booger", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_booger)));

        images.add(new imageHandler("Cloud", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_cloud)));

        images.add(new imageHandler("Donald Trump", "no", getApplicationContext().getResources().getDrawable(R.drawable.img_donaltrump)));

        images.add(new imageHandler("Drone", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_drone)));

        images.add(new imageHandler("Elon Musk", "yes", getApplicationContext().getResources().getDrawable(R.drawable.img_elonmusk)));

        images.add(new imageHandler("Football", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_football)));

        images.add(new imageHandler("Gal Gadot", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_galgodat)));

        images.add(new imageHandler("Hair", "yes", getApplicationContext().getResources().getDrawable(R.drawable.img_hair)));

        images.add(new imageHandler("Hen", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_hen)));

        images.add(new imageHandler("Irfan Junejo", "no", getApplicationContext().getResources().getDrawable(R.drawable.img_irfanjunejo)));

        images.add(new imageHandler("Ironman", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_ironman)));

        images.add(new imageHandler("Jack Sparrow", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_jacksparrow)));

        images.add(new imageHandler("Leonal Messi", "no", getApplicationContext().getResources().getDrawable(R.drawable.img_leonalmessi)));

        images.add(new imageHandler("Logan Paul", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_loganpaul)));

        images.add(new imageHandler("Mobula", "no", getApplicationContext().getResources().getDrawable(R.drawable.img_mobula)));

        images.add(new imageHandler("Mosquito", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_mosquito)));

        images.add(new imageHandler("PK", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_pk)));

        images.add(new imageHandler("Peacock", "yes", getApplicationContext().getResources().getDrawable(R.drawable.img_peacock)));

        images.add(new imageHandler("Penguin", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_penguin)));

        images.add(new imageHandler("Piece of Coth", "yes", getApplicationContext().getResources().getDrawable(R.drawable.img_pieceofcloth)));

        images.add(new imageHandler("Poop", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_poop)));

        images.add(new imageHandler("Raza Samo", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_razasamo)));

        images.add(new imageHandler("Robert Downey Jr", "no", getApplicationContext().getResources().getDrawable(R.drawable.img_robertdowneyjr)));

        images.add(new imageHandler("Rocket", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_rocket)));

        images.add(new imageHandler("Ronaldo", "no", getApplicationContext().getResources().getDrawable(R.drawable.img_ronaldo)));

        images.add(new imageHandler("Saad ur Rehman", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_saadrehman)));

        images.add(new imageHandler("Salman Khan", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_salmankhan)));

        images.add(new imageHandler("Shahrukh Khan", "no", getApplicationContext().getResources().getDrawable(R.drawable.img_shahrukhkhan)));

        images.add(new imageHandler("Sham Idrees", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_shamidrees)));

        images.add(new imageHandler("Sky Diver", "yes", getApplicationContext().getResources().getDrawable(R.drawable.img_skydiver)));

        images.add(new imageHandler("Smoke", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_smoke)));

        images.add(new imageHandler("Snoop Dogg", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_snoopdog)));

        images.add(new imageHandler("Tom and Jerry", "no", getApplicationContext().getResources().getDrawable(R.drawable.img_tom)));

        images.add(new imageHandler("Wonder Women", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_wonderwomen)));

        images.add(new imageHandler("Apple", "no", getApplicationContext().getResources().getDrawable(R.drawable.img_apple)));

        images.add(new imageHandler("Balloons", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_balloons)));

        images.add(new imageHandler("Condor", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_condor)));

        images.add(new imageHandler("Ducky Bhai", "no", getApplicationContext().getResources().getDrawable(R.drawable.img_duckybhai)));

        images.add(new imageHandler("Helicopter", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_helicopter)));

        images.add(new imageHandler("Light", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_light)));

        images.add(new imageHandler("Music Dog", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_musicdog)));

        images.add(new imageHandler("Smell", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_smell)));

        images.add(new imageHandler("Soda", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_soda)));

        images.add(new imageHandler("Space Craft", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_spacecraft)));

        images.add(new imageHandler("Spiderman", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_spiderman)));

        images.add(new imageHandler("Stone", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_stone)));

        images.add(new imageHandler("Water", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_water)));

        images.add(new imageHandler("Weed", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_weed)));

        images.add(new imageHandler("Windows", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_windows)));

    }





}  // main function
