package com.bibinet.biunion.project.ui.expand;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.net.expand.PageEmit;
import com.bibinet.biunion.project.ui.dialog.WaitDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


/**
 * Created by bibinet on 2017-6-7.
 */

public abstract class PageFragment<T> extends Fragment implements SwipeRefreshLayout.OnRefreshListener, PageEmit<T> {
    private View errorV;
    private FrameLayout mainV;

    private View notDataV;

    private LinearLayoutManager linearLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private List<T> dataList;

    private PageActivityAdapter adapter;

    private boolean isLoadMore = false;

    private int lastvisibleitem = 1;
    private int pageNumb = 1;
    private RecyclerView.OnScrollListener listener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (adapter != null) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastvisibleitem + 1 == adapter.getItemCount()) {
                    if(adapter.getCurrentState() == PageActivityAdapter.LOAD_ACCESS){
                        pageNumb++;
                        nextPageLoad(pageNumb);
                        isLoadMore = true;
                        adapter.changeMoreStatus(PageActivityAdapter.LOADING_MORE);
                    }
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastvisibleitem = linearLayoutManager.findLastVisibleItemPosition();
        }
    };
    private WaitDialog waitDialog;

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.from(getActivity()).inflate(R.layout.fragment_page, null, false);
        mainV = (FrameLayout) v.findViewById(R.id.fra_page_main);
        errorV = v.findViewById(R.id.fra_page_error);

        View view = LayoutInflater.from(getActivity()).inflate(getLayoutId(), null, false);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(getSwipeRefreshLayoutId());
        recyclerView = (RecyclerView) view.findViewById(getRecyclerViewId());
        notDataV = view.findViewById(getNotDataViewId());
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(listener);
        swipeRefreshLayout.setOnRefreshListener(this);
        mainV.addView(view);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        waitDialog = new WaitDialog(getActivity());
        ButterKnife.bind(this, view);

        onChildCreate(view, savedInstanceState);

        dataList = new ArrayList<T>();
        adapter = getPageAdapter(dataList);
        recyclerView.setAdapter(adapter);
        adapter.changeMoreStatus(PageActivityAdapter.LOADING_INIT);

        initRefresh();
    }

    protected void initRefresh(){
        adapter.changeMoreStatus(PageActivityAdapter.LOADING_MORE);
        pageNumb = 1;
        initLoad(pageNumb);
        waitDialog.open();
        isLoadMore = false;
    }

    protected abstract int getNotDataViewId();

    protected abstract PageActivityAdapter getPageAdapter(List<T> dataList);

    protected abstract void initLoad(int pageNumb);

    protected abstract void onChildCreate(View view, Bundle savedInstanceState);

    protected abstract int getRecyclerViewId();

    protected abstract int getSwipeRefreshLayoutId();

    protected abstract int getLayoutId();

    protected abstract void nextPageLoad(int pageNumb);

    //刷新
    @Override
    public void onRefresh() {
        adapter.changeMoreStatus(PageActivityAdapter.LOADING_INIT);
        pageNumb = 1;
        refreshLoad(pageNumb);
        isLoadMore = false;
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void loadMoreSuccess(List<T> newDataList) {
        waitDialog.close();
        errorV.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
        if (isLoadMore) {
            dataList.addAll(newDataList);
            if (newDataList.size() <= 0) {
                Toast.makeText(getActivity(), "没有更多数据", Toast.LENGTH_SHORT).show();
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

            int size = dataList.size();
            if(size<1){
                adapter.changeMoreStatus(PageActivityAdapter.LOAD_NODATA);
            }else{
                adapter.changeMoreStatus(PageActivityAdapter.LOAD_ACCESS);
            }
        }
    }

    @Override
    public void loadMoreFail(String s) {
        waitDialog.close();
        Log.i("TAG","加载数据错误----------------------------"+s);
        swipeRefreshLayout.setRefreshing(false);
        adapter.changeMoreStatus(PageActivityAdapter.LOAD_NODATA);
        try{
            Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
        }catch (Exception e){

        }
        //判断列表数量 为0展示错误图
//        if(dataList.size()<=0){
//            errorV.setVisibility(View.VISIBLE);
//        }else{
//            errorV.setVisibility(View.GONE);
//        }
    }

    protected abstract void refreshLoad(int pageNumb);

    protected List<T> getDataList(){
        return dataList;
    }

    protected PageActivityAdapter getAdapter(){
        return adapter;
    }
}
