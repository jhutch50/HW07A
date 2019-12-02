package com.example.hw07a;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    ArrayList<String> mData;

    public ChatAdapter(ArrayList<String> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chatroom_list, parent, false);

        ChatAdapter.ViewHolder viewHolder = new ChatAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String message = mData.get(position);
        HashMap users = (HashMap) ProfileActivity.getHashMap();
        String[] messages = message.split("_");
        String name = (String) users.get(messages[0]);
        if(!messages[0].isEmpty()){
            holder.textViewName.setText(name);
            if(!messages[0].equals(ProfileActivity.user_id)){
                holder.imageViewdelete.setVisibility(View.INVISIBLE);
            }
        }
        if(!messages[1].isEmpty()){
            holder.textViewMessage.setText(messages[1]);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewMessage;
        TextView textViewDate;
        ImageView imageViewMessage;
        ImageView imageViewdelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewMessage = itemView.findViewById(R.id.textView_message);
            imageViewMessage = itemView.findViewById(R.id.imageViewId);
            imageViewdelete = itemView.findViewById(R.id.imageViewdelete);

            imageViewdelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "clicked", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
