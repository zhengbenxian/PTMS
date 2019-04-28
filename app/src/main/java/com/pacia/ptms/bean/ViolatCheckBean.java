package com.pacia.ptms.bean;

import java.io.Serializable;

/**
 * 违规详情 有无证照
 * Created by p on 2019/04/11.
 */

public class ViolatCheckBean implements Serializable {

    private String wfgzDetailGid;
    private String wfgzDetailRuleGid;
    private String wfgzDetailRuleViolatGid;
    private String wfgzDetailGrade;
    private String wfgzDetailUpdUGid;
    private String wfgzDetailSrf1;
    private String wfgzDetailSrf2;
    private String wfgzDetailSrf3;
    private String wfgzDetailSrf4;
    private String wfgzDetailSrf5;
    private String ruleName;
    private String ruleHaveNo;

    public String getWfgzDetailGid() {
        return wfgzDetailGid;
    }

    public void setWfgzDetailGid(String wfgzDetailGid) {
        this.wfgzDetailGid = wfgzDetailGid;
    }

    public String getWfgzDetailRuleGid() {
        return wfgzDetailRuleGid;
    }

    public void setWfgzDetailRuleGid(String wfgzDetailRuleGid) {
        this.wfgzDetailRuleGid = wfgzDetailRuleGid;
    }

    public String getWfgzDetailRuleViolatGid() {
        return wfgzDetailRuleViolatGid;
    }

    public void setWfgzDetailRuleViolatGid(String wfgzDetailRuleViolatGid) {
        this.wfgzDetailRuleViolatGid = wfgzDetailRuleViolatGid;
    }

    public String getWfgzDetailGrade() {
        return wfgzDetailGrade;
    }

    public void setWfgzDetailGrade(String wfgzDetailGrade) {
        this.wfgzDetailGrade = wfgzDetailGrade;
    }

    public String getWfgzDetailUpdUGid() {
        return wfgzDetailUpdUGid;
    }

    public void setWfgzDetailUpdUGid(String wfgzDetailUpdUGid) {
        this.wfgzDetailUpdUGid = wfgzDetailUpdUGid;
    }

    public String getWfgzDetailSrf1() {
        return wfgzDetailSrf1;
    }

    public void setWfgzDetailSrf1(String wfgzDetailSrf1) {
        this.wfgzDetailSrf1 = wfgzDetailSrf1;
    }

    public String getWfgzDetailSrf2() {
        return wfgzDetailSrf2;
    }

    public void setWfgzDetailSrf2(String wfgzDetailSrf2) {
        this.wfgzDetailSrf2 = wfgzDetailSrf2;
    }

    public String getWfgzDetailSrf3() {
        return wfgzDetailSrf3;
    }

    public void setWfgzDetailSrf3(String wfgzDetailSrf3) {
        this.wfgzDetailSrf3 = wfgzDetailSrf3;
    }

    public String getWfgzDetailSrf4() {
        return wfgzDetailSrf4;
    }

    public void setWfgzDetailSrf4(String wfgzDetailSrf4) {
        this.wfgzDetailSrf4 = wfgzDetailSrf4;
    }

    public String getWfgzDetailSrf5() {
        return wfgzDetailSrf5;
    }

    public void setWfgzDetailSrf5(String wfgzDetailSrf5) {
        this.wfgzDetailSrf5 = wfgzDetailSrf5;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleHaveNo() {
        return ruleHaveNo;
    }

    public void setRuleHaveNo(String ruleHaveNo) {
        this.ruleHaveNo = ruleHaveNo;
    }
}
