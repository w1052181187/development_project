package com.bibinet.biunion.project.net.emit;

import java.util.List;

/**
 * Created by bibinet on 2017-6-26.
 */

public interface SearchEmit {
    void onGetHotWordsSuccess(List<String> items);
    void onGetHotWordsFail(String error);
}
