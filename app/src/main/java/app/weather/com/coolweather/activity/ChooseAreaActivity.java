package app.weather.com.coolweather.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.weather.com.coolweather.R;
import app.weather.com.coolweather.db.CoolWeatherDB;
import app.weather.com.coolweather.model.City;
import app.weather.com.coolweather.model.County;
import app.weather.com.coolweather.model.Province;
import app.weather.com.coolweather.utils.HttpUtil;
import app.weather.com.coolweather.utils.Utility;

public class ChooseAreaActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final int LEVEL_NATIONALITY = 0;
    private static final int LEVEL_PROVINCE = 1;
    private static final int LEVEL_CITY = 2;
    private static final int LEVEL_COUNTY = 3;

    private ProgressDialog progressDialog;
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
        provinceList = db.loadProvince();
        if (provinceList.size() > 0) {
            dataList.clear();
            for (Province province : provinceList) {
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_NATIONALITY;
            tvTitle.setText("中国");
        } else {
            queryFromServer();
        }
    }

    private void queryCities() {
        cityList = db.loadCity(currentProvince.getId());
        if (cityList.size() > 0) {
            dataList.clear();
            for (City city : cityList) {
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_PROVINCE;
            tvTitle.setText(currentProvince.getProvinceName());
        }
    }

    private void queryCounties() {
        countyList = db.loadCounty(currentCity.getId());
        dataList.clear();
        for (County county : countyList) {
            dataList.add(county.getCountyName());
        }
        adapter.notifyDataSetChanged();
        listView.setSelection(0);
        currentLevel = LEVEL_CITY;
        tvTitle.setText(currentCity.getCityName());
    }

    private void queryFromServer() {
        showProgressDialog();
        String address = "http://v.juhe.cn/weather/citys?dtype=&key=ed7248a544f45b3cdae099ac26f8b211";
        HttpUtil.sendHttpRequest(address, new HttpUtil.HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Utility.handleJSONResponse(db, response);   //解析json数据存入数据库
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //回到主线程中处理逻辑
                        if (currentLevel == LEVEL_NATIONALITY) {
                            queryProvinces();
                        } else if (currentLevel == LEVEL_PROVINCE) {
                            queryCities();
                        } else if (currentLevel == LEVEL_CITY) {
                            queryCounties();
                        }
                        closeProgressDialog();
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(ChooseAreaActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }


    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载...");
            progressDialog.show();
        }
    }

    private void initData() {
        db = CoolWeatherDB.getInstance(this);
        dataList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    private void initView() {
        listView = findViewById(R.id.list_view);
        tvTitle = findViewById(R.id.title_text);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (currentLevel == LEVEL_NATIONALITY) {
            currentProvince = provinceList.get(position);
            queryCities();
        } else if (currentLevel == LEVEL_PROVINCE) {
            currentCity = cityList.get(position);
            queryCounties();
        }
    }


    @Override
    public void onBackPressed() {
        if (currentLevel == LEVEL_PROVINCE) {
            queryProvinces();
        } else if (currentLevel == LEVEL_CITY) {
            queryCities();
        } else {
            finish();
        }
    }
}
