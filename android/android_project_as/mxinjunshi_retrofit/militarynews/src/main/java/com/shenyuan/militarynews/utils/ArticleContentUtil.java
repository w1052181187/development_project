package com.shenyuan.militarynews.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chengning.common.util.DisplayUtil;
import com.shenyuan.militarynews.App;
import com.shenyuan.militarynews.R;
import com.shenyuan.militarynews.SettingManager;
import com.shenyuan.militarynews.activity.PhotoPageArticleActivity;
import com.shenyuan.militarynews.activity.VideoActivity;
import com.shenyuan.militarynews.activity.VideoActivity.VideoData;
import com.shenyuan.militarynews.beans.data.ArticlesBean;
import com.shenyuan.militarynews.beans.data.MChannelItemBean;

import java.util.ArrayList;

public class ArticleContentUtil {
	
	public static final int REQUEST_PHOTO_PAGE_CODE = 1;
	
	@Deprecated
	public static LinearLayout build(Activity context, ArticlesBean bean, MChannelItemBean cBean){
		return build(context, bean, cBean, true);
	}

	@Deprecated
	public static LinearLayout build(final Activity  context, final ArticlesBean bean, final MChannelItemBean cBean, boolean isUseTitle){
		LayoutInflater inflater = LayoutInflater.from(context);
		
		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
		linearLayout.setOrientation(LinearLayout.VERTICAL);

		int textSize = SettingManager.getInst().getFontSize();
		int noPicModel = SettingManager.getInst().getNoPicModel();
		int nightModel = SettingManager.getInst().getNightModel();
		
		ArrayList<String> contentList = bean.getContent();
		final ArrayList<String> picsList = bean.getPics();
		int cLength = contentList != null ? contentList.size() : 0;
		int pLength = picsList != null ? picsList.size() : 0;
		
		DisplayUtil.getInst().init(context);
		
		// 视频
		if(!TextUtils.isEmpty(bean.getVideo_photo())){
			String image = bean.getVideo_photo();
			if(!TextUtils.isEmpty(image) && !Common.isTrue(noPicModel)){
				ImageView imageView = (ImageView) inflater.inflate(R.layout.item_subscribe_image, null);
				loadImageWithVideoIcon(context, image, imageView, linearLayout);
				if (Common.isTrue(nightModel)) {
					imageView.setColorFilter(context.getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
				}
				
				imageView.setTag(bean);
				VideoClick click = new VideoClick(context);
				imageView.setOnClickListener(click);
			}
		}
		
		// 图文对应
		for(int i = 0; i < cLength; i++){
			// text
			String content = contentList.get(i);
			if(!TextUtils.isEmpty(content)){
				// 分段
				if(content.endsWith("\r\n")){
					content = content.substring(0, content.length() - 2);
				}
				String[] array = content.split("\n\n");
				for(String s : array){
					TextView textView = (TextView) inflater.inflate(R.layout.item_subscribe_text, null);
					textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
//					textView.setText(s);
					textView.setText(Html.fromHtml(s));
					
					linearLayout.addView(textView);
				}
			}
			
			// image
			if(i >= pLength){
				continue;
			}
			String image = picsList.get(i);
			if(!TextUtils.isEmpty(image) && !Common.isTrue(noPicModel)){
				ImageView imageView = (ImageView) inflater.inflate(R.layout.item_subscribe_image, null);
				loadImage(context, image, imageView, linearLayout);
				if (Common.isTrue(SettingManager.getInst().getNightModel())) {
					imageView.setColorFilter(context.getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
				}
				
				imageView.setTag(bean);
				ImageClick click = new ImageClick(context, i, cBean);
				imageView.setOnClickListener(click);
			}
			
		}
		if(pLength > cLength){
			// 多余的img
			for(int i = cLength; i< pLength; i++){
				String image = picsList.get(i);
				if(!TextUtils.isEmpty(image) && !Common.isTrue(noPicModel)){
					ImageView imageView = (ImageView) inflater.inflate(R.layout.item_subscribe_image, null);
					loadImage(context, image, imageView, linearLayout);
					if (Common.isTrue(nightModel)) {
						imageView.setColorFilter(context.getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
					}
					
					imageView.setTag(bean);
					ImageClick click = new ImageClick(context, i, cBean);
					imageView.setOnClickListener(click);
				}
			}
		}
		return linearLayout;
	}
	
	private static void loadImage(final Context context, String url, final ImageView view, ViewGroup root){
		root.addView(view);

		SimpleTarget<Bitmap> target = new SimpleTarget<Bitmap>() {
			@Override
			public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
				ImageView img = view;

				float margin = context.getResources().getDimension(R.dimen.common_horizontal_margin);
				float vWidth = DisplayUtil.getInst().getScreenWidth() - 2 * margin;
				float bWidth= resource.getWidth();
				float bHeight = resource.getHeight();
				float scale = bHeight/bWidth;
				float vHeight = (int) (vWidth * scale);

				if(bWidth == 0 || bHeight == 0){
					return;
				}
				LinearLayout.LayoutParams lp = new LayoutParams((int) vWidth, (int) vHeight);
				lp.leftMargin = (int) margin;
				lp.rightMargin = (int) margin;
				lp.topMargin = (int) margin;
				lp.bottomMargin = (int) margin;
				img.setLayoutParams(lp);
				img.setImageBitmap(resource);
			}
		};
		Glide.with(context).asBitmap().load(url).into(target);
	}
	
	private static void loadImageWithVideoIcon(final Context context, String url, final ImageView view, ViewGroup root){
		root.addView(view);
		SimpleTarget<Bitmap> target = new SimpleTarget<Bitmap>() {
			@Override
			public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
				ImageView img = view;

				float margin = context.getResources().getDimension(R.dimen.common_horizontal_margin);
				float vWidth = DisplayUtil.getInst().getScreenWidth() - 2 * margin;
				float bWidth= resource.getWidth();
				float bHeight = resource.getHeight();
				float scale = bHeight/bWidth;
				float vHeight = (int) (vWidth * scale);

				if(bWidth == 0 || bHeight == 0){
					return;
				}
				LinearLayout.LayoutParams lp = new LayoutParams((int) vWidth, (int) vHeight);
				lp.leftMargin = (int) margin;
				lp.rightMargin = (int) margin;
				lp.topMargin = (int) margin;
				lp.bottomMargin = (int) margin;
				img.setLayoutParams(lp);

				float scaleT = ((float)vWidth) / ((float)bWidth);
				Bitmap bitmap2 = Common.bitmapWithImage(App.getInst(), resource, R.drawable.video_play_icon, scaleT, 0);
				img.setImageBitmap(bitmap2);
			}

		};
		Glide.with(context).asBitmap().load(url).into(target);
	}

	public static class ImageClick implements OnClickListener {

		private int mIndex;
		private Activity context;
		private MChannelItemBean cBean;
		
		public ImageClick(Activity context, int index, MChannelItemBean cBean) {
			this.context = context;
			this.mIndex = index;
			this.cBean = cBean; 
		}
		
		@Override
		public void onClick(View widget) {
			ArticlesBean bean = (ArticlesBean) widget.getTag();
			Intent intent = new Intent(context, PhotoPageArticleActivity.class);
			intent.putExtra("index", mIndex);
			intent.putExtra("articleBean",bean);
			intent.putExtra("channelBean", cBean);
		    context.startActivityForResult(intent, REQUEST_PHOTO_PAGE_CODE); 
		}
	}

	public static class VideoClick implements OnClickListener {

		private Activity context;
		
		public VideoClick(Activity context) {
			this.context = context;
		}
		
		@Override
		public void onClick(View widget) {
			ArticlesBean bean = (ArticlesBean) widget.getTag();
			VideoData video = new VideoData();
			video.setUseUrl(!TextUtils.isEmpty(bean.getVideo_play()));
			video.setUrl(bean.getVideo_play());
			video.setHtml(bean.getVideo_html());
			VideoActivity.launch(context, video); 
		}
	}
}
