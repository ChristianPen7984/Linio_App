package com.app.linio_app.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.linio_app.Models.PanelsModel;
import com.app.linio_app.R;

import java.util.ArrayList;

public class QueueAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<PanelsModel> panelsModels;

    public QueueAdapter(Context context, ArrayList<PanelsModel> panelsModels) {
        this.context = context;
        this.panelsModels = panelsModels;
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
    public View getView(int position, View view, ViewGroup parent) {

        view = LayoutInflater.from(context).inflate(R.layout.task_placeholder,null);
        TextView title = view.findViewById(R.id.task_title);

        PanelsModel panelsModel = (PanelsModel)this.getItem(position);
        title.setText(panelsModel.getQueue_board().getTitle());

        return view;
    }
}
