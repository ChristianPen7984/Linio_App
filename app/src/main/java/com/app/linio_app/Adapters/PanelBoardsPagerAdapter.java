package com.app.linio_app.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.app.linio_app.Fragments.CompleteBoard;
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
            case 0:
                Bundle queueBundle = new Bundle();
                QueueBoard queueBoard = new QueueBoard();
                queueBundle.putString("panelTitleQueue",panel);
                queueBoard.setArguments(queueBundle);
                return queueBoard;
            case 1:
                Bundle inprogressBundle = new Bundle();
                InProgressBoard inProgressBoard = new InProgressBoard();
                inprogressBundle.putString("panelTitleInProgress",panel);
                inProgressBoard.setArguments(inprogressBundle);
                return inProgressBoard;
            case 2:
                Bundle completeBundle = new Bundle();
                CompleteBoard completeBoard = new CompleteBoard();
                completeBundle.putString("panelTitleComplete",panel);
                completeBoard.setArguments(completeBundle);
                return completeBoard;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
