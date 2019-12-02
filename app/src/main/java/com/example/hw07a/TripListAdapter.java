package com.example.hw07a;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TripListAdapter extends RecyclerView.Adapter<TripListAdapter.ViewHolder> {

    ArrayList<Trip> mData;
    private final TripListAdapter.OnItemClickListener listener;

    public TripListAdapter(ArrayList<Trip> mData, TripListAdapter.OnItemClickListener listener) {
        this.mData = mData;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TripListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trip_item, parent, false);

        TripListAdapter.ViewHolder viewHolder = new TripListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Trip trip = mData.get(position);
        holder.textViewName.setText(trip.getName());
        holder.textViewdateID.setText(trip.getCreator_id());
        String urltoImage = trip.getImageUrl();
        if(urltoImage == null || urltoImage.isEmpty()){
            Picasso.get().load(ProfileActivity.deafultUrl).into(holder.imageView2);
        } else {
            Picasso.get().load(urltoImage).into(holder.imageView2);
        }
        final String userInfo = ProfileActivity.user_id;
        if(trip.getUsers().contains(userInfo)){
           holder.buttonjoin.setVisibility(View.INVISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ChatRoom.class);
                intent.putExtra("trip",trip);

                intent.putExtra("user_id",userInfo);
                if(trip.getUsers().contains(userInfo)){
                    v.getContext().startActivity(intent);
                } else {
                    Toast.makeText(v.getContext(), "Join the trip to go to the chatroom", Toast.LENGTH_SHORT).show();
                }

            }
        });
        holder.buttonjoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(trip);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewdateID;
        ImageView imageView2;
        Button buttonjoin;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewNameID);
            textViewdateID = itemView.findViewById(R.id.textViewdateID);
            imageView2 = itemView.findViewById(R.id.imageViewtripID);
            buttonjoin = itemView.findViewById(R.id.buttonJoin);

        }
    }
    public interface OnItemClickListener {
        void onItemClick(Trip trip);
    }
}
