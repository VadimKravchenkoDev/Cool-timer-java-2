package com.kravchenkovadim.cooltimerkotlin;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private CountDownTimer countDownTimer;
    TextView textView;
    private SeekBar seekBar;
    private boolean isTimerOn;
    private Button button;


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

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(600);
        seekBar.setProgress(30);
        isTimerOn = false;
        button = findViewById(R.id.button);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                long progressInMillis = progress * 1000;
                updateTimer(progressInMillis);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void start(View view) {
        if (!isTimerOn) {
            button.setText("Stop");
            seekBar.setEnabled(false);
            isTimerOn = true;
            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000, 1000) {
                @Override
                public void onTick(long l) {
                    updateTimer(l);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bell_sound);
                    mediaPlayer.start();
                    resetTimer();
                }
            };
            countDownTimer.start();
        } else {
            resetTimer();
        }
    }

    private void updateTimer(long l) {
        int minutes = (int) l / 1000 / 60;
        int seconds = (int) l / 1000 - (minutes * 60);

        String minutesString = "";
        String secondsString = "";

        if (minutes < 10) {
            minutesString = "0" + minutes;
        } else {
            minutesString = String.valueOf(minutes);

        }
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = String.valueOf(seconds);
        }
        textView.setText(minutesString + ":" + secondsString);
    }

    private void resetTimer() {
        countDownTimer.cancel();
        textView.setText("00:30");
        button.setText("Start");
        seekBar.setEnabled(true);
        seekBar.setProgress(30);
        isTimerOn = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.timer_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent openSettings = new Intent(this, SettingsActivity.class);
            startActivity(openSettings);
            return true;
        } else {
            if (id == R.id.action_about) {
                Intent openAbout = new Intent(this, AboutActivity.class);
                startActivity(openAbout);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}