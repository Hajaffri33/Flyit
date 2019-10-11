package com.technologycompany.flyornot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

    // database references
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    DatabaseReference imageRef = databaseReference.child("images");

    int  id;                                               //for retrieving purpose
    String name, fly, url;
    int savingRandom;                                       // for randomising
    Random random = new Random();
    List<Integer> randomNo  = new ArrayList<Integer>();
    TextView score,textName;                                 //for displaying entities
    ImageView imageView;
    int best_score, counter =0;                             // for score purposes
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

        random();
        loadImage();




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

        if(e1.getY() - e2.getY() > 50){    // for up

            if(fly.equalsIgnoreCase("y")){
                Toast.makeText(this,"You are right", Toast.LENGTH_SHORT).show();
                counter++;
            }
            return true;
        }

        if(e2.getY() - e1.getY() > 50){  // for down

            if(fly.equalsIgnoreCase("n")){
                Toast.makeText(this,"You are wrong", Toast.LENGTH_SHORT).show();
                counter++;
            }
            return true;
        }

        return false;

    } //    Gestures Ended


// ------------------------------------<> Checking fly <>-------------------------------------------



    public void check_fly(){


   }


// ------------------------------------<> Randomising <>--------------------------------------------

     public void random(){

        int index;
        for(index=0;index<5;index++){

            randomNo.add(index);                  // saving random number in array
        }
        Collections.shuffle(randomNo);            // shuffling random number in array

     }

// ------------------------------------<> Getting Random <>-----------------------------------------

    public int getRandom(){

        int value;
        value = randomNo.get(counter);
        return value;
    }

// ------------------------------------<> Retrieving Data <>----------------------------------------

    public void loadImage(){



        imageRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                id = getRandom();
                String ids = String.valueOf(id);
                name = dataSnapshot.child(ids).child("name").getValue(String.class);
                url  = dataSnapshot.child(ids).child("url").getValue(String.class);
                fly  = dataSnapshot.child(ids).child("fly").getValue(String.class);
                Glide.with(in_game.this).load(url).into(imageView);
                textName.setText(name);
        }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });

    }

// ------------------------------------<!><!><!><!><!><!><!><!>---------------------------------------



}  // main function
