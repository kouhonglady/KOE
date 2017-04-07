package com.voice.bean;

public class RecentChat {
	private String userName;
	private String introduction;
	private String userFeel;
	private String userTime;
	private String imgPath;
	public RecentChat(String userName, String userFeel, String userTime, String img,String introduction) {
		super();
		this.userName = userName;
		this.userFeel = userFeel;
		this.userTime = userTime;
		this.imgPath = img;
		this.introduction = introduction;
	}
	public String getUserName() {
		return userName;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserFeel() {
		return userFeel;
	}
	public void setUserFeel(String userFeel) {
		this.userFeel = userFeel;
	}
	public String getUserTime() {
		return userTime;
	}
	public void setUserTime(String userTime) {
		this.userTime = userTime;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String img) {
		this.imgPath = img;
	}
	public void setIntroduction(String i) {
		this.introduction = i;
	}
}
