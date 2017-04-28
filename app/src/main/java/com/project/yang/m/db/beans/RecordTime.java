package com.project.yang.m.db.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by NiYang on 2017/4/27.
 */
@Entity
public class RecordTime {
    @Id(autoincrement = true)
    private Long id;
    private Long startTime;
    private Long endTime;

    @Generated(hash = 980178429)
    public RecordTime() {
    }

    @Generated(hash = 1302165446)
    public RecordTime(Long id, Long startTime, Long endTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}
