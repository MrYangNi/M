package com.project.yang.m.db.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by NiYang on 2017/4/26.
 */
@Entity
public class AsocciationRulesDataBeans {
    @Id(autoincrement = true)
    private Long id;
    private Long time;
    private String location;
    private String appName;

    @Generated(hash = 2119315611)
    public AsocciationRulesDataBeans() {
    }

    @Generated(hash = 834703493)
    public AsocciationRulesDataBeans(Long id, Long time, String location,
            String appName) {
        this.id = id;
        this.time = time;
        this.location = location;
        this.appName = appName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
