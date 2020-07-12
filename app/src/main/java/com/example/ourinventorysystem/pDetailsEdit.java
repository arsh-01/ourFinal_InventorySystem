package com.example.ourinventorysystem;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class pDetailsEdit extends AppCompatActivity {
    EditText txtTitle,txtDes,txtQty;

    Button btn;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    private static final String TAG = "MainActivity";
    private TextView dob;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_details_edit);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Member").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        txtDes = findViewById(R.id.rDes);
        txtTitle = findViewById(R.id.rTitle);
        txtQty = findViewById(R.id.rQty);
        dob= findViewById(R.id.rdb);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                txtTitle.setHint(dataSnapshot.child("firstName").getValue().toString().trim());
                txtDes.setHint(dataSnapshot.child("lastName").getValue().toString().trim());
                txtQty.setHint(dataSnapshot.child("phno").getValue().toString().trim());
                dob.setHint(dataSnapshot.child("db").getValue().toString().trim());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int date = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        pDetailsEdit.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,onDateSetListener,year,month,date
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

    }
    public void onclicck(View v){
     DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Member");


        txtDes = findViewById(R.id.rDes);
        txtTitle = findViewById(R.id.rTitle);
        txtQty = findViewById(R.id.rQty);



        String titl;
        if(txtTitle.length()==0) {
            titl = txtTitle.getHint().toString();
        }
        else {
            titl = txtTitle.getText().toString();
        }
        String des;
        if(txtDes.length()==0) {
            des = txtDes.getHint().toString();
        }
        else {
            des = txtDes.getText().toString();
        }
        String qty;
        if(txtQty.length()==0) {
            qty = txtQty.getHint().toString();
        }
        else {
            qty = txtQty.getText().toString();
        }
        String dqty;
        if(dob.length()==0) {
            dqty = dob.getHint().toString();
        }
        else {
            dqty = dob.getText().toString();
        }
        databaseRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("firstName").setValue(titl);
        databaseRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("lastName").setValue(des);
        databaseRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("db").setValue(dqty);
        databaseRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("phno").setValue(qty);

        Toast.makeText(this,"done",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,personalDetails.class));
    }
}