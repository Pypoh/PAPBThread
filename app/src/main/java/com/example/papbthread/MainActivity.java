package com.example.papbthread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText minInput, maxInput, intervalInput;
    private Button goBtn, stopBtn;
    private TextView textGenerate;
    private boolean state = false; // false = not generating

    private Handler generatorHandler;
    private Runnable generatorRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViews();



    }

    private void setupViews() {
        minInput = findViewById(R.id.edit_text_min);
        maxInput = findViewById(R.id.edit_text_max);
        intervalInput = findViewById(R.id.edit_text_interval);
        textGenerate = findViewById(R.id.text_generator);
        goBtn = findViewById(R.id.button_go);
        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateRandomNumber();
            }
        });
        stopBtn = findViewById(R.id.button_stop);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatorHandler.removeCallbacks(generatorRunnable);
                goBtn.setEnabled(true);
            }
        });
    }

    private void generateRandomNumber() {
        goBtn.setEnabled(false);
//        int interval;
//        try {
//            interval = Integer.parseInt(intervalInput.getText().toString());
//        } catch (Exception e) {
//            Toast.makeText(this, "Error Input", Toast.LENGTH_SHORT).show();
//            goBtn.setEnabled(true);
//            return;
//        }
        generatorHandler = new Handler();
        generatorRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    double interval = Double.parseDouble(intervalInput.getText().toString());
                    int max = Integer.parseInt(maxInput.getText().toString());
                    int min = Integer.parseInt(minInput.getText().toString());
                    int range = max - min + 1;
                    int value = (int) ((Math.random() * range) + min);
                    textGenerate.setText(value + "");
                    generatorHandler.postDelayed(this, (long) (interval * 1000));
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error Input", Toast.LENGTH_SHORT).show();
                    goBtn.setEnabled(true);
                }
            }
        };
        generatorHandler.post(generatorRunnable);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        generatorHandler.removeCallbacks(generatorRunnable);
    }
}
