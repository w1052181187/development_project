package com.bibinet.biunion.project.ui.pop;

import android.content.Context;
import android.widget.TextView;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.ui.base.BasePop;
import com.bibinet.biunion.project.ui.custom.RadioLayout;

import butterknife.BindView;

/**
 * Created by Wh on 2018-1-3.
 */

public class CollectPop extends BasePop implements RadioLayout.OnRadioLayoutOnClickListener {

    @BindView(R.id.p_message_radio)
    RadioLayout radioLayout;
    private OnCollectPopListener onCollectPopListener;

    public CollectPop(Context context) {
        super(context);
        onCollectPopListener = (OnCollectPopListener) context;
    }

    @Override
    protected void onCreate() {
        radioLayout.setOnRadioLayoutOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pop_collect;
    }

    @Override
    public void onDismiss() {
        onCollectPopListener.onDismiss();
    }

    @Override
    public void onClickPos(int pos) {
        String title = ((TextView) radioLayout.getChildAt(pos)).getText().toString();
        onCollectPopListener.onSelect(pos, title);
        dismiss();
    }

    public void setCurrentType(int currentType) {
        radioLayout.setSelectItem(currentType);
    }

    public interface OnCollectPopListener {
        void onDismiss();
        void onSelect(int pos, String title);
    }
}
