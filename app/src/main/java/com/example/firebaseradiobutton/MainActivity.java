package com.example.firebaseradiobutton;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.provider.Settings;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;


import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button b;
    String deviceId;

    String text;
    RadioButton yes,no;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        yes=(RadioButton)findViewById(R.id.radioButton);
        no=(RadioButton)findViewById(R.id.radioButton2);
        b=(Button)findViewById(R.id.button);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.d("ID",  deviceId);


        DocumentReference reference = db.collection("user").document(deviceId);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(yes.isChecked())
                {
                    text = yes.getText().toString();
                }
                else if(no.isChecked())
                {
                    text = no.getText().toString();
                }
                DocumentReference reference = db.collection("user").document(deviceId);
                reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists())
                        {
                            String name=documentSnapshot.getString("car");

                            String id=documentSnapshot.getString("id");
                            if(id.equals(deviceId))
                            {
                                Toast.makeText(MainActivity.this,"Already user register",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Map<String, Object> user = new HashMap<>();
                                user.put("car",text);
                                user.put("id",deviceId);
                                reference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(MainActivity.this,"data Inserted",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }

                    }
                });

            }

        });



    }



}