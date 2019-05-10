package com.app.linio_app.Services.Firebase_Services;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ListView;
import android.widget.Toast;

import com.app.linio_app.Adapters.PanelsAdapter;
import com.app.linio_app.Models.PanelsModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class PanelRetriever {

    private DatabaseReference database;
    private FirebaseAuth auth;
    private ArrayList<PanelsModel> panelsModel = new ArrayList<>();
    private PanelsAdapter panelsAdapter;
    private ListView listView;
    private Context context;

    public PanelRetriever(DatabaseReference database,Context context, ListView listView) {
        this.database = database;
        this.context = context;
        this.listView = listView;
        this.retrieve();
    }


    public ArrayList<PanelsModel> retrieve() {
        auth = FirebaseAuth.getInstance();
        database.child("panels/" + auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                panelsModel.clear();
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        try {
                            PanelsModel pMod = ds.getValue(PanelsModel.class);
                            panelsModel.add(pMod);
                        } catch (DatabaseException de) {
                        }
                    }
                    panelsAdapter = new PanelsAdapter(context,panelsModel);
                    listView.setAdapter(panelsAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context,"Error" + databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        return panelsModel;
    }


    public ArrayList<PanelsModel> retrievePanel(final String panel) {
        auth = FirebaseAuth.getInstance();
        database.child("panels/" + auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                panelsModel.clear();
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        try {
                            PanelsModel pMod = ds.getValue(PanelsModel.class);
                            if (pMod.getTitle().contains(panel)) {
                                panelsModel.add(pMod);
                            }
                        } catch (DatabaseException de) {
                        }
                    }
                    panelsAdapter = new PanelsAdapter(context,panelsModel);
                    listView.setAdapter(panelsAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context,"Error" + databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        return panelsModel;
    }

}
