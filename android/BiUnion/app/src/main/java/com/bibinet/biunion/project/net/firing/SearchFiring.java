package com.bibinet.biunion.project.net.firing;

import android.content.Context;

import com.bibinet.biunion.project.models.HotWordsModel;
import com.bibinet.biunion.project.models.SearchResultModel;
import com.bibinet.biunion.project.net.MyRetrofitResponseCallback;
import com.bibinet.biunion.project.net.base.BaseFiring;
import com.bibinet.biunion.project.net.emit.SearchEmit;
import com.bibinet.biunion.project.net.emit.SearchResultEmit;
import com.bibinet.biunion.project.net.ready.SearchReady;

/**
 * Created by bibinet on 2017-6-26.
 */

public class SearchFiring extends BaseFiring {
    private SearchReady searchReady;
    private SearchEmit searchEmit;
    private SearchResultEmit searchResultEmit;

    public SearchFiring(SearchEmit searchEmit) {
        super(searchEmit);
        this.searchEmit = searchEmit;
        this.searchReady = new SearchReady();
    }

    public SearchFiring(SearchResultEmit searchResultEmit) {
        super(searchResultEmit);
        this.searchResultEmit = searchResultEmit;
        this.searchReady = new SearchReady();
    }

    public void getHotWords() {
        searchReady.getHotWords(new MyRetrofitResponseCallback<HotWordsModel>() {
            @Override
            protected void onRequestFail(String resMessage) {
                searchEmit.onGetHotWordsFail(resMessage);
            }

            @Override
            protected void onRequestSuccess(HotWordsModel model) {
                searchEmit.onGetHotWordsSuccess(model.getItems());
            }
        });
    }

    public void getSearchData(int pageNumb, String content, String type, String area, String time) {
        searchReady.getSearchData(pageNumb, content, type, area, time, new MyRetrofitResponseCallback<SearchResultModel>() {
            @Override
            protected void onRequestFail(String resMessage) {
                searchResultEmit.loadMoreFail(resMessage);
                searchResultEmit.onSearchTotalCountFail(resMessage);
            }

            @Override
            protected void onRequestSuccess(SearchResultModel model) {
                searchResultEmit.loadMoreSuccess(model.getData().getItems());
                searchResultEmit.onSearchCountSuccess(model.getData().getPage().getTotalRows());
            }
        });
    }
}
