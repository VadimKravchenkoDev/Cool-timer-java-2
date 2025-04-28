package com.kravchenkovadim.cooltimerkotlin;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Boolean checkTimer = true;
    Button button;
    CountDownTimer timer;
    TextView textView;
    private long secondLeft = 60000;
    private SeekBar seekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(600);
        seekBar.setProgress(60);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                int minutes = progress/60;
                int seconds = progress - (minutes*60);

                String minutesString = "";
                String secondsString = "";

                if(minutes < 10) {
                    minutesString ="0"+ minutes;
                } else {
                    minutesString = String.valueOf(minutes);

                }
                if(seconds<10){
                    secondsString = "0" + seconds;
                } else {
                    secondsString = String.valueOf(seconds);
                }
                textView.setText(minutesString + ":" + secondsString);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void onClick(View view) {
        if (checkTimer) {
            timer = new CountDownTimer(secondLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textView.setText("00:" + String.valueOf(millisUntilFinished / 1000));
                secondLeft = millisUntilFinished;
            }

            @Override
            public void onFinish() {
            }
        };
            startTimer();
        } else {
            stopTimer();
        }
    }
    public void startTimer(){
        timer.start();
        button.setText("PAUSE");
        checkTimer = false;
    }
    public void stopTimer(){
        button.setText("START");
        checkTimer = true;
        timer.cancel();
    }
}