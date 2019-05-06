package com.app.linio_app.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.app.linio_app.Adapters.PanelBoardsPagerAdapter;
import com.app.linio_app.Models.PanelsModel;
import com.app.linio_app.Models.QueueModel;
import com.app.linio_app.R;
import com.app.linio_app.Services.Firebase_Services.QueueCreator;

public class Panel extends Fragment implements View.OnClickListener {

    ViewPager panel_boards;

    Button add;
    EditText in_title;
    EditText in_desc;

    public Panel() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_panel, container, false);

        panel_boards = view.findViewById(R.id.panel_boards);
        add = view.findViewById(R.id.add);
        add.setOnClickListener(this);

        panel_boards.setAdapter(new PanelBoardsPagerAdapter(getChildFragmentManager()));

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                showCreateTaskDialog();
                break;
        }
    }
    private void showCreateTaskDialog() {
        final String panel = getPanelContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View createDialog = inflater.inflate(R.layout.add_board_dialog, null);
        in_title = createDialog.findViewById(R.id.title);
        in_desc = createDialog.findViewById(R.id.description);


        builder.setView(createDialog)
                .setTitle("Create Task")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) { }
                })
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String finalTitle = in_title.getText().toString();
                        String finalDesc = in_desc.getText().toString();
                        PanelsModel panelsModel = new PanelsModel();
                        if (finalTitle.length() > 0 && finalDesc.length() > 0) {
                            QueueModel queueModel = new QueueModel();
                            queueModel.setTitle(finalTitle);
                            queueModel.setDesc(finalDesc);
                            panelsModel.setQueue_board(queueModel);
                        }
                    }
                }).show();
    }

    private String getPanelContext() {
        final Bundle bundle = this.getArguments();
        String title = "";
        if (bundle != null) title = bundle.getString("panel");
        return title;
    }
}
