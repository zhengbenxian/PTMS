package com.pacia.ptms.bean;

import java.io.Serializable;

/**
 * 车载监控
 * Created by p on 2019/04/03.
 */

public class MonitorBean implements Serializable {
    private String gid;
    private String name;
    private String code;
    private String videoUrl;
    private String remarks;
    private String srf1;
    private String srf2;
    private String srf3;
    private String srf4;
    private String srf5;
    private String hgid;
    private String cameraGid;
    private String cameraName;
    private String cameraVideoUrl;
    private String cameraStatus;
    private String cameraStatusId;

    public String getCameraStatusId() {
        return cameraStatusId;
    }

    public void setCameraStatusId(String cameraStatusId) {
        this.cameraStatusId = cameraStatusId;
    }

    public String getCameraGid() {
        return cameraGid;
    }

    public void setCameraGid(String cameraGid) {
        this.cameraGid = cameraGid;
    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    public String getCameraVideoUrl() {
        return cameraVideoUrl;
    }

    public void setCameraVideoUrl(String cameraVideoUrl) {
        this.cameraVideoUrl = cameraVideoUrl;
    }

    public String getCameraStatus() {
        return cameraStatus;
    }

    public void setCameraStatus(String cameraStatus) {
        this.cameraStatus = cameraStatus;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSrf1() {
        return srf1;
    }

    public void setSrf1(String srf1) {
        this.srf1 = srf1;
    }

    public String getSrf2() {
        return srf2;
    }

    public void setSrf2(String srf2) {
        this.srf2 = srf2;
    }

    public String getSrf3() {
        return srf3;
    }

    public void setSrf3(String srf3) {
        this.srf3 = srf3;
    }

    public String getSrf4() {
        return srf4;
    }

    public void setSrf4(String srf4) {
        this.srf4 = srf4;
    }

    public String getSrf5() {
        return srf5;
    }

    public void setSrf5(String srf5) {
        this.srf5 = srf5;
    }

    public String getHgid() {
        return hgid;
    }

    public void setHgid(String hgid) {
        this.hgid = hgid;
    }
}
