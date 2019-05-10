package com.app.linio_app.Services.Firebase_Services;

import android.content.Context;

import com.app.linio_app.Models.PanelsModel;
import com.app.linio_app.Services.ErrorDialogs;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class PanelRemover {

    private DatabaseReference database;
    private Context context;

    public PanelRemover(DatabaseReference database,Context context) {
        this.database = database;
        this.context = context;
    }

    public Boolean remove(String panel) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        boolean saved;
        try {
            database.child("panels/" + auth.getUid()).child(panel).removeValue();
            try {
                database.child("queue/" + auth.getUid()).child(panel).removeValue();
                database.child("inprogress/" + auth.getUid()).child(panel).removeValue();
                database.child("complete/" + auth.getUid()).child(panel).removeValue();
            } catch (DatabaseException e) { }
            saved = true;
        }
        catch (DatabaseException e) {
            new ErrorDialogs(context).getFailedPanelCreation();
            saved = false;
        } return saved;
    }

}
