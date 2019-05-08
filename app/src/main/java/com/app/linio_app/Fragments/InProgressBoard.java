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

import com.app.linio_app.Adapters.InProgressAdapter;
import com.app.linio_app.Adapters.QueueAdapter;
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

public class InProgressBoard extends Fragment implements View.OnCreateContextMenuListener {

    ListView inprogresses;

    FirebaseAuth auth;
    DatabaseReference database;

    InProgressModel inProgressModel;
    QueueModel queueModel;

    TasksServices tasksServices;
    BoardSwapper boardSwapper;

    public InProgressBoard() { }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inprogress, container, false);

        inprogresses = (ListView)view.findViewById(R.id.inprogresses);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        inProgressModel = new InProgressModel();
        queueModel = new QueueModel();

        tasksServices = new TasksServices(database,getContext(),inprogresses,getPanelContext(),"inprogress");
        boardSwapper = new BoardSwapper(database,getContext());

        registerForContextMenu(inprogresses);

        return view;
    }

    private String getPanelContext() {
        final Bundle bundle = this.getArguments();
        String title = "";
        if (bundle != null) title = bundle.getString("panelTitleInProgress");
        return title;
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view,
                                    ContextMenu.ContextMenuInfo contextMenuInfo) {
        super.onCreateContextMenu(contextMenu, view, contextMenuInfo);
        MenuInflater inflater = new MenuInflater(getContext());
        inflater.inflate(R.menu.inprogress_actions,contextMenu);
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
                boardSwapper.moveFromInProgressToQueue(panelsModelToQueue,fetchedData.get(info.position).getInProgressModel(),panel,
                        fetchedData.get(info.position).getInProgressModel().getTitle());
                return true;
            case R.id.moveComplete:
                PanelsModel panelsModelToComplete = setData(fetchedData,info);
                boardSwapper.moveFromInProgressToComplete(panelsModelToComplete,fetchedData.get(info.position).getInProgressModel(),panel,
                        fetchedData.get(info.position).getInProgressModel().getTitle());
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private PanelsModel setData(ArrayList<PanelsModel> fetchedData, AdapterView.AdapterContextMenuInfo info) {
        String title = fetchedData.get(info.position).getInProgressModel().getTitle();
        String description = fetchedData.get(info.position).getInProgressModel().getDesc();
        String date = fetchedData.get(info.position).getInProgressModel().getDate();
        inProgressModel.setTitle(title); inProgressModel.setDesc(description); inProgressModel.setDate(date);
        PanelsModel panelsModel = new PanelsModel(); panelsModel.setInProgressModel(inProgressModel);
        return panelsModel;
    }
}
