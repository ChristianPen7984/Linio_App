package com.app.linio_app.Models;

import java.util.ArrayList;

public class PanelsModel {

    private String title;
    private ArrayList<Queue> queue_board;
    private ArrayList<InProgress> inprogess_board;
    private ArrayList<Complete> complete_board;

    public PanelsModel() { }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public ArrayList<Queue> getQueue_board() {
        return queue_board;
    }

    public void setQueue_board(ArrayList<Queue> queue_board) {
        this.queue_board = queue_board;
    }

    public ArrayList<InProgress> getInprogess_board() {
        return inprogess_board;
    }

    public void setInprogess_board(ArrayList<InProgress> inprogess_board) {
        this.inprogess_board = inprogess_board;
    }

    public ArrayList<Complete> getComplete_board() {
        return complete_board;
    }

    public void setComplete_board(ArrayList<Complete> complete_board) {
        this.complete_board = complete_board;
    }
}
