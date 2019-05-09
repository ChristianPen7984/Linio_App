package com.app.linio_app.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class PanelsModel implements Parcelable {

    private String title;
    private String created;
    private String imageUrl;
    private QueueModel queue_board;
    private InProgressModel in_progress_board;
    private CompleteModel complete_board;

    public PanelsModel() { }


    protected PanelsModel(Parcel in) {
        title = in.readString();
    }

    public static final Creator<PanelsModel> CREATOR = new Creator<PanelsModel>() {
        @Override
        public PanelsModel createFromParcel(Parcel in) {
            return new PanelsModel(in);
        }

        @Override
        public PanelsModel[] newArray(int size) {
            return new PanelsModel[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public QueueModel getQueue_board() {
        return queue_board;
    }

    public void setQueue_board(QueueModel queue_board) {
        this.queue_board = queue_board;
    }

    public InProgressModel getInProgressModel() {
        return in_progress_board;
    }

    public void setInProgressModel(InProgressModel inProgressModel) {
        this.in_progress_board = inProgressModel;
    }

    public CompleteModel getCompleteModel() {
        return complete_board;
    }

    public void setCompleteModel(CompleteModel completeModel) {
        this.complete_board = completeModel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
    }
}
