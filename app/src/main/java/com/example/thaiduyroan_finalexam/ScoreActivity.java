package com.example.thaiduyroan_finalexam;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class ScoreActivity extends AppCompatActivity {

    private TextView txtName, txtUsername, txtPassword, txtScore;
    private Button btnSignOut, btnDeleteProfile;

    private DatabaseReference databaseReference;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        // Initialize UI elements
        txtName = findViewById(R.id.txtName);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        txtScore = findViewById(R.id.txtScore);
        btnSignOut = findViewById(R.id.btnSignOut);
        btnDeleteProfile = findViewById(R.id.btnDeleteProfile);

        Intent intent = getIntent();
        username = intent.getStringExtra("USERNAME");

        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(username);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Set data to TextViews
                    txtName.setText(snapshot.child("name").getValue(String.class));
                    txtUsername.setText(snapshot.child("username").getValue(String.class));
                    txtPassword.setText(snapshot.child("password").getValue(String.class));
                    txtScore.setText(String.valueOf(snapshot.child("score").getValue(Integer.class)));
                } else {
                    Toast.makeText(ScoreActivity.this, "User data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ScoreActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });

        // Sign Out button
        btnSignOut.setOnClickListener(v -> {
            Intent backToMain = new Intent(ScoreActivity.this, RegisterActivity.class);
            startActivity(backToMain);
            finish();
        });

        // Delete Profile button
        btnDeleteProfile.setOnClickListener(v -> {
            databaseReference.removeValue().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(ScoreActivity.this, "Profile deleted successfully", Toast.LENGTH_SHORT).show();
                    Intent backToMain = new Intent(ScoreActivity.this, RegisterActivity.class);
                    startActivity(backToMain);
                    finish();
                } else {
                    Toast.makeText(ScoreActivity.this, "Failed to delete profile", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}