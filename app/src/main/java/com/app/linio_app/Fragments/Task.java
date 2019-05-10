package com.app.linio_app.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.linio_app.Adapters.QueueAdapter;
import com.app.linio_app.Adapters.SubtaskAdapter;
import com.app.linio_app.Models.PanelsModel;
import com.app.linio_app.Models.Subtask;
import com.app.linio_app.R;
import com.app.linio_app.Services.Firebase_Services.TaskRemover;
import com.app.linio_app.Services.Firebase_Services.TaskService;
import com.app.linio_app.Services.Firebase_Services.TasksServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class Task extends Fragment implements View.OnClickListener {

    PanelsModel panelsModel;
    String panel;

    EditText task_title;
    ImageButton remove;
    EditText description;
    Button dueDate;
    ListView taskList;

    ArrayList<String> taskData;

    DatePicker datePicker;

    TaskService taskService;


    FirebaseAuth auth;
    DatabaseReference database;

    TaskRemover taskRemover;

    public Task() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        panelsModel = getPanelsModel();
        panel = getPanelContext();

        task_title = view.findViewById(R.id.task_title);
        remove = view.findViewById(R.id.remove);
        description = view.findViewById(R.id.description);
        dueDate = view.findViewById(R.id.dueDate);
        taskList = view.findViewById(R.id.taskList);

        initSubTaskList(taskList);

        dueDate.setOnClickListener(this);
        remove.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        initializeTaskServices();

        taskData = checkDataModelReceived(panelsModel);

        if (taskData != null) {
            task_title.setText(taskData.get(0));
            description.setText(taskData.get(1));
            dueDate.setText(taskData.get(2));
        }

        return view;
    }

    private PanelsModel getPanelsModel() {
        final Bundle bundle = this.getArguments();
        PanelsModel panelsModel = new PanelsModel();
        if (bundle != null) panelsModel = bundle.getParcelable("task");
        return panelsModel;
    }

    private String getPanelContext() {
        final Bundle bundle = this.getArguments();
        String panel = "";
        if (bundle != null) panel = bundle.getString("panel");
        return panel;
    }

    private ArrayList<String> checkDataModelReceived(PanelsModel panelsModel) {
        String title = "";
        String description = "";
        String date = "";
        ArrayList<String> data = new ArrayList<>();
        if (panelsModel.getCompleteModel() != null) {
            title = panelsModel.getCompleteModel().getTitle();
            description = panelsModel.getCompleteModel().getDesc();
            date = panelsModel.getCompleteModel().getDate();
        }
        else if (panelsModel.getInProgressModel() != null) {
            title = panelsModel.getInProgressModel().getTitle();
            description = panelsModel.getInProgressModel().getDesc();
            date = panelsModel.getInProgressModel().getDate();
        }
        else if (panelsModel.getQueue_board() != null) {
            title = panelsModel.getQueue_board().getTitle();
            description = panelsModel.getQueue_board().getDesc();
            date = panelsModel.getQueue_board().getDate();
        }
        if (title != null) {
            data.add(title);
            data.add(description);
            data.add(date);
        }
        return data;
    }

    private TaskService initializeTaskServices() {
        if (panelsModel.getCompleteModel() != null) {
            taskService = new TaskService(database,getContext(),panel,"complete");
        }
        else if (panelsModel.getInProgressModel() != null) {
            taskService = new TaskService(database,getContext(),panel,"inprogress");
        }
        else if (panelsModel.getQueue_board() != null) {
            taskService = new TaskService(database,getContext(),panel,"queue");
        }
        return taskService;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dueDate:
                showDatePicker();
                break;
            case R.id.remove:
                showRemovalWarning();
                break;
        }
    }

    private void showDatePicker() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dueDateDialog = inflater.inflate(R.layout.date_picker, null);
        datePicker = dueDateDialog.findViewById(R.id.datePicker);
        builder.setView(dueDateDialog)
                .setTitle("Set Due Date")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) { } })
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        String finalDate = (datePicker.getMonth() + 1) + "/" +
                                datePicker.getDayOfMonth() + "/" + datePicker.getYear();
                        if (panelsModel.getQueue_board() != null) {
                            panelsModel.getQueue_board().setDate(finalDate);
                            taskService.save(panelsModel,panel,panelsModel.getQueue_board().getTitle());
                            dueDate.setText(panelsModel.getQueue_board().getDate());
                        }
                        else if (panelsModel.getInProgressModel() != null) {
                            panelsModel.getInProgressModel().setDate(finalDate);
                            taskService.save(panelsModel,panel,panelsModel.getInProgressModel().getTitle());
                            dueDate.setText(panelsModel.getInProgressModel().getDate());
                        }
                        else if (panelsModel.getCompleteModel() != null) {
                            panelsModel.getCompleteModel().setDate(finalDate);
                            taskService.save(panelsModel,panel,panelsModel.getCompleteModel().getTitle());
                            dueDate.setText(panelsModel.getCompleteModel().getDate());
                        }
                    }
                }).show();
    }

    private Boolean checkDataChanged() {
        boolean changed = false;
        if (panelsModel.getQueue_board() != null) {
            if ((!panelsModel.getQueue_board().getTitle().equals(task_title.getText().toString()))
            || (!panelsModel.getQueue_board().getDesc().equals(description.getText().toString()))) {
                changed = true;
            }
        }
        else if (panelsModel.getInProgressModel() != null) {
            if ((!panelsModel.getInProgressModel().getTitle().equals(task_title.getText().toString()))
                    || (!panelsModel.getInProgressModel().getDesc().equals(description.getText().toString()))) {
                changed = true;
            }
        }
        else if (panelsModel.getCompleteModel() != null) {
            if ((!panelsModel.getCompleteModel().getTitle().equals(task_title.getText().toString()))
            || (!panelsModel.getCompleteModel().getDesc().equals(description.getText().toString())) ) {
                changed = true;
            }
        }
        return changed;
    }

    private void showRemovalWarning() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View warning = inflater.inflate(R.layout.removal_warning, null);
        TextView warningMessage = warning.findViewById(R.id.warningMessage);
        if (panelsModel.getQueue_board() != null) { String message = "You are about to remove:  " + panelsModel.getQueue_board().getTitle(); warningMessage.setText(message); }
        else if (panelsModel.getInProgressModel() != null) { String message = "You are about to remove:  " + panelsModel.getInProgressModel().getTitle(); warningMessage.setText(message); }
        else if (panelsModel.getCompleteModel() != null) { String message = "You are about to remove:  " + panelsModel.getCompleteModel().getTitle(); warningMessage.setText(message); }
        builder.setView(warning)
        .setTitle("WARNING!")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) { } })
                .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        try {
                            if (panelsModel.getQueue_board() != null) {
                                taskRemover = new TaskRemover(database, getContext(), "queue");
                                taskRemover.remove(panelsModel, getPanelContext(), panelsModel.getQueue_board().getTitle());
                            } else if (panelsModel.getInProgressModel() != null) {
                                taskRemover = new TaskRemover(database, getContext(), "inprogress");
                                taskRemover.remove(panelsModel, getPanelContext(), panelsModel.getInProgressModel().getTitle());
                            } else if (panelsModel.getCompleteModel() != null) {
                                taskRemover = new TaskRemover(database, getContext(), "complete");
                                taskRemover.remove(panelsModel, getPanelContext(), panelsModel.getCompleteModel().getTitle());
                            }
                        } catch (DatabaseException e) { }
                    }
                }).show();
    }

    private void initSubTaskList(ListView listView) {
        ArrayList<Subtask> homeModels = new ArrayList<>();
        homeModels.add(new Subtask("Complete Subtask 1"));
        homeModels.add(new Subtask("Complete Subtask 2"));
        listView.setAdapter(new SubtaskAdapter(getContext(),homeModels));
    }

    @Override
    public void onDestroy() {
        if (checkDataChanged()) {
            if (panelsModel.getQueue_board() != null) {
                taskService.remove(panelsModel,panel,panelsModel.getQueue_board().getTitle());
                panelsModel.getQueue_board().setTitle(task_title.getText().toString());
                panelsModel.getQueue_board().setDesc(description.getText().toString());
                taskService.save(panelsModel, panel, panelsModel.getQueue_board().getTitle());
            } else if (panelsModel.getInProgressModel() != null) {
                taskService.remove(panelsModel,panel,panelsModel.getInProgressModel().getTitle());
                panelsModel.getInProgressModel().setTitle(task_title.getText().toString());
                panelsModel.getInProgressModel().setDesc(description.getText().toString());
                taskService.save(panelsModel, panel, panelsModel.getInProgressModel().getTitle());
            } else if (panelsModel.getCompleteModel() != null) {
                taskService.remove(panelsModel,panel,panelsModel.getCompleteModel().getTitle());
                panelsModel.getCompleteModel().setTitle(task_title.getText().toString());
                panelsModel.getCompleteModel().setDesc(description.getText().toString());
                taskService.save(panelsModel, panel, panelsModel.getCompleteModel().getTitle());
            }
        }
        super.onDestroy();
    }
}
