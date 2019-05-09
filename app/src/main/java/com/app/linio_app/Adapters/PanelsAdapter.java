package com.app.linio_app.Adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.linio_app.Fragments.Panel;
import com.app.linio_app.Fragments.QueueBoard;
import com.app.linio_app.Models.ImageModel;
import com.app.linio_app.Models.PanelsModel;
import com.app.linio_app.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
        final ImageView logo = view.findViewById(R.id.panel_logo);

        final PanelsModel panelsModel = (PanelsModel)this.getItem(position);
        title.setText(panelsModel.getTitle());

        Picasso.with(context)
                .load(panelsModel.getImageUrl())
                .fit()
                .centerCrop()
                .into(logo);

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

}
