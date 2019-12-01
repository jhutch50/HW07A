package com.example.hw07a;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    private static final int ADD_TRIP_REQ_CODE = 123;
    FirebaseFirestore db;
    FirebaseUser user;
    Uri imageURI;
    String user_info;
    Button button_edit;
    Button buttonaddTrip;
    Button buttonusers;
    Button buttontrips;
    ArrayList<profile> profiles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        button_edit = findViewById(R.id.btn_edit);
        buttonaddTrip = findViewById(R.id.buttonTrip);
        buttonusers = findViewById(R.id.buttonUsers);
        buttontrips = findViewById(R.id.buttontrips);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            user  = (FirebaseUser) bundle.get("user_info");
            user_info = user.getUid();
        }
        db = FirebaseFirestore.getInstance();

        buttonusers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, UserListActivity.class);
                intent.putExtra("user_info",user);
                startActivityForResult(intent,123);
            }
        });


        button_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                intent.putExtra("user_info",user);
                intent.putExtra("load_profile",true);
                startActivity(intent);
                finish();
            }
        });

        buttonaddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,AddTrip.class);
                intent.putExtra("userId",user_info);
                startActivityForResult(intent,ADD_TRIP_REQ_CODE);
            }
        });

        buttontrips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, TripList.class);
                intent.putExtra("user_info",user);
                intent.putExtra("load_profile",true);
                startActivity(intent);
            }
        });


        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()){
                        profile tempProfile = new profile();
                        tempProfile.setFname((String) document.getData().get("fname"));
                        tempProfile.setLname((String) document.getData().get("lname"));
                        tempProfile.setGender((String) document.getData().get("gender"));
                        tempProfile.setImage((String) document.getData().get("uri"));
                        profiles.add(tempProfile);
                    }
                }
            }
        });
    }
}
