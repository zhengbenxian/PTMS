package com.pacia.ptms.bean;

import java.io.Serializable;

/**
 * 登录
 * Created by p on 2018/05/08.
 */

public class LoginBean implements Serializable {

    /**
     * gid : A73150D1-17EC-40DE-B4EF-13872A949F5C
     * insert_date : 1533806548000
     * org_gid : 51A52AFD-774A-44B9-8003-73C477773005
     * id_card_num :
     * user_id : WXJYLNJYZ
     * user_name : 无锡江阴陆南加油站
     * login_name : WXJYLNJYZ
     * is_online : 1
     * use_state : 1
     * lang_type : 2
     * role_gid : F735FA72-80B5-4944-ABB1-2AF10C552567
     * role_type : 40
     * telephone :
     * phone :
     * email_address :
     * sort_index : 0
     * org_code : RB66
     * org_name : 无锡江阴陆南加油站
     * org_short_name : 无锡江阴陆南加油站
     */

    private String gid;
    private String org_gid;
    private String id_card_num;
    private String user_id;
    private String user_name;
    private String login_name;
    private int is_online;
    private int use_state;
    private int lang_type;
    private String role_gid;
    private String role_type;
    private String telephone;
    private String phone;
    private String email_address;
    private String org_code;
    private String org_name;
    private String org_short_name;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getOrg_gid() {
        return org_gid;
    }

    public void setOrg_gid(String org_gid) {
        this.org_gid = org_gid;
    }

    public String getId_card_num() {
        return id_card_num;
    }

    public void setId_card_num(String id_card_num) {
        this.id_card_num = id_card_num;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public int getIs_online() {
        return is_online;
    }

    public void setIs_online(int is_online) {
        this.is_online = is_online;
    }

    public int getUse_state() {
        return use_state;
    }

    public void setUse_state(int use_state) {
        this.use_state = use_state;
    }

    public int getLang_type() {
        return lang_type;
    }

    public void setLang_type(int lang_type) {
        this.lang_type = lang_type;
    }

    public String getRole_gid() {
        return role_gid;
    }

    public void setRole_gid(String role_gid) {
        this.role_gid = role_gid;
    }

    public String getRole_type() {
        return role_type;
    }

    public void setRole_type(String role_type) {
        this.role_type = role_type;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getOrg_code() {
        return org_code;
    }

    public void setOrg_code(String org_code) {
        this.org_code = org_code;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public String getOrg_short_name() {
        return org_short_name;
    }

    public void setOrg_short_name(String org_short_name) {
        this.org_short_name = org_short_name;
    }
}
