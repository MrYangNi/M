package com.project.yang.m.main;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.PolygonOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 地理围栏数据
 * Created by NiYang on 2017/4/25.
 */

public class AllGeofence {
    public static Map<Integer,PolygonOptions> allGeofence = new HashMap<>();
    public static Map<Integer, String> location = new HashMap<>();
    static {
        //15号宿舍楼
        PolygonOptions optionsDorm = new PolygonOptions();
        optionsDorm.add(new LatLng(39.056825, 117.143742));
        optionsDorm.add(new LatLng(39.056853, 117.144429));
        optionsDorm.add(new LatLng(39.05631,117.14439));
        optionsDorm.add(new LatLng(39.056192,117.144034));
        optionsDorm.add(new LatLng(39.056292,117.14369));

        //篮球场
        PolygonOptions optionsBasketballCourt = new PolygonOptions();
        optionsBasketballCourt.add(new LatLng(39.057886,117.142996));
        optionsBasketballCourt.add(new LatLng(39.057876,117.143366));
        optionsBasketballCourt.add(new LatLng(39.05746,117.143372));
        optionsBasketballCourt.add(new LatLng(39.057446,117.143085));
        optionsBasketballCourt.add(new LatLng(39.057636,117.142912));

        //羽毛球场
        PolygonOptions optionsBadmintonCourt = new PolygonOptions();
        optionsBadmintonCourt.add(new LatLng(39.05896,117.141223));
        optionsBadmintonCourt.add(new LatLng(39.058848,117.141871));
        optionsBadmintonCourt.add(new LatLng(39.058385,117.141312));

        //三食堂
        PolygonOptions optionsThirdMess = new PolygonOptions();
        optionsThirdMess.add(new LatLng(39.057162,117.146287));
        optionsThirdMess.add(new LatLng(39.057193,117.14673));
        optionsThirdMess.add(new LatLng(39.057059,117.147203));
        optionsThirdMess.add(new LatLng(39.056705,117.147201));
        optionsThirdMess.add(new LatLng(39.056535,117.146801));
        optionsThirdMess.add(new LatLng(39.056594,117.146287));

        //公交站
        PolygonOptions optionsBusStation = new PolygonOptions();
        optionsBusStation.add(new LatLng(39.054676,117.145428));
        optionsBusStation.add(new LatLng(39.054703,117.146148));
        optionsBusStation.add(new LatLng(39.054231,117.14623));
        optionsBusStation.add(new LatLng(39.054118,117.145802));
        optionsBusStation.add(new LatLng(39.054295,117.145464));

        //乐来得超市
        PolygonOptions optionsSupermarket = new PolygonOptions();
        optionsSupermarket.add(new LatLng(39.059223,117.14382));
        optionsSupermarket.add(new LatLng(39.059065,117.143514));
        optionsSupermarket.add(new LatLng(39.059407,117.143501));

        allGeofence.put(0,optionsDorm);
        allGeofence.put(1,optionsBasketballCourt);
        allGeofence.put(2,optionsBadmintonCourt);
        allGeofence.put(3,optionsThirdMess);
        allGeofence.put(4,optionsBusStation);
        allGeofence.put(5,optionsSupermarket);

        /*
        * 0-----15号宿舍楼
        * 1-----篮球场
        * 2-----羽毛球场
        * 3-----三食堂
        * 4-----公交站
        * 5-----乐来得超市
        * */

        location.put(0, "15dorm");
        location.put(1, "basketballCourt");
        location.put(2, "badmintonCourt");
        location.put(3, "thirdMess");
        location.put(4, "busStation");
        location.put(5, "lelaideSupermarket");
    }
}

