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

public class TaskService {

    private DatabaseReference database;
    private FirebaseAuth auth;
    private Context context;

    private String target;

    public TaskService(DatabaseReference database,Context context, String panel, String target) {
        this.database = database;
        this.context = context;
        this.target = target;
    }

    public Boolean save(PanelsModel panelsModel,String panel, String title) {
        auth = FirebaseAuth.getInstance();
        boolean saved;
        if (panelsModel == null) saved = false;
        else {
            try {
                database.child(target + "/" + auth.getUid())
                        .child(panel).child(title).setValue(panelsModel);
                saved = true;
            }
            catch (DatabaseException e) {
                new ErrorDialogs(context).getFailedPanelCreation();
                saved = false;
            }
        } return saved;
    }

    public Boolean remove(PanelsModel panelsModel, String panel, String title) {
        auth = FirebaseAuth.getInstance();
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
