package com.bibinet.biunion.project.event;

import com.bibinet.biunion.project.dao.MediaVideoData;

public class MediaVideoStudyEvent {
    MediaVideoData musicData;

    public MediaVideoStudyEvent(MediaVideoData musicData) {
        this.musicData = musicData;
    }

    public MediaVideoData getVideoData() {
        return musicData;
    }

    public void setVideoData(MediaVideoData musicData) {
        this.musicData = musicData;
    }
}
