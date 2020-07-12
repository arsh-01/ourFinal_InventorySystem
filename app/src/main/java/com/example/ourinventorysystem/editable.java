package com.example.ourinventorysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.net.URL;

public class editable extends AppCompatActivity implements ValueEventListener{
EditText txtTitle,txtDes,txtQty;

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
        setContentView(R.layout.activity_editable);
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
            String Selected = get.getStringExtra("key");
            databaseReference = FirebaseDatabase.getInstance().getReference("uploads");
            txtDes = findViewById(R.id.rDes);
            txtTitle = findViewById(R.id.rTitle);
            txtQty = findViewById(R.id.rQty);
            String title;
            if(txtTitle.length()==0) {
                title = txtTitle.getHint().toString();
            }
            else {
                title = txtTitle.getText().toString();
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
            databaseReference.child(Selected).child("mName").setValue(title);
            databaseReference.child(Selected).child("mQty").setValue(qty);
            databaseReference.child(Selected).child("mDescription").setValue(des);

            Toast.makeText(this,"done",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,imagesActivity.class));
        }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        String showingData=dataSnapshot.getValue().toString();
        {
            String key = dataSnapshot.getKey();
            if (key.equals("mQty")) {
                txtQty.setHint(showingData);
            }
            if(key.equals("mName")) {
                txtTitle.setHint(showingData);
            }
                if (key.equals("mDescription")) {
                    txtDes.setHint(showingData);
                }
            if (key.equals("mImageUrl")) {
                Picasso.with(this).load(showingData).into(img);
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