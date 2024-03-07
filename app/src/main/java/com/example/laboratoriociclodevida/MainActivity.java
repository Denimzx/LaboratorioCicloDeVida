package com.example.laboratoriociclodevida;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView timerTextView;
    private Button startButton, pauseButton, resetButton, lapButton;
    private Handler handler;
    private boolean isRunning;
    private long startTime, elapsedTime;
    private int lapCount = 0;
    private LinearLayout lapLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = findViewById(R.id.timerTextView);
        startButton = findViewById(R.id.startButton);
        pauseButton = findViewById(R.id.pauseButton);
        resetButton = findViewById(R.id.resetButton);
        lapButton = findViewById(R.id.lapButton);
        lapLayout = findViewById(R.id.lapLayout);

        handler = new Handler();
    }

    public void onStartButtonClick(View view) {
        startButton.setVisibility(View.GONE);
        pauseButton.setVisibility(View.VISIBLE);
        resetButton.setVisibility(View.VISIBLE);
        lapButton.setVisibility(View.VISIBLE);

        if (!isRunning) {
            startTime = System.currentTimeMillis() - elapsedTime;
            handler.postDelayed(timerRunnable, 0);
            isRunning = true;
        }
    }

    public void onPauseButtonClick(View view) {
        startButton.setVisibility(View.VISIBLE);
        pauseButton.setVisibility(View.GONE);

        handler.removeCallbacks(timerRunnable);
        isRunning = false;
    }

    public void onResetButtonClick(View view) {
        startButton.setVisibility(View.VISIBLE);
        pauseButton.setVisibility(View.GONE);
        resetButton.setVisibility(View.GONE);
        lapButton.setVisibility(View.GONE);
        lapLayout.removeAllViews();

        handler.removeCallbacks(timerRunnable);
        isRunning = false;
        elapsedTime = 0;
        timerTextView.setText("00:00:00");
        lapCount = 0;
    }

    public void onLapButtonClick(View view) {
        lapCount++;
        if (lapCount <= 5) {
            TextView lapTextView = new TextView(this);
            lapTextView.setText("Lap " + lapCount + ": " + timerTextView.getText());
            lapLayout.addView(lapTextView);
        }
    }

    private final Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            elapsedTime = System.currentTimeMillis() - startTime;
            int seconds = (int) (elapsedTime / 1000) % 60;
            int minutes = (int) ((elapsedTime / (1000 * 60)) % 60);
            int hours = (int) ((elapsedTime / (1000 * 60 * 60)) % 24);
            String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            timerTextView.setText(time);
            handler.postDelayed(this, 500);
        }
    };
}
