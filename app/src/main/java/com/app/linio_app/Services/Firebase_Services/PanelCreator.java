package com.app.linio_app.Services.Firebase_Services;

import android.content.Context;
import com.app.linio_app.Models.PanelsModel;
import com.app.linio_app.Services.ErrorDialogs;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

public class PanelCreator {

    private DatabaseReference database;
    private FirebaseAuth auth;
    private Context context;

    public PanelCreator(DatabaseReference database,Context context) {
        this.database = database;
        this.context = context;
    }

    public Boolean save(PanelsModel panelsModel,String title) {
        auth = FirebaseAuth.getInstance();
        boolean saved;
        if (panelsModel == null) saved = false;
        else {
            try {
                database.child("panels/" + auth.getUid()).child(title).setValue(panelsModel);
                saved = true;
            }
            catch (DatabaseException e) {
                new ErrorDialogs(context).getFailedPanelCreation();
                saved = false;
            }
        } return saved;
    }

}
