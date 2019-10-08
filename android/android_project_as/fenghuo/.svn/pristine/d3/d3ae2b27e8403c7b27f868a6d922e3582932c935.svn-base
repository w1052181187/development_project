package com.chengning.fenghuo.util;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.text.Html;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengning.common.util.DisplayUtil;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.SettingManager;
import com.chengning.fenghuo.activity.PhotoPageArticleActivity;
import com.chengning.fenghuo.data.bean.ArticlesBean;
import com.chengning.fenghuo.data.bean.BaseArticlesBean;
import com.chengning.fenghuo.data.bean.DynamicItemBean;
import com.chengning.fenghuo.data.bean.Image;
import com.chengning.fenghuo.data.bean.SubscribeContentItemBean;
import com.chengning.fenghuo.widget.DynamicTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

@SuppressLint("InflateParams")
public class ArticleContentUtil<T> {
	public static final int REQUEST_PHOTO_PAGE_CODE = 1;

	private static List<String> imgList = null;
	
	private BaseArticlesBean<T> mChannelBean;

	private LayoutInflater inflater;

	private LinearLayout linearLayout;

	private int textSize;

	private int noPicModel;

	public LinearLayout build(Activity context, BaseArticlesBean bean) {
		return build(context, bean, true);
	}

	public LinearLayout build(Activity context, BaseArticlesBean bean,
			boolean isUseTitle) {
		inflater = LayoutInflater.from(context);

		linearLayout = new LinearLayout(context);
		linearLayout.setLayoutParams(new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT));
		linearLayout.setOrientation(LinearLayout.VERTICAL);

		textSize = SettingManager.getInst().getFontSize();

		noPicModel = SettingManager.getInst().getNoPicModel();
		
		if (bean instanceof ArticlesBean) {
			linearLayout = handleArticle(context,(ArticlesBean)bean);
		} else if (bean instanceof DynamicItemBean) {
			linearLayout = handleDynamic(context,(DynamicItemBean)bean);
		}
		
		return linearLayout;
	}

	private LinearLayout handleDynamic(Activity context, DynamicItemBean bean) {
		if (bean.getContent() != null) {
			List<String> list = bean.getContent();
			imgList = new ArrayList<String>();
			for (int i = 0; i < list.size(); i++) {
				String content = list.get(i);
//				content = content.replace("\r\n\r\n","<br />");
				content = "<div>" + content + "</div>";
				if (!TextUtils.isEmpty(content)) {
					
					DynamicTextView textView = (DynamicTextView) inflater.inflate(
							R.layout.item_dynamic_text, null);
					textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,
							textSize);
					textView.setText(Html.fromHtml(content));

					linearLayout.addView(textView);

				} 
			}
		}
		if (null != bean.getImage_list()
				&& !Common.isTrue(noPicModel)) {
			List<Image> list = bean.getImage_list();
			int flag = 0;
			imgList = new ArrayList<String>();
			for (int i = 0; i < list.size(); i++) {
				Image image = list.get(i);
				float w = image.getImage_width();
				float h = image.getImage_height();
				float margin = context.getResources().getDimension(
						R.dimen.common_horizontal_margin);
				float tarWidthFloat = DisplayUtil.getInst()
						.getScreenWidth() - 2 * margin;
				float tarHeightFloat = h * tarWidthFloat / w;

				ImageView imageView = (ImageView) inflater.inflate(
						R.layout.item_subscribe_image, null);
				LinearLayout.LayoutParams lp = new LayoutParams(
						(int) tarWidthFloat, (int) tarHeightFloat);
				lp.leftMargin = (int) margin;
				lp.rightMargin = (int) margin;
				lp.topMargin = (int) margin;
				lp.bottomMargin = (int) margin;
				imageView.setLayoutParams(lp);

				imgList.add(image.getImage_original());
				imageView.setTag(bean);
				ImageClick click = new ImageClick(context, flag, mChannelBean);
				imageView.setOnClickListener(click);

				linearLayout.addView(imageView);
				flag++;
				ImageLoader.getInstance().displayImage(image.getImage_original(),
						imageView);
				if (Common.isTrue(SettingManager.getInst().getNightModel())) {
					imageView.setColorFilter(context.getResources()
							.getColor(R.color.night_img_color),
							PorterDuff.Mode.MULTIPLY);

				}
				
			}
			
		}
			
		return linearLayout;
	}
//	private LinearLayout handleDynamic(Activity context, DynamicBean bean) {
//		if (bean.getContent() != null) {
//			List<String> list = bean.getContent();
//			int flag = 0;
//			imgList = new ArrayList<String>();
//			for (int i = 0; i < list.size(); i++) {
//				String content = list.get(i);
//				if (!TextUtils.isEmpty(content)) {
//					
//					// 分段
//					if (content.endsWith("\r\n")) {
//						content = content
//								.substring(0, content.length() - 2);
//					}
//					String[] array = content.split("\n\n");
//					for (String s : array) {
//						DynamicTextView textView = (DynamicTextView) inflater.inflate(
//								R.layout.item_dynamic_text, null);
//						// if (b.isBold()) {
//						// TextPaint tp = textView.getPaint();
//						// tp.setFakeBoldText(true);
//						// }
//						textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,
//								textSize);
////						textView.setContent(s, ImageSpan.ALIGN_BOTTOM);
//						textView.setContent(s);
////						textView.setText(Html.fromHtml(s));
//						
//						linearLayout.addView(textView);
//					}
//					
//				} 
//			}
//		}
//		if (null != bean.getImage_list()
//				&& !Common.isTrue(noPicModel)) {
//			List<Image> list = bean.getImage_list();
//			int flag = 0;
//			imgList = new ArrayList<String>();
//			for (int i = 0; i < list.size(); i++) {
//				Image image = list.get(i);
//				float w = image.getImage_width();
//				float h = image.getImage_height();
//				float margin = context.getResources().getDimension(
//						R.dimen.common_horizontal_margin);
//				float tarWidthFloat = DisplayUtil.getInst()
//						.getScreenWidth() - 2 * margin;
//				float tarHeightFloat = h * tarWidthFloat / w;
//				
//				ImageView imageView = (ImageView) inflater.inflate(
//						R.layout.item_subscribe_image, null);
//				LinearLayout.LayoutParams lp = new LayoutParams(
//						(int) tarWidthFloat, (int) tarHeightFloat);
//				lp.leftMargin = (int) margin;
//				lp.rightMargin = (int) margin;
//				lp.topMargin = (int) margin;
//				lp.bottomMargin = (int) margin;
//				imageView.setLayoutParams(lp);
//				
//				imgList.add(image.getImage_original());
//				imageView.setTag(bean);
//				ImageClick click = new ImageClick(context, flag, mChannelBean);
//				imageView.setOnClickListener(click);
//				
//				linearLayout.addView(imageView);
//				flag++;
//				ImageLoader.getInstance().displayImage(image.getImage_original(),
//						imageView);
//				if (Common.isTrue(SettingManager.getInst().getNightModel())) {
//					imageView.setColorFilter(context.getResources()
//							.getColor(R.color.night_img_color),
//							PorterDuff.Mode.MULTIPLY);
//					
//				}
//				
//			}
//			
//		}
//		
//		return linearLayout;
//	}

	private LinearLayout handleArticle(Activity context, ArticlesBean bean) {
		if (bean.getContent() != null) {
			List<SubscribeContentItemBean> list = bean.getContent();
			int flag = 0;
			imgList = new ArrayList<String>();
			for (int i = 0; i < list.size(); i++) {
				SubscribeContentItemBean b = list.get(i);
				if (b.getType().equals("txt")) {
					String content = b.getContent();
					if (!TextUtils.isEmpty(content)) {
						// int previous = i - 1;
						// if(previous >= 0 &&
						// list.get(previous).getType().equals("img")){
						// content = "\n" + content;
						// }

						// 分段
						if (content.endsWith("\r\n")) {
							content = content
									.substring(0, content.length() - 2);
						}
						String[] array = content.split("\n\n");
						for (String s : array) {
							TextView textView = (TextView) inflater.inflate(
									R.layout.item_subscribe_text, null);
							// if (b.isBold()) {
							// TextPaint tp = textView.getPaint();
							// tp.setFakeBoldText(true);
							// }
							textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,
									textSize);
							textView.setText(s);

							linearLayout.addView(textView);
						}
					}

				} else if (b.getType().equals("img")
						&& !Common.isTrue(noPicModel)) {
					float w = b.getWidth();
					float h = b.getHeight();
					float margin = context.getResources().getDimension(
							R.dimen.common_horizontal_margin);
					float tarWidthFloat = DisplayUtil.getInst()
							.getScreenWidth() - 2 * margin;
					float tarHeightFloat = h * tarWidthFloat / w;

					ImageView imageView = (ImageView) inflater.inflate(
							R.layout.item_subscribe_image, null);
					LinearLayout.LayoutParams lp = new LayoutParams(
							(int) tarWidthFloat, (int) tarHeightFloat);
					lp.leftMargin = (int) margin;
					lp.rightMargin = (int) margin;
					lp.topMargin = (int) margin;
					lp.bottomMargin = (int) margin;
					imageView.setLayoutParams(lp);

					imgList.add(b.getSrc());
					imageView.setTag(bean);
					ImageClick click = new ImageClick(context, flag, mChannelBean);
					imageView.setOnClickListener(click);

					linearLayout.addView(imageView);
					flag++;
					ImageLoader.getInstance().displayImage(b.getSrc(),
							imageView);
					if (Common.isTrue(SettingManager.getInst().getNightModel())) {
						imageView.setColorFilter(context.getResources()
								.getColor(R.color.night_img_color),
								PorterDuff.Mode.MULTIPLY);

					}
				}
			}
		}
		return linearLayout;
	}

	public void setChannelBean(BaseArticlesBean<T> bean) {
		this.mChannelBean = bean;
	}
	
	public class ImageClick implements OnClickListener {

		private int mIndex;
		private Activity context;
		private BaseArticlesBean<T> mChannelBean;

		public ImageClick(Activity context, int index, BaseArticlesBean<T> bean) {
			this.context = context;
			this.mIndex = index;
			this.mChannelBean = bean;
		}

		@Override
		public void onClick(View widget) {
			BaseArticlesBean bean = (BaseArticlesBean) widget.getTag();
			Intent intent = new Intent(context, PhotoPageArticleActivity.class);
			intent.putExtra("index", mIndex);
			intent.putExtra("articleBean",bean);
			intent.putExtra("channelBean", mChannelBean);
			intent.putStringArrayListExtra("imgList",
					(ArrayList<String>) imgList);
			context.startActivityForResult(intent, REQUEST_PHOTO_PAGE_CODE);
		}

	}
}
