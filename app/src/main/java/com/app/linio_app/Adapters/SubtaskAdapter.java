package com.app.linio_app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.app.linio_app.Models.Subtask;
import com.app.linio_app.R;

import java.util.ArrayList;

public class SubtaskAdapter extends BaseAdapter {

    Context context;
    ArrayList<Subtask> subtasks;

    public SubtaskAdapter(Context context, ArrayList<Subtask> subtasks) {
        this.context = context;
        this.subtasks = subtasks;
    }

    @Override
    public int getCount() {
        return subtasks.size();
    }

    @Override
    public Object getItem(int position) {
        return subtasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.subtask_placeholder,null);
        CheckBox subtask = view.findViewById(R.id.subtask);
        subtask.setText(subtasks.get(position).getTitle());
        return view;
    }
}
