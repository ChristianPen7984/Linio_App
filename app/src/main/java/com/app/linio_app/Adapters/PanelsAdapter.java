package com.app.linio_app.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.linio_app.Fragments.Panel;
import com.app.linio_app.Models.PanelsModel;
import com.app.linio_app.R;
import com.app.linio_app.Services.Firebase_Services.PanelRemover;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PanelsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<PanelsModel> panelsModels;
    private DatabaseReference database;
    private PanelRemover panelRemover;

    TextView title;

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
        title = view.findViewById(R.id.title);
        final ImageView logo = view.findViewById(R.id.panel_logo);

        database = FirebaseDatabase.getInstance().getReference();
        panelRemover = new PanelRemover(database,context);

        final PanelsModel panelsModel = (PanelsModel)this.getItem(position);
        if (panelsModel.getTitle() != null) title.setText(panelsModel.getTitle());
        setImage(panelsModel,logo);

        view.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                MenuItem remove = menu.add(Menu.NONE,1,1,"Remove");
                remove.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (panelsModel.getTitle() != null) {
                            panelRemover.remove(panelsModel.getTitle());
                        }
                        return true;
                    }
                });
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("panel",panelsModel.getTitle());
                Panel panels = new Panel();
                panels.setArguments(bundle);
                ft.replace(R.id.fragmentContainer, panels).addToBackStack(null).commit();
            }
        });

        return view;

    }

    private void setImage(PanelsModel panelsModel, ImageView logo) {
        if (panelsModel.getImageUrl() != null) {
            Picasso.with(context)
                    .load(panelsModel.getImageUrl())
                    .fit()
                    .centerCrop()
                    .into(logo);
        } else logo.setImageResource(R.mipmap.ic_launcher_foreground);
    }

}
