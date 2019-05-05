package com.app.linio_app.Services.Firebase_Services;

import android.content.Context;
import android.widget.ListView;
import android.widget.Toast;

import com.app.linio_app.Models.Complete;
import com.app.linio_app.Models.InProgress;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Queue;

public class PanelBoardCreator {

    private DatabaseReference database;
    private FirebaseAuth auth;
    private boolean saved;
    private ArrayList<Queue> queue = new ArrayList<>();
    private ArrayList<InProgress> inProgress = new ArrayList<>();
    private ArrayList<Complete> complete = new ArrayList<>();
    private ListView list;
    private Context context;

    public PanelBoardCreator(DatabaseReference database, Context context, ListView list) {
        this.database = database;
        this.context = context;
        this.list = list;
    }

    public Boolean saveQueue(Queue queue) {
        auth = FirebaseAuth.getInstance();
        if (queue == null) saved = false;
        else {
            try {
                database.child("panels/" + auth.getUid()).getKey();
                Toast.makeText(context,database.child("panels/" + auth.getUid()).getKey(),Toast.LENGTH_LONG).show();
            } catch (DatabaseException e) {

            }
        }

        return saved;
    }

//    public boolean saveInProgress() {
//
//    }
//
//    public boolean saveComplete() {
//
//    }

}
