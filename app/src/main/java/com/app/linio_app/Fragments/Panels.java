package com.app.linio_app.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.app.linio_app.Adapters.PanelsAdapter;
import com.app.linio_app.Dialogs.CreatePanelDialog;
import com.app.linio_app.Models.PanelsModel;
import com.app.linio_app.R;

import com.app.linio_app.Services.Firebase_Services.PanelCreator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Panels extends Fragment implements View.OnClickListener {

    DatabaseReference db;
    PanelCreator panelCreator;
    PanelsAdapter panelsAdapter;
    ListView panels;
    EditText title;
    FirebaseAuth auth;

    public Panels() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_panels, container, false);

        panels = view.findViewById(R.id.panels_list);
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();
        panelCreator = new PanelCreator(db,getContext(),panels);
        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.add);
        fab.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                panels.smoothScrollToPosition(4);
                showCreatePanelDialog();
                break;
        }
    }

    private void showCreatePanelDialog() {
        Dialog d = new Dialog(getContext());
        d.setTitle("Save to Firebase");
        d.setContentView(R.layout.add_panel_dialog);
        title = d.findViewById(R.id.title);
        Button save = d.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String finalTitle = title.getText().toString();
                PanelsModel panelsModel = new PanelsModel();
                panelsModel.setTitle(finalTitle);
                if (finalTitle != null && finalTitle.length() > 0) {
                    if (panelCreator.save(panelsModel)) {
                        title.setText("");
                        ArrayList<PanelsModel> fetchedData = panelCreator.retrieve();
                        panelsAdapter = new PanelsAdapter(getContext(),fetchedData);
                        panels.setAdapter(panelsAdapter);
                        panels.smoothScrollToPosition(fetchedData.size());
                    }
                } else {
                    Toast.makeText(getContext(),"Empty Name",Toast.LENGTH_LONG).show();
                }
            }
        });
        d.show();

    }

}
