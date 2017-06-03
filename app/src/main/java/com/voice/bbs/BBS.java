package com.voice.bbs;

/**
 * Created by EXcalibur on 2017/5/20.
 */


public class BBS {
    private String mBBSTime;
    private String mBBSTitle;
    private String mBBSUrl;
    private String mBBSAuthor;
    private String mBBSAbstract;
    private String mBBSHot;
    private String mBBSReply;

    public BBS() {

    }

    public BBS(String BBSTime, String BBSTitle, String BBSUrl) {
        mBBSTime = BBSTime;
        mBBSTitle = BBSTitle;
        mBBSUrl = BBSUrl;
    }

    public String getBBSAuthor() {
        return mBBSAuthor;
    }

    public void setBBSAuthor(String BBSAuthor) {
        mBBSAuthor = BBSAuthor;
    }

    public String getBBSAbstract() {
        return mBBSAbstract;
    }

    public void setBBSAbstract(String BBSAbstract) {
        mBBSAbstract = BBSAbstract;
    }

    public String getBBSHot() {
        return mBBSHot;
    }

    public void setBBSHot(String BBSHot) {
        mBBSHot = BBSHot;
    }

    public String getBBSReply() {
        return mBBSReply;
    }

    public void setBBSReply(String BBSReply) {
        mBBSReply = BBSReply;
    }

    public String getBBSTime() {
        return mBBSTime;
    }

    public void setBBSTime(String BBSTime) {
        mBBSTime = BBSTime;
    }

    public String getBBSTitle() {
        return mBBSTitle;
    }

    public void setBBSTitle(String BBSTitle) {
        mBBSTitle = BBSTitle;
    }

    public String getBBSUrl() {
        return mBBSUrl;
    }

    public void setBBSUrl(String BBSUrl) {
        mBBSUrl = BBSUrl;
    }
}
