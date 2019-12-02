package com.example.hw07a;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    ArrayList<profile> mData;

    public UserAdapter(ArrayList<profile> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        profile user = mData.get(position);
        holder.textViewName.setText(user.getFname()+ user.getLname());
        holder.textViewGender.setText(user.getGender());
        String urltoImage = user.getImage();
        if(urltoImage == null || urltoImage.isEmpty()){
            Picasso.get().load(ProfileActivity.deafultUrl).into(holder.imageView2);
        } else {
            Picasso.get().load(urltoImage).into(holder.imageView2);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewGender;
        ImageView imageView2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewNameID);
            textViewGender = itemView.findViewById(R.id.textViewdateID);
            imageView2 = itemView.findViewById(R.id.imageViewtripID);

        }
    }
}
