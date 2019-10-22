package com.technologycompany.flyornot;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class in_game extends AppCompatActivity implements GestureDetector.OnGestureListener {

    ArrayList<imageHandler> images = new ArrayList<>();
    imageHandler imgh;
    int[] skipArray = new int[3];
    int skipCount=0;
    TextView score,textName, count;                          //for displaying entities
    ImageView imageView;
    ProgressBar progressBar;
    CountDownTimer countDownTimer, countDownTimer2, countDownTimer3, counterStart;
    public View viewMain, viewSec;
    int storeRandom=0, counter = 0, variable=4, progress = 0;               // for score purposes
    Button skip;
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
        skip = findViewById(R.id.skip);
        viewMain = findViewById(R.id.viewGroupMain);
        viewSec  = findViewById(R.id.viewGroupSec);

        viewMain.setVisibility(View.GONE);
        viewSec.setVisibility(View.VISIBLE);

//  ----------------------------------<> Countdown Main Start <>------------------------------------

        counterStart = new CountDownTimer(2800,700) {
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
                Animation animation = new AlphaAnimation(0.1f , 1.0f);
                animation.setDuration(500);
                viewMain.startAnimation(animation);

                getImages();
                Collections.shuffle(images);
                imageHandler imageHandler = getData();
                showData(imageHandler);

 //                      ---------------<> Countdown 1 Start <>--------------

                countDownTimer = new CountDownTimer(3000, 500) {
                    @Override
                    public void onTick(long l) {

                        progressBar.setProgress((int)progress*100/(3000/500));
                        progress++;
                    }

                    @Override
                    public void onFinish() {
                        saveHighScore();
                        save();
                        Intent intent = new Intent(in_game.this, game_end.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.zoom_in,R.anim.fade_in);
                        finish();

                    }
                };
                countDownTimer.start();

//                        ---------------<> Countdown 1 End <>--------------


//                        ---------------<> Countdown 2 Start <>--------------

                countDownTimer2 = new CountDownTimer(2000, 150) {
                    @Override
                    public void onTick(long l) {

                        progressBar.setProgress((int)progress*100/(2000/150));
                        progress++;
                    }

                    @Override
                    public void onFinish() {
                        saveHighScore();
                        save();
                        Intent intent = new Intent(in_game.this, game_end.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.zoom_in,R.anim.fade_in);
                        finish();

                    }
                };

//                        ---------------<> Countdown 2 End <>--------------

//                        ---------------<> Countdown 3 Start <>--------------

                countDownTimer3 = new CountDownTimer(1000, 100) {
                    @Override
                    public void onTick(long l) {

                        progressBar.setProgress((int)progress*100/(1000/100));
                        progress++;
                    }

                    @Override
                    public void onFinish() {
                        saveHighScore();
                        save();
                        Intent intent = new Intent(in_game.this, game_end.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.zoom_in,R.anim.fade_in);
                        finish();

                    }
                };

//                        ---------------<> Countdown 3 End <>--------------

            }
        }.start();



//                        ---------------<> Countdown Main End <>--------------


        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                imageHandler imageHandler = imgh;
                int id = imageHandler.getId();
                skipArray[skipCount]= id;
                int c= 2 - skipCount;
                skipCount++;
                skip.setText("skip" + "(" + c + ")");
                if(skipCount == 3){
                    skip.setEnabled(false);
                    skip.setTextColor(Color.parseColor("#556B2F"));
                }
                countDownTimer.cancel();
                showData(getData());
                countDownTimer.start();

            }
        });

    } // onCreate end

//  -----------------------------------<> Countdown methods <>--------------------------------------

    public void countDownStart(){
        if(counter<=20){
            countDownTimer.start();
        }
        if(counter>=20){
            countDownTimer2.start();
        }
        if(counter>=40){
            countDownTimer3.start();
        }
    }


    public void countDownCancel(){
        if(counter<=20){
            countDownTimer.cancel();
        }
        if(counter>=20){
            countDownTimer2.cancel();
        }
        if(counter>=40){
            countDownTimer3.cancel();
        }
    }




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

//                      ----------------<> Swipe Gesture <>------------------

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float v, float v1) {

        imageHandler imageHandler = imgh;
        String fly = imageHandler.getFly();

        if(e1.getY() - e2.getY() > 50){    // for up

            if(fly.equals("yes")){

                countDownCancel();
                counter = counter + 1;
                showData(getData());
                countDownStart();
                return true;
            }
            else if(fly.equals("no")){

                countDownCancel();
                saveHighScore();
                save();
                Intent intent = new Intent(in_game.this,game_end.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in,R.anim.fade_in);
                finish();
                return true;
            }
        }
        if(e2.getY() - e1.getY() > 50){  // for down

            if(fly.equals("no")){

                countDownCancel();
                counter = counter + 1;
                showData(getData());
                countDownStart();
                return true;
            }
            else if(fly.equals("yes")){

                countDownCancel();
                saveHighScore();
                save();
                Intent intent = new Intent(in_game.this,game_end.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in,R.anim.fade_in);
                return true;
            }
        }
        return false;
    }
//                    ----------------<> Swipe Gesture Ended <>------------------

// -------------------------------------<> Gestures Ended <>----------------------------------------




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
        if(storeRandom == images.size() - 1){
            Collections.shuffle(images);
            storeRandom = 0;
        }
        imgHndlr.setName(images.get(storeRandom).getName());
        imgHndlr.setFly(images.get(storeRandom).getFly());
        imgHndlr.setUrl(images.get(storeRandom).getUrl());
        storeRandom++;
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

// --------------------------------------<> Saving High Score <>------------------------------------

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

        images.add(new imageHandler(1,"Sparrow", "yes", getApplicationContext().getResources().getDrawable(R.drawable.img_sparrow)));

        images.add(new imageHandler(2,"Crow", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_crow)));

        images.add(new imageHandler(3,"Tiger", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_tiger)));

        images.add(new imageHandler(4,"Cow", "no", getApplicationContext().getResources().getDrawable(R.drawable.img_cow)));

        images.add(new imageHandler(5,"Aeroplane", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_aeroplane)));

        images.add(new imageHandler(6,"Roses", "no", getApplicationContext().getResources().getDrawable(R.drawable.img_roses)));

        images.add(new imageHandler(7,"Batman", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_batman)));

        images.add(new imageHandler(8,"Bee", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_bee)));

        images.add(new imageHandler(9,"Dog", "no", getApplicationContext().getResources().getDrawable(R.drawable.img_dog)));

        images.add(new imageHandler(10,"Pen", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_pen)));

        images.add(new imageHandler(11,"Alien", "no", getApplicationContext().getResources().getDrawable(R.drawable.img_alien)));

        images.add(new imageHandler(12,"Booger", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_booger)));

        images.add(new imageHandler(13,"Cloud", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_cloud)));

        images.add(new imageHandler(14,"Donald Trump", "no", getApplicationContext().getResources().getDrawable(R.drawable.img_donaltrump)));

        images.add(new imageHandler(15,"Drone", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_drone)));

        images.add(new imageHandler(16,"Elon Musk", "yes", getApplicationContext().getResources().getDrawable(R.drawable.img_elonmusk)));

        images.add(new imageHandler(17,"Football", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_football)));

        images.add(new imageHandler(18,"Gal Gadot", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_galgodat)));

        images.add(new imageHandler(19,"Hair", "yes", getApplicationContext().getResources().getDrawable(R.drawable.img_hair)));

        images.add(new imageHandler(20,"Hen", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_hen)));

        images.add(new imageHandler(21,"Irfan Junejo", "no", getApplicationContext().getResources().getDrawable(R.drawable.img_irfanjunejo)));

        images.add(new imageHandler(22,"Ironman", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_ironman)));

        images.add(new imageHandler(23,"Jack Sparrow", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_jacksparrow)));

        images.add(new imageHandler(24,"Leonal Messi", "no", getApplicationContext().getResources().getDrawable(R.drawable.img_leonalmessi)));

        images.add(new imageHandler(25,"Logan Paul", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_loganpaul)));

        images.add(new imageHandler(26,"Mobula", "no", getApplicationContext().getResources().getDrawable(R.drawable.img_mobula)));

        images.add(new imageHandler(27,"Mosquito", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_mosquito)));

        images.add(new imageHandler(28,"PK", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_pk)));

        images.add(new imageHandler(29,"Peacock", "yes", getApplicationContext().getResources().getDrawable(R.drawable.img_peacock)));

        images.add(new imageHandler(30,"Penguin", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_penguin)));

        images.add(new imageHandler(31,"Piece of Coth", "yes", getApplicationContext().getResources().getDrawable(R.drawable.img_pieceofcloth)));

        images.add(new imageHandler(32,"Poop", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_poop)));

        images.add(new imageHandler(33,"Raza Samo", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_razasamo)));

        images.add(new imageHandler(34,"Robert Downey Jr", "no", getApplicationContext().getResources().getDrawable(R.drawable.img_robertdowneyjr)));

        images.add(new imageHandler(35,"Rocket", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_rocket)));

        images.add(new imageHandler(36,"Ronaldo", "no", getApplicationContext().getResources().getDrawable(R.drawable.img_ronaldo)));

        images.add(new imageHandler(37,"Saad ur Rehman", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_saadrehman)));

        images.add(new imageHandler(38,"Salman Khan", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_salmankhan)));

        images.add(new imageHandler(39,"Shahrukh Khan", "no", getApplicationContext().getResources().getDrawable(R.drawable.img_shahrukhkhan)));

        images.add(new imageHandler(40,"Sham Idrees", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_shamidrees)));

        images.add(new imageHandler(41,"Sky Diver", "yes", getApplicationContext().getResources().getDrawable(R.drawable.img_skydiver)));

        images.add(new imageHandler(42,"Smoke", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_smoke)));

        images.add(new imageHandler(43,"Snoop Dogg", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_snoopdog)));

        images.add(new imageHandler(44,"Tom and Jerry", "no", getApplicationContext().getResources().getDrawable(R.drawable.img_tom)));

        images.add(new imageHandler(45,"Wonder Women", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_wonderwomen)));

        images.add(new imageHandler(46,"Apple", "no", getApplicationContext().getResources().getDrawable(R.drawable.img_apple)));

        images.add(new imageHandler(47,"Balloons", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_balloons)));

        images.add(new imageHandler(48,"Condor", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_condor)));

        images.add(new imageHandler(49,"Ducky Bhai", "no", getApplicationContext().getResources().getDrawable(R.drawable.img_duckybhai)));

        images.add(new imageHandler(50,"Helicopter", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_helicopter)));

        images.add(new imageHandler(51,"Light", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_light)));

        images.add(new imageHandler(52,"Music Dog", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_musicdog)));

        images.add(new imageHandler(53,"Smell", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_smell)));

        images.add(new imageHandler(55,"Soda", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_soda)));

        images.add(new imageHandler(56,"Space Craft", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_spacecraft)));

        images.add(new imageHandler(57,"Spiderman", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_spiderman)));

        images.add(new imageHandler(58,"Stone", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_stone)));

        images.add(new imageHandler(59,"Water", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_water)));

        images.add(new imageHandler(60,"Weed", "yes",  getApplicationContext().getResources().getDrawable(R.drawable.img_weed)));

        images.add(new imageHandler(61,"Windows", "no",  getApplicationContext().getResources().getDrawable(R.drawable.img_windows)));

    }





}  // main function
