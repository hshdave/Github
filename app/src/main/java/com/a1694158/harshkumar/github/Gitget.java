package com.a1694158.harshkumar.github;

/**
 * Created by Harsh on 9/3/2017.
 */

public class Gitget {

    private String full_name;
    private String desc;
    private String respourl;
    private String name;
    private String lang;
    private String owner;


    public Gitget(String full_name, String desc, String respourl) {
        this.full_name = full_name;
        this.desc = desc;
        this.respourl = respourl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRespourl() {
        return respourl;
    }

    public void setRespourl(String respourl) {
        this.respourl = respourl;
    }
}

