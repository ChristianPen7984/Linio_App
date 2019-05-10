package com.app.linio_app.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.linio_app.Adapters.PanelsAdapter;
import com.app.linio_app.Dialogs.CreatePanel;
import com.app.linio_app.Models.PanelsModel;
import com.app.linio_app.R;
import com.app.linio_app.Services.Firebase_Services.PanelCreator;
import com.app.linio_app.Services.Firebase_Services.PanelRemover;
import com.app.linio_app.Services.Firebase_Services.PanelRetriever;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class Panels extends Fragment implements View.OnClickListener,
        AdapterView.OnItemSelectedListener{

    DatabaseReference database;
    PanelCreator panelCreator;
    PanelRemover panelRemover;
    PanelRetriever panelRetriever;
    ListView panels;
    FloatingActionButton add;
    FirebaseAuth auth;

    Spinner filter;
    SearchView search;

    public Panels() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_panels, container, false);

        panels = view.findViewById(R.id.panels_list);
        add = view.findViewById(R.id.add);
        filter = view.findViewById(R.id.filter);
        search = view.findViewById(R.id.search);
        initFilter();
        searchPanels(search);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        panelCreator = new PanelCreator(database, getContext());
        panelRemover = new PanelRemover(database,getContext());
        panelRetriever = new PanelRetriever(database,getContext(),panels);

        add.setOnClickListener(this);
        filter.setOnItemSelectedListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                CreatePanel createPanelDialog = new CreatePanel();
                if (getFragmentManager() != null) {
                    createPanelDialog.show(getFragmentManager(),"dialog");
                }
        }
    }

    private void initFilter() {
        String[] filterOptions = {"Sort by:","Asc","Desc"};
        filter.setAdapter(new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_spinner_dropdown_item,filterOptions));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 1:
                break;
            case 2:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void searchPanels(final SearchView search) {
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                panelRetriever.retrievePanel(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                panelRetriever.retrievePanel(newText);
                return false;
            }
        });
    }

}
