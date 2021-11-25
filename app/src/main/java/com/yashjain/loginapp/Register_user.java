package com.yashjain.loginapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.yashjain.loginapp.databinding.ActivityRegisterUserBinding;

public class Register_user extends AppCompatActivity {
    ActivityRegisterUserBinding binding;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding=ActivityRegisterUserBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        mAuth=FirebaseAuth.getInstance();

        binding.btnRegister.setOnClickListener(view -> {
            createUser();
        });



        binding.LoginHere.setOnClickListener(view -> {
          finish();
        });
    }
    private void createUser(){
        String email = binding.etRegEmail.getText().toString();
        String password = binding.etRegPass.getText().toString();

        if (TextUtils.isEmpty(email)){
            binding.etRegEmail.setError("Email cannot be empty");
            binding.etRegEmail.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            binding.etRegPass.setError("Password cannot be empty");
            binding.etRegPass.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Toast.makeText(Register_user.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Register_user.this, loginActivity.class));
                }else{
                    Toast.makeText(Register_user.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}