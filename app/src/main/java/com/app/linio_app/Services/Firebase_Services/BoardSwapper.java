package com.app.linio_app.Services.Firebase_Services;

import android.content.Context;

import com.app.linio_app.Models.CompleteModel;
import com.app.linio_app.Models.InProgressModel;
import com.app.linio_app.Models.PanelsModel;
import com.app.linio_app.Models.QueueModel;
import com.app.linio_app.Services.ErrorDialogs;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

public class BoardSwapper {

    private DatabaseReference database;
    private Context context;
    private FirebaseAuth auth;

    public BoardSwapper(DatabaseReference database, Context context) {
        this.database = database;
        this.context = context;
    }

    public Boolean moveFromQueueToInProgress(PanelsModel panelsModel, QueueModel queueModel, String panel, String title) {
        auth = FirebaseAuth.getInstance();
        boolean saved = false;
        if (panelsModel.getQueue_board() != null) {
            try {
                database.child("queue/" + auth.getUid()).child(panel).child(title).removeValue();
                database.child("inprogress/" + auth.getUid()).child(panel).child(title).setValue(setInProgressFromQueue(queueModel));
                saved = true;
            } catch (DatabaseException e) { new ErrorDialogs(context).getFailedPanelCreation(); }
        }
        return saved;
    }

    public Boolean moveFromInProgressToQueue(PanelsModel panelsModel, InProgressModel inProgressModel, String panel, String title) {
        auth = FirebaseAuth.getInstance();
        boolean saved = false;
        if (panelsModel.getInProgressModel() != null) {
            try {
                database.child("inprogress/" + auth.getUid()).child(panel).child(title).removeValue();
                database.child("queue/" + auth.getUid()).child(panel).child(title).setValue(setQueueFromInProgress(inProgressModel));
                saved = true;
            } catch (DatabaseException e) { new ErrorDialogs(context).getFailedPanelCreation(); }
        }
        return saved;
    }

    public Boolean moveFromInProgressToComplete(PanelsModel panelsModel, InProgressModel inProgressModel, String panel, String title) {
        auth = FirebaseAuth.getInstance();
        boolean saved = false;
        if (panelsModel.getInProgressModel() != null) {
            try {
                database.child("inprogress/" + auth.getUid()).child(panel).child(title).removeValue();
                database.child("complete/" + auth.getUid()).child(panel).child(title).setValue(setCompleteFromInProgress(inProgressModel));
                saved = true;
            } catch (DatabaseException e) { new ErrorDialogs(context).getFailedPanelCreation(); }
        }
        return saved;
    }

    public Boolean moveFromCompleteToInProgress(PanelsModel panelsModel, CompleteModel completeModel, String panel, String title) {
        auth = FirebaseAuth.getInstance();
        boolean saved = false;
        if (panelsModel.getCompleteModel() != null) {
            try {
                database.child("complete/" + auth.getUid()).child(panel).child(title).removeValue();
                database.child("inprogress/" + auth.getUid()).child(panel).child(title).setValue(setInprogressFromComplete(completeModel));
                saved = true;
            } catch (DatabaseException e) { new ErrorDialogs(context).getFailedPanelCreation(); }
        }
        return saved;
    }

    private PanelsModel setInProgressFromQueue(QueueModel queueModel) {
        PanelsModel panelsModel = new PanelsModel();
        InProgressModel inProgressModel = new InProgressModel();
        inProgressModel.setTitle(queueModel.getTitle());
        inProgressModel.setDesc(queueModel.getDesc());
        inProgressModel.setDate(queueModel.getDate());
        panelsModel.setInProgressModel(inProgressModel);
        return panelsModel;
    }

    private PanelsModel setQueueFromInProgress(InProgressModel inProgressModel) {
        PanelsModel panelsModel = new PanelsModel();
        QueueModel queueModel = new QueueModel();
        queueModel.setTitle(inProgressModel.getTitle());
        queueModel.setDesc(inProgressModel.getDesc());
        queueModel.setDate(inProgressModel.getDate());
        panelsModel.setQueue_board(queueModel);
        return panelsModel;
    }

    private PanelsModel setCompleteFromInProgress(InProgressModel inProgressModel) {
        PanelsModel panelsModel = new PanelsModel();
        CompleteModel completeModel = new CompleteModel();
        completeModel.setTitle(inProgressModel.getTitle());
        completeModel.setDesc(inProgressModel.getDesc());
        completeModel.setDate(inProgressModel.getDate());
        panelsModel.setCompleteModel(completeModel);
        return panelsModel;
    }

    private PanelsModel setInprogressFromComplete(CompleteModel completeModel) {
        PanelsModel panelsModel = new PanelsModel();
        InProgressModel inProgressModel = new InProgressModel();
        inProgressModel.setTitle(completeModel.getTitle());
        inProgressModel.setDesc(completeModel.getDesc());
        inProgressModel.setDate(completeModel.getDate());
        panelsModel.setInProgressModel(inProgressModel);
        return panelsModel;
    }
}