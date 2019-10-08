package com.bibinet.biunion.project.ui.manager;

public interface RxjavaDownloadListener {
    void onStartDownload();

    void onProgress(long totalSize, long progress);

    void onFinishDownload();

    void onFail(String errorInfo);
}
