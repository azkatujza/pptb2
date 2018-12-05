package com.example.user.kusiar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Set;

public class SignupActivity extends AppCompatActivity
{
    private EditText UserEmail, UserPassword;
    private Button JoinButton;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        ImageView closeSignupIv = (ImageView) findViewById(R.id.closeSignupIv);
        closeSignupIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignupActivity.super.onBackPressed();
            }
        });

        UserEmail = (EditText) findViewById(R.id.signup_email);
        UserPassword = (EditText) findViewById(R.id.signup_password);
        JoinButton = (Button) findViewById(R.id.signup_join_button);
        loadingBar = new ProgressDialog(this);

        JoinButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                CreateNewAccount();
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            SendUserToHomeActivity();
        }
    }

    private void SendUserToHomeActivity()
    {
        Intent homeIntent = new Intent(SignupActivity.this, HomeActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
        finish();
    }

    private void CreateNewAccount()
    {
        String email = UserEmail.getText().toString();
        String password = UserPassword.getText().toString();

        if(TextUtils.isEmpty(email))
            Toast.makeText(this, "Email is empty", Toast.LENGTH_SHORT).show();

        else if(TextUtils.isEmpty(password))
            Toast.makeText(this, "Password is empty", Toast.LENGTH_SHORT).show();

        else
        {
            loadingBar.setTitle("Creating Account");
            loadingBar.setMessage("Please wait");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if (task.isSuccessful())
                    {
                        SendUserToSetupActivity();
                        Toast.makeText(SignupActivity.this, "Authenticating Successful", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                    else
                    {
                        String message = task.getException().getMessage();
                        Toast.makeText(SignupActivity.this, "Error Occurred" + message, Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
            });
        }

    }

    private void SendUserToSetupActivity()
    {
        Intent setupIntent = new Intent(SignupActivity.this, SetupActivity.class);
        setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(setupIntent);
        finish();
    }
}
