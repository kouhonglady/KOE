package com.voice;

public class Hornor {
	private static int studynum=0;
	private static String date="暂无";
	private static int grade=0;
	private static int testnum=0;
	private static int bbsnum=0;
	public Hornor(int studynum,String date,int grade,int testnum,int bbsnum){
		this.studynum=studynum;
		this.date=date;
		this.grade=grade;
		this.testnum=testnum;
		this.bbsnum=bbsnum;
    }
    public Hornor(){
        studynum=0;
        date="暂无";
        grade=0;
        testnum=0;
        bbsnum=0;
    }

	public static int getstudynum() {
        return studynum;
    }
    public static void setstudynum(int s) {
    	studynum=s;
    }
    public static int gettestnum() {
        return testnum;
    }
    public static void settestnum(int s) {
    	testnum=s;
    }
    public static int getbbsnum() {
        return bbsnum;
    }
    public void setbbsnum(int s) {
    	bbsnum=s;
    }
    public static String getdate() {
        return date;
    }
    public static void setdate(String s) {
    	date=s;
    }
    public static int getgrade() {
        return grade;
    }
    public void setgrade(int s) {
    	grade=s;
    }
}
