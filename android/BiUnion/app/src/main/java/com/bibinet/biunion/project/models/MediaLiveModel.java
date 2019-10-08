/**
 * Copyright 2017 bejson.com
 */
package com.bibinet.biunion.project.models;


import java.util.List;

public class MediaLiveModel extends PageModel {
    private List<MediaLiveItemModel> superJudgeCourseList;

    public List<MediaLiveItemModel> getSuperJudgeCourseList() {
        return superJudgeCourseList;
    }

    public void setSuperJudgeCourseList(List<MediaLiveItemModel> superJudgeCourseList) {
        this.superJudgeCourseList = superJudgeCourseList;
    }
}