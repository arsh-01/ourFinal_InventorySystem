package com.example.ourinventorysystem;

import android.content.Context;
import android.net.Uri;
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

import java.net.URL;
import java.util.List;

public class imageAdapter extends RecyclerView.Adapter<imageAdapter.ImageViewHolder> {

    private Context pcontext;
    private List<upload> puploads;

    private OnItemClickListener mlistener;

    public imageAdapter(Context context, List<upload> uploads){
        pcontext=context;
        puploads=uploads;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(pcontext).inflate(R.layout.image_item,parent,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        upload uploadCurrent = puploads.get(position);
        holder.textViewName.setText(uploadCurrent.getmName());
        holder.textViewDes.setText(uploadCurrent.getmDescription());
        holder.textViewQQty.setText(uploadCurrent.getmQty());
        Picasso.with(pcontext).load(uploadCurrent.getmImageUrl()).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return puploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        public TextView textViewName;
        public TextView textViewQQty;
        public  TextView textViewDes;
        public ImageView imageView;

        public ImageViewHolder(View itemView){
            super(itemView);
            textViewDes=itemView.findViewById(R.id.txtViewDescription);
            textViewQQty=itemView.findViewById(R.id.txtViewQty);
            textViewName=itemView.findViewById(R.id.txtViewName);
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
            MenuItem nxtPage = menu.add(Menu.NONE,2,2,"Edit");
            MenuItem nxt = menu.add(Menu.NONE,1,1, "View");
            MenuItem delete = menu.add(Menu.NONE,3,3,"Delete");

            nxtPage.setOnMenuItemClickListener(this);
            nxt.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
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
                        case 2:
                            mlistener.NextPageClick(position);
                            return true;

                        case 3:
                            mlistener.onDeleteClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }
public interface OnItemClickListener{

        void NextPage(int Position);

        void NextPageClick(int Position);

        void onDeleteClick(int Position);
}
public void setOnItemClickListener(OnItemClickListener listener){
            mlistener = listener;
}
}

