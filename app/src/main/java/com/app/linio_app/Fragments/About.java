package com.app.linio_app.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.linio_app.R;

public class About extends Fragment implements View.OnClickListener {

    TextView gmail;
    TextView repo;

    public About() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        gmail = view.findViewById(R.id.mailLink);
        repo = view.findViewById(R.id.projectLink);
        gmail.setOnClickListener(this);
        repo.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mailLink:
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,new String[] { gmail.getText().toString() });
                startActivity(emailIntent);
                break;
            case R.id.projectLink:

                break;
        }
    }
}
