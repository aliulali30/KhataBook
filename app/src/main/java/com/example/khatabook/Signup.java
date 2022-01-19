package com.example.khatabook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class Signup extends AppCompatActivity {

    private FirebaseAuth auth;
    FirebaseDatabase database;
    EditText name, username, password, confirmPassword;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = findViewById(R.id.PersonName);
        username = findViewById(R.id.username);
        password = findViewById(R.id.TextPassword);
        confirmPassword = findViewById(R.id.ConfirmPass);
        signup = findViewById(R.id.signup);

        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String usernamee = username.getText().toString();
                String passwordd = password.getText().toString();
                String confirmpasswordd = confirmPassword.getText().toString();


                if(TextUtils.isEmpty(usernamee)){
                    username.setError("Username is required!");
                    return;
                }
                if(TextUtils.isEmpty(passwordd)){
                    password.setError("Password is required!");
                    return;
                }
                if(TextUtils.isEmpty(confirmpasswordd)){
                    confirmPassword.setError("Confirm Password is required!");
                    return;
                }
                if(passwordd.length() < 6 ){
                    Toast.makeText(getApplicationContext(), "Password should contain atleast 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!passwordd.equals(confirmpasswordd)){
                    Toast.makeText(getApplicationContext(), "Password do not match!", Toast.LENGTH_SHORT).show();
                    return;
                }


                auth.createUserWithEmailAndPassword(usernamee, passwordd ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
//                                    Users user = new Users(
//                                            binding.edUser.getText().toString(),
//                                            binding.edEmail.getText().toString(),
//                                            binding.edPassword.getText().toString()
//                                    );
//                                    String id = task.getResult().getUser().getUid();
//                                    database.getReference().child("Users").child(id).setValue(user);
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            Toast.makeText(getApplicationContext(), "Account created!", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();

                        }

                    }
                });
            }
        });
    }
}