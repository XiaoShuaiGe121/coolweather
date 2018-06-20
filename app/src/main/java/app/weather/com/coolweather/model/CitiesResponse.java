package app.weather.com.coolweather.model;

import java.util.List;

/**
 * Created by Administrator on 2018/6/14.
 */

public class CitiesResponse {

    /**
     * resultcode : 200
     * reason : successed
     * result : [{"id":"1","province":"北京","city":"北京","district":"北京"},{"id":"2","province":"北京","city":"北京","district":"海淀"},{"id":"3","province":"北京","city":"北京","district":"朝阳"}]
     * error_code : 0
     */

    private String resultcode;
    private String reason;
    private int error_code;
    private List<ObjectBean> result;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<ObjectBean> getResult() {
        return result;
    }

    public void setResult(List<ObjectBean> result) {
        this.result = result;
    }

    public static class ObjectBean {
        /**
         * id : 1
         * province : 北京
         * city : 北京
         * district : 北京
         */

        private String id;
        private String province;
        private String city;
        private String district;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }
    }
}
