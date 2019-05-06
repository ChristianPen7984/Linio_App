package com.app.linio_app.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.app.linio_app.Fragments.InProgressBoard;
import com.app.linio_app.Fragments.QueueBoard;

public class PanelBoardsPagerAdapter extends FragmentPagerAdapter {

    public PanelBoardsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0: return new QueueBoard();
            case 1: return new InProgressBoard();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
