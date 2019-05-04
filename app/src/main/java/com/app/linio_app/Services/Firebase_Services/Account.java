package com.app.linio_app.Services.Firebase_Services;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.app.linio_app.Services.ErrorDialogs;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Account {

    Context context;

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public Account(Context context) {
        this.context = context;
    }

    public void createAccount(final String email, String password) {
        auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) new ErrorDialogs(context).getFailedAccountSignUp();
                        else {
                            String uid = auth.getUid();
                            database.child("users/" + uid + "/email/").setValue(email);
                        }
                    }
                });
    }
}