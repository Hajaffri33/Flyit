package com.technologycompany.flyit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class in_game extends AppCompatActivity {

    Button fly,cant_fly;
    ProgressBar pb;
    int counter = 0;
    private int best_score = 0;
    TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);

        prog();
        fly = findViewById(R.id.fly);
        cant_fly = findViewById(R.id.cantfly);


        fly.setOnClickListener(new View.OnClickListener() {
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
        });
    }

   public void prog(){

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
    }





}
