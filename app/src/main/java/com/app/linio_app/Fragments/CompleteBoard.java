package com.app.linio_app.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.linio_app.Models.CompleteModel;
import com.app.linio_app.Models.InProgressModel;
import com.app.linio_app.Models.PanelsModel;
import com.app.linio_app.Models.QueueModel;
import com.app.linio_app.R;
import com.app.linio_app.Services.Firebase_Services.BoardSwapper;
import com.app.linio_app.Services.Firebase_Services.TasksServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CompleteBoard extends Fragment implements View.OnCreateContextMenuListener {

    ListView completes;

    FirebaseAuth auth;
    DatabaseReference database;

    CompleteModel completeModel;
    InProgressModel inProgressModel;
    QueueModel queueModel;

    TasksServices tasksServices;
    BoardSwapper boardSwapper;

    public CompleteBoard() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complete_board, container, false);

        completes = (ListView)view.findViewById(R.id.completes);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        completeModel = new CompleteModel();
        inProgressModel = new InProgressModel();
        queueModel = new QueueModel();

        tasksServices = new TasksServices(database,getContext(),completes,getPanelContext(),"complete");
        boardSwapper = new BoardSwapper(database,getContext());

        registerForContextMenu(completes);

        return view;
    }

    private String getPanelContext() {
        final Bundle bundle = this.getArguments();
        String title = "";
        if (bundle != null) title = bundle.getString("panelTitleComplete");
        return title;
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view,
                                    ContextMenu.ContextMenuInfo contextMenuInfo) {
        super.onCreateContextMenu(contextMenu, view, contextMenuInfo);
        MenuInflater inflater = new MenuInflater(getContext());
        inflater.inflate(R.menu.complete_actions,contextMenu);
        contextMenu.setHeaderTitle("Move to:");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final String panel = getPanelContext();
        ArrayList<PanelsModel> fetchedData = tasksServices.retrieve(panel);
        switch (item.getItemId()) {
            case R.id.moveQueue:
                PanelsModel panelsModelToQueue = setData(fetchedData,info);
                boardSwapper.moveFromCompleteToQueue(panelsModelToQueue,fetchedData.get(info.position).getCompleteModel(),panel,
                        fetchedData.get(info.position).getCompleteModel().getTitle());
                return true;
            case R.id.moveInProgress:
                PanelsModel panelsModelToInProgress = setData(fetchedData,info);
                boardSwapper.moveFromCompleteToInProgress(panelsModelToInProgress,fetchedData.get(info.position).getCompleteModel(),panel,
                        fetchedData.get(info.position).getCompleteModel().getTitle());
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private PanelsModel setData(ArrayList<PanelsModel> fetchedData, AdapterView.AdapterContextMenuInfo info) {
        String title = fetchedData.get(info.position).getCompleteModel().getTitle();
        String description = fetchedData.get(info.position).getCompleteModel().getDesc();
        completeModel.setTitle(title); completeModel.setDesc(description);
        PanelsModel panelsModel = new PanelsModel(); panelsModel.setCompleteModel(completeModel);
        return panelsModel;
    }

}
