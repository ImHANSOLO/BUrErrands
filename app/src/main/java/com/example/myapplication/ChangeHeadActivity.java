package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.util.DBHelper;
import com.example.myapplication.util.ImageUtil;
import com.example.myapplication.util.User;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class ChangeHeadActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_TAKE = 1;
    public static final int REQUEST_CODE_CHOOSE = 0;
    public static final int REQUEST_CODE_CROPPING = 2;
    private Uri imageUri;
    private ImageView ivAvatar;
    private String headImageBase64, imagePath;
    private DBHelper dbHelper;
    File imageCropFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "crop_image.jpg");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_head);

        ivAvatar = findViewById(R.id.iv_headView);

        dbHelper = new DBHelper(this);//private DBHelper dbHelper = new DBHelper(this)
        showUserHeadImage();
    }

    private void showUserHeadImage() {                            //在更换头像界面显示当前图片
        ArrayList<User> userData = dbHelper.getData();
        User user = userData.get(LoginActivity.getUserId()); //找到当前用户

        if (user.getHeadImage() == null) {             //一开始表中的数据是null而不是empty
            ivAvatar.setImageResource(R.mipmap.avatar);
        } else {
            ivAvatar.setImageBitmap(ImageUtil.base64ToImage(user.getHeadImage()));//若头像不为空，则取出字节，转化为图片，再显示出来
        }
    }

    public void takePhoto(View view) {        //检查是否申请权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            doTakePhoto();           //拍照
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_TAKE);
        }   //申请权限
    }

    public void choosePhoto(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            //openAlbum();               //打开相册
            choosePicture();

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_CHOOSE);
        }   //申请权限
    }

    @Override                      //检查申请权限的结果
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_TAKE) {           //拍照
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                doTakePhoto();
            } else {
                Toast.makeText(this, "No Permission", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_CODE_CHOOSE) {    //访问本地图片
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //openAlbum();
                choosePicture();
            } else {
                Toast.makeText(this, "No Permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void doTakePhoto() {
        File imageTemp = new File(getExternalCacheDir(), "/imageOut.jpeg");  //创建空文件夹和文件用来存放图片
        if (imageTemp.exists()) {
            imageTemp.delete();        //读取最新的
        }

        try {
            imageTemp.createNewFile();         //检查文件操作错误
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= 24) {        //API版本
            //contentProvider,内容提供
            imageUri = FileProvider.getUriForFile(this, "com.example.myfirst.fileprovider", imageTemp);
        } else {
            imageUri = Uri.fromFile(imageTemp);
        }                                          //从文件获取uri

        Intent intent = new Intent();
        intent.setAction("android.media.action.IMAGE_CAPTURE");//调用摄像头
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);        //拍完照后，图片放入imageUri的路径
        startActivityForResult(intent, REQUEST_CODE_TAKE);
    }

    @Override                 //根据不同的请求码进行处理
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_TAKE && resultCode == RESULT_OK) {
            try {                                           //获取拍摄的照片
                InputStream inputStream = getContentResolver().openInputStream(imageUri);   //将读取的数据转化为输入流，二进制
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);     //将输入流解析，创建一个图片

                ivAvatar.setImageBitmap(bitmap);                           //显示拍摄的照片
                headImageBase64 = ImageUtil.imageToBase64(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {

            handleImageOnApi(data);
            pictureCropping(getMediaUriFromPath(this,imagePath));

        } else if (requestCode == REQUEST_CODE_CROPPING && resultCode == RESULT_OK) {
            //图片剪裁返回
            Bitmap bitmap = BitmapFactory.decodeFile(imageCropFile.getPath());
            ivAvatar.setImageBitmap(bitmap);
            headImageBase64 = ImageUtil.imageToBase64(bitmap);
        }
    }

    private void choosePicture() {
        Intent intent;
        if (Build.VERSION.SDK_INT >= 29) {// Android 11 (API level 30)
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("image/*");
            Intent.createChooser(intent, null);
        }
        startActivityForResult(intent, REQUEST_CODE_CHOOSE);
    }

    private void handleImageOnApi(Intent data) {
        imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String documentId = DocumentsContract.getDocumentId(uri);

            if (TextUtils.equals(uri.getAuthority(), "com.android.providers.media.documents")) {
                String id = documentId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if (TextUtils.equals(uri.getAuthority(), "com.android.providers.downloads.documents")) {
                if (documentId != null && documentId.startsWith("msf:")) {
                    resolveMSFContent(uri);
                    return;
                }
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
    }

    private void resolveMSFContent(Uri uri) {
        File file = new File(getCacheDir(), "temp_file" + getContentResolver().getType(uri).split("/")[1]);

        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);

            OutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[4 * 1024];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("Range")
    public static Uri getMediaUriFromPath(Context context, String path) {
        Uri mediaUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = context.getContentResolver().query(mediaUri,
                null,
                MediaStore.Images.Media.DISPLAY_NAME + "= ?",
                new String[] {path.substring(path.lastIndexOf("/") + 1)},
                null);

        Uri uri = null;
        if(cursor.moveToFirst()) {
            uri = ContentUris.withAppendedId(mediaUri,
                    cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID)));
        }
        cursor.close();
        return uri;
    }

    @SuppressLint("Range")
    private String getImagePath(Uri uri, String selection) {         //低版本
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private File pictureCropping(Uri uri) {
//        File imageCropFile  = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),"crop_image.jpg");
        // Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null,null));
        if (imageCropFile != null) {

            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            intent.setDataAndType(uri, "image/*"); //设置数据源
            intent.putExtra("crop", "true");

            intent.putExtra("aspectX", 1);    //X方向上的比例
            intent.putExtra("aspectY", 1);    //Y方向上的比例
            intent.putExtra("outputX", 256);  //裁剪区的宽
            intent.putExtra("outputY", 256); //裁剪区的高
            intent.putExtra("scale ", true);  //是否保留比例
            intent.putExtra("return-data", true);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageCropFile));

            startActivityForResult(intent, REQUEST_CODE_CROPPING);

        }
        return imageCropFile;
    }

    public void saveHead(View view) {                   //保存头像
        dbHelper.updateHeadImage(headImageBase64);
        Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
        SlideActivity.slideActivity.finish();                                  //销毁跳转过来的活动，因为其信息没有更新
        Intent intent = new Intent(this, SlideActivity.class);
        startActivity(intent);
        finish();
    }

    private void openAlbum() {
        Intent intent;              //Intent.ACTION_GET_CONTENT必须设置setType("image/*")表示返回的数据类型，

        intent = new Intent(Intent.ACTION_GET_CONTENT);

        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_CHOOSE);    //Intent.ACTION_GET_CONTENT必须设置setType("image/*")表示返回的数据类型，
    }

}
