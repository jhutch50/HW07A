package com.example.hw07a;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    ProgressBar progressBar;
    Bitmap bitmapUpload = null;
    ImageView imageView;
    EditText editTextFname;
    EditText editTextLname;
    RadioGroup radioGroup;
    Uri imageURI;
    String user_info;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    Button buttonsave;
    int selectedButton =0;
    String gender;
    FirebaseFirestore db;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        imageView = findViewById(R.id.imageView);
        buttonsave = findViewById(R.id.butto_save);
        editTextFname = findViewById(R.id.et_firstname);
        editTextLname = findViewById(R.id.et_lastname);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){

            user  = (FirebaseUser) bundle.get("user_info");
            user_info = user.getUid();
        }
        db = FirebaseFirestore.getInstance();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Filechooser();
            }
        });
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                selectedButton= radioGroup.getCheckedRadioButtonId();

                if(selectedButton==R.id.rb_male)
                {
                    gender="Male";
                }
                else if(selectedButton==R.id.rb_female) {
                    gender = "Female";
                }
            }
        });

        db.collection("users").document(user_info).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Uri uri = (Uri) documentSnapshot.get("uri");
                if(uri!=null)
                    imageView.setImageURI(uri);
                gender = (String) documentSnapshot.get("gender");
                Log.d("Gender",gender);
                if (gender.equals("Male")) {
                    radioGroup.check(R.id.rb_male);
                }else if(gender.equals("Female")){
                    radioGroup.check(R.id.rb_female);
                }

                editTextFname.setText((String) documentSnapshot.get("fname"));
                editTextLname.setText((String) documentSnapshot.get("lname"));
            }
        });

        buttonsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final profile userProfile = new profile();
                String fname = editTextFname.getText().toString();
                String lname = editTextLname.getText().toString();
                userProfile.fname = fname;
                userProfile.lname = lname;
                userProfile.gender = gender;
                if(imageURI != null){
                    userProfile.image = imageURI.toString();
                }

                Map<String,Object> usermap = userProfile.toHashMap();
                db.collection("users").document(user_info).set(usermap, SetOptions.merge())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                                intent.putExtra("user_info",user);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditProfileActivity.this, "Not Added", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }

    //    Upload Camera Photo to Cloud Storage....
    private void uploadImage(Bitmap photoBitmap){
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();

        final StorageReference imageRepo = storageReference.child("images/camera.png");

//        Converting the Bitmap into a bytearrayOutputstream....
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imageRepo.putBytes(data);
//        uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.e(TAG, "onFailure: "+e.getMessage());
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Log.d(TAG, "onSuccess: "+"Image Uploaded!!!");
//            }
//        });

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                return null;
                if (!task.isSuccessful()){
                    throw task.getException();
                }

                return imageRepo.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()){
                    Log.d("demo", "Image Download URL"+ task.getResult());
                    String imageURL = task.getResult().toString();
                }
            }
        });

//        ProgressBar......

        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                progressBar.setProgress((int) progress);
                System.out.println("Upload is " + progress + "% done");
            }
        });

    }
    //    TAKE PHOTO USING CAMERA...

    private void Filechooser(){
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Log.d("demo","image found");
            imageURI = data.getData();
            imageView.setImageURI(imageURI);

        }
    }
}
