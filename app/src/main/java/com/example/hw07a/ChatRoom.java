package com.example.hw07a;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class ChatRoom extends AppCompatActivity {
    Trip trip;
    ImageView imageViewsend;
    EditText editTextmessage;
    ListView listView;
    String[] messages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        trip = (Trip) getIntent().getSerializableExtra("trip");
        messages = new String[trip.getChatroom().size()];
//        messages = (ArrayList<String>) trip.getChatroom();
        for (int i=0;i<trip.getChatroom().size();i++){
            messages[i]= trip.getChatroom().get(i);
        }
        listView= findViewById(R.id.messageList);
        imageViewsend = findViewById(R.id.sendMessage);
        editTextmessage = findViewById(R.id.EdittextMesage);
        Toast.makeText(this, trip.toString(), Toast.LENGTH_SHORT).show();
        imageViewsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                String message = editTextmessage.getText().toString();
                trip.chatroom.add(message);
                Map<String,Object> tripmap = trip.toHashMap();

                db.collection("trips").document(trip.getId()).set(tripmap, SetOptions.merge())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("demo","success");
                                displayMessage();
//                        Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
//                        intent.putExtra("user_info",user);
//                        startActivity(intent);
//                        finish();
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

    private void displayMessage(){
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, (String[]) messages);
        listView.setAdapter(adapter);
    }
}
