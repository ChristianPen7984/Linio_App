package com.app.linio_app.Services.Firebase_Services;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PanelCreator {

    Context context;

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public PanelCreator(Context context) {
        this.context = context;
    }

    public void createPanel(String title) {
        auth = FirebaseAuth.getInstance();
        String uid = auth.getUid();
        database.child("panels/" + uid + "/" + title + "/title/").setValue(title);
    }

}
