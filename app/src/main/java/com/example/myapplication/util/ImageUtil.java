package com.example.myapplication.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import android.util.Base64;


//图片到字节流
public class ImageUtil {
    public static String imageToBase64(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); //字节输出流
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);   //压缩，输出
        byte[] buffer = byteArrayOutputStream.toByteArray();    //输出流放入数组
        String baseStr = Base64.encodeToString(buffer,Base64.DEFAULT); //接收数组，通过编码变成字节
        return baseStr;
    }
//字节流到图片
    public static Bitmap base64ToImage(String bitmap64){
        byte[] bytes = Base64.decode(bitmap64,Base64.DEFAULT);     //将字节解析放入比特数组
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);  //再将比特数组转化为图片
        return bitmap;
    }
}
