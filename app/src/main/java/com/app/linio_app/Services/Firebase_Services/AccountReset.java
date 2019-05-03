package com.app.linio_app.Services.Firebase_Services;

import android.content.Context;
import android.support.annotation.NonNull;

import com.app.linio_app.Services.ErrorDialogs;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AccountReset {

    Context context;

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public AccountReset(Context context) {
        this.context = context;
    }

    public void resetAccount(String email) {
        auth = FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) new ErrorDialogs(context).getFailedPasswordReset();
                    }
                });
    }

}
