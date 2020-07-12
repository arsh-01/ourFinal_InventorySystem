package com.example.ourinventorysystem;

import android.content.Context;

import android.view.ContextMenu;
import android.view.LayoutInflater;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.BreakIterator;
import java.util.List;

public class studentImgAdapter extends RecyclerView.Adapter<studentImgAdapter.ImageViewHolder>{

    private Context pcontext;
    private List<upload> puploads;
    private studentImgAdapter.OnItemClickListener mlistener;

    public studentImgAdapter(Context context, List<upload> uploads){
        pcontext=context;
        puploads=uploads;
    }
    @NonNull
    @Override
    public studentImgAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(pcontext).inflate(R.layout.image_item,parent,false);
        return new studentImgAdapter.ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull studentImgAdapter.ImageViewHolder holder, int position) {

        upload uploadCurrent = puploads.get(position);
        holder.textViewName.setText(uploadCurrent.getmName());
        holder.txtViewDescription.setText(uploadCurrent.getmDescription());
        holder.textViewQQty.setText(uploadCurrent.getmQty());
        Picasso.with(pcontext).load(uploadCurrent.getmImageUrl()).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return puploads.size();
    }




    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener{

        public TextView textViewName;
        public ImageView imageView;
        public TextView textViewQQty;
        public TextView txtViewDescription;

        public ImageViewHolder(View itemView){
            super(itemView);
            textViewName=itemView.findViewById(R.id.txtViewName);
            textViewQQty=itemView.findViewById(R.id.txtViewQty);
            txtViewDescription=itemView.findViewById(R.id.txtViewDescription);
            imageView = itemView.findViewById(R.id.imageUpload);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

        }
        @Override
        public void onClick(View v) {
            if(mlistener != null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    // mlistener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem nxt = menu.add(Menu.NONE,1,1, "View");

            nxt.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if(mlistener != null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    switch (item.getItemId()){
                        case 1:
                            mlistener.NextPage(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }
    public interface OnItemClickListener{

        void NextPage(int Position);
    }
    public void setOnItemClickListener(studentImgAdapter.OnItemClickListener listener){
        mlistener = listener;
    }
    }
