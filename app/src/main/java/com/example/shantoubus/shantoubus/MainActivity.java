package com.example.shantoubus.shantoubus;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Inputtips.InputtipsListener {
    private MapView mapView=null;
    private AMap aMap = null;
    private UiSettings mUiSettings;//定义一个UiSettings对象
    private AMapOptions aMapOptions;
    private MyLocationStyle myLocationStyle;
    private List TisList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        加载侧边栏
        final DrawerLayout drawer=(DrawerLayout)findViewById(R.id.drawer_layout);
        final FloatingSearchView mSearchView=(FloatingSearchView)findViewById(R.id.floating_search_view);
        mSearchView.attachNavigationDrawerToMenuButton(drawer);
        mSearchView.setOnLeftMenuClickListener(new FloatingSearchView.OnLeftMenuClickListener(){
            @Override
            public void onMenuOpened() {
                drawer.openDrawer(GravityCompat.START);
                Log.i("tag","打开抽屉");
            }
            @Override
            public void onMenuClosed() {
                Log.i("tag","关闭抽屉");
            }
        });
        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {

            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                Log.i("oldtext",oldQuery);
                Log.i("newtext",newQuery);
                if (newQuery!=null) {
                    InputtipsListener(newQuery);
                }
                //mSearchView.showProgress();
                if (TisList!=null) {
                    mSearchView.swapSuggestions(TisList);
                }
                List<? extends SearchSuggestion> subtypeList = new ArrayList<>();
                List<? extends SearchSuggestion> list = subtypeList;
//                list.add(new MyType());
//                MySubType sub = subtypeList.get(0);

            }
        });
//      加载地图
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 加载地图
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        aMap.moveCamera(CameraUpdateFactory.zoomTo(16));//设置缩放

        mUiSettings = aMap.getUiSettings();//实例化UiSettings类对象
        mUiSettings.setZoomControlsEnabled(false);//缩放按钮
        mUiSettings.setScaleControlsEnabled(true);//比例尺

        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位模式。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        mUiSettings.setMyLocationButtonEnabled(true);//定位按钮，注真机无效
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。



    }
    public void InputtipsListener(String text){
        InputtipsQuery inputquery=new InputtipsQuery(text,"汕头");
        inputquery.setCityLimit(true);
        Inputtips inputTips = new Inputtips(MainActivity.this, inputquery);
        inputTips.setInputtipsListener(MainActivity.this);
        inputTips.requestInputtipsAsyn();

    }
    @Override
    public void onGetInputtips(List<Tip> list, int rCode) {
        Log.i("cuowu", String.valueOf(rCode));
        List<HashMap<String, String>> listString = new ArrayList<HashMap<String, String>>();
       if (rCode == AMapException.CODE_AMAP_SUCCESS){
           for (int i=0;i<list.size();i++){
               HashMap<String,String> map=new HashMap<String,String>();
               map.put("name",list.get(i).getName());
               listString.add(map);
           }
       }
//        TisList=listString;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

}
