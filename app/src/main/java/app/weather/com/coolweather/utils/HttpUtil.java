package app.weather.com.coolweather.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import app.weather.com.coolweather.db.CoolWeatherDB;
import app.weather.com.coolweather.model.CitiesResponse;
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




    public interface HttpCallbackListener {
        void onFinish(String response);

        void onError(Exception e);
    }
}
