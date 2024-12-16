package com.example.thaiduyroan_finalexam;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private TextView txtViewUsername, txtViewColor, txtViewTimer, txtViewScore;
    private Button btnRed, btnGreen, btnBlue, btnYellow, btnSubmit;
    private DatabaseReference databaseReference;
    private String username;

    private String[] colors = {"RED", "GREEN", "BLUE", "YELLOW"};
    private int[] colorValues = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
    private Random random = new Random();
    private int currentColorIndex;
    private int score = 0;
    private int round = 0;
    private static final int MAX_ROUNDS = 5;

    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Initialize Views
        txtViewUsername = findViewById(R.id.txtViewUsername);
        txtViewColor = findViewById(R.id.txtViewColor);
        txtViewTimer = findViewById(R.id.txtViewTimer);
        txtViewScore = findViewById(R.id.txtViewScore);
        btnRed = findViewById(R.id.btnRed);
        btnGreen = findViewById(R.id.btnGreen);
        btnBlue = findViewById(R.id.btnBlue);
        btnYellow = findViewById(R.id.btnYellow);
        btnSubmit = findViewById(R.id.btnReturn);

        // Get username from Intent
        username = getIntent().getStringExtra("USERNAME");
        txtViewUsername.setText(username);

        // Firebase reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(username);

        startNewRound();

        // Button click listeners for color buttons
        btnRed.setOnClickListener(v -> checkAnswer("RED"));
        btnGreen.setOnClickListener(v -> checkAnswer("GREEN"));
        btnBlue.setOnClickListener(v -> checkAnswer("BLUE"));
        btnYellow.setOnClickListener(v -> checkAnswer("YELLOW"));

        // Submit button now returns to MainActivity
        btnSubmit.setText("RETURN TO MAIN");
        btnSubmit.setOnClickListener(v -> {
            saveScoreToDatabase();
            backToMainActivity();
        });
    }

    private void startNewRound() {
        if (round < MAX_ROUNDS) {
            round++;
            currentColorIndex = random.nextInt(colors.length);

            // Randomly set color text and text color
            txtViewColor.setText(colors[random.nextInt(colors.length)]);
            txtViewColor.setTextColor(colorValues[currentColorIndex]);

            txtViewTimer.setText("Time left: 5s");
            startTimer();
        } else {
            Toast.makeText(this, "Game Finished!", Toast.LENGTH_SHORT).show();
            saveScoreToDatabase();
            backToMainActivity();
        }
    }

    private void startTimer() {
        if (countDownTimer != null) countDownTimer.cancel();

        countDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtViewTimer.setText("Time left: " + millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                txtViewTimer.setText("Time's up!");
                startNewRound();
            }
        }.start();
    }

    private void checkAnswer(String chosenColor) {
        if (colors[currentColorIndex].equalsIgnoreCase(chosenColor)) {
            score++;
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
        }
        txtViewScore.setText("Score: " + score);
        startNewRound();
    }

    private void saveScoreToDatabase() {
        databaseReference.child("score").setValue(score).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(GameActivity.this, "Score saved successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(GameActivity.this, "Failed to save score.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void backToMainActivity() {
        // Navigate back to MainActivity
        Intent intent = new Intent(GameActivity.this, MainActivity.class);
        intent.putExtra("USERNAME", username);
        intent.putExtra("SCORE", score);
        startActivity(intent);
        finish();
    }
}
