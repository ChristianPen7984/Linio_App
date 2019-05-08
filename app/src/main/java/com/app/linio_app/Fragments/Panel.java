package com.app.linio_app.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.linio_app.Adapters.PanelBoardsPagerAdapter;
import com.app.linio_app.R;

public class Panel extends Fragment {

    ViewPager panel_boards;

    public Panel() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_panel, container, false);

        panel_boards = view.findViewById(R.id.panel_boards);

        panel_boards.setAdapter(new PanelBoardsPagerAdapter(getChildFragmentManager(),getPanelContext()));

        return view;
    }

    private String getPanelContext() {
        final Bundle bundle = this.getArguments();
        String title = "";
        if (bundle != null) title = bundle.getString("panel");
        return title;
    }
}
