package com.bibinet.biunion.project.ui.custom;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.adapter.MediaLiveChatMesageAdapter;
import com.bibinet.biunion.project.adapter.MediaLiveUserAdapter;
import com.bibinet.biunion.project.bean.LoginResultBean;
import com.bibinet.biunion.project.models.MediaLiveCommentModel;
import com.bibinet.biunion.project.models.MediaLiveMsgItemModel;
import com.bibinet.biunion.project.models.MediaLiveOnlineUserModel;
import com.bibinet.biunion.project.net.MyRetrofitResponseCallback;
import com.bibinet.biunion.project.net.ready.MediaLiveReady;
import com.bibinet.biunion.project.ui.activity.MediaLiveRecordGuideActivity;
import com.bibinet.biunion.project.ui.pop.MediaLiveMenuPop;
import com.bibinet.biunion.project.utils.Common;
import com.bibinet.biunion.project.utils.ImageUtils;
import com.bibinet.biunion.project.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.LinkedList;

public class MediaLiveRecordView {

    private static final int MSG_QUERY_SUCCESS = 2;
    private static final int MSG_QUERY_TIME = 3;
    private static final int MSG_ONLINE_USER_SUCCESS = 4;
    private static final int MSG_COMMENT_SUCCESS = 5;
    private final int FLAG_MSG_RECORD_TIME = 1;
    private final Activity mActivity;
    private volatile boolean isCommentStart ;
    private volatile boolean isRecordStart ;
    private TimeThread mTimeThread;
    private CommentThread mTimeComment;
    private MediaLiveChatMesageAdapter mAdapter;
    private ImageView mAvatarImg;
    private TextView mUsernameTv;
    private TextView mCountTv;
    private TextView mRecordTimeTopTv;
    private View mRecordingLl;
    private ImageView mLiveMenuImg;
    private EditText mLiveReply;
    private View mLiveCloseImg;
    private RecyclerView mRecyclerView;
    private RecyclerView mUserRecyclerView;
    private MediaLiveUserAdapter mUserAdapter;
    private MediaLiveMenuPop.OnLiveMenuPopListener mLiveListener;
    private Handler mHandler;
    private ArrayList<MediaLiveMsgItemModel> list;
    private ArrayList<String> mOnlineUsers;
    private LoginResultBean.UserBean mUser;
    private String mObjectId;
    MediaLiveReady mediaLiveReady;
    private final Object lock = new Object();
    private boolean isRecordResume;

    public MediaLiveRecordView(Activity activity, View rootView) {
        this.mActivity = activity;
        initView(activity,rootView);
    }

    private void initView(Activity activity, View rootView) {
        View view  = rootView;
        mUserRecyclerView = view.findViewById(R.id.act_media_live_record_recycler_user);
        mRecyclerView = view.findViewById(R.id.act_media_live_record_recycler);
        mAvatarImg = view.findViewById(R.id.act_media_live_record_avatar);
        mUsernameTv = view.findViewById(R.id.act_media_live_record_username);
        mCountTv = view.findViewById(R.id.act_media_live_record_count);
        mRecordTimeTopTv = view.findViewById(R.id.act_media_live_record_time_top);
        mRecordingLl = view.findViewById(R.id.act_media_live_recording_ll);
        mLiveMenuImg = view.findViewById(R.id.act_media_live_record_menu);
        mLiveReply = view.findViewById(R.id.act_media_live_record_reply);
        mLiveCloseImg = view.findViewById(R.id.act_media_live_record_close);
        
        initOnclickListener();

        isRecordStart = true;
        isRecordResume = true;
        isCommentStart = true;
        mTimeThread = new TimeThread();
        mTimeComment = new CommentThread();

        list = new ArrayList<>();
        MediaLiveMsgItemModel firstComment = new MediaLiveMsgItemModel();
        firstComment.setUserName("欢迎各位招标行业相关人员");
        list.add(firstComment);
        mAdapter = new MediaLiveChatMesageAdapter(mActivity,list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        mOnlineUsers = new ArrayList<>();
        mUserAdapter = new MediaLiveUserAdapter(mActivity, mOnlineUsers);
        mUserRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity,LinearLayoutManager.HORIZONTAL,false));
        mUserRecyclerView.setHasFixedSize(true);
        mUserRecyclerView.setAdapter(mUserAdapter);
        mediaLiveReady = new MediaLiveReady();
    }

    private void initOnclickListener() {
        mLiveCloseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCloseClick();
            }
        });
        mLiveMenuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMenuClick();
            }
        });
        mLiveReply.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    handled = true;
                    /*隐藏软键盘*/
//                    Common.hideInput(mActivity);
                    handleReply();
                }
                return handled;
            }
        });
    }

    private void handleReply() {
        if (TextUtils.isEmpty(mLiveReply.getText().toString())) {
            ToastUtils.showShort("内容为空，请重新输入！");
            return;
        }
        mediaLiveReady.handleLiveComment(mUser.getEnterprise().getContactName(), mUser.getUserId(),
                mLiveReply.getText().toString(), mObjectId, "0", new MyRetrofitResponseCallback<MediaLiveCommentModel>() {
            @Override
            protected void onRequestFail(String resMessage) {

            }

            @Override
            protected void onRequestSuccess(MediaLiveCommentModel model) {
                recordTimeHandler.obtainMessage(MSG_COMMENT_SUCCESS, model).sendToTarget();
            }

        });
    }

    public void startRecord() {
        mTimeThread.start();
    }

    synchronized public void resumeRecord(boolean mRecordFlag) {
        isRecordResume = mRecordFlag;
        mRecordingLl.setVisibility(mRecordFlag ? View.VISIBLE : View.GONE);
        if (mRecordFlag) {
            synchronized (lock) {
                lock.notifyAll();
            }
        }
    }

    public void onDismiss() {
        mLiveMenuImg.setImageResource(R.mipmap.easya);
        mLiveMenuImg.setBackgroundResource(R.drawable.live_close_bg);
    }

    public void setData(Handler mHandler, LoginResultBean.UserBean user) {
        this.mHandler = mHandler;
        this.mUser = user;

        if ( user != null ) {
            if (!TextUtils.isEmpty(user.getImage())) {
                RequestOptions options = new RequestOptions().override(100).circleCrop();
                Glide.with(mActivity).asBitmap()
                        .load(ImageUtils.base64ToBitmap(user.getImage()))
                        .apply(options)
                        .into(mAvatarImg);
            } else {
                Glide.with(mActivity).load(R.mipmap.wode_toux).into(mAvatarImg);
            }

            mUsernameTv.setText(user.getEnterprise().getContactName());
            mAvatarImg.setVisibility(View.VISIBLE);
            mUsernameTv.setVisibility(View.VISIBLE);
        } else {
            mAvatarImg.setVisibility(View.GONE);
            mUsernameTv.setVisibility(View.GONE);
        }

    }

    public void setObjectId(String objectId) {
        this.mObjectId = objectId;
        if (mUser != null && mUser.getEnterprise() != null && !TextUtils.isEmpty(objectId)) {
            mTimeComment.start();
        }
    }

    class CommentThread extends Thread {

        @Override
        public void run() {
            do {
                try {
                    recordTimeHandler.sendEmptyMessage(MSG_QUERY_TIME);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            } while (isCommentStart);
        }
    }

    class TimeThread extends Thread {

        @Override
        public void run() {
            do {
                while (!isRecordResume) {
                    onRecordTimePause();
                }
                try {
                    Thread.sleep(1000);
                    recordTimeHandler.sendEmptyMessage(FLAG_MSG_RECORD_TIME);// 每隔1秒发送一个msg给mHandler
                } catch (InterruptedException e) {
                    break;
                }
            } while (isRecordStart);

        }
    }

    /**
     * 注意：这个方法只能在run方法里调用，不然会阻塞主线程，导致页面无响应
     */
    void onRecordTimePause() {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isAdded;
    Handler recordTimeHandler = new Handler(){
        public int count_time = 0;
        private int hh,mm,ss;
        private String date;

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FLAG_MSG_RECORD_TIME:
                    if (count_time == 0) {
                        mRecordingLl.setVisibility(View.VISIBLE);
                    }

                    count_time++;
                    hh = count_time / 3600;
                    mm = (count_time % 3600) /60;
                    ss = count_time % 60;
                    //System.out.println(count_time);
                    if (hh > 0) {
                        date = String.format("%02d",hh) + ":" + String.format("%02d",mm) + ":" + String.format("%02d",ss);
                    } else {
                        date = String.format("%02d",mm) + ":" + String.format("%02d",ss);
                    }

                    mRecordTimeTopTv.setText(date); //更新时间
                    break;
                case MSG_QUERY_SUCCESS:
                    MediaLiveCommentModel commentQueryModel = (MediaLiveCommentModel) msg.obj;
                    if (commentQueryModel != null) {
                        LinkedList<MediaLiveMsgItemModel> comments = commentQueryModel.getAppComments();
                        if (!Common.isListEmpty(comments)) {
                            isAdded = false;
                            if (Common.isListEmpty(list) || list.size() == 1) {
                                list.addAll(comments);
                                isAdded = true;
                            } else {
                                MediaLiveMsgItemModel lastModel = list.get(list.size() - 1);
                                for (MediaLiveMsgItemModel comment : comments) {
                                    if (Integer.valueOf(comment.getObjectId()) > Integer.valueOf(lastModel.getObjectId())) {
                                        list.add(comment);
                                        isAdded = true;
                                    }
                                }
                            }
                            if (isAdded) {
                                mAdapter.notifyDataSetChanged();
                                mRecyclerView.smoothScrollToPosition(list.size() - 1);
                            }
                        }
                    }
                    break;
                case MSG_QUERY_TIME:
                    queryComment();
                    break;
                case MSG_ONLINE_USER_SUCCESS:
                    MediaLiveOnlineUserModel users = (MediaLiveOnlineUserModel) msg.obj;
                    if (users != null && !Common.isListEmpty(users.getUserLogos())) {
                        mOnlineUsers.clear();
                        mOnlineUsers.addAll(users.getUserLogos());
                        mCountTv.setText(users.getUserLogos().size() + "");
                    } else {
                        mOnlineUsers.clear();
                        mCountTv.setText("0");
                    }
                    mUserAdapter.notifyDataSetChanged();
                    break;
                case MSG_COMMENT_SUCCESS:
                    mLiveReply.getText().clear();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private void queryComment() {

        mediaLiveReady.handleLiveComment(mUser.getEnterprise().getContactName(), mUser.getUserId(), "", mObjectId, "1", new MyRetrofitResponseCallback<MediaLiveCommentModel>() {
            @Override
            protected void onRequestFail(String resMessage) {
            }

            @Override
            protected void onRequestSuccess(MediaLiveCommentModel model) {
                recordTimeHandler.obtainMessage(MSG_QUERY_SUCCESS, model).sendToTarget();
            }

        });

        mediaLiveReady.getOnlineUser(mObjectId, new MyRetrofitResponseCallback<MediaLiveOnlineUserModel>() {
            @Override
            protected void onRequestFail(String resMessage) {

            }

            @Override
            protected void onRequestSuccess(MediaLiveOnlineUserModel model) {
                recordTimeHandler.obtainMessage(MSG_ONLINE_USER_SUCCESS, model).sendToTarget();
            }

        });

    }
    
    void onCloseClick() {
        handleLiveClose(mActivity);
    }

    public void handleLiveClose(Activity activity) {
        AlertDialog.Builder builder = null;
        builder = new AlertDialog.Builder(activity);
        AlertDialog dialog = builder
                .setMessage("确定要结束直播？")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mHandler.sendEmptyMessage(MediaLiveRecordGuideActivity.MSG_FINISH_LIVE);
                    }
                }).setNegativeButton("否", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }


    void onMenuClick() {
        MediaLiveMenuPop liveMenuPop = new MediaLiveMenuPop(mActivity);
        if (mLiveListener != null) {
            liveMenuPop.setListener(mLiveListener);
        }
        liveMenuPop.initView(mActivity);
        liveMenuPop.showUp(mLiveMenuImg);
        mLiveMenuImg.setImageBitmap(null);
        mLiveMenuImg.setBackgroundResource(R.mipmap.easy);
    }

    public  void setLiveMenuListener(MediaLiveMenuPop.OnLiveMenuPopListener liveMenuListener) {
        this.mLiveListener = liveMenuListener;
    }

    public void onDestroy() {
        //终止线程
        if (mTimeThread != null) {
            if (isRecordStart) {
                isRecordStart = false;
            }
            mTimeThread  = null;
        }

        if (isRecordResume) {
            isRecordResume = false;
        }

        if (mTimeComment != null) {
            if (isCommentStart) {
                isCommentStart = false;
            }
            mTimeComment = null;
        }

    }

}
