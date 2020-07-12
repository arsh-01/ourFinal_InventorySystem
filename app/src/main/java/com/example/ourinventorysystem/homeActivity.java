package com.example.ourinventorysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.UUID;

public class homeActivity extends AppCompatActivity {
    Button btnLogout;
    Button admbtn;
    Button buttonforUserActivity;
    Button requestButton;
    Button inventoryButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        admbtn=findViewById(R.id.admbtn);
        btnLogout = findViewById(R.id.button2);
        requestButton = findViewById(R.id.admbtn);
        buttonforUserActivity = findViewById(R.id.msg);
        inventoryButton = findViewById(R.id.viewInventory);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(homeActivity.this, MainActivity.class);
                startActivity(intToMain);
            }
        });
        buttonforUserActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(homeActivity.this,users.class));
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user.getUid().equals("ElodiodkcJdzwSkACRSRasUYj3h2")){
            admbtn.setEnabled(true);
            inventoryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(homeActivity.this,imagesActivity.class);
                    startActivity(intent);
                }
            });
        }
        else {
            admbtn.setText("Request Item");
            requestButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(homeActivity.this, Requestitem.class);
                    startActivity(intent);
                }
            });


        }
    }



    public void onClick(View v) {
        Intent i = new Intent(this, addProduct.class);
        startActivity(i);
    }

    public void onClick1(View v) {
        Intent ii = new Intent(this, studentImgActivity.class);
        startActivity(ii);
    }
    public void onClick2(View v) {
        Intent iii = new Intent(this, forgotPass.class);
        startActivity(iii);
    }

    public void onClick3(View v) {
        Intent iiii = new Intent(this, personalDetails.class);
        startActivity(iiii);
    }


}