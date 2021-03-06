package com.example.ourinventorysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class imagesActivity extends AppCompatActivity implements imageAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private imageAdapter Adapter;

    private ProgressBar LoadingBar;
    private FirebaseStorage storage;
    private DatabaseReference databaseReference;
    private ValueEventListener mDBListener;
    private List<upload> puploads;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        recyclerView=findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        LoadingBar =findViewById(R.id.loadBar);
        LoadingBar.setVisibility(View.VISIBLE);


        puploads = new ArrayList<>();
        Adapter = new imageAdapter(imagesActivity.this,puploads);
        recyclerView.setAdapter(Adapter);

        Adapter.setOnItemClickListener(imagesActivity.this);
        storage = FirebaseStorage.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");

        mDBListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                puploads.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    upload picUpload = postSnapshot.getValue(upload.class);
                    picUpload.setKey(postSnapshot.getKey());
                    puploads.add(picUpload);
                }

                Adapter.notifyDataSetChanged();


                LoadingBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(imagesActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                LoadingBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void NextPage(int Position) {

        upload selectedItem = puploads.get(Position);
        final String selectedKey = selectedItem.getKey();
        Toast.makeText(this,"View Single Product",Toast.LENGTH_SHORT).show();

        Intent ii= new Intent(this,showSingleProduct.class);
        ii.putExtra("key",selectedKey);
        startActivity(ii);
    }


    public void NextPageClick(int Position) {

        upload selectedItem = puploads.get(Position);
        final String selectedKey = selectedItem.getKey();
        Toast.makeText(this,"Edit Your Product",Toast.LENGTH_SHORT).show();

        Intent i= new Intent(this,editable.class);
        i.putExtra("key",selectedKey);
        startActivity(i);
    }

    @Override

    public void onDeleteClick(int Position) {
        upload selectedItem = puploads.get(Position);
        final String selectedKey = selectedItem.getKey();

        StorageReference imgRef = storage.getReferenceFromUrl(selectedItem.getmImageUrl());
        imgRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                databaseReference.child(selectedKey).removeValue();

                Toast.makeText(imagesActivity.this,"Item Deleted",Toast.LENGTH_SHORT).show();

            }
        });
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        databaseReference.removeEventListener(mDBListener);
    }
}