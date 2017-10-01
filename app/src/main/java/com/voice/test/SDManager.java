package com.voice.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.voice.util.FileUtil;

import android.content.Context;
import android.content.res.AssetManager;

public class SDManager {
	private Context mContext;
	private String[] names={"songhuiqiao.jpg","zhangzetian.jpg","songqian.jpg","hangxiaozhu.jpg","jingtian.jpg"
			,"liuyifei.jpg","kangyikun.jpg","dengziqi.jpg","sysd.jpg"};
	private String[] p_files={"a.wav","o.wav","e.wav","i.wav","u.wav","v.wav"};
	public SDManager(Context contex){
		this.mContext=contex;
	}
	
	public void moveUserIcon(){
		String path=FileUtil.getRecentChatPath();
		InputStream is=null;
		FileOutputStream out=null;
		for(int i=0;i<9;i++){
			try {
				is=mContext.getResources().getAssets().open(names[i]);
				out=new FileOutputStream(new File(path+names[i]));
				int len=0;
				byte[] buffer=new byte[1024];
				while((len=is.read(buffer))!=-1){
					out.write(buffer, 0, len);
					out.flush();
				}
				is.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void movePronunciation(){
		String path=FileUtil.getPronunciationPath();
		InputStream is=null;
		FileOutputStream out=null;
		for(int i=0;i<p_files.length;i++){
			try {
				is=mContext.getResources().getAssets().open(p_files[i]);
				out=new FileOutputStream(new File(path+p_files[i]));
				int len=0;
				byte[] buffer=new byte[1024];
				while((len=is.read(buffer))!=-1){
					out.write(buffer, 0, len);
					out.flush();
				}
				is.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
