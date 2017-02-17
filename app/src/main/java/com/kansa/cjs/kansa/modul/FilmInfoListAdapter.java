package com.kansa.cjs.kansa.modul;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kansa.cjs.kansa.R;

import java.util.ArrayList;

/**
 * Created by cjs on 2017/2/5.
 */

public class FilmInfoListAdapter extends BaseAdapter {
    private ArrayList<FilmData> filmdata;//电影数据
    private Context context;

    public FilmInfoListAdapter(ArrayList<FilmData> filmData, Context context) {
        this.filmdata = filmData;
        this.context = context;

    }

    @Override
    public int getCount() {
        return filmdata == null ? 0 : filmdata.size();
    }

    @Override
    public Object getItem(int i) {
        return filmdata == null ? 0 : filmdata.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        //找到View
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view1 = inflater.inflate(R.layout.film_info, null);

        //获取控件
        final ImageView film_imageView = (ImageView) view1.findViewById(R.id.film_image);
        TextView film_title= (TextView) view1.findViewById(R.id.film_title);
        TextView film_tag = (TextView) view1.findViewById(R.id.film_tag);
        TextView film_act = (TextView) view1.findViewById(R.id.film_act);
        TextView film_year = (TextView) view1.findViewById(R.id.film_year);

        //对控件赋值
        final FilmData filmData = (FilmData) getItem(position);
        final Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bitmap bitmap= (Bitmap) msg.obj;
                film_imageView.setImageBitmap(bitmap);
            }
        };
        if (filmData != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String url=filmData.getCover();//从网络加载图片
                    Bitmap bitmap=HttpUtility.getHttpBitmap(url);
                    Message message=new Message();
                    message.obj=bitmap;
                    handler.sendMessage(message);
                }
            }).start();

            //film_imageView.setImageBitmap(HttpUtility.getHttpBitmap(filmData.getCover()));
            film_tag.setText("类型："+filmData.getTag());
            film_act.setText("主演："+filmData.getAct());
            film_year.setText("年份："+filmData.getYear());
            film_title.setText(filmData.getTitle());
        }
        return view1;
    }
}
