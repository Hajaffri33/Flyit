package com.technologycompany.flyornot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

public class swipe extends AppCompatActivity {

    private TextView touch_to_continue ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        touch_to_continue = findViewById(R.id.touch);
        startAnimation();





    }

    public void startAnimation(){

        Animation animation = new AlphaAnimation(0.1f , 1.0f);
        animation.setDuration(1000);
        animation.setStartOffset(0);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        touch_to_continue.startAnimation(animation);

    }

    public void move(View view) {

        startActivity(new Intent(this, in_game.class));
        finish();


    }
}
