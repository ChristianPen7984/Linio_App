package com.app.linio_app.Services.Firebase_Services;

import android.content.Context;
import android.widget.ListView;

import com.app.linio_app.Models.PanelsModel;
import com.app.linio_app.Services.ErrorDialogs;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

public class TaskRemover {

    private DatabaseReference database;
    private Context context;

    private String target;


    public TaskRemover(DatabaseReference database,Context context, String target) {
        this.database = database;
        this.context = context;
        this.target = target;
    }

    public Boolean remove(PanelsModel panelsModel,String panel, String title) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        boolean saved;
        if (panelsModel == null) saved = false;
        else {
            try {
                database.child(target + "/" + auth.getUid()).child(panel).child(title).removeValue();
                saved = true;
            }
            catch (DatabaseException e) {
                new ErrorDialogs(context).getFailedPanelCreation();
                saved = false;
            }
        } return saved;
    }
}
