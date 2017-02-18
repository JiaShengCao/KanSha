package com.kansa.cjs.kansa.modul;

import java.util.HashMap;
import java.util.List;

/**
 * Created by cjs on 2017/2/5.
 * 影片数据对象
 */

public class FilmData {
    private String title; //名字
    private String tag;     //类型
    private String act;        //主演
    private String year;        //年份
    private String area;        //区域
    private String dir;         //导演
    private String desc;        //介绍
    private String cover;       //封面url
    private HashMap<String,String> playlinks;//播放地址

    public HashMap<String, String> getPlaylinks() {
        return playlinks;
    }

    public void setPlaylinks(HashMap<String, String> playlinks) {
        this.playlinks = playlinks;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
