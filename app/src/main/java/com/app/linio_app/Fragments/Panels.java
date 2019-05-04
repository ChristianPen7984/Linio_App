package com.app.linio_app.Fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.linio_app.Dialogs.CreatePanelDialog;
import com.app.linio_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Panels extends Fragment implements View.OnClickListener {

    FloatingActionButton add;

    FirebaseAuth auth;
    DatabaseReference database;

    public Panels() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_panels, container, false);

        add = view.findViewById(R.id.add);

        add.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

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
        CreatePanelDialog createPanelDialog = new CreatePanelDialog();
        if (getFragmentManager() != null) {
            createPanelDialog.show(getFragmentManager(),"dialog");
        }
    }
}
