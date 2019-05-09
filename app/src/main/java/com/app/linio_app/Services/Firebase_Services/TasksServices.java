package com.app.linio_app.Services.Firebase_Services;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ListView;
import android.widget.Toast;

import com.app.linio_app.Adapters.CompleteAdapter;
import com.app.linio_app.Adapters.InProgressAdapter;
import com.app.linio_app.Adapters.QueueAdapter;
import com.app.linio_app.Fragments.Panel;
import com.app.linio_app.Models.InProgressModel;
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
import java.util.Comparator;

public class TasksServices {

    private DatabaseReference database;
    private FirebaseAuth auth;
    private ListView listView;
    private Context context;
    private ArrayList<PanelsModel> panelsModel = new ArrayList<>();

    private String target;

    public TasksServices(DatabaseReference database,Context context, ListView listView,
                        String panel, String target) {
        this.database = database;
        this.context = context;
        this.listView = listView;
        this.target = target;
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

    public ArrayList<PanelsModel> retrieve(final String panel) {
        auth = FirebaseAuth.getInstance();
        if (target.equals("queue") || target.equals("inprogress") || target.equals("complete")) {
            database.child(target + "/" + auth.getUid()).child(panel).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    panelsModel.clear();
                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            PanelsModel pMod = ds.getValue(PanelsModel.class);
                            panelsModel.add(pMod);
                        }
                        setAdapter(panel);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        return panelsModel;
    }

    private void setAdapter(String panel) {
        switch (target) {
            case "queue":
                QueueAdapter queueAdapter = new QueueAdapter(context, panelsModel,panel);
                listView.setAdapter(queueAdapter);
                break;
            case "inprogress":
                InProgressAdapter inProgressAdapter = new InProgressAdapter(context, panelsModel,panel);
                listView.setAdapter(inProgressAdapter);
                break;
            case "complete":
                CompleteAdapter completeAdapter = new CompleteAdapter(context, panelsModel,panel);
                listView.setAdapter(completeAdapter);
                break;
        }
    }

}
