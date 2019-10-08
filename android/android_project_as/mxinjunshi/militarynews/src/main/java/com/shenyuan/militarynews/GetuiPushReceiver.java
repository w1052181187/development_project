package com.shenyuan.militarynews;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;
import com.shenyuan.militarynews.activity.AdDetailActivity;
import com.shenyuan.militarynews.activity.ArticleActivity;
import com.shenyuan.militarynews.activity.HomeActivity;
import com.shenyuan.militarynews.activity.PhotoPageActivity;
import com.shenyuan.militarynews.activity.VideoDetailActivity;
import com.shenyuan.militarynews.beans.data.GetuiPushBean;
import com.shenyuan.militarynews.beans.data.MChannelItemBean;
import com.shenyuan.militarynews.utils.Common;
import com.shenyuan.militarynews.utils.SPHelper;

public class GetuiPushReceiver extends BroadcastReceiver {
	
	private final String NOTIFY_HEAD = "[个推]";

    @Override
    public void onReceive(Context context, Intent intent) {
    	if (!Common.isTrue(SettingManager.getInst().getIsPush()) 
    			|| SPHelper.getInst().getInt(SPHelper.KEY_PUSH_TUNNEL) != Const.PUSH_TUNNEL_GETUI) {
			return;
		}
        Bundle bundle = intent.getExtras();
        Log.d("GetuiSdkDemo", "onReceive() action=" + bundle.getInt("action"));

        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_MSG_DATA:
                // 获取透传数据
                byte[] payload = bundle.getByteArray("payload");
                // String appid = bundle.getString("appid");
                String taskid = bundle.getString("taskid");
                String messageid = bundle.getString("messageid");

                // smartPush第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
                boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90001);
                Log.d("GetuiSdkDemo","第三方回执接口调用" + (result ? "成功" : "失败"));

                //可根据payload是否为null来判断是通知（null）还是透传消息（不为null）
                if (payload != null) {
                    String data = new String(payload);
                    Log.d("GetuiSdkDemo", "receiver payload : " + data);
                    
        			try {

                    	Gson gson = new Gson();
                    	GetuiPushBean bean = gson.fromJson(data, new TypeToken<GetuiPushBean>(){}.getType());
        				
        				String alert = (Const.DEBUG_PUSH_HEAD ? NOTIFY_HEAD : "") + bean.getAlert();
        				String title = (Const.DEBUG_PUSH_HEAD ? NOTIFY_HEAD : "") + bean.getTitle();
        				alert = Common.repalcePushEmoji(alert);
        				title = Common.repalcePushEmoji(title);
        				int type = 0;
        				type = bean.getT_type(); 
        				int nId = 100 + type;
        				
        				final String tid = bean.getTid();
    					
    					int tempTid = 0;
    					if (!TextUtils.isEmpty(tid) && TextUtils.isDigitsOnly(tid)) {
    						tempTid = Integer.valueOf(tid);
    					}
    					if (tempTid == 0) {
    						tempTid = Common.getRandomNum();
    					}
    					nId += tempTid;
        				
        				Log.i(PushReceiver.class.getSimpleName(), "alert: " + alert + ", title: " + title + ", type: " + type);
        				
        				Intent resultIntent = null;
        				
        				if(type == PushReceiver.TYPE_ARTICLE){
        					
        					//是否显示桌面红点
        					if (TextUtils.equals(bean.getBadge(), PushReceiver.IS_SHOW_BADGE)) {
        						Common.showBadge(context, 1);
        					}
        					
        					if(!Common.isTrue(SettingManager.getInst().getSettingBean().getIs_push())){
        						return;
        					}
        					// 文章页
        					
        					MChannelItemBean dbean = new MChannelItemBean();
        					int arcType = bean.getArc_type();
        					switch (arcType) {
        					case 2: // 图文
        						
        						dbean.setAid(String.valueOf(tid));
        						resultIntent = new Intent(context, PhotoPageActivity.class);
        						resultIntent.putExtra("bean", dbean);
        						resultIntent.putExtra("article_area", "push");
        						break;
        					case 3: //视频
        						dbean.setAid(String.valueOf(tid));
        						resultIntent = new Intent(context, VideoDetailActivity.class);
        						resultIntent.putExtra("bean", dbean);
        						resultIntent.putExtra("article_area", "push");
        						break;
        					case 4: //自定义广告
        						String adUrl = bean.getTz_url();
        						resultIntent = new Intent(context, AdDetailActivity.class);
        						resultIntent.putExtra("type", AdDetailActivity.TYPE_ARTICLE);
        						resultIntent.putExtra("adurl", adUrl);
        						resultIntent.putExtra("imgurl", adUrl);
        						break;

        					default:
        						dbean.setAid(String.valueOf(tid));
        						resultIntent = new Intent(context, ArticleActivity.class);
        						resultIntent.putExtra("bean", dbean);
        						resultIntent.putExtra("article_area", "push");
        						break;
        					}
        					
        					resultIntent.putExtra("push", true);
        					resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        					
        				}else{
        					Object[] rets = PushMsgManager.getInstance().filterLCPush(context, type);
        					if(((Boolean)rets[0]) == true){
        						return;
        					}else{
        						resultIntent = (Intent) rets[1];
        						if(resultIntent == null){
        							// 首页
        							resultIntent = new Intent(context, HomeActivity.class);
        							resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        						}
        					}
        				}
        				
        				PendingIntent pendingIntent = PendingIntent.getActivity(
        						context, nId, resultIntent,
        						PendingIntent.FLAG_UPDATE_CURRENT);

        				NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
        						context)
        						.setSmallIcon(R.drawable.ic_launcher)
        						.setContentTitle(title)
        						.setContentText(alert)
        						.setTicker(title);
        				mBuilder.setContentIntent(pendingIntent);
        				mBuilder.setAutoCancel(true);
        				mBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS);
        				
        				int mNotificationId = nId;
        				NotificationManager mNotifyMgr = (NotificationManager) context
        						.getSystemService(Context.NOTIFICATION_SERVICE);
        				mNotifyMgr.notify(mNotificationId, mBuilder.build());
        			} catch (Exception e) {
        				e.printStackTrace();
        			}

                }
                break;

            case PushConsts.GET_CLIENTID:
                // 获取ClientID(CID)
                // 第三方应用需要将CID上传到第三方服务器，并且将当前用户帐号和CID进行关联，以便日后通过用户帐号查找CID进行消息推送
                String cid = bundle.getString("clientid");
                Log.d("GetuiSdkDemo", "cid = " + cid);
                break;
            case PushConsts.GET_SDKONLINESTATE:
                boolean online = bundle.getBoolean("onlineState");
                Log.d("GetuiSdkDemo", "online = " + online);
                break;

            case PushConsts.SET_TAG_RESULT:
                String sn = bundle.getString("sn");
                String code = bundle.getString("code");

                String text = "设置标签失败, 未知异常";
                switch (Integer.valueOf(code)) {
                    case PushConsts.SETTAG_SUCCESS:
                        text = "设置标签成功";
                        break;

                    case PushConsts.SETTAG_ERROR_COUNT:
                        text = "设置标签失败, tag数量过大, 最大不能超过200个";
                        break;

                    case PushConsts.SETTAG_ERROR_FREQUENCY:
                        text = "设置标签失败, 频率过快, 两次间隔应大于1s";
                        break;

                    case PushConsts.SETTAG_ERROR_REPEAT:
                        text = "设置标签失败, 标签重复";
                        break;

                    case PushConsts.SETTAG_ERROR_UNBIND:
                        text = "设置标签失败, 服务未初始化成功";
                        break;

                    case PushConsts.SETTAG_ERROR_EXCEPTION:
                        text = "设置标签失败, 未知异常";
                        break;

                    case PushConsts.SETTAG_ERROR_NULL:
                        text = "设置标签失败, tag 为空";
                        break;

                    case PushConsts.SETTAG_NOTONLINE:
                        text = "还未登陆成功";
                        break;

                    case PushConsts.SETTAG_IN_BLACKLIST:
                        text = "该应用已经在黑名单中,请联系售后支持!";
                        break;

                    case PushConsts.SETTAG_NUM_EXCEED:
                        text = "已存 tag 超过限制";
                        break;

                    default:
                        break;
                }

                Log.d("GetuiSdkDemo", "settag result sn = " + sn + ", code = " + code);
                Log.d("GetuiSdkDemo", "settag result sn = " + text);
                break;
            case PushConsts.THIRDPART_FEEDBACK:
                /*
                 * String appid = bundle.getString("appid"); String taskid =
                 * bundle.getString("taskid"); String actionid = bundle.getString("actionid");
                 * String result = bundle.getString("result"); long timestamp =
                 * bundle.getLong("timestamp");
                 *
                 * Log.d("GetuiSdkDemo", "appid = " + appid); Log.d("GetuiSdkDemo", "taskid = " +
                 * taskid); Log.d("GetuiSdkDemo", "actionid = " + actionid); Log.d("GetuiSdkDemo",
                 * "result = " + result); Log.d("GetuiSdkDemo", "timestamp = " + timestamp);
                 */
                break;

            default:
                break;
        }
    }
}
