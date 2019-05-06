package com.app.linio_app.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.app.linio_app.Fragments.InProgressBoard;
import com.app.linio_app.Fragments.QueueBoard;

public class PanelBoardsPagerAdapter extends FragmentPagerAdapter {

    private String panel;

    public PanelBoardsPagerAdapter(FragmentManager fm,String panel) {
        super(fm);
        this.panel = panel;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0: {
                Bundle bundle = new Bundle();
                QueueBoard queueBoard = new QueueBoard();
                bundle.putString("panelTitle",panel);
                queueBoard.setArguments(bundle);
                return queueBoard;
            }
            case 1: return new InProgressBoard();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
