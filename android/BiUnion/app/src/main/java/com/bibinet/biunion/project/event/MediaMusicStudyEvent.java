package com.bibinet.biunion.project.event;

import com.bibinet.biunion.project.dao.MediaMusicData;

public class MediaMusicStudyEvent {
    MediaMusicData musicData;

    public MediaMusicStudyEvent(MediaMusicData musicData) {
        this.musicData = musicData;
    }

    public MediaMusicData getMusicData() {
        return musicData;
    }

    public void setMusicData(MediaMusicData musicData) {
        this.musicData = musicData;
    }
}
