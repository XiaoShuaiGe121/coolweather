package app.weather.com.coolweather.utils;

import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import app.weather.com.coolweather.db.CoolWeatherDB;
import app.weather.com.coolweather.model.City;
import app.weather.com.coolweather.model.County;
import app.weather.com.coolweather.model.Province;

/**
 * Created by Administrator on 2018/6/14.
 */

public class HttpUtil {

    /**
     * 异步网络请求（METHOD:get）
     *
     * @param address
     * @param listener
     */
    public static void sendHttpRequest(final String address, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    connection = (HttpURLConnection) new URL(address).openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(8000);
                    connection.setConnectTimeout(8000);
                    InputStream is = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    reader.close();
                    if (listener != null) {
                        listener.onFinish(stringBuilder.toString());
                    }
                } catch (IOException e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    /**
     * 解析response字符串，将解析后的Province对象存入数据库
     *
     * @param db
     * @param response
     * @return
     */
    public synchronized static boolean handleProvinceResponse(CoolWeatherDB db, String response) {
        if (!TextUtils.isEmpty(response)) {
            Gson gson = new Gson();
            List<Province> provinceList = gson.fromJson(response, new TypeToken<List<Province>>() {
            }.getType());
            for (Province province : provinceList) {
                db.saveProvince(province);
            }
            return true;
        }
        return false;
    }

    /**
     * 解析response字符串，将解析后的City对象存入数据库
     *
     * @param db
     * @param response
     * @param provinceId
     * @return
     */
    public synchronized static boolean handleCityResponse(CoolWeatherDB db, String response,int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            Gson gson = new Gson();
            List<City> cityList = gson.fromJson(response, new TypeToken<List<City>>() {
            }.getType());
            for (City city : cityList) {
                city.setProvinceId(provinceId);
                db.saveCity(city);
            }
            return true;
        }
        return false;
    }

    /**
     * 解析response字符串，将解析后的County对象存入数据库
     *
     * @param db
     * @param response
     * @param cityId
     * @return
     */
    public synchronized static boolean handleCountyResponse(CoolWeatherDB db, String response,int cityId) {
        if (!TextUtils.isEmpty(response)) {
            Gson gson = new Gson();
            List<County> countyList = gson.fromJson(response, new TypeToken<List<County>>() {
            }.getType());
            for (County county : countyList) {
                county.setCityId(cityId);
                db.saveCounty(county);
            }
            return true;
        }
        return false;
    }



    public interface HttpCallbackListener {
        void onFinish(String response);

        void onError(Exception e);
    }
}
