package com.app.linio_app.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.linio_app.Adapters.PanelPagerAdapter;
import com.app.linio_app.R;

import java.util.ArrayList;

public class Panel extends Fragment {

    ViewPager panel_boards;

    public Panel() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_panel, container, false);

        panel_boards = view.findViewById(R.id.panel_boards);
        ArrayList<String> list = new ArrayList<>();
        list.add("Queue");
        list.add("In-Progress");
        list.add("Complete");
        panel_boards.setAdapter(new PanelPagerAdapter(getContext(),list));

        return view;
    }

}
