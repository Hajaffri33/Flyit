package com.technologycompany.flyornot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class game_end extends AppCompatActivity {


    Button try_again;
    TextView theScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_end);

        try_again = findViewById(R.id.again);
        theScore  = findViewById(R.id.the_score);


        load();
        try_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(game_end.this, in_game.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void home(View view) {

        Intent intent = new Intent(game_end.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void load() {

        SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
        String score = sharedPreferences.getString("score", "no_name_defined");

        theScore.setText(score);

    }

}
