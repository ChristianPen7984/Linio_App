package com.app.linio_app.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.app.linio_app.Adapters.PanelsAdapter;
import com.app.linio_app.Models.Complete;
import com.app.linio_app.Models.InProgress;
import com.app.linio_app.Models.PanelsModel;
import com.app.linio_app.Models.Queue;
import com.app.linio_app.R;
import com.app.linio_app.Services.Firebase_Services.PanelCreator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Panels extends Fragment implements View.OnClickListener {

    DatabaseReference database;
    PanelCreator panelCreator;
    PanelsAdapter panelsAdapter;
    ListView panels;
    EditText title;
    FloatingActionButton add;
    FirebaseAuth auth;

    public Panels() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_panels, container, false);

        panels = view.findViewById(R.id.panels_list);
        add = view.findViewById(R.id.add);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        panelCreator = new PanelCreator(database, getContext(), panels);

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

    private void showCreatePanelDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_panel_dialog, null);
        title = view.findViewById(R.id.title);

        builder.setView(view)
                .setTitle("Create Panel")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) { } })
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        String finalTitle = title.getText().toString();
                        PanelsModel panelsModel = new PanelsModel();
                        if (finalTitle.length() > 0) {
                            if (panelCreator.save(panelsModel)) {
                                title.setText("");
                                ArrayList<PanelsModel> fetchedData = panelCreator.retrieve();
                                panelsAdapter = new PanelsAdapter(getContext(), fetchedData);
                                panels.setAdapter(panelsAdapter);
                            }
                        } else Toast.makeText(getContext(),"Cannot create panel: missing title",Toast.LENGTH_LONG).show();
                    }
                }).show();

    }

}
