package com.bibinet.biunion.project.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andview.refreshview.utils.Utils;
import com.bibinet.biunion.R;
import com.bibinet.biunion.project.bean.BookmarkBean;
import com.bibinet.biunion.project.utils.Common;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2018/4/7/007.
 */

public class MagazineBookmarkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<BookmarkBean> bookmarkBeanList;

    public MagazineBookmarkAdapter(Context context, List<BookmarkBean> bookmarkBeanList) {
        this.context = context;
        this.bookmarkBeanList = bookmarkBeanList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_magazine_bookmark, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        BookmarkBean bookmarkBean = bookmarkBeanList.get(position);
        viewHolder.mainTV.setText(bookmarkBean.getMagazineChildModel().getMagazineName());
        viewHolder.dateTV.setText(getAddTime(bookmarkBean.getDate()));
        viewHolder.pageTV.setText("第" + bookmarkBean.getPageNum() + "页");
    }

    @Override
    public int getItemCount() {
        return Common.isListEmpty(bookmarkBeanList) ? 0 : bookmarkBeanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ada_magazine_bookmark_main)
        TextView mainTV;
        @BindView(R.id.ada_magazine_bookmark_page)
        TextView pageTV;
        @BindView(R.id.ada_magazine_bookmark_date)
        TextView dateTV;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public String getAddTime(long lastRefreshTime) {
        // 获取当前时间
        Calendar mCalendar = Calendar.getInstance();
        long refreshTime = mCalendar.getTimeInMillis();
        long howLong = refreshTime - lastRefreshTime;
        int minutes = (int) (howLong / 1000 / 60);
        String refreshTimeText = null;
        Resources resources = context.getResources();
        if (minutes < 1) {
            refreshTimeText = resources
                    .getString(R.string.str_bookmark_date_current);
        } else if (minutes < 60) {
            refreshTimeText = resources
                    .getString(R.string.str_bookmark_date_minute);
            refreshTimeText = Utils.format(refreshTimeText, minutes);
        } else if (minutes < 60 * 24) {
            refreshTimeText = resources
                    .getString(R.string.str_bookmark_date_hour);
            refreshTimeText = Utils.format(refreshTimeText, minutes / 60);
        } else {
            refreshTimeText = resources
                    .getString(R.string.str_bookmark_date_day);
            refreshTimeText = Utils.format(refreshTimeText, minutes / 60 / 24);
        }
        return refreshTimeText;
    }
}
