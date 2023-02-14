package com.example.acdat_survivor_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.acdat_survivor_game.databinding.ActivityLoseBinding;
import com.example.acdat_survivor_game.databinding.ActivityMainBinding;

public class LoseActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityLoseBinding binding;
    private Integer score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = ActivityLoseBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        score = (Integer) getIntent().getSerializableExtra("score");

        binding.btnRestart.setOnClickListener(this);
        binding.btnHome.setOnClickListener(this);
        binding.txtScore.setText("Monedas ganadas: " + score);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnRestart){
            Intent intent = new Intent(LoseActivity.this, SurvivorActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            this.finish();
        } else if(v.getId() == R.id.btnHome){
            Intent intent = new Intent(LoseActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            this.finish();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoseActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        this.finish();
    }
}