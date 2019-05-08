package com.app.linio_app.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

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

public class QueueBoard extends Fragment implements View.OnClickListener, View.OnCreateContextMenuListener {

    ListView queues;
    ImageButton add;

    EditText in_title, in_desc;

    TasksServices tasksServices;
    BoardSwapper boardSwapper;

    QueueModel queueModel;
    QueueAdapter queueAdapter;

    InProgressModel inProgressModel;

    FirebaseAuth auth;
    DatabaseReference database;

    public QueueBoard() { }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_queue, container, false);

        queues = (ListView)view.findViewById(R.id.queues);
        add = view.findViewById(R.id.add);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        queueModel = new QueueModel();
        inProgressModel = new InProgressModel();
        tasksServices = new TasksServices(database,getContext(),queues,getPanelContext(),"queue");
        boardSwapper = new BoardSwapper(database,getContext());

        add.setOnClickListener(this);
        registerForContextMenu(queues);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                showCreatePanelDialog();
                break;
        }
    }

    private String getPanelContext() {
        final Bundle bundle = this.getArguments();
        String title = "";
        if (bundle != null) title = bundle.getString("panelTitleQueue");
        return title;
    }

    private void showCreatePanelDialog() {

        final String panel = getPanelContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View createDialog = inflater.inflate(R.layout.add_board_dialog, null);
        in_title = createDialog.findViewById(R.id.title);
        in_desc = createDialog.findViewById(R.id.description);

        builder.setView(createDialog)
                .setTitle("Create Task")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) { } })
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        String finalTitle = in_title.getText().toString();
                        String finalDesc = in_desc.getText().toString();
                        queueModel.setTitle(finalTitle); queueModel.setDesc(finalDesc);
                        PanelsModel panelsModel = new PanelsModel(); panelsModel.setQueue_board(queueModel);
                        if (tasksServices.save(panelsModel,panel,finalTitle)) {
                            ArrayList<PanelsModel> fetchedData = tasksServices.retrieve(panel);
                            queueAdapter = new QueueAdapter(getContext(), fetchedData);
                            queues.setAdapter(queueAdapter);
                        }
                    }
                }).show();

    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view,
                                    ContextMenu.ContextMenuInfo contextMenuInfo) {
        super.onCreateContextMenu(contextMenu, view, contextMenuInfo);
        MenuInflater inflater = new MenuInflater(getContext());
        inflater.inflate(R.menu.task_actions,contextMenu);
        contextMenu.setHeaderTitle("Move to:");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final String panel = getPanelContext();
        ArrayList<PanelsModel> fetchedData = tasksServices.retrieve(panel);
        switch (item.getItemId()) {
            case R.id.moveInProgress:
                PanelsModel panelsModelToInProgress = setData(fetchedData,info);
                boardSwapper.moveFromQueueToInProgress(panelsModelToInProgress,fetchedData.get(info.position).getQueue_board(),panel,
                        fetchedData.get(info.position).getQueue_board().getTitle());
                return true;
            case R.id.moveComplete:
                PanelsModel panelsModelToComplete = setData(fetchedData,info);
                boardSwapper.moveFromQueueToComplete(panelsModelToComplete,fetchedData.get(info.position).getQueue_board(),panel,
                        fetchedData.get(info.position).getQueue_board().getTitle());
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private PanelsModel setData(ArrayList<PanelsModel> fetchedData,
                                AdapterView.AdapterContextMenuInfo info) {
        String title = fetchedData.get(info.position).getQueue_board().getTitle();
        String description = fetchedData.get(info.position).getQueue_board().getDesc();
        queueModel.setTitle(title); queueModel.setDesc(description);
        PanelsModel panelsModel = new PanelsModel(); panelsModel.setQueue_board(queueModel);
        return panelsModel;
    }

}
