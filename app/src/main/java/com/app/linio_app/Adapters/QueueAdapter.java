package com.app.linio_app.Adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.app.linio_app.Fragments.Panel;
import com.app.linio_app.Fragments.QueueBoard;
import com.app.linio_app.Fragments.Task;
import com.app.linio_app.Models.PanelsModel;
import com.app.linio_app.Models.QueueModel;
import com.app.linio_app.R;

import java.util.ArrayList;

public class QueueAdapter extends BaseAdapter implements View.OnCreateContextMenuListener {

    private Context context;
    private ArrayList<PanelsModel> panelsModels;
    private String panel;

    public QueueAdapter(Context context, ArrayList<PanelsModel> panelsModels, String panel) {
        this.context = context;
        this.panelsModels = panelsModels;
        this.panel = panel;
    }

    @Override
    public int getCount() {
        return panelsModels.size();
    }

    @Override
    public Object getItem(int position) {
        return panelsModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View view, final ViewGroup parent) {

        view = LayoutInflater.from(context).inflate(R.layout.task_placeholder,null);
        TextView title = view.findViewById(R.id.task_title);

        final PanelsModel panelsModel = (PanelsModel)this.getItem(position);
        title.setText(panelsModel.getQueue_board().getTitle());

        view.setOnCreateContextMenuListener(this);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putParcelable("task", panelsModel);
                bundle.putString("panel",panel);
                Task task = new Task();
                task.setArguments(bundle);
                ft.replace(R.id.fragmentContainer, task).addToBackStack(null).commit();
            }
        });

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) { }
}
