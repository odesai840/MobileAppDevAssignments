// Assignment02
// MainActivity.java
// Ohm Desai and Sullivan Crouse

package com.example.assignment02;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBarRed;
    SeekBar seekBarGreen;
    SeekBar seekBarBlue;
    TextView textViewRedBarValue;
    TextView textViewGreenBarValue;
    TextView textViewBlueBarValue;
    TextView colorHEX;
    TextView colorRGB;
    ImageView colorViewBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBarRed = findViewById(R.id.redSeekBar);
        seekBarGreen = findViewById(R.id.greenSeekBar);
        seekBarBlue = findViewById(R.id.blueSeekBar);
        textViewRedBarValue = findViewById(R.id.redBarValue);
        textViewGreenBarValue = findViewById(R.id.greenBarValue);
        textViewBlueBarValue = findViewById(R.id.blueBarValue);
        colorHEX = findViewById(R.id.colorHEXValue);
        colorRGB = findViewById(R.id.colorRGBValue);
        colorViewBox = findViewById(R.id.colorViewBox);

        setColorRGBAndHEX();

        seekBarRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textViewRedBarValue.setText(String.valueOf(i));
                setColorRGBAndHEX();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarGreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textViewGreenBarValue.setText(String.valueOf(i));
                setColorRGBAndHEX();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        seekBarBlue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textViewBlueBarValue.setText(String.valueOf(i));
                setColorRGBAndHEX();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        findViewById(R.id.whiteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seekBarRed.setProgress(255);
                seekBarGreen.setProgress(255);
                seekBarBlue.setProgress(255);
                setColorRGBAndHEX();
            }
        });

        findViewById(R.id.blackButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seekBarRed.setProgress(0);
                seekBarGreen.setProgress(0);
                seekBarBlue.setProgress(0);
                setColorRGBAndHEX();
            }
        });

        findViewById(R.id.blueButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seekBarRed.setProgress(0);
                seekBarGreen.setProgress(0);
                seekBarBlue.setProgress(255);
                setColorRGBAndHEX();
            }
        });

        findViewById(R.id.resetButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seekBarRed.setProgress(64);
                seekBarGreen.setProgress(128);
                seekBarBlue.setProgress(0);
                setColorRGBAndHEX();
            }
        });
    }

    private void setColorRGBAndHEX(){
        int r = seekBarRed.getProgress();
        int g = seekBarGreen.getProgress();
        int b = seekBarBlue.getProgress();

        int color = Color.rgb(r, g, b);

        colorRGB.setText(String.format("(%d, %d, %d)", r, g, b));
        colorHEX.setText(String.format("#%06X", (0xFFFFFF & color)));
        colorViewBox.setBackgroundColor(Color.rgb(r, g, b));
    }
}