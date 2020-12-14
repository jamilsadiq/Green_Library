package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private TextView home;
    private EditText name,email,sid,pass;
    private Button register;
    ProgressBar progress2;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        home = findViewById(R.id.home);
        home.setOnClickListener(this);



        register = findViewById(R.id.register);
        register.setOnClickListener(this);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        sid = findViewById(R.id.sid);
        pass = findViewById(R.id.pass);

        progress2 = findViewById(R.id.progress2);

    }

        @Override
        public void onClick(View v) {
        switch (v.getId()){
            case R.id.home:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.register:
                registeruser();
                break;
        }
        }

    private void registeruser() {
        String nam = name.getText().toString().trim();
        String mail = email.getText().toString().trim();
        String stdnid = sid.getText().toString().trim();
        String passw = pass.getText().toString().trim();

        if(nam.isEmpty()){
            name.setError("Full Name is Required");
            name.requestFocus();
            return;
        }

        if(mail.isEmpty()){
            email.setError("Email is Required");
            email.requestFocus();
            return;
        }
       if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
           email.setError("Please provide a valid Email");
           email.requestFocus();
           return;
       }

        if(stdnid.isEmpty()){
            sid.setError("Student ID is Required");
            sid.requestFocus();
            return;
        }

        if(passw.isEmpty()){
            pass.setError("Password is Required");
            pass.requestFocus();
            return;
        }
        if (passw.length()<6){
            pass.setError("Password Length Should more that 6 charecter");
            pass.requestFocus();
            return;
        }

        progress2.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(mail, passw)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        user user1 = new user(nam,mail,stdnid);

                        FirebaseDatabase.getInstance().getReference("user")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user1).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(Register.this,"Registration Done Successfully", Toast.LENGTH_LONG).show();
                                    progress2.setVisibility(View.GONE);
                                }
                                else
                                    Toast.makeText(Register.this, "Failed to Register! Try Again", Toast.LENGTH_LONG).show();
                                    progress2.setVisibility(View.GONE);
                            }
                        });

                        }
                    else
                        Toast.makeText(Register.this, "Failed to Register! Try Again", Toast.LENGTH_LONG).show();
                        progress2.setVisibility(View.GONE);
                }
            });



    }

}