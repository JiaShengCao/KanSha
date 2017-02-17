package com.kansa.cjs.kansa.modul;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cjs on 2017/2/5.
 */

public class HttpUtility {
    private static Map<String,Bitmap> bitmapMapCache=new HashMap<String,Bitmap>();
    /**用于获取网络图片
     * 参数：url：图片地址
     * 返回值：bitmap
     * */
    public static Bitmap getHttpBitmap(String url){
        //首先查看缓存区有没有图片
        Bitmap bitmap=bitmapMapCache.get(url);
        //如果有
        if (bitmap!=null){
            return bitmap;
        }
        //如果没有,重新下载
        try {
            URL myFileurl=new URL(url);
            HttpURLConnection conn=(HttpURLConnection) myFileurl.openConnection();
            conn.setConnectTimeout(6000);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.connect();
            InputStream inputStream=conn.getInputStream();
            bitmap= BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
            //添加到缓存区
            if (bitmap!=null){
                bitmapMapCache.put(url,bitmap);
            }
        return bitmap;
    }


}
