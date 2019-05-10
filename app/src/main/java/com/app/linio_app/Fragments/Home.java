package com.app.linio_app.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.linio_app.Adapters.HomeAdapter;
import com.app.linio_app.Models.HomeModel;
import com.app.linio_app.R;

import java.util.ArrayList;

public class Home extends Fragment {

    ListView listView;

    public Home() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        listView = view.findViewById(R.id.futureEvents);
        ArrayList<HomeModel> homeModels = new ArrayList<>();
        homeModels.add(new HomeModel("Lighter/quicker process utilization for Firebase Storage"));
        homeModels.add(new HomeModel("Implement sub-tasks for individual tasks"));
        homeModels.add(new HomeModel("Overall UI/UX improvements"));
        homeModels.add(new HomeModel("Ability to EDIT pre-existing panels"));
        homeModels.add(new HomeModel("Draggable tasks to other boards"));
        homeModels.add(new HomeModel("Create a more responsive UI"));
        homeModels.add(new HomeModel("Clean-up Firebase Creator Services"));
        listView.setAdapter(new HomeAdapter(getContext(),homeModels));
        return view;
    }

}
