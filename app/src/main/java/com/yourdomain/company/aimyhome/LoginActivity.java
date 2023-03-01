package com.yourdomain.company.aimyhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button login;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email1);
        password = findViewById(R.id.password1);
        login = findViewById(R.id.login);

        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(view->{
            loginUser();
        });
    }
    private void loginUser(){
        String mail = email.getText().toString();
        String pass = password.getText().toString();

        if (TextUtils.isEmpty(mail)){
            email.setError("Email can not be empty");
            email.requestFocus();
        }else if (TextUtils.isEmpty(pass)){
            password.setError("Password can not be empty");
            password.requestFocus();
        }else {
            mAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(LoginActivity.this,"User logged in successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    }else {
                        Toast.makeText(LoginActivity.this, "Login Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}