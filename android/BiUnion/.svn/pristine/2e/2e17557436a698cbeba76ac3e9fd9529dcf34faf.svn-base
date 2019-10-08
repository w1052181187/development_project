package com.bibinet.biunion.project.utils;

        import android.os.Environment;

        import java.io.File;
        import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.text.DecimalFormat;
        import java.io.FileInputStream;

/**
 * Created by 吴昊 on 2017-5-8.
 */

public class FileUtils {

    public static boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 创建文件
     *
     * @param path
     * @param fileName
     * @return
     */
    public static File creatFile(String path, String fileName) {
        File file = new File(checkFileDir(path), fileName);

        return file;
    }

    /**
     * 检查目录是否存在，不存在则创建
     *
     * @param path
     * @return
     */
    public static File checkFileDir(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public static void clearDir(String path) {
        File file = new File(path);
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                clearDir(f.getPath());
                f.delete();
            }
        }
        file.delete();
    }

    public static long getFileSize(File f) {
        try {
            long size = 0;
            File flist[] = f.listFiles();
            for (int i = 0; i < flist.length; i++) {
                if (flist[i].isDirectory()) {
                    size = size + getFileSize(flist[i]);
                } else {
                    size = size + flist[i].length();
                }
            }
            return size;
        }catch (Exception e){

        }
        return -1;
    }


    public static String formetFileSize(String path) {// 转换文件大小
        long fileS = getFileSize(new File(path));
        if(fileS <= 0){
            return "0.00M";
        }
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 将输入流写入文件
     *
     * @param inputString
     * @param filePath
     */
    public static boolean writeFile(InputStream inputString, String filePath, String fileName) {

        if (inputString == null) {
          return false;
        }
        File file = creatFile(filePath, fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);

            byte[] b = new byte[1024];

            int len;
            while ((len = inputString.read(b)) != -1) {
                fos.write(b,0,len);
            }
            inputString.close();
            fos.close();
            return true;

        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }

    }

}
