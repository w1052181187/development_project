package com.bibinet.biunion.project.ui.expand;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.listener.OnBottomLoadMoreTime;
import com.andview.refreshview.listener.OnTopRefreshTime;
import com.bibinet.biunion.R;
import com.bibinet.biunion.project.net.expand.PageEmit;
import com.bibinet.biunion.project.ui.base.BaseActivity;
import com.bibinet.biunion.project.ui.custom.RefreshHeaderView;
import com.bibinet.biunion.project.ui.dialog.WaitDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


/**
 * Created by bibinet on 2017-6-7.
 */

public abstract class PageActivity<T> extends TitleActivity implements SwipeRefreshLayout.OnRefreshListener, PageEmit<T>, XRefreshView.XRefreshViewListener, OnTopRefreshTime, OnBottomLoadMoreTime {
    private View errorV;
    private FrameLayout mainV;

    private View notDataV;

    private LinearLayoutManager linearLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private XRefreshView xRefreshView;
    private RecyclerView recyclerView;

    private List<T> dataList;

    private PageActivityAdapter adapter;

    private boolean isLoadMore = false;

    private int lastvisibleitem = 1;
    private int pageNumb = 1;
    private WaitDialog waitDialog;
    private boolean isTop = true;
    private RecyclerView.OnScrollListener listener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (adapter != null) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastvisibleitem + 1 == adapter.getItemCount()) {
                    if (adapter.getCurrentState() == PageActivityAdapter.LOAD_ACCESS) {
                        pageNumb++;
                        nextPageLoad(pageNumb);
                        isLoadMore = true;
                        adapter.changeMoreStatus(PageActivityAdapter.LOADING_MORE);
                    }
                }
            }
            isTop = !recyclerView.canScrollVertically(-1);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastvisibleitem = linearLayoutManager.findLastVisibleItemPosition();
        }
    };

    @Override
    protected View getLayoutView() {
        View parentView = super.getLayoutView();
        mainV = (FrameLayout) parentView.findViewById(R.id.act_page_main);
        errorV = parentView.findViewById(R.id.act_page_error);


        View view = createView(getPageLayoutId());
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(getSwipeRefreshLayoutId());
        xRefreshView = (XRefreshView) view.findViewById(getXRefreshLayoutId());
        recyclerView = (RecyclerView) view.findViewById(getRecyclerViewId());
        notDataV = view.findViewById(getNotDataViewId());
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(listener);
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setOnRefreshListener(this);
        }
        if (xRefreshView != null) {
            xRefreshView.setPullRefreshEnable(true);
            xRefreshView.setMoveForHorizontal(true);
            xRefreshView.setPullLoadEnable(false);
            RefreshHeaderView hv = getHeadView();
            if (hv != null) {
                xRefreshView.setCustomHeaderView(hv);
            }
            xRefreshView.setXRefreshViewListener(this);
            xRefreshView.setOnTopRefreshTime(this);
            xRefreshView.setOnBottomLoadMoreTime(this);
        }
        mainV.addView(view);
        return parentView;
    }

    @Override
    protected final void onTitleCreate(Bundle savedInstanceState) {
        waitDialog = new WaitDialog(this);

        onPageCreate(savedInstanceState);

        dataList = new ArrayList<T>();
        adapter = getPageAdapter(dataList);


        adapter.changeMoreStatus(PageActivityAdapter.LOADING_INIT);

        initRefresh();
    }

    @Override
    protected final int getTitleLayoutId() {
        return R.layout.activity_page;
    }

    protected int getXRefreshLayoutId() {
        return -1;
    }

    protected int getSwipeRefreshLayoutId() {
        return -1;
    }

    protected void initRefresh() {
//        adapter.changeMoreStatus(PageActivityAdapter.LOADING_MORE);
        pageNumb = 1;
        initLoad(pageNumb);
        waitDialog.open();
        isLoadMore = false;
    }

    protected abstract int getNotDataViewId();

    protected abstract PageActivityAdapter getPageAdapter(List<T> dataList);

    protected abstract void initLoad(int pageNumb);

    protected abstract void onPageCreate(Bundle savedInstanceState);

    protected abstract int getRecyclerViewId();

    protected abstract int getPageLayoutId();

    protected abstract void nextPageLoad(int pageNumb);

    //刷新
    @Override
    public void onRefresh() {
        adapter.changeMoreStatus(PageActivityAdapter.LOADING_INIT);
        pageNumb = 1;
        refreshLoad(pageNumb);
        isLoadMore = false;
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void onRefresh(boolean isPullDown) {

    }

    @Override
    public void onLoadMore(boolean isSilence) {

    }

    @Override
    public void onRelease(float direction) {

    }

    @Override
    public void onHeaderMove(double headerMovePercent, int offsetY) {

    }

    @Override
    public void loadMoreSuccess(List<T> newDataList) {
        waitDialog.close();
        errorV.setVisibility(View.GONE);
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
        if (xRefreshView != null && getHeadView() != null) {
            getHeadView().stopRefresh(xRefreshView);
        }
        if (isLoadMore) {
            dataList.addAll(newDataList);
            if (newDataList.size() <= 0) {
                Toast.makeText(this, "没有更多数据", Toast.LENGTH_SHORT).show();
                adapter.changeMoreStatus(PageActivityAdapter.LOAD_NODATA);
            } else {
                adapter.changeMoreStatus(PageActivityAdapter.LOAD_ACCESS);
            }
            adapter.notifyDataSetChanged();
        } else {
            dataList.clear();
            dataList.addAll(newDataList);
            adapter = getPageAdapter(dataList);
            recyclerView.setAdapter(adapter);
            if (dataList.size() <= 0) {
                notDataV.setVisibility(View.VISIBLE);
            } else {
                notDataV.setVisibility(View.GONE);
            }
            int size = dataList.size();
            if (size < 8) {
                adapter.changeMoreStatus(PageActivityAdapter.LOAD_NODATA);
            } else {
                adapter.changeMoreStatus(PageActivityAdapter.LOAD_ACCESS);
            }
        }
    }

    @Override
    public void loadMoreFail(String s) {
        waitDialog.close();
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
        if (xRefreshView != null && getHeadView() != null) {
            getHeadView().stopRefresh(xRefreshView);
        }
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        //判断列表数量 为0展示错误图
        if (dataList.size() <= 0) {
            errorV.setVisibility(View.VISIBLE);
            notDataV.setVisibility(View.GONE);
        } else {
            errorV.setVisibility(View.GONE);
        }
    }


    protected abstract void refreshLoad(int pageNumb);

    protected List<T> getDataList() {
        return dataList;
    }

    protected PageActivityAdapter getAdapter() {
        return adapter;
    }

    public boolean isTop() {
        return isTop;
    }

    protected RefreshHeaderView getHeadView() {
        return null;
    }

    @Override
    public boolean isBottom() {
        return false;
    }
}
