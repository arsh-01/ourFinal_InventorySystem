package com.example.ourinventorysystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    ImageView profle_image;
    TextView usernameText;
    FirebaseUser fuser;
    DatabaseReference reference;
    Intent intent;
    ImageButton button_send;
    EditText typedMsg;

    MessageAdapter messageAdapter;
    List<Chat> mchat;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.recycler_View_Msg);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
       // profle_image = findViewById(R.id.);
        usernameText = findViewById(R.id.usernameForChat);
        button_send = findViewById(R.id.btn_send);
        typedMsg = findViewById(R.id.txt_type);
        intent = getIntent();
        final String userId = intent.getStringExtra("userid");
        final String toolbarName = intent.getStringExtra("displayName");

        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = typedMsg.getText().toString();
                if (!msg.equals("")) {
                    sendMessage(fuser.getUid(),userId,msg);
                }
                else {
                    Toast.makeText(MessageActivity.this,"You can't send empty Message", Toast.LENGTH_SHORT).show();
                }
                typedMsg.setText("");
            }
        });

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Member").child(userId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    Member user = dataSnapshot.getValue(Member.class);
                     usernameText.setText(toolbarName);

                     readMessages(fuser.getUid(),userId);
                }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void sendMessage(String sender, String receiver, String message){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("message",message);

        reference.child("Chats").push().setValue(hashMap);
    }
    private void readMessages(final String myid, final String useridformsg){
        mchat = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mchat.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(useridformsg) && chat.getSender().equals(myid) ||
                         chat.getReceiver().equals(myid)   && chat.getSender().equals(useridformsg)) {
                        mchat.add(chat);
                    }

                    messageAdapter = new MessageAdapter(MessageActivity.this, mchat);
                    recyclerView.setAdapter(messageAdapter);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}