package com.example.thaiduyroan_finalexam;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private TextView txtViewUsername;
    private Button btnGame, btnScore;
    private DatabaseReference databaseReference;
    private String username = "default_user";

    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtViewUsername = findViewById(R.id.txtViewUsername);
        btnGame = findViewById(R.id.btnGame);
        btnScore = findViewById(R.id.btnScore);

        // Retrieve username from Bundle
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            username = bundle.getString("USERNAME", "default_user");
        }

        // Fetch username from Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(username);
        databaseReference.child("username").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String fetchedUsername = snapshot.getValue(String.class);
                    txtViewUsername.setText(fetchedUsername);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                txtViewUsername.setText("Failed to load username");
            }
        });

        // Game Button click
        btnGame.setOnClickListener(v -> {
            Intent gameIntent = new Intent(MainActivity.this, GameActivity.class);
            gameIntent.putExtra("USERNAME", username);
            startActivity(gameIntent);
        });

        // Score Button click
        btnScore.setOnClickListener(v -> {
            Intent scoreIntent = new Intent(MainActivity.this, ScoreActivity.class);
            scoreIntent.putExtra("USERNAME", username);
            startActivity(scoreIntent);
        });
    }

}