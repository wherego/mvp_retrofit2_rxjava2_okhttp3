package com.ecarx.car.netlive.utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class SharedPreferencesUtils {

    private static SharedPreferencesUtils instance;
    Context context;
    private SharedPreferences sp;
    public final String SP_NAME = "EcarxLiving";
    public static final String status_height = "status_height";
    public static final String recover_height = "recover_height";
    public static final String screen_height = "screen_height";

    public SharedPreferencesUtils(Context context) {
        this.context = context;
        sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferencesUtils getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesUtils(context);
        }
        return instance;
    }

    public SharedPreferences getSp() {
        if (instance == null) {
            instance = new SharedPreferencesUtils(context);
        }
        return sp;
    }

    public void cleanData() {
        sp.edit().clear().commit();
    }

    public void setString(String key, String value) {
        sp.edit().putString(key, value).commit();
    }

    public String getString(String key) {
        return sp.getString(key, "");
    }

    public int getInt(String key) {
        return sp.getInt(key, -1);
    }

    public void setInt(String key, int value) {
        sp.edit().putInt(key, value).commit();
    }


    public <V> void saveMap(String keyStr, Map<String, V> map) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        for (Entry<String, V> entry :
                map.entrySet()) {
            String key = entry.getKey();
            V value = entry.getValue();
            jsonObject.put(key, value);
        }
        sp.edit().putString(keyStr, jsonObject.toString()).commit();
    }

    public <V> Map<String, V> getMap(String keyStr) {
        JSONObject jsonObject;
        try {
//            String string = sp.getString(keyStr, "");
            jsonObject = new JSONObject(sp.getString(keyStr, ""));
        } catch (JSONException e) {
            return null;
        }
        if (jsonObject == null) {
            return null;
        }
        Map<String, V> map = new HashMap<>();
        JSONArray names = jsonObject.names();
        if (names != null) {
            for (int j = 0; j < names.length(); j++) {
                try {
                    String name;

                    name = names.getString(j);
                    V value = (V) jsonObject.get(name);
                    map.put(name, value);
                } catch (JSONException e) {
                    return null;
                }

            }
        }
        return map;
    }

    public boolean getFirstState(String key) {
        return sp.getBoolean(key, true);
    }

    public void setFirstState(String key, boolean value) {
        sp.edit().putBoolean(key, value).commit();
    }


}
