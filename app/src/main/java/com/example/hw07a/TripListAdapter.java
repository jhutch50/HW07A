package com.example.hw07a;

import android.content.Intent;
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

public class TripListAdapter extends RecyclerView.Adapter<TripListAdapter.ViewHolder> {

    ArrayList<Trip> mData;

    public TripListAdapter(ArrayList<Trip> mData) {
        this.mData = mData;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Trip trip = mData.get(position);
        holder.textViewName.setText(trip.getName());
        holder.textViewdateID.setText(trip.getCreator_id());
        String urltoImage = trip.getImageUrl();
        Picasso.get().load(urltoImage).into(holder.imageView2);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ChatRoom.class);
                intent.putExtra("trip",trip);
                v.getContext().startActivity(intent);
//                Toast.makeText(v.getContext(), trip.toString(), Toast.LENGTH_SHORT).show();
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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewNameID);
            textViewdateID = itemView.findViewById(R.id.textViewdateID);
            imageView2 = itemView.findViewById(R.id.imageViewtripID);

        }
    }
}
