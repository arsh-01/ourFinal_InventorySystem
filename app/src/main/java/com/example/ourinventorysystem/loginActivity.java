package com.example.ourinventorysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginActivity extends AppCompatActivity {
    EditText emailId, password;
    Button btnsignIn;
    TextView tvSignUp;
    TextView forgotPass;
    FirebaseAuth mFirebaseAuth;
private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mFirebaseAuth=FirebaseAuth.getInstance();
        emailId = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        tvSignUp = findViewById(R.id.txtView);
        forgotPass = findViewById(R.id.pass);
        btnsignIn = findViewById(R.id.button);

        mAuthStateListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser= mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser != null){
                    Toast.makeText(loginActivity.this,"you are logged in",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(loginActivity.this,homeActivity.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(loginActivity.this,"please login",Toast.LENGTH_SHORT);
                }
            }
        };
    btnsignIn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email=emailId.getText().toString();
            String pwd = password.getText().toString();
            if(email.isEmpty()){
                emailId.setError("plz check email id");
                emailId.requestFocus();
            }
            else if(pwd.isEmpty()){
                password.setError("plz enter your password");
                password.requestFocus();
            }
            else if(email.isEmpty() && pwd.isEmpty()){
                Toast.makeText(loginActivity.this,"Fields are Empty!",Toast.LENGTH_SHORT).show();
            }
            else if(!(email.isEmpty() && pwd.isEmpty())){
                mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(loginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(loginActivity.this,"login Error",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Intent intToHome = new Intent(loginActivity.this,homeActivity.class);
                            startActivity(intToHome);
                        }
                    }
                });
            }
            else if(password.length()<6){
                Toast.makeText(loginActivity.this,"password worng",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(loginActivity.this,"Error occurred!",Toast.LENGTH_SHORT);
            }
        }
    });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intSignUp = new Intent(loginActivity.this,MainActivity.class);
                startActivity(intSignUp);
            }
        });


    }

    public void  forgpas(View v){
        startActivity(new Intent(this,forgotPass.class));
    }
    protected void onStart(){
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}