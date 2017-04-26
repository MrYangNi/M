package com.project.yang.m.beans;

import com.amap.api.maps.model.Polygon;

/**
 * Created by NiYang on 2017/4/25.
 */

public class GeoFenceInfo {
    private Polygon polygon;
    private int label;

    public GeoFenceInfo(Polygon polygon, int label) {
        this.polygon = polygon;
        this.label = label;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }
}
