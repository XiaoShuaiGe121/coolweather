package app.weather.com.coolweather.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/6/14.
 */

public class Province implements Parcelable{
    private int id;
    private String provinceName;
    private String provinceCode;

    protected Province(Parcel in) {
        id = in.readInt();
        provinceName = in.readString();
        provinceCode = in.readString();
    }

    public static final Creator<Province> CREATOR = new Creator<Province>() {
        @Override
        public Province createFromParcel(Parcel in) {
            return new Province(in);
        }

        @Override
        public Province[] newArray(int size) {
            return new Province[size];
        }
    };

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

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public Province(int id, String provinceName, String provinceCode) {

        this.id = id;
        this.provinceName = provinceName;
        this.provinceCode = provinceCode;
    }

    public Province() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(provinceName);
        dest.writeString(provinceCode);
    }
}
