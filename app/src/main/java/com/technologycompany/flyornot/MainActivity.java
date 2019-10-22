package com.technologycompany.flyornot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button play;
    TextView bestscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = (Button) findViewById(R.id.main_button);
        bestscore =findViewById(R.id.bestscore);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goto_in_game();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("GameHighScore", MODE_PRIVATE);
        int score = sharedPreferences.getInt("score", 0);
        bestscore.setText(String.valueOf(score));

    }

    public void goto_in_game(){
        Intent intent = new Intent(this, swipe.class);
        startActivity(intent);
        overridePendingTransition(R.anim.zoom_in, R.anim.fade_in);
        finish();
    }



}
