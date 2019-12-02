package com.example.hw07a;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ChatRoom extends AppCompatActivity {
    Trip trip;
    ImageView imageViewsend;
    EditText editTextmessage;
    ListView listView;
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String[] messages;
    List<String> messageList = new ArrayList<>();
    String userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        trip = (Trip) getIntent().getSerializableExtra("trip");
        userInfo = getIntent().getStringExtra("user_id");
        mRecyclerView = findViewById(R.id.my_recycler_view);
        messages = new String[trip.getChatroom().size()];
//        messages = (ArrayList<String>) trip.getChatroom();
        for (int i=0;i<trip.getChatroom().size();i++){
            messages[i]= trip.getChatroom().get(i);
        }
        mRecyclerView = findViewById(R.id.my_recycler_view);
        imageViewsend = findViewById(R.id.sendMessage);
        editTextmessage = findViewById(R.id.EdittextMesage);
        getMessage();
        imageViewsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                String message = editTextmessage.getText().toString();
                trip.chatroom.add(userInfo+"_"+message);
                Map<String,Object> tripmap = trip.toHashMap();

                db.collection("trips").document(trip.getId()).set(tripmap, SetOptions.merge())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("demo","success");
                                mAdapter.notifyDataSetChanged();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ChatRoom.this, "Not Added", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }


    private void loadRecyclerView(List<String> message){
        mLayoutManager = new LinearLayoutManager(ChatRoom.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        Log.d("demo",message.toString());
        mAdapter =  new ChatAdapter((ArrayList<String>) message, new ChatAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String mData) {
                deleteMessage(mData);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    public void deleteMessage(String mData){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        trip.chatroom.remove(mData);
        Map<String,Object> tripmap = trip.toHashMap();
        db.collection("trips").document(trip.getId()).set(tripmap, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("demo","success");
                        loadRecyclerView(trip.getChatroom());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ChatRoom.this, "Not Added", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getMessage(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("trips").document(trip.getId())
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Toast.makeText(ChatRoom.this, e + "", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (queryDocumentSnapshots != null && queryDocumentSnapshots.exists()) {
                            Trip trip = (queryDocumentSnapshots.toObject(Trip.class));

                            loadRecyclerView(trip.getChatroom());
                        } else {
                            Log.d("demo", "Current data: null");
                        }
                    }
                });
    }
}
