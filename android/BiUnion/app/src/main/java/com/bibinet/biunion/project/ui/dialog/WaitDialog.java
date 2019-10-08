package com.bibinet.biunion.project.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.ui.custom.WaitView;


/**
 * Created by bibinet on 2017-5-3.
 */

public class WaitDialog extends Dialog{

    private WaitView waitView;
    public WaitDialog(Context context) {
        super(context);
        initDialog();
        setContentView(R.layout.dialog_wait);
        waitView = (WaitView) findViewById(R.id.dia_wait);
    }

    public void open(){
        waitView.start();
        show();
    }

    public void close(){
        waitView.stop();
        dismiss();
    }

    private void initDialog(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(lp);
        setCanceledOnTouchOutside(false);
    }


}
