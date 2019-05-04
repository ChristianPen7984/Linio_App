package com.app.linio_app.Services.Firebase_Services;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.app.linio_app.Models.PanelInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PanelsRetriever {

    Context context;
    ListView listView;

    FirebaseDatabase database;
    FirebaseAuth auth;
    DatabaseReference dataRef;
    String uid;

    public PanelsRetriever(Context context, ListView listView) {
        this.context = context;
        this.listView = listView;
    }

    public void retrievePanels() {
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        dataRef = database.getReference();
        FirebaseUser user = auth.getCurrentUser();
        uid = user.getUid();

        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getData(DataSnapshot dataSnapshot) {
//        for (DataSnapshot dSnap : dataSnapshot.getChildren()) {
//            PanelInformation panelInformation = new PanelInformation();
//            panelInformation.setTitle(dSnap.child("panels/" + uid + "/").getValue(PanelInformation.class).getTitle());
//            ArrayList<String> arrayList = new ArrayList<>();
//            arrayList.add(panelInformation.getTitle());
//            ArrayAdapter arrayAdapter = new ArrayAdapter(context,android.R.layout.simple_dropdown_item_1line,listView)

    }

}
