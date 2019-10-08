package com.bibinet.biunion.project.models;

public class MediaLiveAddModel extends BaseModel {

    private MediaLiveRecordItemModel appLive;
    private LiveUser user;

    public LiveUser getUser() {
        return user;
    }

    public void setUser(LiveUser user) {
        this.user = user;
    }

    public MediaLiveRecordItemModel getAppLive() {
        return appLive;
    }

    public void setAppLive(MediaLiveRecordItemModel appLive) {
        this.appLive = appLive;
    }
}
