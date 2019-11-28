package com.example.hw07a;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddTrip extends AppCompatActivity {

    Button buttonsearch;
    Button buttonaddtrip;
    EditText editTextcity;
    EditText editTexttitle;
    String city;
    String type;
    String placeId;
    String userId;
    ProgressDialog progressDialog;

    private static final String key= "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        buttonsearch = findViewById(R.id.buttonSearch);
        buttonaddtrip = findViewById(R.id.buttonaddtrip);
        editTextcity = findViewById(R.id.editTextCity);

        editTexttitle = findViewById(R.id.editTexttripName);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            userId = (String) bundle.get("userId");
        }

        buttonsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String input ="input="+editTextcity.getText().toString();

                String apikey = "key="+ key;
                String types = "types=(cities)";
                String url = "https://maps.googleapis.com/maps/api/place/autocomplete/json?"+ input+"&" +types+"&"+apikey;
                String type = "city";
                new GetNewsAsync().execute(url,type);
            }
        });

        buttonaddtrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input ="placeid="+placeId;

                String apikey = "key="+ key;
                String url = "https://maps.googleapis.com/maps/api/place/details/json?"+ input+"&"+apikey;
                String type = "place";
                new GetNewsAsync().execute(url,type);

                String name  = editTexttitle.getText().toString();
                Intent returnIntent = getIntent();
                returnIntent.putExtra("city",city);
                returnIntent.putExtra("name",name);
                setResult(AddTrip.RESULT_OK,returnIntent);
                finish();
            }
        });
    }
    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }

    private class GetNewsAsync extends AsyncTask<String ,Void, Map<String,String>> {

        @Override
        protected Map<String,String> doInBackground(String... strings) {
            StringBuilder stringBuilder = new StringBuilder();
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            ArrayList<City> result = new ArrayList<City>();
            Map<String,String> cityMap = new HashMap<>();
            try {
                URL url = new URL(strings[0]);
                type = strings[1];
                Log.d("demo",url.toString());
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    String json = stringBuilder.toString();
                    JSONObject root = new JSONObject(json);
                    Log.d("demo",root.toString());
                    if(type == "city"){
                        JSONArray predictions = root.getJSONArray("predictions");
                        for(int i=0;i<predictions.length();i++){
                            JSONObject cityJSON = predictions.getJSONObject(i);
                            cityMap.put(cityJSON.getString("description"),cityJSON.getString("place_id"));
                        }
                    }
                    if(type == "place"){
                        JSONObject res = root.getJSONObject("result");
                        JSONObject geometry = res.getJSONObject("geometry");
                        JSONObject location = geometry.getJSONObject("location");
                        cityMap.put("lat",location.getString("lat"));
                        cityMap.put("lng",location.getString("lng"));
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return cityMap;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(AddTrip.this);
            progressDialog.setMessage("Loading");
            progressDialog.setProgress(0);
            progressDialog.setMax(20);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Map<String,String> cities) {
            Log.d("demo",cities.toString());
            progressDialog.dismiss();
            if(type == "city"){
                showAlertBox(cities);
            }
            if(type =="place"){
                savePlaceToFireBase(cities);
            }

        }
    }

    public void showAlertBox(final Map<String,String> cities){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(AddTrip.this);
        builderSingle.setTitle("Select City:-");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddTrip.this, android.R.layout.select_dialog_singlechoice);
        for (Map.Entry<String,String> city :cities.entrySet()){
            arrayAdapter.add(city.getKey());
        }

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                city = arrayAdapter.getItem(which);
                placeId = cities.get(city);
                editTextcity.setText(city);
            }
        });
        builderSingle.show();
    }

    public void savePlaceToFireBase(Map<String,String> loc) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Trip trip = new Trip();
        trip.title = editTexttitle.getText().toString();
        trip.name = city;
        trip.placeId = placeId;
        trip.creator_id = userId;
        trip.locLat = loc.get("lat");
        trip.locLong = loc.get("lng");
        trip.photo = "";
        trip.chatroom = "";
        Map<String,Object> tripmap = trip.toHashMap();
        String id = UUID.randomUUID().toString().replace("-", "");
        db.collection("trips").document(id).set(tripmap, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                            Log.d("demo","success");
//                        Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
//                        intent.putExtra("user_info",user);
//                        startActivity(intent);
//                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddTrip.this, "Not Added", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
