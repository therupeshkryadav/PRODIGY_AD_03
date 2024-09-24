package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView timeDisplay;
    private ImageButton startButton, stopButton, resetButton;
    private Handler handler = new Handler();
    private long startTime, timeInMillis, timeSwapBuff, updateTime = 0L;
    private Runnable updateTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeDisplay = findViewById(R.id.time_display);
        startButton = findViewById(R.id.start_button);
        stopButton = findViewById(R.id.stop_button);
        resetButton = findViewById(R.id.reset_button);

        updateTimer = new Runnable() {
            @Override
            public void run() {
                timeInMillis = System.currentTimeMillis() - startTime;
                int milliseconds = (int) (timeInMillis % 1000);
                int seconds = (int) (timeInMillis / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;

                String time = String.format("%02d:%02d:%03d", minutes, seconds, milliseconds);
                timeDisplay.setText(time);

                handler.postDelayed(this, 10); // Update every 10 milliseconds
            }
        };

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = System.currentTimeMillis();
                handler.postDelayed(updateTimer, 0);
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSwapBuff += timeInMillis;
                handler.removeCallbacks(updateTimer);
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = 0L;
                timeInMillis = 0L;
                timeSwapBuff = 0L;
                updateTime = 0L;
                timeDisplay.setText("00:00:000");
                handler.removeCallbacks(updateTimer);
            }
        });
    }
}
