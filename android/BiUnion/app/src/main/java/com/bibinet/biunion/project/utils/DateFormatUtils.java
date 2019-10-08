package com.bibinet.biunion.project.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bibinet.biunion.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bibinet on 2017-6-2.
 */
//首页弹出工具类
public class DateFormatUtils {
    public static String format(long time, String formal){
        SimpleDateFormat f = new SimpleDateFormat(formal);
        return f.format(time);
    }

    public static String format(String time, String formal) {
        SimpleDateFormat f = null;
        try{
            f = new SimpleDateFormat(formal);
            long t = Long.valueOf(time);
            return f.format(t);
        }catch (Exception e){
            e.printStackTrace();
        }
        return f.format(System.currentTimeMillis());
    }

    public static Long convertTimeToLong(String time) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            date = sdf.parse(time);
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }
}
