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

    FirebaseFirestore db;
    FirebaseUser user;
    Uri imageURI;
    String user_info;
    Button button_edit;
    ArrayList<profile> profiles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        button_edit = findViewById(R.id.btn_edit);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            user  = (FirebaseUser) bundle.get("user_info");
            user_info = user.getUid();
        }
        db = FirebaseFirestore.getInstance();


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

        //Below is how to populate the ArrayList profiles. Not sure if this should be done inside a RecyclerView or not,
        //but I do know that this is the way we are supposed to obtain them.
        //If you look at the logs you'll see this works. Same thing can be done when listing trips collection

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
                        Log.d("demo",tempProfile.toString());
                    }
                }
            }
        });
    }
}
