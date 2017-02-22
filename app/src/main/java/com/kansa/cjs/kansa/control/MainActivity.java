package com.kansa.cjs.kansa.control;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kansa.cjs.kansa.R;
import com.kansa.cjs.kansa.modul.DecodeJson;
import com.kansa.cjs.kansa.modul.FilmData;
import com.kansa.cjs.kansa.modul.FilmInfoListAdapter;
import com.kansa.cjs.kansa.modul.GetPostUtil;
import com.kansa.cjs.kansa.widge.MyEditextWithDele;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ProgressBar myprogress;//进度
    private ImageButton btn_find;//搜索按钮
    private ImageButton btn_cancel;//取消按钮
    private MyEditextWithDele ET_testInfo;//文本输入框
    private String params;//查询参数
    private String key = "0f22f8075ffcc4d20c9348f5fb4137eb";//申请密钥
    private ListView filmlist;
    private ArrayList<FilmData> tempfilmDataGroup = new ArrayList<FilmData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();//初始化view
    }


    private void initview() {
        myprogress = (ProgressBar) findViewById(R.id.myprogress);
        filmlist = (ListView) findViewById(R.id.film_list);
        btn_cancel = (ImageButton) findViewById(R.id.Btn_cancel);
        btn_find = (ImageButton) findViewById(R.id.Btn_find);
        ET_testInfo = (MyEditextWithDele) findViewById(R.id.ET_info);
        //显示查询的结果
        final Handler mhandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0x123:
                        final ArrayList<FilmData> filmDataGroup = new ArrayList<FilmData>();
                        //加载一条主要数据

                        FilmData realdata = new FilmData();
                        try {
                            realdata = DecodeJson.decode(String.valueOf(msg.obj));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        filmDataGroup.add(realdata);
                        //得到相关影片名字信息
                        List<String> otherfilms = new ArrayList<>();
                        try {
                            otherfilms = DecodeJson.GteOtherFilm(String.valueOf(msg.obj));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //遍历相关推荐
                        Handler handler = new Handler() {
                            @Override
                            public void handleMessage(Message msg1) {
                                super.handleMessage(msg1);
                                try {
                                    FilmData tempdata = DecodeJson.decode(String.valueOf(msg1.obj));
                                    filmDataGroup.add(tempdata);//所有影片数据
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        for (int i = 0; i < otherfilms.size(); i++) {
                            params = "key=" + key + "&q=" + otherfilms.get(i);
                            new Thread(new AccessNetwork("GET", "http://op.juhe.cn/onebox/movie/video",
                                    params,
                                    handler)).start();

                        }
                        tempfilmDataGroup=filmDataGroup;
                        FilmInfoListAdapter adapter = new FilmInfoListAdapter(filmDataGroup, MainActivity
                                .this);
                        //隐藏进度显示listview
                        filmlist.setVisibility(View.VISIBLE);
                        myprogress.setVisibility(View.GONE);

                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                        filmlist.setAdapter(adapter);



                        break;
                    case 0x124:
                        Toast.makeText(MainActivity.this, "影片名不能为空", Toast.LENGTH_SHORT).show();
                        myprogress.setVisibility(View.GONE);
                        break;
                    case 0x125:
                        Toast.makeText(MainActivity.this, "查询不到该影片相关信息", Toast.LENGTH_SHORT).show();
                        myprogress.setVisibility(View.GONE);
                        break;
                    case 0x126:
                        Toast.makeText(MainActivity.this, "网络错误，请重试", Toast.LENGTH_SHORT).show();
                        myprogress.setVisibility(View.GONE);
                        break;
                    case 0x127:
                        Toast.makeText(MainActivity.this, "城市名不能为空", Toast.LENGTH_SHORT).show();
                        myprogress.setVisibility(View.GONE);
                        break;
                    case 0x128:
                        Toast.makeText(MainActivity.this, "查询不到该热映电影相关信息", Toast.LENGTH_SHORT).show();
                        myprogress.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }
        };
        //查询
        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                params = "key=" + key + "&q=" + ET_testInfo.getText().toString();
                //隐藏listview显示进图条
                filmlist.setVisibility(View.GONE);
                myprogress.setVisibility(View.VISIBLE);
                //强制解除软键盘
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(ET_testInfo.getWindowToken(), 0);


                new Thread(new AccessNetwork("GET", "http://op.juhe.cn/onebox/movie/video", params,
                        mhandler)).start();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        filmlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapterView.getItemAtPosition(i);
                FilmData tempdata = tempfilmDataGroup.get(i);
                Intent intent = new Intent(MainActivity.this, FilmInfoActivity.class);

                //得到链接数据
                HashMap<String, String> playlinks = tempdata.getPlaylinks();
                String playlink=null;
                String qq = playlinks.get("qq");
                String youku = playlinks.get("youku");
                String leshi = playlinks.get("leshi");
                String pptv = playlinks.get("ppty");
                String souhu = playlinks.get("souhu");
                if (qq != null) {
                    playlink = qq;
                } else if (youku != null) {
                    playlink = youku;
                } else if (leshi != null) {
                    playlink = leshi;
                } else if (pptv != null) {
                    playlink = pptv;
                } else if (souhu != null) {
                    playlink = souhu;
                }

                //activity跳转并传递数据
                intent.putExtra("image", tempdata.getCover()).putExtra("act", tempdata.getAct()).putExtra
                        ("area", tempdata.getArea()).putExtra("desc", tempdata.getDesc()).putExtra("tag",
                        tempdata.getTag()).putExtra("title", tempdata.getTitle()).putExtra("year", tempdata
                        .getYear()).putExtra("dir", tempdata.getDir()).putExtra("playlink",playlink);
                tempfilmDataGroup.clear();

                startActivity(intent);
            }
        });
    }
}

class AccessNetwork implements Runnable {
    private String op, url, params;
    private Handler handler;

    public AccessNetwork(String op, String url, String params, Handler handler) {
        this.op = op;
        this.handler = handler;
        this.params = params;
        this.url = url;
    }

    @Override
    public void run() {
        Message message = new Message();
        message.obj = GetPostUtil.SendGet(url, params);

        //初步判断是否获取到数据
        try {
            JSONObject jsonObject = new JSONObject(String.valueOf(message.obj));
            int error_code = jsonObject.getInt("error_code");//拿到error_code并进行判断
            if (error_code == 0) {
                message.what = 0x123;

            } else {
                //报错
                switch (error_code) {
                    case 209401:
                        message.what = 0x124;
                        break;
                    case 209402:
                        message.what = 0x125;
                        break;
                    case 209403:
                        message.what = 0x126;
                        break;
                    case 209404:
                        message.what = 0x127;
                        break;
                    case 209405:
                        message.what = 0x128;
                        break;
                    default:
                        break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        handler.sendMessage(message);
    }
}