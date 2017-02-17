package com.kansa.cjs.kansa.modul;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cjs on 2017/1/25.
 */

public class DecodeJson {
    /**
     * 用于解析json数据
     * 输入参数：message ：解析的json数据
     * 输出参数：Filmdata
     */

    public static FilmData decode(String message) throws JSONException {
        FilmData filmData = new FilmData();

        JSONObject messageToJson = new JSONObject(message);
        JSONObject result = messageToJson.getJSONObject("result");
        String desc = result.getString("desc");
        String cover = result.getString("cover");
        String title = result.getString("title");
        String tag = result.getString("tag");
        String act = result.getString("act");
        String year = result.getString("year");
        String temp = null;

        HashMap<String, String> playlink = new HashMap<>();
        JSONObject playlinks = result.getJSONObject("playlinks");
        if (playlinks.has("qq")) {
            temp = playlinks.getString("qq");
        } else {
            temp = null;
        }
        playlink.put("qq", temp);
        if (playlinks.has("youku")) {
            temp = playlinks.getString("youku");
        } else {
            temp = null;
        }
        playlink.put("youku", temp);
        if (playlinks.has("leshi")) {
            temp = playlinks.getString("leshi");
        } else {
            temp = null;
        }
        playlink.put("leshi", temp);
        if (playlinks.has("pptv")) {
            temp = playlinks.getString("pptv");
        } else {
            temp = null;
        }
        playlink.put("pptv", temp);
        if (playlinks.has("sohu")) {
            temp = playlinks.getString("sohu");
        } else {
            temp = null;
        }
        playlink.put("sohu", temp);
        Log.e("eeeeeeeee", "links of qq is : " + playlink.get("qq") + "\nlinks of youku is: " + playlink.get
                ("youku") + "\nlinks of pptv is :" + playlink.get("pptv") + "\nlinks of leshi is: " +
                playlink.get("leshi")+"\n links of sohu is"+playlink.get("sohu"));

        filmData.setPlaylinks(playlink);
        filmData.setCover(cover);
        filmData.setDesc(desc);
        filmData.setAct(act);
        filmData.setTitle(title);
        filmData.setTag(tag);
        filmData.setYear(year);

        Log.e("eeeeeeeeeeeee", "cover is " + cover + "  title is " + title + "  tag is: " + tag);
        return filmData;
    }

    public static List<String> GteOtherFilm(String message) throws JSONException {
        List<String> list_of_name = new ArrayList<>();
        JSONObject messageToJson = new JSONObject(message);
        JSONObject result = messageToJson.getJSONObject("result");
        JSONArray video_rec = result.getJSONArray("video_rec");
        for (int i = 0; i < video_rec.length(); i++) {
            JSONObject temp = (JSONObject) video_rec.get(i);
            String name = temp.getString("title");
            list_of_name.add(name);
        }
        return list_of_name;
    }
}
