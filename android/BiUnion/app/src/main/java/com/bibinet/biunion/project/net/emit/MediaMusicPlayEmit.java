package com.bibinet.biunion.project.net.emit;

/**
 * Created by bibinet on 2017-6-26.
 */

public interface MediaMusicPlayEmit {
    void onGetMusicFileSuccess(String path);
    void onGetMusicFileFail(String error);
}
