/**
 * Copyright 2017 bejson.com
 */
package com.bibinet.biunion.project.models;


import java.util.LinkedList;
import java.util.List;

public class MediaLiveCommentModel extends BaseModel {
    private LinkedList<MediaLiveMsgItemModel> appComments;
    MediaLiveMsgItemModel appComment;

    public LinkedList<MediaLiveMsgItemModel> getAppComments() {
        return appComments;
    }

    public void setAppComments(LinkedList<MediaLiveMsgItemModel> appComments) {
        this.appComments = appComments;
    }

    public MediaLiveMsgItemModel getAppComment() {
        return appComment;
    }

    public void setAppComment(MediaLiveMsgItemModel appComment) {
        this.appComment = appComment;
    }

}