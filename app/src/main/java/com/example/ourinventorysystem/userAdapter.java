package com.example.ourinventorysystem;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class userAdapter extends RecyclerView.Adapter<userAdapter.ViewHolder> {
    private Context mcontext;
    private List<Member> mUsers;

    public userAdapter(Context mcontext, List<Member> mUsers){
        this.mcontext = mcontext;
        this.mUsers = mUsers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.user_item,parent,false);

        return new userAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Member user = mUsers.get(position);
        holder.username.setText(user.getFirstName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, MessageActivity.class);
                intent.putExtra("userid",user.getuId());
                intent.putExtra("displayName",user.getFirstName());
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username;


        public ViewHolder(View itemView){
            super(itemView);

            username =itemView.findViewById(R.id.username);
        }
    }
}
