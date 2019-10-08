package com.bibinet.biunion.project.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.bean.KeyValueSelectBean;
import com.bibinet.biunion.project.ui.activity.CompanyInfoActivity;

/**
 * Created by Wh on 2017-7-6.
 */

public class ShowCompanyInfoSelectDialog extends Dialog implements AdapterView.OnItemClickListener, View.OnClickListener {
    private ListView mainLV;
    private Context context;
    private KeyValueSelectBean keyValueSelectBean;
    private TextView showTV;

    public ShowCompanyInfoSelectDialog(Context context, KeyValueSelectBean keyValueSelectBean, TextView showTV) {
        super(context);
        this.context = context;
        this.showTV = showTV;
        this.keyValueSelectBean = keyValueSelectBean;
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_show_company_info_select, null, false);
        mainLV = (ListView) view.findViewById(R.id.dia_show_company_info_select_main);
        view.findViewById(R.id.cancel).setOnClickListener(this);
        mainLV.setOnItemClickListener(this);
        mainLV.setAdapter(new DialogSelectAdapter());
        setContentView(view);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String k = keyValueSelectBean.getKeyValueList().get(i).getKey();
        String v = keyValueSelectBean.getKeyValueList().get(i).getValue();
        keyValueSelectBean.setSelectKey(k);
        keyValueSelectBean.setSelectValue(v);
        showTV.setText(v);
        showTV.setTextColor(Color.parseColor("#333333"));
        dismiss();
    }


    @Override
    public void onClick(View view) {
        dismiss();
    }

    public int dip2px(Context context, float dipValue) {
        float fontScale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * fontScale + 0.5f);
    }

    private class DialogSelectAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return keyValueSelectBean.getKeyValueList().size();
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
            if (view == null) {
                view = new TextView(context);
            }
            TextView tv = (TextView) view;
            tv.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, dip2px(context, 33)));
            tv.setGravity(Gravity.CENTER);
            tv.setText(keyValueSelectBean.getKeyValueList().get(i).getValue());
            return view;
        }
    }

}
