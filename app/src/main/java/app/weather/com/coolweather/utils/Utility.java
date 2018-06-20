package app.weather.com.coolweather.utils;

import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import app.weather.com.coolweather.db.CoolWeatherDB;
import app.weather.com.coolweather.model.CitiesResponse;
import app.weather.com.coolweather.model.City;
import app.weather.com.coolweather.model.County;
import app.weather.com.coolweather.model.Province;

/**
 * Created by Administrator on 2018/6/17.
 */

public class Utility {
    /**
     * 解析response字符串，将解析后的County对象存入数据库
     *
     * @param db
     * @param response
     * @return
     */
    public synchronized static void handleJSONResponse(CoolWeatherDB db, String response) {
        if (!TextUtils.isEmpty(response)) {
            Gson gson = new Gson();
            CitiesResponse citiesResponse = gson.fromJson(response, CitiesResponse.class);
            List<CitiesResponse.ObjectBean> beans = citiesResponse.getResult();

            List<Province> provinceList = new ArrayList<Province>();
            List<City> cityList = new ArrayList<City>();
            List<County> countyList = new ArrayList<County>();



            int provinceId = 0;
            int cityId = 0;
            for (CitiesResponse.ObjectBean bean : beans) {
                //p
                Province p = new Province();
                p.setProvinceName(bean.getProvince());
                if (!provinceList.contains(p)) {
                    provinceList.add(p);
                    provinceId++;
                }
                //city
                City city = new City();
                city.setCityName(bean.getCity());
                city.setProvinceId(provinceId);
                if (!cityList.contains(city)) {
                    cityList.add(city);
                    cityId++;
                }
                //county
                County county = new County();
                county.setCountyName(bean.getDistrict());
                county.setCountyCode(bean.getId());
                county.setCityId(cityId);
                if (!countyList.contains(county)) {
                    countyList.add(county);
                }
            }
            addData(db, provinceList, cityList, countyList);
        }
    }

    private static void addData(CoolWeatherDB db, List<Province> provinceList, List<City> cityList, List<County> countyList) {
        for (Province province : provinceList) {
            db.saveProvince(province);
        }
        for (City city : cityList) {
            db.saveCity(city);
        }
        for (County county : countyList) {
            db.saveCounty(county);
        }
    }

}
