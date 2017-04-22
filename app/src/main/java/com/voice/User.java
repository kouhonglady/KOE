package com.voice;

import java.io.Serializable;

public class User implements Serializable{
    private static final long serialVersionUID = 1L;
    private String LogId;
    private String LogName;
    private String LogPassword;
    private String Sign;
    private String Sex;
    private String RegistDate;
    public User(String LogId,String LogName,String LogPassword,String Sign,String Sex,String RegistDate){
        this.LogId=LogId;
        this.LogName=LogName;
        this.LogPassword=LogPassword;
        this.Sign=Sign;
        this.Sex=Sex;
        this.RegistDate=RegistDate;
    }
    public String getLogId() {
        return LogId;
    }
    public String getLogName() {
        return LogName;
    }
    public String getSign() {
        return Sign;
    }
    public String getSex(){
        return Sex;
    }
    public String getRegistDate(){
        return RegistDate;
    }
    public void setLogName(String New) {
        LogName=New;
    }
    public void setSign(String New) {
        Sign=New;
    }
    public void setSex(String New) {
        Sex=New;
    }
    public String getLogPassword() {
        return LogPassword;
    }
}
