package com.bibinet.biunion.project.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.bean.subscribe.PrivateInfoDesignBean;

import java.util.List;

/**
 * Created by Wh on 2017-7-6.
 */

public class PrivateInfoDesignSelectDialog extends Dialog implements AdapterView.OnItemClickListener, View.OnClickListener {

    private ListView mainLV;
    private Context context;

    private List<PrivateInfoDesignBean.BaseData> dataList;
    private OnPrivateInfoDesignSelectListener listener;

    public PrivateInfoDesignSelectDialog(Context context, List<PrivateInfoDesignBean.BaseData> dataList, OnPrivateInfoDesignSelectListener listener) {
        super(context);
        initDialog();
        this.context = context;
        this.dataList = dataList;
        this.listener = listener;

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_private_info_design_select, null, false);
        mainLV = (ListView) view.findViewById(R.id.dia_private_info_design_select_main);
        view.findViewById(R.id.cancel).setOnClickListener(this);
        mainLV.setOnItemClickListener(this);
        mainLV.setDivider(new ColorDrawable(Color.parseColor("#e1e1e1")));
        mainLV.setDividerHeight(1);
        int count = dataList.size();
        if(count<=6){
            ViewGroup.LayoutParams lp = mainLV.getLayoutParams();
            lp.height = count * dip2px(context, 50) + (count-1) * dip2px(context, 1);
            mainLV.setLayoutParams(lp);
        }
        mainLV.setAdapter(new PrivateInfoDesignSelectAdapter());
        setContentView(view);
    }

    private void initDialog() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.getDecorView().setPadding(100, 0, 100, 0);

        dialogWindow.setAttributes(lp);
        setCanceledOnTouchOutside(true);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        dismiss();
        //回调位置
        listener.onSelect(i);
    }

    @Override
    public void onClick(View view) {
        dismiss();
    }

    public int dip2px(Context context, float dipValue) {
        float fontScale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * fontScale + 0.5f);
    }

    public interface OnPrivateInfoDesignSelectListener{
        void onSelect(int pos);
    }

    private class PrivateInfoDesignSelectAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(view == null){
                view = new TextView(context);
            }
            TextView tv = (TextView) view;
            tv.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, dip2px(context, 50)));
            tv.setGravity(Gravity.CENTER);
            tv.setText(dataList.get(i).getName());
            tv.setTextSize(14);
            tv.setTextColor(Color.parseColor("#333333"));
            return view;
        }
    }

}
