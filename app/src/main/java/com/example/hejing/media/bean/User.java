package com.example.hejing.media.bean;

/**
 * Created by limx on 2018/3/12.
 */

public class User {
    private int id;
    private int group_id;
    private String name;
    private int developer_id;
    private int house_build_id;
    private int grade;
    private int type;
    private String fullname;
    private String email;
    private int tel;
    private String describe;
    private int purview_ids;
    private int disabled;
    private int is_del;
    private int create_admin;
    private String create_time;

    public User(){

    }

    public User(int id, int group_id, String name, int developer_id, int house_build_id, int grade, int type, String fullname, String email, int tel, String describe, int purview_ids, int disabled, int is_del, int create_admin, String create_time) {
        this.id = id;
        this.group_id = group_id;
        this.name = name;
        this.developer_id = developer_id;
        this.house_build_id = house_build_id;
        this.grade = grade;
        this.type = type;
        this.fullname = fullname;
        this.email = email;
        this.tel = tel;
        this.describe = describe;
        this.purview_ids = purview_ids;
        this.disabled = disabled;
        this.is_del = is_del;
        this.create_admin = create_admin;
        this.create_time = create_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDeveloper_id() {
        return developer_id;
    }

    public void setDeveloper_id(int developer_id) {
        this.developer_id = developer_id;
    }

    public int getHouse_build_id() {
        return house_build_id;
    }

    public void setHouse_build_id(int house_build_id) {
        this.house_build_id = house_build_id;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getPurview_ids() {
        return purview_ids;
    }

    public void setPurview_ids(int purview_ids) {
        this.purview_ids = purview_ids;
    }

    public int getDisabled() {
        return disabled;
    }

    public void setDisabled(int disabled) {
        this.disabled = disabled;
    }

    public int getIs_del() {
        return is_del;
    }

    public void setIs_del(int is_del) {
        this.is_del = is_del;
    }

    public int getCreate_admin() {
        return create_admin;
    }

    public void setCreate_admin(int create_admin) {
        this.create_admin = create_admin;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", group_id=" + group_id +
                ", name='" + name + '\'' +
                ", developer_id=" + developer_id +
                ", house_build_id=" + house_build_id +
                ", grade=" + grade +
                ", type=" + type +
                ", fullname='" + fullname + '\'' +
                ", email='" + email + '\'' +
                ", tel=" + tel +
                ", describe='" + describe + '\'' +
                ", purview_ids=" + purview_ids +
                ", disabled=" + disabled +
                ", is_del=" + is_del +
                ", create_admin=" + create_admin +
                ", create_time='" + create_time + '\'' +
                '}';
    }
}
