package com.example.gossips;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.gossips.databinding.ActivityPhonenumberBinding;
import com.google.firebase.auth.FirebaseAuth;

public class phonenumber extends AppCompatActivity {
    ActivityPhonenumberBinding binding;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding =ActivityPhonenumberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!= null){

            Intent i = new Intent(phonenumber.this,MainActivity.class);
            startActivity(i);
            finish();
        }

        getSupportActionBar().hide();


        binding.continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(phonenumber.this,otp.class);
                i.putExtra("phonenumber",binding.namebox.getText().toString());
                startActivity(i);

            }
        });
    }
}