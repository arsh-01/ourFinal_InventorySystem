package com.example.ourinventorysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class showSingleProduct extends AppCompatActivity implements ValueEventListener {
    TextView txtTitle,txtDes,txtQty;

    Button btn;
    DatabaseReference databaseReference;
    ImageView img;
    FirebaseDatabase firebaseDatabase;
    Storage storage;
    StorageReference storageReference;
    DatabaseReference QQty,title,Des,ur;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_single_product);
        Intent get =getIntent();
        String Selected = get.getStringExtra("key");
        firebaseDatabase = FirebaseDatabase.getInstance();
        QQty = firebaseDatabase.getReference("uploads").child(Selected).child("mQty");
        title = firebaseDatabase.getReference("uploads").child(Selected).child("mName");
        Des = firebaseDatabase.getReference("uploads").child(Selected).child("mDescription");
        ur = firebaseDatabase.getReference("uploads").child(Selected).child("mImageUrl");
        txtDes = findViewById(R.id.rDes);
        txtTitle = findViewById(R.id.rTitle);
        txtQty = findViewById(R.id.rQty);
        img= findViewById(R.id.imggg);
    }

    public void onClick(View v) {
        Intent get =getIntent();
        startActivity(new Intent(this,editable.class));
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        //String showingData=dataSnapshot.getValue().toString();
        {
            String key = dataSnapshot.getKey();
            if (key.equals("mQty")) {
                txtQty.setText(dataSnapshot.getValue().toString());
            }
            if(key.equals("mName")) {
                txtTitle.setText(dataSnapshot.getValue().toString());
            }
            if (key.equals("mDescription")) {
                txtDes.setText(dataSnapshot.getValue().toString());
            }
            if (key.equals("mImageUrl")) {
                Picasso.with(this).load(dataSnapshot.getValue().toString()).into(img);
            }
        }
    }


    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
    protected void onStart(){
        super.onStart();
        QQty.addValueEventListener(this);
        title.addValueEventListener(this);
        Des.addValueEventListener(this);
        ur.addValueEventListener(this);
    }
}