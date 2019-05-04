package com.app.linio_app.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.app.linio_app.Models.PanelsModel;
import com.app.linio_app.R;

import java.util.ArrayList;

public class PanelsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<PanelsModel> panelsModels;

    public PanelsAdapter(Context context, ArrayList<PanelsModel> panelsModels) {
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
        view = LayoutInflater.from(context).inflate(R.layout.panels_card_placeholder,null);
        TextView title = view.findViewById(R.id.title);

        final PanelsModel panelsModel = (PanelsModel)this.getItem(position);
        title.setText(panelsModels.get(position).getTitle());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,panelsModel.getTitle(),Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}
