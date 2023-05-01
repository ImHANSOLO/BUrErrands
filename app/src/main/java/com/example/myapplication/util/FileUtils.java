package com.example.myapplication.util;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtils {

//    public static File getAppRootDirPath() {
//
//
//        return GlobalService.getAppContext().getExternalFilesDir(null).getAbsoluteFile();;
//    }
//    public static Uri uri;
//    public static File createImageFile(Context context, boolean isCrop) {
//        try {
//            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//            String fileName = "";
//            if (isCrop) {
//                fileName = "IMG_"+timeStamp+"_CROP.jpg";
//            } else {
//                fileName = "IMG_"+timeStamp+".jpg";
//            }
//            File rootFile = new File(getAppRootDirPath() + File.separator + "capture");
//            if (!rootFile.exists()) {
//                rootFile.mkdirs();
//            }
//            File imgFile;
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                imgFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + fileName);
//                // 通过 MediaStore API 插入file 为了拿到系统裁剪要保存到的uri（因为App没有权限不能访问公共存储空间，需要通过 MediaStore API来操作）
//                ContentValues values = new ContentValues();
//                values.put(MediaStore.Images.Media.DATA, imgFile.getAbsolutePath());
//                values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
//                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
//                uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//            }else {
//                imgFile = new File(rootFile.getAbsolutePath() + File.separator + fileName);
//            }
//            return imgFile;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
}
