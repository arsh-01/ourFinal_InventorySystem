package com.example.ourinventorysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText emailId, password,checkPass, fname,lname,PhnNo;
    Button btnsignUp;
    TextView tvSignIn;
    FirebaseAuth mFirebaseAuth;
    DatabaseReference ref;
    Member member;

    private static final String TAG = "MainActivity";
    private TextView dob;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       mFirebaseAuth =FirebaseAuth.getInstance();
        lname=findViewById(R.id.lname);
        fname=findViewById(R.id.fname);
        PhnNo=findViewById(R.id.phn);
        dob=findViewById(R.id.d);

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int date = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MainActivity.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,onDateSetListener,year,month,date
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                Log.d(TAG,"onDateSet : mm/dd/yyyy: " + month +"/"+date+"/"+year);

                String day = month+ "/"+date+"/"+year;
                dob.setText(day);
            }
        };

        emailId = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        checkPass = findViewById(R.id.editTextPassword);


        tvSignIn = findViewById(R.id.txtView);
        btnsignUp = findViewById(R.id.button);
        btnsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    final String email=emailId.getText().toString();
                    String pwd = password.getText().toString();
                    String pwd2 = checkPass.getText().toString();
                final String firstName=fname.getText().toString();
                final String lastName = lname.getText().toString();
                final String phno=PhnNo.getText().toString();
                if(phno.length()<10||phno.length()>10){
                    PhnNo.setError("please correct your contact");
                }
                final String db = dob.getText().toString();
                member =new Member();

                ref = FirebaseDatabase.getInstance().getReference().child("Member");
                  if(email.isEmpty()){
                        emailId.setError("plz check email id");
                        emailId.requestFocus();
                    }
                else if(pwd.isEmpty()){
                    password.setError("plz enter your password");
                    password.requestFocus();
                }
                else if(email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(MainActivity.this,"Fields are Empty!",Toast.LENGTH_SHORT).show();
                }
                else if(!(pwd.equals(pwd2))){
                    checkPass.setError("plz enter same pass");
                  }

                else if(!(email.isEmpty() && pwd.isEmpty()&&pwd.equals(pwd2))){
                    mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MainActivity.this,"signUp Unsuccessful, please Try Again",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                member.setFirstName(firstName.toString().trim());
                                member.setLastName(lastName.toString().trim());
                                member.setPhno(phno.toString().trim());
                                member.setDb(db.toString().trim());
                                member.setEmail(email.toString().trim());
                                member.setuId(user.toString().trim());
                                ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(member);
                                startActivity(new Intent(MainActivity.this,homeActivity.class));
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(MainActivity.this,"Error occurred!",Toast.LENGTH_SHORT);
                }
            }
        });
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iu = new Intent(MainActivity.this,loginActivity.class);
                startActivity(iu);
            }
        });
    }
}