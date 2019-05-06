package com.app.linio_app.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.app.linio_app.Adapters.PanelsAdapter;
import com.app.linio_app.Adapters.QueueAdapter;
import com.app.linio_app.Models.PanelsModel;
import com.app.linio_app.Models.QueueModel;
import com.app.linio_app.R;
import com.app.linio_app.Services.Firebase_Services.QueueCreator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class QueueBoard extends Fragment implements View.OnClickListener {

    ListView queues;
    ImageButton add;

    EditText in_title, in_desc;

    QueueCreator queueCreator;

    QueueModel queueModel;
    QueueAdapter queueAdapter;

    FirebaseAuth auth;
    DatabaseReference database;

    public QueueBoard() { }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_queue, container, false);

        queues = view.findViewById(R.id.queues);
        add = view.findViewById(R.id.add);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        queueModel = new QueueModel();
        queueCreator = new QueueCreator(database,getContext(),queues,getPanelContext());

        add.setOnClickListener(this);

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
        if (bundle != null) title = bundle.getString("panel");
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
                        if (queueCreator.save(panelsModel,panel,finalTitle)) {
                            ArrayList<PanelsModel> fetchedData = queueCreator.retrieve(panel);
                            queueAdapter = new QueueAdapter(getContext(), fetchedData);
                            queues.setAdapter(queueAdapter);
                        }
                    }
                }).show();

    }

}
