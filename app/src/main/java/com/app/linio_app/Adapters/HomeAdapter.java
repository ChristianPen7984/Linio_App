package com.app.linio_app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.linio_app.Models.HomeModel;
import com.app.linio_app.R;

import java.util.ArrayList;

public class HomeAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<HomeModel> homeModels;

    public HomeAdapter(Context context, ArrayList<HomeModel> homeModels) {
        this.context = context;
        this.homeModels = homeModels;
    }

    @Override
    public int getCount() {
        return homeModels.size();
    }

    @Override
    public Object getItem(int position) {
        return homeModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = LayoutInflater.from(context).inflate(R.layout.home_placeholder,null);
        TextView event = view.findViewById(R.id.event);
        event.setText(homeModels.get(position).getEvent());
        return view;
    }
}
