package com.ms.ebangw.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.ms.ebangw.R;
import com.ms.ebangw.bean.Area;
import com.ms.ebangw.bean.City;
import com.ms.ebangw.bean.Province;

import java.util.List;

/**
 * 含有省市两级联动的Spinner
 * User: WangKai(123940232@qq.com)
 * 2015-09-30 14:59
 */
public class ProvinceAndCityView extends FrameLayout {
    private ViewGroup layout;
    private Spinner provinceSp;
    private Spinner citySp;

    private Context mContext;

    private List<Province> provinces;
    private List<City> citys;


    private Province currentProvince;
    private City currentCity;

    private Spinner areaSp;
    private List<Area> areas;
    private Area currentArea;
    ArrayAdapter<Area> adapter03;

    ArrayAdapter<Province> adapter01;
    ArrayAdapter<City> adapter02;
//    protected String provinceId;


    protected String cityId;
    public ProvinceAndCityView(Context context) {
        super(context);
    }

    public ProvinceAndCityView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        layout = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.layout_province_and_city, this, true);
        provinceSp = (Spinner) layout.findViewById(R.id.sp_a);
        citySp = (Spinner) layout.findViewById(R.id.sp_b);
        areaSp = (Spinner) layout.findViewById(R.id.sp_c);

    }

    public ProvinceAndCityView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    public void initSpinner() {
        if (null == provinces) {
            return;
        }

        adapter01 = new ArrayAdapter<>(mContext,
            R.layout.layout_spinner_item, provinces);


        provinceSp.setAdapter(adapter01);
        provinceSp.setSelection(0, true);

        adapter02 = new ArrayAdapter<>(mContext, R.layout.layout_spinner_item, provinces
            .get(0).getCitys());
        citySp.setAdapter(adapter02);
        citySp.setSelection(0, true);

        adapter03 = new ArrayAdapter<Area>(mContext, R.layout.layout_spinner_item, provinces.get(0).getCitys().get(0).getAreas());
        areaSp.setAdapter(adapter03);
        areaSp.setSelection(0, true);

        provinceSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                currentProvince = provinces.get(position);

                citys = provinces.get(position).getCitys();

                adapter02 = new ArrayAdapter<>(mContext,
                        R.layout.layout_spinner_item, citys);

                citySp.setAdapter(adapter02);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        citySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
//                currentCity = citys.get(position);
//
//                adapter02 = new ArrayAdapter<>(mContext,
//                    R.layout.layout_spinner_item, citys);
//
//                citySp.setAdapter(adapter02);
                currentCity = citys.get(position);
                areas = currentCity.getAreas();

                if(areas == null){
                    areaSp.setVisibility(View.GONE);
                    return;
                }else{
                    areaSp.setVisibility(View.VISIBLE);
                    adapter03 = new ArrayAdapter<>(mContext,
                            R.layout.layout_spinner_item, areas);

                    areaSp.setAdapter(adapter03);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        areaSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentArea = areas.get(position);

//                adapter03 = new ArrayAdapter<Area>(mContext, R.layout.layout_spinner_item, areas);
//                areaSp.setAdapter(adapter03);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setProvinces(List<Province> provinces) {
        this.provinces = provinces;

        if (null != provinces) {
            initSpinner();
        }
    }

    public String getCityId() {
        if (null != currentCity) {
            return currentCity.getId();
        }
        return null;
    }

    public String getProvinceId() {
        if (null != currentProvince) {
            return currentProvince.getId();
        }
        return null;
    }
    public String getAreaId(){
        if(null != currentArea){
            return currentArea.getId();
        }
        return null;
    }


}
