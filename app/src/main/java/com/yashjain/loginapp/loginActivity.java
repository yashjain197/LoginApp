package com.yashjain.loginapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.yashjain.loginapp.databinding.ActivityLoginBinding;

public class loginActivity extends AppCompatActivity {
    com.yashjain.loginapp.databinding.ActivityLoginBinding binding;
    GoogleSignInClient GoogleSignInClient;
    int RC_SIGN_IN=0;
    FirebaseAuth mAuth;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding= ActivityLoginBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth=FirebaseAuth.getInstance();
        binding.loginbtn.setOnClickListener(view -> {
            loginUser();
        });

        binding.signInButton.setOnClickListener(view -> {
            loginWithGoogle(view);
        });

        //Registration for firebase auth
     binding.signup.setOnClickListener(view -> {
         Intent intent=new Intent(loginActivity.this,Register_user.class);
         startActivity(intent);
     });

    binding.forgotPassword.setOnClickListener(view -> {
      Intent intent=new Intent(this,ForgotPassword.class);
      startActivity(intent);
    });

    }


    private void loginUser(){
        String email = binding.username.getText().toString().trim();
        String password = binding.password.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            binding.username.setError("Email cannot be empty");
            binding.username.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            binding.password.setError("Password cannot be empty");
            binding.password.requestFocus();
        }else{
            showDialog();
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Intent intent=new Intent(loginActivity.this,MainActivity.class);
                    Toast.makeText(loginActivity.this, "Logged in!", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }else{
                    dialog.dismiss();
                    Toast.makeText(loginActivity.this, "Log in Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void loginWithGoogle(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            // ...
        }
    }

    private void signIn() {
        Intent signInIntent = GoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        showDialog();
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            Intent intent=new Intent(this,MainActivity.class);
            Toast.makeText(this, "Logged in!", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            dialog.dismiss();
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(GoogleSignIn.getLastSignedInAccount(this)!=null || FirebaseAuth.getInstance().getCurrentUser()!=null){
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();


        }
    }

    public void showDialog()
    {
        dialog=new ProgressDialog(loginActivity.this);
        dialog.setMessage("Please wait...");
        dialog.show();
    }
}