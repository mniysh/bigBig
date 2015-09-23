package com.ms.ebangw.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.UserAuthenActivity;
import com.ms.ebangw.bean.Area;
import com.ms.ebangw.bean.City;
import com.ms.ebangw.bean.Province;
import com.ms.ebangw.commons.Constants;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 个人基本信息
 */
public class PersonBaseInfoFragment extends BaseFragment {

	private static final String CATEGORY = "category";

	private String category;
	private View contentLayout;

	@Bind(R.id.et_phone)
	EditText phoneEt;
	@Bind(R.id.rg_gender)
	RadioGroup genderRg;
	@Bind(R.id.sp_a)
	Spinner provinceSp;
	@Bind(R.id.sp_b)
	Spinner citySp;
	@Bind(R.id.btn_next)
	Button nextBtn;

	private List<Province> provinces;
	private Province province;
	ArrayAdapter<Province> adapter01;
	ArrayAdapter<City> adapter02;
	ArrayAdapter<Area> adapter03;


	public static PersonBaseInfoFragment newInstance(String category) {
		PersonBaseInfoFragment fragment = new PersonBaseInfoFragment();
		Bundle args = new Bundle();
		args.putString(CATEGORY, category);
		fragment.setArguments(args);
		return fragment;
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			category = getArguments().getString(CATEGORY);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		contentLayout = inflater.inflate(R.layout.fragment_person_base_info, null);
		ButterKnife.bind(this, contentLayout);
		initView();
		initData();
		return contentLayout;
	}

	@Override
	public void initView() {

	}

	@Override
	public void initData() {
		nextBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((UserAuthenActivity) mActivity).goNext();
			}
		});
		initSpinner();
	}



	/**
	 * 获取籍贯
	 * @return
	 */
	public String getNativePlace() {
		StringBuilder builder = new StringBuilder();


		return builder.toString();
	}

	public String getGender() {
		int checkId = genderRg.getCheckedRadioButtonId();
		if (checkId == R.id.rb_male) {
			return Constants.MALE;
		}else {
			return Constants.FEMALE;
		}
	}

	public void initSpinner() {


		provinces = getProvinces();

		adapter01 = new ArrayAdapter<>(mActivity,
			android.R.layout.simple_list_item_1, provinces);
		provinceSp.setAdapter(adapter01);
		provinceSp.setSelection(0, true);

		adapter02 = new ArrayAdapter<>(mActivity, android.R.layout.simple_list_item_1, provinces
			.get(0).getCitys());
		citySp.setAdapter(adapter02);
		citySp.setSelection(0, true);

		adapter03 = new ArrayAdapter<>(mActivity,
			android.R.layout.simple_list_item_1, provinces.get(0)
			.getCitys().get(0).getAreas());

		provinceSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
									   int position, long id) {
				province = provinces.get(position);
				adapter02 = new ArrayAdapter<>(mActivity,
					android.R.layout.simple_list_item_1, provinces.get(
					position).getCitys());
				citySp.setAdapter(adapter02);

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});


//		citySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//			@Override
//			public void onItemSelected(AdapterView<?> parent, View view,
//									   int position, long id) {
//
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> parent) {
//				// TODO Auto-generated method stub
//
//			}
//		});

	}



	public List<Province> getProvinces() {
		return ((UserAuthenActivity)mActivity).getTotalRegion().getProvince();

	}

	public List<Province> getProvincesFromXml() throws XmlPullParserException,
		IOException {
		List<Province> provinces = null;
		Province province = null;
		List<City> citys = null;
		City city = null;
		List<Area> districts = null;
		Area district = null;
		Resources resources = getResources();

		InputStream in = resources.openRawResource(R.raw.citys_weather);

		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		XmlPullParser parser = factory.newPullParser();

		parser.setInput(in, "utf-8");
		int event = parser.getEventType();
		while (event != XmlPullParser.END_DOCUMENT) {
			switch (event) {
				case XmlPullParser.START_DOCUMENT:
					provinces = new ArrayList<Province>();
					break;
				case XmlPullParser.START_TAG:
					String tagName = parser.getName();
					if ("p".equals(tagName)) {
						province = new Province();
						citys = new ArrayList<City>();
						int count = parser.getAttributeCount();
						for (int i = 0; i < count; i++) {
							String attrName = parser.getAttributeName(i);
							String attrValue = parser.getAttributeValue(i);
							if ("p_id".equals(attrName))
								province.setId(attrValue);
						}
					}
					if ("pn".equals(tagName)) {
						province.setName(parser.nextText());
					}
					if ("c".equals(tagName)) {
						city = new City();
						districts = new ArrayList<Area>();
						int count = parser.getAttributeCount();
						for (int i = 0; i < count; i++) {
							String attrName = parser.getAttributeName(i);
							String attrValue = parser.getAttributeValue(i);
							if ("c_id".equals(attrName))
								city.setId(attrValue);
						}
					}
					if ("cn".equals(tagName)) {
						city.setName(parser.nextText());
					}
					if ("d".equals(tagName)) {
						district = new Area();
						int count = parser.getAttributeCount();
						for (int i = 0; i < count; i++) {
							String attrName = parser.getAttributeName(i);
							String attrValue = parser.getAttributeValue(i);
							if ("d_id".equals(attrName))
								district.setId(attrValue);
						}
						district.setName(parser.nextText());
						districts.add(district);
					}
					break;
				case XmlPullParser.END_TAG:
					if ("c".equals(parser.getName())) {
						city.setAreas(districts);
						citys.add(city);
					}
					if ("p".equals(parser.getName())) {
						province.setCitys(citys);
						provinces.add(province);
					}

					break;

			}
			event = parser.next();

		}
		return provinces;
	}
}
