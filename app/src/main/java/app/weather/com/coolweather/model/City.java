package app.weather.com.coolweather.model;

/**
 * Created by Administrator on 2018/6/15.
 */

public class City {
    private int id;
    private String cityName;
    private int provinceId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    @Override
    public boolean equals(Object obj) {
        City c = (City)obj;
        return c.getCityName().equals(this.cityName);
    }
}
