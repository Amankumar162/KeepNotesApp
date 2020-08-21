package com.example.keepnotes.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.keepnotes.MainActivity;
import com.example.keepnotes.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText regUserName,regUserEmail,regUserPassword,regUserConfPassword;
    Button syncAccount;
    TextView loginAccount;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("Create New Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        regUserName = findViewById(R.id.uName);
        regUserEmail = findViewById(R.id.uEmail);
        regUserPassword = findViewById(R.id.password);
        regUserConfPassword = findViewById(R.id.passwordConfirm);

        syncAccount = findViewById(R.id.uSync);
        loginAccount = findViewById(R.id.uLogin);
        progressBar = findViewById(R.id.progressBar4);

        firebaseAuth = FirebaseAuth.getInstance();

        loginAccount.setOnClickListener(new View.OnClickListener() {
          @Override
         public void onClick(View view) {
         startActivity(new Intent(getApplicationContext(),Login.class));

         }
          });


        syncAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uUsername = regUserName.getText().toString();
                String uUserEmail = regUserEmail.getText().toString();
                String uUserPass = regUserPassword.getText().toString();
                String uConfPass = regUserConfPassword.getText().toString();

                if (uUserEmail.isEmpty() || uUsername.isEmpty() || uUserPass.isEmpty() || uConfPass.isEmpty()) {
                    Toast.makeText(Register.this, "Fill All The Fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!uUserPass.equals(uConfPass)) {
                    regUserConfPassword.setError("Password Do not Match");
                }

                progressBar.setVisibility(View.VISIBLE);

                AuthCredential credential = EmailAuthProvider.getCredential(uUserEmail, uUserPass);
                firebaseAuth.getCurrentUser().linkWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(Register.this, "Notes are Synced", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, "Failed to Connect", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
        return super.onOptionsItemSelected(item);
    }
}