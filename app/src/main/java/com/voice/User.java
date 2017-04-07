package com.voice;

import java.io.Serializable;

public class User implements Serializable{
	private static final long serialVersionUID = 1L;
    private String LogId;
    private String LogName;
    private String LogPassword;
    private String Sign;
    public User(String LogId,String LogName,String LogPassword,String Sign){
        this.LogId=LogId;
        this.LogName=LogName;
        this.LogPassword=LogPassword;
        this.Sign=Sign;
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
    public void setLogName(String New) {
        LogName=New;
    }
    public void setSign(String New) {
        Sign=New;
    }
    public String getLogPassword() {
        return LogPassword;
    }
}
