package app.weather.com.coolweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import app.weather.com.coolweather.model.CitiesResponse;
import app.weather.com.coolweather.model.City;
import app.weather.com.coolweather.model.County;
import app.weather.com.coolweather.model.Province;

/**
 * Created by Administrator on 2018/6/14.
 */

public class CoolWeatherDB {
    private static final String DB_NAME = "coolweather";
    private static final int DB_VERSION = 1;

    private CoolWeatherOpenHelper helper;
    private SQLiteDatabase db;
    private static CoolWeatherDB coolWeatherDB;


    private CoolWeatherDB(Context context) {
        helper = new CoolWeatherOpenHelper(context, DB_NAME, null, DB_VERSION);
        db = helper.getWritableDatabase();
    }

    public synchronized static CoolWeatherDB getInstance(Context context) {
        if (coolWeatherDB == null) {
            coolWeatherDB = new CoolWeatherDB(context);
        }
        return coolWeatherDB;
    }


    /**
     * 将City实例存储到数据库。
     *
     * @param city
     */
    public void saveCity(City city) {
        if (city != null) {
            ContentValues values = new ContentValues();
            values.put("city_name", city.getCityName());
            values.put("province_id", city.getProvinceId());
            db.insert("city", null, values);
        }
    }

    /**
     * 将Province实例存储到数据库。
     *
     * @param province
     */
    public void saveProvince(Province province) {
        if (province != null) {
            ContentValues values = new ContentValues();
            values.put("province_name", province.getProvinceName());
            db.insert("province", null, values);
        }
    }

    /**
     * 将County实例存储到数据库。
     *
     * @param county
     */
    public void saveCounty(County county) {
        if (county != null) {
            ContentValues values = new ContentValues();
            values.put("county_name", county.getCountyName());
            values.put("city_id", county.getCityId());
            values.put("county_code", county.getCountyCode());
            db.insert("county", null, values);
        }
    }


    /**
     * 从数据库中读取全国所有的省份信息
     *
     * @return
     */
    public List<Province> loadProvince() {
        List<Province> provinceList = new ArrayList<Province>();
        Cursor cursor = db.query("province", null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                provinceList.add(province);
            }
            cursor.close();
        }
        return provinceList;
    }

    /**
     * 从数据库中读取某省下的所有城市信息。
     *
     * @return
     */
    public List<City> loadCity(int provinceId) {
        List<City> cityList = new ArrayList<City>();
        Cursor cursor = db.query("city", null, "province_id=?", new String[]{String.valueOf(provinceId)}, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setProvinceId(cursor.getInt(cursor.getColumnIndex("province_id")));
                cityList.add(city);
            }
            cursor.close();
        }
        return cityList;
    }

    /**
     * 从数据库中读取某城市下的所有县信息。
     *
     * @return
     */
    public List<County> loadCounty(int cityId) {
        List<County> countyList = new ArrayList<County>();
        Cursor cursor = db.query("county", null, "city_id=?", new String[]{String.valueOf(cityId)}, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                County county = new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCityId(cursor.getInt(cursor.getColumnIndex("city_id")));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
                countyList.add(county);
            }
            cursor.close();
        }
        return countyList;
    }
}
