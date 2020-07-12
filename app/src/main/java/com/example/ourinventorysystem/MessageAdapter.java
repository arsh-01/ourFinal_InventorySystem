package com.example.ourinventorysystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private static final int MSG_TYPE_LEFT = 0;
    private static final int MSG_TYPE_RIGHT = 1;
    private Context mcontext;
    private List<Chat> mChat;

    FirebaseUser fuser;

    public MessageAdapter(Context mcontext, List<Chat> mChat){
        this.mcontext = mcontext;
        this.mChat = mChat;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mcontext).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(mcontext).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chat chat = mChat.get(position);
        holder.show_messages.setText(chat.getMessage());

    }

    @Override
    public int getItemCount() {

        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView show_messages;


        public ViewHolder(View itemView){
            super(itemView);

            show_messages =itemView.findViewById(R.id.show_message);
        }
    }

    @Override
    public int getItemViewType(int position) {
      fuser = FirebaseAuth.getInstance().getCurrentUser();
      if(mChat.get(position).getSender().equals(fuser.getUid())){
          return MSG_TYPE_RIGHT;
      }
      else {
          return MSG_TYPE_LEFT;
      }
    }
}

