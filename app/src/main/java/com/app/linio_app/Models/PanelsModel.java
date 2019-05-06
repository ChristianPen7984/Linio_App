package com.app.linio_app.Models;

public class PanelsModel {

    private String title;
    private QueueModel queue_board;
    private InProgressModel inProgressModel;
    private CompleteModel completeModel;

    public PanelsModel() { }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public QueueModel getQueue_board() {
        return queue_board;
    }

    public void setQueue_board(QueueModel queue_board) {
        this.queue_board = queue_board;
    }

    public InProgressModel getInProgressModel() {
        return inProgressModel;
    }

    public void setInProgressModel(InProgressModel inProgressModel) {
        this.inProgressModel = inProgressModel;
    }

    public CompleteModel getCompleteModel() {
        return completeModel;
    }

    public void setCompleteModel(CompleteModel completeModel) {
        this.completeModel = completeModel;
    }
}
