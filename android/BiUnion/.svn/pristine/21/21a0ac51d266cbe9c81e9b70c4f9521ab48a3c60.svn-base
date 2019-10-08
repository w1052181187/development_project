package com.bibinet.biunion.project.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Base64;
import android.util.LruCache;

import com.bibinet.biunion.project.adapter.MediaLiveRecordListAdapter;
import com.bibinet.biunion.project.interf.RxjavaLoadBitmapInterf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.SoftReference;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static org.greenrobot.greendao.generator.PropertyType.ByteArray;
import static org.greenrobot.greendao.generator.PropertyType.Int;

/**
 * Created by Wh on 2017-7-12.
 */

public class ImageUtils {

    /**
     * @Description: 根据图片地址转换为base64编码字符串
     * @Author:
     * @CreateTime:
     * @return
     */
    public static String getImageStr(String imgFile) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    public static void compressBmpFromBmp(Bitmap image, File file) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 90, baos);
        try {
            //
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 保存方法 */
    public static void saveBitmap(Bitmap bitmap, String imagePath) {
        File f = new File(imagePath);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * @param
     * @return
     * @author 吴昊
     * @time 2017-5-4 14:57
     * 压缩图片
     */
    public static Bitmap getSmallBitmap(String path) {
        // 设置参数
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 只获取图片的大小信息，而不是将整张图片载入在内存中，避免内存溢出
        BitmapFactory.decodeFile(path, options);
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1; // 默认像素压缩比例，压缩为原图的1/2
        int minLen = Math.min(height, width); // 原图的最小边长
        if (minLen > 200) { // 如果原始图像的最小边长大于100dp（此处单位我认为是dp，而非px）
            float ratio = (float) minLen / 200.0f; // 计算像素压缩比例
            inSampleSize = (int) ratio;
        }
        options.inJustDecodeBounds = false; // 计算好压缩比例后，这次可以去加载原图了
        options.inSampleSize = inSampleSize; // 设置为刚才计算的压缩比例
        Bitmap bm = BitmapFactory.decodeFile(path, options); // 解码文件
//        LogUtils.e("TAG", "size: " + bm.getByteCount() + " width: " + bm.getWidth() + " heigth:" + bm.getHeight()); // 输出图像数据
        return bm;
    }

    public static Bitmap getScreenBitmap(Activity activity, String path) {
        // 设置参数
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 只获取图片的大小信息，而不是将整张图片载入在内存中，避免内存溢出
        BitmapFactory.decodeFile(path, options);

//        int inSampleSize = calculateInSampleSize(activity, options); // 默认原比例
        options.inJustDecodeBounds = false; // 计算好压缩比例后，这次可以去加载原图了
        options.inSampleSize = 1;
        Bitmap bm = BitmapFactory.decodeFile(path, options); // 解码文件
        return bm;
    }


    /**
     * 根据图片的宽高,以定义的MAX_WIDTH和MAX_HEIGHT做参照，计算图片需要缩放的倍数
     **/
    private static int calculateInSampleSize(Activity activity, BitmapFactory.Options options) {
        final int MAX_HEIGHT = DensityUtil.getScreenHeight(activity);
        final int MAX_WIDTH = DensityUtil.getScreenWidth(activity);

        final int imageHeight = options.outHeight;
        final int imageWidth = options.outWidth;


        if (imageWidth <= MAX_WIDTH && imageHeight <= MAX_HEIGHT) {
            return 1;
        } else {
            double scale = imageWidth >= imageHeight ? imageWidth / MAX_WIDTH : imageHeight / MAX_HEIGHT;
            double log = Math.log(scale) / Math.log(2);
            double logCeil = Math.ceil(log);// 向上舍入
            return (int) Math.pow(2, logCeil);// 2的x数倍，因为图片的缩放处理是以2的整数倍进行的
        }
    }

    /**
     * 获取圆角矩形图片方法
     * @param bitmap
     *
     *
     */
    public static Bitmap getCircleBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }

        int size = Math.min(bitmap.getWidth(), bitmap.getHeight());
        int x = (bitmap.getWidth() - size) / 2;
        int y = (bitmap.getHeight() - size) / 2;

        Bitmap squared = Bitmap.createBitmap(bitmap, x, y, size, size);

        Bitmap result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);
        return result;
    }

    public static Bitmap bitmapWithImage(Context context, Bitmap bitmap, int resId, float scale, int roundPixels) {
        if(bitmap == null){
            return null;
        }
        try {
            int width, height;
            height = bitmap.getHeight();
            width = bitmap.getWidth();

            int tW = (int) (width * scale);
            int tH = (int) (height * scale);

            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale);
            Bitmap scaleBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            Canvas c = new Canvas(scaleBitmap);
            Paint paint = new Paint();
            paint.setAntiAlias(true);

            // image
            Bitmap imgBitmap = BitmapFactory.decodeResource(context.getResources(), resId);
            c.drawBitmap(imgBitmap, (tW / 2 - imgBitmap.getWidth() / 2), (tH / 2 - imgBitmap.getHeight() / 2), paint);

            Bitmap outBitmap;
            if(roundPixels == 0){
                outBitmap = scaleBitmap;
            }else{
                outBitmap = Bitmap.createBitmap(tW, tH, Bitmap.Config.ARGB_8888);
                Canvas c2 = new Canvas(outBitmap);
                Paint paint2 = new Paint();
                Rect srcRectF = new Rect(0, 0, tW, tH);
                RectF destRectF = new RectF(0, 0, tW, tH);
                paint2.setAntiAlias(true);
                c2.drawARGB(0, 0, 0, 0);
                paint2.setColor(-16777216);
                c2.drawRoundRect(destRectF, roundPixels, roundPixels, paint2);
                paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                c2.drawBitmap(scaleBitmap, srcRectF, destRectF, paint2);
            }

            return outBitmap;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    // 缩放图片
    public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight){
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        if (width ==0 || height == 0) {
            return bm;
        }
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = scaleWidth;
        if (newHeight != 0) {
            scaleHeight = ((float) newHeight) / height;
        }
//        float scale;
//        int whRate = width / height;
//        if (scaleHeight > scaleWidth) {
//            scale = scaleHeight;
//        } else {
//            scale = scaleWidth;
//        }

        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

    private static LruCache<String, Bitmap> mMemoryCache;
    private static int cacheSize = 0;

    /**
     * base64转为bitmap
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        Bitmap bitmap = null;
        byte[] bytes = null;
        if(cacheSize == 0){
            // 获取到可用内存的最大值，使用内存超出这个值会引起OutOfMemory异常。
            // LruCache通过构造函数传入缓存值，以KB为单位。
            long maxMemory = Runtime.getRuntime().maxMemory() / 1024;
            // 使用最大可用内存值的1/8作为缓存的大小。
            cacheSize = (int) (maxMemory / 8);
        }

        if(mMemoryCache == null){
            mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getByteCount() /1024;
                }
            };
        }

        if (mMemoryCache.get(base64Data) != null) {
            bitmap = mMemoryCache.get(base64Data);
        }

        if (bitmap == null) {

            BASE64Decoder decoder = new BASE64Decoder();
//            bytes = Base64.decode(base64Data, Base64.DEFAULT);
            try {
                bytes = decoder.decodeBuffer(base64Data);
            } catch (IOException e) {
                return null;
            }
            bitmap = byteToBitmap(base64Data,bytes);
        }

//        SoftReference softRef = new SoftReference(Base64.decode(base64Data, Base64.DEFAULT));
//        bytes = (byte[]) softRef.get();
        return bitmap;
    }

    public static Bitmap byteToBitmap(String base64Data, byte[] imgByte) {
        InputStream input = null;
        Bitmap bitmap = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
//            options.inTempStorage = new byte[5*1024*1024];
            input = new ByteArrayInputStream(imgByte);
            SoftReference softRef = new SoftReference(BitmapFactory.decodeStream(
                    input, null, options));
            bitmap = (Bitmap) softRef.get();
            softRef.clear();
            mMemoryCache.put(base64Data,bitmap);
//            if (imgByte != null) {
//                imgByte = null;
//            }
//
            if (input != null) {
                input.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            imgByte = null;
            try {
                input.close();
                System.gc();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    public static void handleBitmapByRxjava(String url, final RxjavaLoadBitmapInterf interf) {
        Observable.just(url)
                .map(new Function<String, Bitmap>() {
                    @Override
                    public Bitmap apply(@NonNull String url) throws Exception {
                        return ImageUtils.base64ToBitmap(url);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap bitmap) throws Exception {
                        interf.success(bitmap);
                    } });

    }

}
