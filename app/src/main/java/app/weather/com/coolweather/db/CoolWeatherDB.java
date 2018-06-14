package app.weather.com.coolweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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

    public synchronized  static  CoolWeatherDB getInstance(Context context) {
        if (coolWeatherDB == null) {
            coolWeatherDB = new CoolWeatherDB(context);
        }
        return coolWeatherDB;
    }


    /**
     * Store the Province instance to the database
     *
     * @param province
     */
    public void saveProvince(Province province) {
        if(province!=null){
            ContentValues values = new ContentValues();
            values.put("province_name", province.getProvinceName());
            values.put("province_code", province.getProvinceCode());
            db.insert("province", null, values);
        }
    }


    /**
     * Read all province-wide information from the database
     *
     * @return
     */
    public List<Province> loadProvinces() {
        List<Province> provinceList = new ArrayList<Province>();
        Cursor cursor = db.query("province", null, null, null, null, null, null);
        Province province = new Province();
        while (cursor.moveToNext()) {
            province.setId(cursor.getInt(cursor.getColumnIndex("id")));
            province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
            province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
            provinceList.add(province);
        }
        return provinceList;
    }

    /**
     * Store City instances to the database
     *
     * @param city
     */
    public void saveCity(City city){
        if(city!=null){
            ContentValues values = new ContentValues();
            values.put("city_name",city.getCityName());
            values.put("city_code",city.getCityCode());
            values.put("province_id",city.getProvinceId());
            db.insert("city",null,values);
        }
    }

    /**
     * Read all city information in the province from the database
     *
     * @return
     */
    public List<City> loadCities() {
        List<City> cityList = new ArrayList<City>();
        Cursor cursor = db.query("city", null, null, null, null, null, null);
        City city = new City();
        while (cursor.moveToNext()) {
            city.setId(cursor.getInt(cursor.getColumnIndex("id")));
            city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
            city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
            city.setProvinceId(cursor.getInt(cursor.getColumnIndex("province_id")));
            cityList.add(city);
        }
        return cityList;
    }

    /**
     * Store County instances to the database
     *
     * @param county
     */
    public void saveCounty(County county){
        if(county!=null){
            ContentValues values = new ContentValues();
            values.put("county_name",county.getCountyName());
            values.put("county_code",county.getCountyCode());
            values.put("city_id",county.getCityId());
            db.insert("county",null,values);
        }
    }

    /**
     * Read all county information in the city from the database
     *
     * @return
     */
    public List<County> loadCounties() {
        List<County> countyList = new ArrayList<County>();
        Cursor cursor = db.query("county", null, null, null, null, null, null);
        County county = new County();
        while (cursor.moveToNext()) {
            county.setId(cursor.getInt(cursor.getColumnIndex("id")));
            county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
            county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
            county.setCityId(cursor.getInt(cursor.getColumnIndex("city_id")));
            countyList.add(county);
        }
        return countyList;
    }

}
