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
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText email, password, repassword;
    Button signup, signin;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);
        signup = findViewById(R.id.signup);
        signin = findViewById(R.id.signin);

        mAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(view -> {
            createUser();
        });
        signin.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        });
    }

    private void createUser(){
        String mail = email.getText().toString();
        String pass = password.getText().toString();
        String repass = repassword.getText().toString();

        if (TextUtils.isEmpty(mail)){
            email.setError("Email can not be empty");
            email.requestFocus();
        }else if (TextUtils.isEmpty(pass)){
            password.setError("Password can not be empty");
            password.requestFocus();
        }else if (TextUtils.isEmpty(repass)){
            repassword.setError("Re-Password can not be empty");
            repassword.requestFocus();
        }else if (pass.equals(repass)){
            mAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(MainActivity.this,"User registered successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    }else{
                        Toast.makeText(MainActivity.this,"Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            repassword.setError("Re-Password is not the same as Password");
            repassword.requestFocus();
        }
    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser user = mAuth.getCurrentUser();
//        if (user != null){
//            startActivity(new Intent(MainActivity.this, HomeActivity.class));
//        }
//    }
}