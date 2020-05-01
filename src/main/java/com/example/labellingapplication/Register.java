package com.example.labellingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText nUserName,nuserEmail,nPassword;
    Button nRegister;
    TextView ngoLogin;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);


        nUserName  = findViewById (R.id.UserName);
        nuserEmail = findViewById (R.id.userEmail);
        nPassword  = findViewById (R.id.Password);
        nRegister  = findViewById (R.id.Register);
        ngoLogin   = findViewById (R.id.goLogin);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }


        nRegister.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick(View v) {
                String email = nuserEmail.getText().toString().trim();
                String password = nPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    nuserEmail.setError("Email not entered.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    nPassword.setError("Password not entered.");
                    return;
                }

                if(password.length() < 9){
                    nPassword.setError("Password Must 9 Characters or more");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "Account added.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                        }else {
                            Toast.makeText(Register.this, "Something went wrong ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        ngoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Log_in.class));

            }
        });


    }
}




