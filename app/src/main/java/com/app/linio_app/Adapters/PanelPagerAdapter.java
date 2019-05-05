package com.app.linio_app.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.linio_app.Models.PanelsModel;
import com.app.linio_app.R;

import java.util.ArrayList;

public class PanelPagerAdapter extends PagerAdapter implements View.OnClickListener {

    private Context context;
    private ArrayList<String> list;

    public PanelPagerAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.panel_placeholder,container,false);

        TextView header = view.findViewById(R.id.panel_header);
        ImageButton add = view.findViewById(R.id.add);

        if (position > 0) add.setVisibility(View.GONE);
        add.setOnClickListener(this);
        header.setText(list.get(position));
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.add_board_dialog, null);
        builder.setView(view).setTitle("Create Task")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) { } })
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }
}
