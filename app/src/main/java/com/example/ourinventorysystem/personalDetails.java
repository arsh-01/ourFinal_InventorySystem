package com.example.ourinventorysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class personalDetails extends AppCompatActivity {
    TextView txtTitle,txtDes,txtQty,txtdb;

    Button btn;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Member").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        txtDes = findViewById(R.id.rDes);
        txtTitle = findViewById(R.id.rTitle);
        txtQty = findViewById(R.id.rQty);
      txtdb= findViewById(R.id.rdb);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                            txtTitle.setText(dataSnapshot.child("firstName").getValue().toString().trim());
                            txtDes.setText(dataSnapshot.child("lastName").getValue().toString().trim());
                            txtQty.setText(dataSnapshot.child("phno").getValue().toString().trim());
                            txtdb.setText(dataSnapshot.child("db").getValue().toString().trim());
                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }
    public void ccc(View v){
        Intent i = new Intent(this,pDetailsEdit.class);
        startActivity(i);
    }
}