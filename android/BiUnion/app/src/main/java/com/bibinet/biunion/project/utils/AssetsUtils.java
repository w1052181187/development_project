package com.bibinet.biunion.project.utils;

import android.content.Context;

import com.bibinet.biunion.project.application.BiUnionApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * assets目录下数据读取的类
 * Created by 吴昊 on 2017-4-20.
 */

public class AssetsUtils {
    /**
     * @author 吴昊
     * @time 2017-4-20 9:26
     * 读取配置文件中的url
     * @param context
     * @param fileName 文件名
     * @return
     */
    public static String getPropertiesUrl(Context context, String fileName){
        BufferedReader br = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            br = new BufferedReader(new InputStreamReader(is));
            String len = "";
            String data = "";
            while((len=br.readLine()) != null){
                data+=len;
            }
            return data.replace("haseUrl = ", "");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            if(br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    /**
     * 根据文件名读取数据
     * @param fileName
     * @return
     */
    public static String getJsonFile(String fileName) {
        String json = "";
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(BiUnionApplication.context.getAssets().open(fileName),  "utf-8"));
            String len;
            while ((len = br.readLine()) != null) {
                json += len;
            }
            return json;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return json;
    }

    public static InputStream getInputStream(Context context, String fileName){
        try {
            InputStream is = context.getAssets().open(fileName);
            return is;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
