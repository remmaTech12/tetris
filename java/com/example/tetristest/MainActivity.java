package com.example.tetristest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private CanvasView tetrisCanvasView;

    // Set variables for drop blocks.
    private int dropPeriod = 1200;
    private final Handler handler = new Handler();
    private Runnable runnable;
    private int score = 0;
    private String gameOverString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        tetrisCanvasView = this.findViewById(R.id.test_view);
        tetrisCanvasView.showCanvas(CanvasView.STATIONAL);

        timerSet();

        Button downButton = findViewById(R.id.downButton);
        setButtonFunction(downButton, CanvasView.DOWN);

        Button leftButton = findViewById(R.id.leftButton);
        setButtonFunction(leftButton, CanvasView.LEFT);

        Button rightButton = findViewById(R.id.rightButton);
        setButtonFunction(rightButton, CanvasView.RIGHT);

        Button rotateButton = findViewById(R.id.rotateButton);
        setButtonFunction(rotateButton, CanvasView.ROTATE);

    }

    private void setButtonFunction(Button button, final int motion) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tetrisCanvasView.showCanvas(motion);
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

                score = tetrisCanvasView.getScore();
                TextView scoreTextView = findViewById(R.id.scoreTextView);
                scoreTextView.setText(String.valueOf(score));
                TextView gameTextView = findViewById(R.id.gameTextView);
                if (tetrisCanvasView.getGameOverFlag() == true) {
                    gameTextView.setText(R.string.gameOver);
                }
            }
        };

        handler.post(runnable);
    }
}