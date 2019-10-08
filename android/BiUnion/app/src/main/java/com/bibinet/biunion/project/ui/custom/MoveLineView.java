package com.bibinet.biunion.project.ui.custom;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.utils.DensityUtil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MoveLineView extends View {
    private Context context;
    private Paint paint;
    private int count = 3;

    private int lineWidth = 0;
    private int marign = 0;
    private int spec = 1;

    /**
     * ƫ����
     */
    private int offset = 0;

    private boolean isStart = false;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            int newPos = bundle.getInt("newPos");
            int oldPos = bundle.getInt("oldPos");

            if (newPos > oldPos) {
                offset += (lineWidth / 5);
            } else {
                offset -= (lineWidth / 5);
            }
            invalidate();
            if (Math.abs(offset) < lineWidth * (Math.abs(newPos - oldPos))) {
                //�ٴο�ʼ���÷��� �ٴ�ִ�з���Ϣ ��Ϣ���ܶ��ʹ��
                startMove(newPos, oldPos);
            } else {
                offset = 0;
                spec = newPos;
                isStart = false;
            }
        }

    };

    public MoveLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(context.getResources().getColor(R.color.col_title_background));
        paint.setStrokeWidth(Integer.MAX_VALUE);
        this.context = context;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        lineWidth = DensityUtil.dip2px(context, 30);
//        marign = (getMeasuredWidth() - count * lineWidth) / (count - 1);
        marign = (getMeasuredWidth() - count * lineWidth) / (count * 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        //lineWidth = getMeasuredWidth()/count;
        Log.e("spec__-d-", spec + "--" + offset);
        //canvas.drawLine(lineWidth * spec + offset, 0, lineWidth * (spec+1) + offset, 0, paint);

//        canvas.drawLine(spec * (lineWidth + marign) + offset, 0, spec * (lineWidth + marign) + lineWidth + offset, 0, paint);
        int startX = (2 * spec - 1) * marign + (spec - 1) * lineWidth;
        canvas.drawLine( startX, 0, startX +  lineWidth, 0, paint);
    }

    /**
     * ���õ�ǰ��λ��(����)
     */
    public void setCurrentPosAnimation(int pos) {
        if (!isStart) {
            startMove(pos, spec);
            isStart = true;
        }
    }

    /**
     * ���õ�ǰ��λ��(�޶���)
     */
    public void setCurrentPos(int pos) {
        spec = pos;
        invalidate();
    }

    /**
     * �õ���ǰλ��
     *
     * @return
     */
    public int getCurrentPos() {
        return spec;
    }

    private boolean isLeft = false;

    /**
     * ͨ��ƫ��ֵ������ �ô˷������� ����Ҫ��������λ��
     *
     * @param offset viewpagerƫ����
     * @param pos    viewpagerλ��
     */
    public void setCurrentOffset(float offset, int pos) {
        Log.e("lisezz", offset + "-");
        //һ��ʼ�ͽ�pos ���� �������Ҷ������pos��ʼ����
        spec = pos;
        if (offset == 0) {
            this.offset = 0;
            //һ��ʼλ�þ͸����� ���һ�ξͲ��ø�����λ�ã�ֻ��ƫ�������¾�����
			spec = pos;
            //invalidate();
            return;
        }
        int mxae = marign * (count - 2);
        int value = (int) (offset * (getMeasuredWidth() - lineWidth * (count - 1) - mxae));
        this.offset = value;

        if (this.offset == getMeasuredWidth() - lineWidth) {
            return;
        }
        invalidate();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private void startMove(int newPos, int oldPos) {
        // TODO Auto-generated method stub
        Message msg = new Message();
        Bundle data = new Bundle();
        data.putInt("newPos", newPos);
        data.putInt("oldPos", oldPos);
        msg.setData(data);
        handler.sendMessageDelayed(msg, 1);
    }
}
