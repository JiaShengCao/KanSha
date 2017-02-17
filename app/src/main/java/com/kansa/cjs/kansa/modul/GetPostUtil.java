package com.kansa.cjs.kansa.modul;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created by cjs on 2017/1/20.
 */

public class GetPostUtil {
/**
 * 使用GET方式进行网络请求
 * 参数说明：
 * url：请求地址，params请求体
 * 返回值：请求结果。
*/
    public static String SendGet(String url,String params){
        String result="";
        BufferedReader br=null;
        try {
            String urlName=url+"?"+params;
            Log.i("iiiiiiiiiii","发送的消息>>>>>>>>>>>>>"+urlName);
            URL url1=new URL(urlName);
            //建立连接
            URLConnection conn=url1.openConnection();
            //设置请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.connect();
            Map<String,List<String>> map =conn.getHeaderFields();
            for (String key:map.keySet()){
                Log.i("result   ",key+">>>>>>>>>>>>>>"+map.get(key));
            }
            br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line=br.readLine())!=null)
            {
                result+="\n"+line;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Log.i("iiiiiiiiiiii","返回的消息>>>>>>>"+result);
            return result;
        }

    }
}
