package com.example.tetristest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Handler;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private CanvasView tetrisCanvasView;

    // Set variables for drop blocks.
    private int dropPeriod = 1000;
    private final Handler handler = new Handler();
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        tetrisCanvasView = this.findViewById(R.id.test_view);
        tetrisCanvasView.showCanvas(CanvasView.STATIONAL);

        timerSet();

        Button dropButton = findViewById(R.id.dropButton);
        dropButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tetrisCanvasView.showCanvas(CanvasView.DOWN);
            }
        });

        Button leftButton = findViewById(R.id.leftButton);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tetrisCanvasView.showCanvas(CanvasView.LEFT);
            }
        });

        Button rightButton = findViewById(R.id.rightButton);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tetrisCanvasView.showCanvas(CanvasView.RIGHT);
            }
        });

        Button rotateButton = findViewById(R.id.rotateButton);
        rotateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tetrisCanvasView.showCanvas(CanvasView.ROTATE);
            }
        });
    }

    private void timerSet(){
        runnable = new Runnable() {
            @Override
            public void run() {
                // Drop a block.
                tetrisCanvasView.showCanvas(CanvasView.DOWN);
                handler.postDelayed(this, dropPeriod);
            }
        };

        handler.post(runnable);
    }
}