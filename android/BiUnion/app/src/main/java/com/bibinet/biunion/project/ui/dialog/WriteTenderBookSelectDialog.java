package com.bibinet.biunion.project.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
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
import com.bibinet.biunion.project.bean.WriteTenderBookBean;

/**
 * Created by Wh on 2017-7-6.
 */

public class WriteTenderBookSelectDialog extends Dialog implements AdapterView.OnItemClickListener, View.OnClickListener {

    private ListView mainLV;
    private Context context;
    private TextView showTV;
    private WriteTenderBookBean.WriteTenderBookSelect writeTenderBookSelect;

    public WriteTenderBookSelectDialog(Context context, WriteTenderBookBean.WriteTenderBookSelect writeTenderBookSelect, TextView showTV) {
        super(context);
        initDialog();
        this.context = context;
        this.writeTenderBookSelect = writeTenderBookSelect;
        this.showTV = showTV;

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_write_tender_book_select, null, false);
        mainLV = (ListView) view.findViewById(R.id.dia_write_tender_book_select_recycler);
        view.findViewById(R.id.dia_write_tender_book_select_cancel).setOnClickListener(this);
        mainLV.setOnItemClickListener(this);
        int count = writeTenderBookSelect.getKeyValueBeanList().size();
        if(count<=5){
            ViewGroup.LayoutParams lp = mainLV.getLayoutParams();
            lp.height = count * dip2px(context, 50);
            mainLV.setLayoutParams(lp);
        }
        mainLV.setAdapter(new WriteTenderBookSelectAdapter());
        setContentView(view);
    }

    private void initDialog() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
/*
         * 鑾峰彇鍦ｈ癁妗嗙殑绐楀彛瀵硅薄鍙婂弬鏁板璞′互淇敼瀵硅瘽妗嗙殑甯冨眬璁剧疆,
         * 鍙互鐩存帴璋冪敤getWindow(),琛ㄧず鑾峰緱杩欎釜Activity鐨刉indow
         * 瀵硅薄,杩欐牱杩欏彲浠ヤ互鍚屾牱鐨勬柟寮忔敼鍙樿繖涓狝ctivity鐨勫睘鎬?
         */
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
        writeTenderBookSelect.setId(writeTenderBookSelect.getKeyValueBeanList().get(i).getKey());
        writeTenderBookSelect.setName(writeTenderBookSelect.getKeyValueBeanList().get(i).getValue());
        showTV.setText(writeTenderBookSelect.getKeyValueBeanList().get(i).getValue());
        showTV.setTextColor(Color.parseColor("#333333"));
    }

    @Override
    public void onClick(View view) {
        dismiss();
    }

    public int dip2px(Context context, float dipValue) {
        float fontScale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * fontScale + 0.5f);
    }

    private class WriteTenderBookSelectAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return writeTenderBookSelect.getKeyValueBeanList().size();
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
            tv.setText(writeTenderBookSelect.getKeyValueBeanList().get(i).getValue());
            tv.setTextSize(13);
            tv.setTextColor(Color.parseColor("#666666"));
            return view;
        }
    }

}
