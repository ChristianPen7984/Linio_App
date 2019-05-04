package com.app.linio_app.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.app.linio_app.Adapters.PanelsAdapter;
import com.app.linio_app.Models.PanelsModel;
import com.app.linio_app.R;
import com.app.linio_app.Services.Firebase_Services.PanelCreator;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class CreatePanelDialog extends AppCompatDialogFragment {
    private EditText title;
    private PanelCreator panelCreator;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_panel_dialog, null);
        title = view.findViewById(R.id.title);

        builder.setView(view)
                .setTitle("Create Panel")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }
}
