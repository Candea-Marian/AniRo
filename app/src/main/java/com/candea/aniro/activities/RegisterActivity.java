package com.candea.aniro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.candea.aniro.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    EditText passwordVerifier;
    CardView signupButton;
    TextView login;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        // auth with email and password
        email = findViewById(R.id.email_register);
        password = findViewById(R.id.password_register);
        passwordVerifier = findViewById(R.id.password_confirm_register);
        signupButton = findViewById(R.id.signup_button);
        login = findViewById(R.id.open_login_act);
        firebaseAuth = FirebaseAuth.getInstance();

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailId = email.getText().toString();
                String passwrd = password.getText().toString();
                String passwrdVrf = passwordVerifier.getText().toString();

                if (emailId.isEmpty() || passwrd.isEmpty() || passwrdVrf.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please complete all fields!", Toast.LENGTH_SHORT).show();
                } else if (!passwrd.equals(passwrdVrf)) {
                    Toast.makeText(RegisterActivity.this, "The passwords don't match!", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.createUserWithEmailAndPassword(emailId, passwrd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Connection failed. Please try again!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(RegisterActivity.this, "the account has been successfully registered", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            }
                        }
                    });
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
}
