package com.example.hw07a;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class TripList extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference myRef;
    FirebaseUser currentUser;
    FirebaseDatabase mDatabase;
    StorageReference storageRef;
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Trip> trips = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);
        mRecyclerView = findViewById(R.id.my_recycler_view);
        getListItems();
    }
    private void getListItems() {
        FirebaseFirestore.getInstance().collection("trips")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Toast.makeText(TripList.this, e + "", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (queryDocumentSnapshots != null) {
                            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                if (documentSnapshot.getData() != null) {
                                    Trip trip  = (documentSnapshot.toObject(Trip.class));
                                    Log.d("demo", "onSuccess: " + trip);
                                    trips.add(trip);
                                }
                            }
                            loadRecyclerView();
                        }
                    }
                });
    };

    public void loadRecyclerView(){
        mLayoutManager = new LinearLayoutManager(TripList.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter =  new TripListAdapter(trips);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
