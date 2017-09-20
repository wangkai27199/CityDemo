package com.zl.citydemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weiwangcn.betterspinner.library.BetterSpinner;
import com.zl.citydemo.bean.CityBean;
//
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<CityBean> cityList;
    private List<String> list = new ArrayList<>();
    private List<String> pList = new ArrayList<>();
    private List<String> cList = new ArrayList<>();

    private BetterSpinner arrSpinner;
    private BetterSpinner citySpinner;
    private BetterSpinner spinnerProvince;
    private ArrayAdapter<String> pAdapter;
    private ArrayAdapter<String> cAdapter;
    private CityBean.ProvicesBean provicesBean;
    private List<CityBean.ProvicesBean> provices;
    private CityBean.ProvicesBean.CityAreasBean cityAreasBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        onCk();
    }

    private void onCk() {
        arrSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String s = list.get(i);
                cList.clear();
                pList.clear();
                pAdapter.clear();
                cAdapter.clear();

                check(s, 1);
                pAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, s + pList.size(), Toast.LENGTH_SHORT).show();

            }
        });
        spinnerProvince.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String s = pList.get(i);
                cAdapter.clear();
                check(s, 2);
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                cAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initData() {
        String json = getJson();
        if (json != null && !json.equals("")) {
            cityList = new Gson().fromJson(json, new TypeToken<List<CityBean>>() {
            }.getType());
        }
    }

    private void initView() {
        arrSpinner = (BetterSpinner) findViewById(R.id.spinnerArr);
        citySpinner = (BetterSpinner) findViewById(R.id.spinnerCity);
        spinnerProvince = (BetterSpinner) findViewById(R.id.spinnerProvince);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, list);

        for (int i = 0; i < cityList.size(); i++) {
            CityBean cityBean = cityList.get(i);
            list.add(cityBean.AreaName);
        }
        arrSpinner.setAdapter(adapter);

        pAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, pList);

        spinnerProvince.setAdapter(pAdapter);

        cAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, cList);

        citySpinner.setAdapter(cAdapter);
    }

    public String getJson() {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            InputStream in = getAssets().open("cityJson.json");
            BufferedReader bf = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuffer.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }

    public void check(String s, int sw) {
        if (sw == 1) {
            for (int a = 0; a < cityList.size(); a++) {
                CityBean cityBean = cityList.get(a);
                if (s.equals(cityBean.AreaName)) {
                    provices = cityBean.Provices;
                    for (int p = 0; p < provices.size(); p++) {
                        provicesBean = provices.get(p);
                        pList.add(provicesBean.ProvinceName);
                    }
                }
            }
            pAdapter.addAll(pList);
            Log.e("S", pList.toString());
        } else if (sw == 2) {
            for (int a = 0; a < provices.size(); a++) {
                CityBean.ProvicesBean provicesBean = provices.get(a);
                if (s.equals(provicesBean.ProvinceName)) {
                    List<CityBean.ProvicesBean.CityAreasBean> cityAreas = provicesBean.CityAreas;
                    for (int p = 0; p < cityAreas.size(); p++) {
                        cityAreasBean = cityAreas.get(p);
                        cList.add(cityAreasBean.CityAreaName);
                    }
                }
            }
            cAdapter.addAll(cList);
            Log.e("S", cList.toString());
        }
    }
}
