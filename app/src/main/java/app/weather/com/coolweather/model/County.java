package app.weather.com.coolweather.model;

/**
 * Created by Administrator on 2018/6/17.
 */

public class County {
    private int id;
    private String countyName;
    private String countyCode;

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    private int cityId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    @Override
    public boolean equals(Object obj) {
        County c= (County) obj;
        return c.getCountyName().equals(this.countyName);
    }
}
