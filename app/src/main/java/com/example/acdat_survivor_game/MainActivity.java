package com.example.acdat_survivor_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.acdat_survivor_game.controllerdriver.Dpad;
import com.example.acdat_survivor_game.databinding.ActivityMainBinding;
import com.example.acdat_survivor_game.surfaceviews.SurvivorView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.btnStart.setOnClickListener(this);
        binding.btnExit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnStart){
            Intent intent = new Intent(MainActivity.this, SurvivorActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            this.finish();
        } else if(v.getId() == R.id.btnExit){
            System.exit(0);
        }
    }

    @Override
    public void onBackPressed() {
        System.exit(0);
    }
}