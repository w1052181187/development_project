package com.bibinet.biunion.project.models;

public class MediaLiveFinishModel extends BaseModel {

    private String liveDuration;

    private String userNum;
    private boolean isRecorded;//本地

    public String getLiveDuration() {
        return liveDuration;
    }

    public void setLiveDuration(String liveDuration) {
        this.liveDuration = liveDuration;
    }
    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public boolean isRecorded() {
        return isRecorded;
    }

    public void setRecorded(boolean recorded) {
        isRecorded = recorded;
    }
}
