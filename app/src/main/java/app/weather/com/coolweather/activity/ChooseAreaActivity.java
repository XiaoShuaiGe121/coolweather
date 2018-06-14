package app.weather.com.coolweather.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.weather.com.coolweather.R;
import app.weather.com.coolweather.db.CoolWeatherDB;
import app.weather.com.coolweather.model.City;
import app.weather.com.coolweather.model.County;
import app.weather.com.coolweather.model.Province;
import app.weather.com.coolweather.utils.HttpUtil;

public class ChooseAreaActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final int LEVEL_NATIONALITY = 0;
    private static final int LEVEL_PROVINCE = 1;
    private static final int LEVEL_CITY = 2;
    private static final int LEVEL_COUNTY = 3;

    private int currentLevel;   //当前级别
    private Province currentProvince;   //当前省级
    private City currentCity;   //当前市级
    private County currentCounty;   //当前县级
    private ArrayAdapter<String> adapter;
    private List<String> dataList;
    private CoolWeatherDB db;
    private ListView listView;
    private TextView tvTitle;

    private List<Province> provinceList;//用来存储查询到的省
    private List<City> cityList;//用来存储查询到的省
    private List<County> countyList;//用来存储查询到的省

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_choose_area);
        initView();
        initData();
        queryProvinces();
    }

    /**
     * 加载省级数据：
     * 查询全国所有的省，优先从数据库查询，如果没有查询到再去服务器上查询
     */
    private void queryProvinces() {
        provinceList = db.loadProvinces();
        if(provinceList==null){
            String address = "http://v.juhe.cn/weather/citys?dtype=&key=ed7248a544f45b3cdae099ac26f8b211";
            HttpUtil.sendHttpRequest(address, new HttpUtil.HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    HttpUtil.handleProvinceResponse(db,response);
                }

                @Override
                public void onError(Exception e) {

                }
            });
        }
    }

    private void initData() {
        currentLevel = LEVEL_NATIONALITY;
        tvTitle.setText("中国");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        db = CoolWeatherDB.getInstance(this);
        listView.setOnItemClickListener(this);
    }

    private void initView() {
        listView = findViewById(R.id.list_view);
        tvTitle = findViewById(R.id.title_text);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
