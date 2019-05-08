package com.app.linio_app.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.linio_app.Models.PanelsModel;
import com.app.linio_app.R;

public class Task extends Fragment {

    PanelsModel panelsModel;

    public Task() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        panelsModel = getPanelsModel();

        return view;
    }

    private PanelsModel getPanelsModel() {
        final Bundle bundle = this.getArguments();
        PanelsModel panelsModel = new PanelsModel();
        if (bundle != null) panelsModel = bundle.getParcelable("task");
        return panelsModel;
    }

}
