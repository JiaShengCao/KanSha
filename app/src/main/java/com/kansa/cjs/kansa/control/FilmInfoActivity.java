package com.kansa.cjs.kansa.control;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kansa.cjs.kansa.R;
import com.kansa.cjs.kansa.modul.HttpUtility;

/**
 * Created by cjs on 2017/2/19.
 */

public class FilmInfoActivity extends AppCompatActivity {
    private ImageButton play;//播放视屏按钮
    private ImageView film_img;//图片
    private TextView title;//片名
    private TextView tag;//类型
    private TextView year;//年份
    private TextView dir;//导演
    private TextView area;//区域
    private TextView act;//主演
    private TextView desc;//简介
    private String film_image = null, film_act = null, film_area = null, film_desc = null, film_tag = null,
            film_title = null, film_year = null, film_dir = null, playlink = null;
    //影片数据

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filminfo_layout);
        initdata();
        initview();
    }

    private void initdata() {
        Intent intent = getIntent();
        film_image = intent.getStringExtra("image");
        film_act = intent.getStringExtra("act");
        film_area = intent.getStringExtra("area");
        film_desc = intent.getStringExtra("desc");
        film_tag = intent.getStringExtra("tag");
        film_title = intent.getStringExtra("title");
        film_year = intent.getStringExtra("year");
        film_dir = intent.getStringExtra("dir");
        playlink = intent.getStringExtra("playlink");
        Log.d("dddddddddddd", "image is: " + film_image + "\n act is " + film_act + "\n area is " +
                film_area + "\n desc is " + film_desc + "\n filmtag is " + film_tag + "\n title is " +
                film_title + "\n year is" + film_year + "\n dir is" + film_dir);
    }

    private void initview() {
        play = (ImageButton) findViewById(R.id.play);
        film_img = (ImageView) findViewById(R.id.film_cover);
        title = (TextView) findViewById(R.id.film_title);
        tag = (TextView) findViewById(R.id.film_tag);
        year = (TextView) findViewById(R.id.film_year);
        dir = (TextView) findViewById(R.id.film_dir);
        area = (TextView) findViewById(R.id.film_area);
        act = (TextView) findViewById(R.id.film_act);
        desc = (TextView) findViewById(R.id.film_desc);
        //给imageview赋值
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                film_img.setImageBitmap((Bitmap) msg.obj);
            }
        };
        if (film_image != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = HttpUtility.getHttpBitmap(film_image);
                    Message message = new Message();
                    message.obj = bitmap;
                    handler.sendMessage(message);
                }
            }).start();
        }
        //给textview赋值
        title.setText("片名：" + film_title);
        tag.setText("类型：" + film_tag);
        year.setText("年份：" + film_year);
        dir.setText("导演：" + film_dir);
        area.setText("区域：" + film_area);
        act.setText("演员：" + film_act);
        desc.setText("介绍：" + film_desc);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playlink != null) {
                    Intent intent=new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    intent.setData(Uri.parse(playlink));
                    intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
                    startActivity(intent);
                } else {
                    Toast.makeText(FilmInfoActivity.this, "抱歉，搜索不到该影片数据", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
