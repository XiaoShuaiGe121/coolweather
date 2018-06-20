package app.weather.com.coolweather.model;

/**
 * Created by Administrator on 2018/6/17.
 */

public class Province {
    private int id;
    private String provinceName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }


    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    @Override
    public boolean equals(Object obj) {
        Province p = (Province) obj;
        return p.getProvinceName().equals(this.provinceName);
    }
}
