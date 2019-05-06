package com.app.linio_app.Services.Firebase_Services;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ListView;
import android.widget.Toast;

import com.app.linio_app.Adapters.PanelsAdapter;
import com.app.linio_app.Adapters.QueueAdapter;
import com.app.linio_app.Models.PanelsModel;
import com.app.linio_app.Models.QueueModel;
import com.app.linio_app.Services.ErrorDialogs;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class QueueCreator {

    private DatabaseReference database;
    private FirebaseAuth auth;
    private ListView listView;
    private Context context;
    private ArrayList<PanelsModel> panelsModel = new ArrayList<>();
    private QueueAdapter queueAdapter;

    public QueueCreator(DatabaseReference database,Context context, ListView listView,
                        String panel) {
        this.database = database;
        this.context = context;
        this.listView = listView;
        retrieve(panel);
    }

    public Boolean save(PanelsModel panelsModel,String panel, String title) {
        auth = FirebaseAuth.getInstance();
        boolean saved;
        if (panelsModel == null) saved = false;
        else {
            try {
                database.child("queue/" + auth.getUid())
                        .child(panel).child(title).setValue(panelsModel);
                saved = true;
            }
            catch (DatabaseException e) {
                new ErrorDialogs(context).getFailedPanelCreation();
                saved = false;
            }
        } return saved;
    }

    public ArrayList<PanelsModel> retrieve(String panel) {
        auth = FirebaseAuth.getInstance();
        database.child("queue/" + auth.getUid()).child(panel).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                panelsModel.clear();
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        PanelsModel pMod = ds.getValue(PanelsModel.class);
                        panelsModel.add(pMod);
                    }
                    queueAdapter = new QueueAdapter(context,panelsModel);
                    Collections.reverse(panelsModel);
                    listView.setAdapter(queueAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return panelsModel;
    }

}
