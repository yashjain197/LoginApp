package com.yashjain.loginapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.yashjain.loginapp.databinding.ActivityForgotPasswordBinding;

public class ForgotPassword extends AppCompatActivity {
    ActivityForgotPasswordBinding binding;
    FirebaseAuth auth;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        auth=FirebaseAuth.getInstance();


        binding.btnReset.setOnClickListener(view -> {
            if(binding.etRegEmail.getText().toString().trim().isEmpty()){
                binding.etRegEmail.setError("Email cannot be empty");
            }else{
                showDialog();
                String email= binding.etRegEmail.getText().toString().trim();
                auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(ForgotPassword.this, "Reset link sent to your Email!", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        dialog.dismiss();
                        Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        binding.LoginHere.setOnClickListener(view -> finish());
    }
    public void showDialog()
    {
        dialog=new ProgressDialog(ForgotPassword.this);
        dialog.setMessage("Please wait...");
        dialog.show();
    }
}