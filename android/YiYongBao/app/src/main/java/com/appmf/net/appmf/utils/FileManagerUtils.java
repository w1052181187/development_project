package com.appmf.net.appmf.utils;


import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.appmf.net.appmf.bean.file.FileParamsBean;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.io.File;
import java.io.FileFilter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.appmf.net.appmf.app.Constants.FILE_MANAGER_RESOURSE_DIR;
import static com.appmf.net.appmf.app.Constants.FILE_PATH_BASE;
import static com.appmf.net.appmf.app.Constants.FILE_PATH_SD;

public class FileManagerUtils {

    public static String getRealPath(String path){
//        if (TextUtils.isEmpty(path)) {
//            return "";
//        }
        return FILE_PATH_BASE + "/" + path;
    }

    public static String getRealFastPath(String path){
//        if (TextUtils.isEmpty(path)) {
//            return "";
//        }
        return FILE_MANAGER_RESOURSE_DIR + "/" + path;
    }

    public static boolean checkFileName(String name) {
        //首先,使用Pattern解释要使用的正则表达式，其中^表是字符串的开始，$表示字符串的结尾。
        Pattern pt = Pattern.compile("^[0-9a-zA-Z_]+$");
        //然后使用Matcher来对比目标字符串与上面解释得结果
        Matcher mt = pt.matcher(name);
        //如果能够匹配则返回true。实际上还有一种方法mt.find()，某些时候，可能不是比对单一的一个字符串，
        //可能是一组，那如果只要求其中一个字符串符合要求就可以用find方法了.
        return mt.matches();
    }

    /**
     * 处理手机系统特定类型下所有文件列表
     * @param paramFile
     */
    public static JsonElement handleSysAllFile(Context context, FileParamsBean paramFile) {
        ArrayList<File> fileLists = new ArrayList<>();
        String fileType = paramFile.getType();
//        final String filePath = paramFile.getPath();
        JsonObject jsonObject =  new JsonObject();
        switch (fileType) {
            case "photos":
                JsonArray jsonArray = new JsonArray();
                jsonArray.add(getAllLocalPhotos(context));
                jsonArray.add(getAllLocalVideos(context));
                jsonObject.addProperty("file", jsonArray.toString());
                break;
            case "photo":
                jsonObject.addProperty("file", getAllLocalPhotos(context).toString());
                break;
            case "video":
                jsonObject.addProperty("file", getAllLocalVideos(context).toString());
                break;
            case "audio":
                jsonObject.addProperty("file", getAllLocalAudios(context).toString());
                break;
            case "doc":
                JsonPrimitive primitive = new JsonPrimitive(FILE_PATH_SD);
                paramFile.setPath(primitive);
                paramFile.setType("doc");
                jsonObject = (JsonObject) handleCommonFile(paramFile);
                break;
            case "zip":
                JsonPrimitive primitiveZip = new JsonPrimitive(FILE_PATH_SD);
                paramFile.setPath(primitiveZip);
                paramFile.setType("zip");
                jsonObject = (JsonObject) handleCommonFile(paramFile);
                break;
            case "apk":
                JsonPrimitive primitiveApk = new JsonPrimitive(FILE_PATH_SD);
                paramFile.setPath(primitiveApk);
                paramFile.setType("apk");
                jsonObject = (JsonObject) handleCommonFile(paramFile);
                break;
            default:
                break;
        }

        int page = paramFile.getPage();
        if (page > 0) {
            // 开启分页
            int limit = paramFile.getLimit();
            int fromIndex = (page - 1) * limit;
            int endIndex = page * limit - 1;
            if (endIndex > fileLists.size()) {
                endIndex = fileLists.size() - 1;
            }
            fileLists = (ArrayList<File>) fileLists.subList(fromIndex, endIndex);
        }
        switch (paramFile.getOrder()) {
            case "time":
                Collections.sort(fileLists, new Comparator<File>() {
                    @Override
                    public int compare(File o1, File o2) {
                        if (FileUtils.getFileLastModified(o1) < FileUtils.getFileLastModified(o2)) {
                            return 1;// 最后修改的文件在前
                        } else {
                            return -1;
                        }
                    }
                });
                break;
            case "name":
                Collections.sort(fileLists, new Comparator<File>() {
                    @Override
                    public int compare(File o1, File o2) {
                        return FileUtils.getFileName(o2).compareTo(FileUtils.getFileName(o1));
                    }
                });
                break;
        }
        JsonArray jsonArray =  new JsonArray();
        String unit = paramFile.getUnit();
        for (File file : fileLists) {
            jsonArray.add(generateFileInfo(file, unit));
        }
        jsonObject.add("file", jsonArray);
        return jsonObject;
    }

    /**
     *  处理指定目录下文件
     * @param paramFile
     */
    public static JsonElement handleCommonFile(FileParamsBean paramFile) {
        ArrayList<File> fileLists = new ArrayList<>();
        String fileType = paramFile.getType();
        String filePath = FILE_PATH_BASE;
        if (paramFile.getPath().isJsonPrimitive()) {
            filePath = getRealPath(paramFile.getPath().getAsJsonPrimitive().getAsString());
        }
        switch (fileType) {
            case "all":
                fileLists.addAll(FileUtils.listFilesInDir(filePath));
                break;
            case "dir":
                fileLists.addAll(FileUtils.listFilesInDirWithFilter(filePath, new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        return pathname.isDirectory();
                    }
                }));
                break;
            case "file":
                fileLists.addAll(FileUtils.listFilesInDirWithFilter(filePath, new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        return pathname.isFile();
                    }
                }));
                break;
            case "photo":
                fileLists.addAll(FileUtils.listFilesInDirWithFilter(filePath, new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        return FileUtils.isImage(pathname);
                    }
                }));
                break;
            case "video":
                fileLists.addAll(FileUtils.listFilesInDirWithFilter(filePath, new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        return FileUtils.isVideo(pathname);
                    }
                }));
                break;
            case "audio":
                fileLists.addAll(FileUtils.listFilesInDirWithFilter(filePath, new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        return FileUtils.isAudio(pathname);
                    }
                }));
                break;
            case "doc":
                fileLists.addAll(FileUtils.listFilesInDirWithFilter(filePath, new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        return FileUtils.isDoc(pathname);
                    }
                }));
                break;
            case "zip":
                fileLists.addAll(FileUtils.listFilesInDirWithFilter(filePath, new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        return FileUtils.isZip(pathname);
                    }
                }));
                break;
            case "apk":
                fileLists.addAll(FileUtils.listFilesInDirWithFilter(filePath, new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        return FileUtils.isApk(pathname);
                    }
                }));
                break;
            case "custom":
                final ArrayList<String> suffixs = paramFile.getExtension();
                fileLists.addAll(FileUtils.listFilesInDirWithFilter(filePath, new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        return suffixs.contains(FileUtils.getFileExtension(pathname));
                    }
                }));
                break;
            default:
                break;
        }

        int page = paramFile.getPage();
        if (page > 0) {
            // 开启分页
            int limit = paramFile.getLimit();
            int fromIndex = (page - 1) * limit;
            int endIndex = fileLists.size() - fromIndex;
            if (fromIndex < endIndex) {
                fileLists = (ArrayList<File>) fileLists.subList(fromIndex, endIndex);
            }
        }
        JsonArray jsonArray =  new JsonArray();
        JsonObject jsonObject =  new JsonObject();
        String unit = paramFile.getUnit();
        for (File file : fileLists) {
            jsonArray.add(generateFileInfo(file, unit));
        }
        jsonObject.add("file", jsonArray);
        return jsonObject;
    }

    public static JsonObject generateFileInfo(File file, String unit) {
        JsonObject object = new JsonObject();
        object.addProperty("path", file.getAbsolutePath());
        object.addProperty("name", file.getName());
        object.addProperty("extension", FileUtils.getFileExtension(file));
        object.addProperty("size", formetFileSize(file, unit));
        object.addProperty("createTime", FileUtils.getFileCreatTime(file));
        object.addProperty("updateTime", FileUtils.getFileLastModified(file));
        if (FileUtils.isDir(file)) {
            object.addProperty("type", "dir");
        } else if (FileUtils.isFile(file)) {
            if (FileUtils.isImage(file)) {
                object.addProperty("type", "photo");
            } else if (FileUtils.isVideo(file)) {
                object.addProperty("type", "video");
            } else if (FileUtils.isAudio(file)) {
                object.addProperty("type", "audio");
            } else if (FileUtils.isDoc(file)) {
                object.addProperty("type", "doc");
            } else if (FileUtils.isZip(file)) {
                object.addProperty("type", "zip");
            } else if (FileUtils.isApk(file)) {
                object.addProperty("type", "apk");
            } else {
                object.addProperty("type", FileUtils.getFileExtension(file));
            }
        }
        return object;
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    private static String formetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 转换文件大小,指定转换的类型
     *
     * @param file
     * @param sizeType
     * @return
     */
    private static String formetFileSize(File file, String sizeType) {
        long fileS = FileUtils.isDir(file) ? FileUtils.getDirLength(file) : FileUtils.getFileLength(file);
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS == 0) {
            return "0B";
        }
        switch (sizeType.toLowerCase()) {
            case "b":
                fileSizeString = df.format((double) fileS) + "B";
                break;
            case "kb":
                fileSizeString = df.format((double) fileS / 1024) + "KB";
                break;
            case "mb":
                fileSizeString = df.format((double) fileS / 1048576) + "MB";
                break;
            case "gb":
                fileSizeString = df.format((double) fileS / 1073741824) + "GB";
                break;
            default:
                fileSizeString = formetFileSize(fileS);
                break;
        }
        return fileSizeString;
    }

    /**
     * 获取本地所有的图片
     *
     * @return list
     */
    public static JsonArray getAllLocalPhotos(Context context) {
        JsonArray jsonArray =  new JsonArray();
        String[] projection = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE
        };
        //全部图片
        String where = MediaStore.Images.Media.MIME_TYPE + "=? or "
                + MediaStore.Images.Media.MIME_TYPE + "=? or "
                + MediaStore.Images.Media.MIME_TYPE + "=? or "
                + MediaStore.Images.Media.MIME_TYPE + "=? or "
                + MediaStore.Images.Media.MIME_TYPE + "=? or "
                + MediaStore.Images.Media.MIME_TYPE + "=?";
        //指定格式
        String[] whereArgs = {"image/jpeg", "image/png", "image/jpg", "image/gif", "image/bmp", "image/tif"};
        //查询
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, where, whereArgs,
                MediaStore.Images.Media.DATE_MODIFIED + " desc ");
        if (cursor == null) {
            return jsonArray;
        }
        try {
            //遍历
            while (cursor.moveToNext()) {
                int index = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
    //            int thumbId = cursor
    //                    .getColumnIndexOrThrow(MediaStore.Images.Media._ID);
    //
    //            BitmapFactory.Options options = new BitmapFactory.Options();
    //            options.inDither = false;
    //            options.inPreferredConfig = Bitmap.Config.RGB_565;
    //            Bitmap bitmap = MediaStore.Images.Thumbnails.getThumbnail(context.getContentResolver(), thumbId, MediaStore.Images.Thumbnails.MINI_KIND,options);
                String path = cursor.getString(index);
                File file = new File(path);

                if (FileUtils.isFileExists(file)) {
                    JsonObject object = new JsonObject();
                    object.addProperty("path", file.getAbsolutePath());
    //                object.addProperty("thum", "");
                    object.addProperty("name", file.getName());
                    object.addProperty("extension", FileUtils.getFileExtension(file));
                    object.addProperty("size",FileUtils.isDir(file) ? FileUtils.getDirLength(file) : FileUtils.getFileLength(file));
                    object.addProperty("createTime", FileUtils.getFileCreatTime(file));
                    object.addProperty("updateTime", FileUtils.getFileLastModified(file));
                    object.addProperty("type", "photo");
                    jsonArray.add(object);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return jsonArray;
    }

    /**
     * 获取本地所有的视频
     *
     * @return list
     */
    public static JsonArray getAllLocalVideos(Context context) {
        JsonArray jsonArray =  new JsonArray();
        String[] projection = {
                MediaStore.Video.Media.DATA
        };
        //全部图片
        String where = MediaStore.Images.Media.MIME_TYPE + "=? or "
                + MediaStore.Video.Media.MIME_TYPE + "=? or "
                + MediaStore.Video.Media.MIME_TYPE + "=? or "
                + MediaStore.Video.Media.MIME_TYPE + "=? or "
                + MediaStore.Video.Media.MIME_TYPE + "=? or "
                + MediaStore.Video.Media.MIME_TYPE + "=? or "
                + MediaStore.Video.Media.MIME_TYPE + "=? or "
                + MediaStore.Video.Media.MIME_TYPE + "=? or "
                + MediaStore.Video.Media.MIME_TYPE + "=?";
        String[] whereArgs = {"video/mp4", "video/3gp", "video/avi", "video/rmvb", "video/vob", "video/flv",
                "video/mkv", "video/mov", "video/mpg"};
        Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                projection, where, whereArgs, MediaStore.Video.Media.DATE_MODIFIED + " DESC ");
        if (cursor == null) {
            return jsonArray;
        }
        try {
            while (cursor.moveToNext()) {
                int index = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);String path = cursor.getString(index);
                File file = new File(path);

                if (FileUtils.isFileExists(file)) {
                    JsonObject object = new JsonObject();
                    object.addProperty("path", file.getAbsolutePath());
//                object.addProperty("thum", "");
                    object.addProperty("name", file.getName());
                    object.addProperty("extension", FileUtils.getFileExtension(file));
                    object.addProperty("size",FileUtils.isDir(file) ? FileUtils.getDirLength(file) : FileUtils.getFileLength(file));
                    object.addProperty("createTime", FileUtils.getFileCreatTime(file));
                    object.addProperty("updateTime", FileUtils.getFileLastModified(file));
                    object.addProperty("type", "video");
                    jsonArray.add(object);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return jsonArray;
    }

    /**
     * 获取本地所有的视频
     *
     * @return list
     */
    public static JsonArray getAllLocalAudios(Context context) {
        JsonArray jsonArray =  new JsonArray();
        String[] projection = {
                MediaStore.Video.Media.DATA
        };
        //全部图片
        String where = MediaStore.Images.Media.MIME_TYPE + "=? or "
                + MediaStore.Video.Media.MIME_TYPE + "=? or "
                + MediaStore.Video.Media.MIME_TYPE + "=? or "
                + MediaStore.Video.Media.MIME_TYPE + "=? or "
                + MediaStore.Video.Media.MIME_TYPE + "=? or "
                + MediaStore.Video.Media.MIME_TYPE + "=? or "
                + MediaStore.Video.Media.MIME_TYPE + "=? or "
                + MediaStore.Video.Media.MIME_TYPE + "=? or "
                + MediaStore.Video.Media.MIME_TYPE + "=? or "
                + MediaStore.Video.Media.MIME_TYPE + "=?";
        String[] whereArgs = {"audio/wav", "audio/mp3", "audio/wma", "audio/aif", "audio/au", "audio/ram",
                "audio/mmf", "audio/amr", "audio/aac", "audio/flac"};
        Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                projection, where, whereArgs, MediaStore.Video.Media.DATE_MODIFIED + " DESC ");
        if (cursor == null) {
            return jsonArray;
        }
        try {
            while (cursor.moveToNext()) {
                int index = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);String path = cursor.getString(index);
                File file = new File(path);

                if (FileUtils.isFileExists(file)) {
                    JsonObject object = new JsonObject();
                    object.addProperty("path", file.getAbsolutePath());
//                object.addProperty("thum", "");
                    object.addProperty("name", file.getName());
                    object.addProperty("extension", FileUtils.getFileExtension(file));
                    object.addProperty("size",FileUtils.isDir(file) ? FileUtils.getDirLength(file) : FileUtils.getFileLength(file));
                    object.addProperty("createTime", FileUtils.getFileCreatTime(file));
                    object.addProperty("updateTime", FileUtils.getFileLastModified(file));
                    object.addProperty("type", "audio");
                    jsonArray.add(object);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return jsonArray;
    }

}
