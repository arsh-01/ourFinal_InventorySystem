package com.example.ourinventorysystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class addProduct extends AppCompatActivity {
private static final int pickImgRqst=1;
private Button btnChooseImg;
private Button btnUpload;
private TextView TxtViewShowUploads;
private EditText EditTxtname;
    private EditText EditTxtQty;
    private EditText EditDescription;
private ImageView imageView;
private ProgressBar progressBar;
private Uri Imguri;

private StorageReference storageReference;
 DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        btnChooseImg = findViewById(R.id.chooseBtn);
        btnUpload = findViewById(R.id.btnUpload);
        TxtViewShowUploads =findViewById(R.id.txtView);
        EditTxtname = findViewById(R.id.filename);
        EditTxtQty = findViewById(R.id.ViewQty);
        EditDescription = findViewById(R.id.fileDescrip);
        imageView=findViewById(R.id.img);
        progressBar = findViewById(R.id.bar);


        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        databaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        btnChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });

        TxtViewShowUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(addProduct.this,imagesActivity.class));
            }
        });

    }
    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,pickImgRqst);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == pickImgRqst && resultCode == RESULT_OK && data != null && data.getData() != null){
            Imguri =data.getData();
            Picasso.with(this).load(Imguri).into(imageView);
        }
    }
    //method used to get file extension
    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    private void uploadFile(){
        if (Imguri != null){
            StorageReference fileRef = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(Imguri));

            fileRef.putFile(Imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(0);
                        }
                    },500);
                    Toast.makeText(addProduct.this,"Upload successful",Toast.LENGTH_LONG).show();
                        upload Up = new upload(EditTxtname.getText().toString().trim(), EditDescription.getText().toString().trim(), EditTxtQty.getText().toString().trim(), taskSnapshot.getDownloadUrl().toString());
                        String uplodId = databaseRef.push().getKey();

                        databaseRef.child(uplodId).setValue(Up);

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        Toast.makeText(addProduct.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            double progress = (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressBar.setProgress((int) progress);
                        }
                    });
        }
        else {
            Toast.makeText(this,"No file Selected",Toast.LENGTH_SHORT).show();
        }
    }

}