package com.technologycompany.flyornot;

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

public class in_game extends AppCompatActivity implements GestureDetector.OnGestureListener {

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    DatabaseReference imageRef = databaseReference.child("images");


    int  id = 1, counter = 0;
    private int best_score = 0;
    String name, url;
    TextView score,textName;
    ImageView imageView;

    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);

        gestureDetector = new GestureDetector(this,this);
        score = findViewById(R.id.score);
        textName  = findViewById(R.id.name);
        imageView = findViewById(R.id.image_viewer);

       loadimage();




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
            id++;
            loadimage();
            check_fly();
            return true;
        }

        if(e2.getY() - e1.getY() > 50){
            score.setText("DOWN");
            id--;
            loadimage();
            check_fly();
            return true;
        }

        return false;
    }
//    Gestures Ended


//    Checking if fly or not
    public void check_fly(){




    }
//    Checking Ended



//    Retrieving the images/names

    public void loadimage(){

        imageRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String ids = String.valueOf(id);
                name = dataSnapshot.child(ids).child("name").getValue(String.class);
                url  = dataSnapshot.child(ids).child("url").getValue(String.class);
                textName.setText(name);
                Glide.with(in_game.this).load(url).into(imageView);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });



    }



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
*/

}
