package com.voice;

import java.io.File;

import com.voice.bbs.BBSFragment;
import com.voice.bbs.BBS_MainActivity;
import com.voice.fragment.DynamicFragment;
import com.voice.fragment.NewsFatherFragment;
import com.voice.fragment.SettingFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import android.content.Context;
import android.content.Intent;

import com.voice.sidebar.Feedback;
import com.voice.sidebar.ResideMenu;
import com.voice.sidebar.ResideMenuItem;
import com.voice.sidebar_calendarview.Characterset;
import com.voice.sidebar_calendarview.SetplanActivity;

public class MainActivity extends FragmentActivity implements OnClickListener{

	protected static final String TAG = "MainActivity";
	private Context mContext;
	private ImageButton mNews,mConstact,mDeynaimic,mSetting;
	private View mPopView;
	private View currentButton;
	

	public static User user;



	private LinearLayout buttomBarGroup;
	
	//侧边栏
	ResideMenu resideMenu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		mContext=this;
		findView();
		init();
		 // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.wel_background);
        resideMenu.attachToActivity(this);
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

       

        // create menu items;
        String titles[] = { "我的计划", "个性学习", "关于"};
        int icon[] = { R.drawable.sidebar_icon_home, R.drawable.sidebar_icon_profile, R.drawable.sidebar_icon_calendar, R.drawable.sidebar_icon_settings };

        for ( int i = 0; i < titles.length; i++){
            ResideMenuItem item = new ResideMenuItem(this, icon[i], titles[i],i);
           OnClickListener myOnClickListener = new OnClickListener(){
    			@Override
    			public void onClick(View v) {
    				    int temp=((ResideMenuItem) v).getid();
    				    if(temp==0){
    					Intent intent = new Intent(mContext, SetplanActivity.class);
    					startActivity(intent);
    						}
    				    if(temp==1){
        					Intent intent = new Intent(mContext, Characterset.class);
        					startActivity(intent);
        						}	
    				    if(temp==2){
        					Intent intent = new Intent(mContext, Feedback.class);
        					startActivity(intent);
        						}	
    				 //   if(temp==3){
        				//	Intent intent = new Intent(mContext, Characterset.class);
        				//	startActivity(intent);
        				//		}	
    			}
        }; 
            
            item.setOnClickListener(myOnClickListener);
            resideMenu.addMenuItem(item,  ResideMenu.DIRECTION_LEFT); // or  ResideMenu.DIRECTION_RIGHT
        }
        
	}
	@Override
	public void onResume(){
		super.onResume();
	}
	private void findView(){
		mPopView=LayoutInflater.from(mContext).inflate(R.layout.app_exit, null);
		buttomBarGroup=(LinearLayout) findViewById(R.id.buttom_bar_group);
		mDeynaimic=(ImageButton) findViewById(R.id.buttom_deynaimic);
		mConstact=(ImageButton) findViewById(R.id.buttom_constact);
		mNews=(ImageButton) findViewById(R.id.buttom_news);
		mSetting=(ImageButton) findViewById(R.id.buttom_setting);

	}
	
	private void init(){
		Intent intent=getIntent();
        Bundle u=intent.getExtras();
        user=(User)u.getSerializable("user");
        System.out.println(user.getLogId());
		mDeynaimic.setOnClickListener(this);
		mConstact.setOnClickListener(this);
		mNews.setOnClickListener(this);
		mSetting.setOnClickListener(this);
		
		mDeynaimic.performClick();

		File dir = new File("data/data/com.qq/databases"); 
		File res = new File("data/data/com.qq/video"); 
		System.out.println("创建数据库。。。。");
	    if (!dir.exists()){ 
	    	try{
	    		dir.mkdirs(); 
	    	} catch (Exception e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
	    	} 
	        if (dir.exists())
	        	System.out.println("创建文件夹成功");
	        else
	        	System.out.println("创建文件夹失败");
	    }
	    if (!res.exists()){ 
	    	try{
	    		res.mkdirs(); 
	    	} catch (Exception e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
	    	} 
	        if (res.exists())
	        	System.out.println("创建文件夹成功");
	        else
	        	System.out.println("创建文件夹失败");
	    }

	}
	
	private void setButton(View v){
		if(currentButton!=null&&currentButton.getId()!=v.getId()){
			currentButton.setEnabled(true);
		}
		v.setEnabled(false);
		currentButton=v;
	}
	public void onClick(View v) {
		FragmentManager fm=getSupportFragmentManager();
		FragmentTransaction ft=fm.beginTransaction();
		Fragment newFragment;


		if (v==mDeynaimic){
			newFragment=new DynamicFragment();
		 }
		else if(v==mNews){
			newFragment=new NewsFatherFragment();
		}else if(v == mConstact){
			newFragment=new BBSFragment();
		}
		else{
			//this is a test
			newFragment=new SettingFragment();
		}

		ft.replace(R.id.fl_content, newFragment,MainActivity.TAG);
		ft.commit();
		setButton(v);
	}
}
