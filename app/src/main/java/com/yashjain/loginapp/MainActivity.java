package com.yashjain.loginapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.yashjain.loginapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseAuth auth;
    com.google.android.gms.auth.api.signin.GoogleSignInClient GoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();

        binding.logout.setOnClickListener(view -> {
            if(auth.getCurrentUser()!=null){
                auth.signOut();
            }
            if(GoogleSignIn.getLastSignedInAccount(this)!=null){
                GoogleSignInOptions gso = new GoogleSignInOptions.
                        Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                        build();

                com.google.android.gms.auth.api.signin.GoogleSignInClient googleSignInClient=GoogleSignIn.getClient(this,gso);
                googleSignInClient.signOut();
            }
            Intent intent=new Intent(MainActivity.this,loginActivity.class);
            startActivity(intent);
            finish();
        });

    }
}