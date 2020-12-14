package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {
    private TextView r;
    private EditText email,pass;
    private Button login;

    private FirebaseAuth mAuth ;
    private ProgressBar progress1 = findViewById(R.id.progress2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth=FirebaseAuth.getInstance();
        email=findViewById(R.id.email);
        pass=findViewById(R.id.pass);

        r = findViewById(R.id.r);
        //r.setOnClickListener((View.OnClickListener) this);
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,Register.class);
                startActivity(intent);
                finish();
            }
        });

        login=findViewById(R.id.login);
        //login.setOnClickListener((View.OnClickListener) this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
                finish();
            }
        });
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.r:
//                startActivity(new Intent(this,Register.class));
//                break;
//            case R.id.login:
//                 userLogin();
//                 break;
//        }
//    }

    private void userLogin() {
        String email11= email.getText().toString().trim();
        String pass11= pass.getText().toString().trim();

        if (email11.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher((CharSequence) email11).matches()){
            email.setError("Please provide a valid Email");
            email.requestFocus();
            return;
        }
        if (pass11.isEmpty()){
            pass.setError("Password is required");
            pass.requestFocus();
            return;
        }
        if (pass11.length()<6){
            pass.setError("Password Length Should more that 6 charecter");
            pass.requestFocus();
            return;
        }
        progress1.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email11,pass11).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this,Userpage.class));
                }
                else{
                    Toast.makeText(MainActivity.this,"Failed to login! Please check your email & password.",Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}