/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.pacia.ptms.utils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;

public class FileUtils {
    private static String FILE_NAME = "ptms";

    public static File getSaveFile(Context context) {
        File file = new File(context.getFilesDir(), "pic.jpg");
        return file;
    }

    /**
     * 判断有无SDCard
     *
     * @return
     */
    public static boolean isSdCard() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
            return true;
        else
            return false;
    }

    /**
     * 创建文件夹
     *
     * @param context
     * @param name
     */
    public static String createFile(Context context, String name) {
        String dir;
        if (isSdCard()) {
            dir = Environment.getExternalStorageDirectory() + File.separator +
                    FILE_NAME + File.separator + name;
        } else {
            dir = context.getFilesDir().getAbsolutePath() + File.separator + FILE_NAME +
                    File.separator + name;
        }
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return dir;
    }

    //根据文件名获取路径
    public static String getPath(Context context, String name) {
        String dir;
        if (isSdCard()) {
            dir = Environment.getExternalStorageDirectory() + File.separator +
                    FILE_NAME + File.separator + name;
        } else {
            dir = context.getFilesDir().getAbsolutePath() + File.separator + FILE_NAME +
                    File.separator + name;
        }
        return dir;
    }

    public static String getPath(Context context) {
        String dir = getPath(context, "");
        return dir;
    }

    /**
     * 根据Uri 得到path
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getUriPath(Activity context, Uri uri) {
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.managedQuery
                    (uri, proj, null, null, null);
            int actual_image_column_index = cursor.getColumnIndexOrThrow
                    (MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String img_path = cursor.getString(actual_image_column_index);
            return img_path;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //删除文件
    public static void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                deleteFile(f);
            }
//            file.delete();//如要保留文件夹，只删除文件，请注释这行
        } else if (file.exists()) {
            file.delete();
        }
    }
}
