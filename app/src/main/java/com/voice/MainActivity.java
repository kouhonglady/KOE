package com.voice;

import java.io.File;




import com.voice.User;

import com.voice.activity.LoginActivity;
import com.voice.bbs.BBS_MainActivity;
import com.voice.fragment.DynamicFragment;
import com.voice.fragment.NewsFatherFragment;
import com.voice.fragment.SettingFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.voice.sidebar.Feedback;
import com.voice.sidebar.ResideMenu;
import com.voice.sidebar.ResideMenuItem;
import com.voice.sidebar_calendarview.Characterset;
import com.voice.sidebar_calendarview.SetplanActivity;

public class MainActivity extends FragmentActivity {

	protected static final String TAG = "MainActivity";
	private Context mContext;
	private ImageButton mNews,mConstact,mDeynaimic,mSetting;
	private View mPopView;
	private View currentButton;
	
	private TextView app_cancle;
	private TextView app_exit;
	private TextView app_change;
	public static User user;

	private PopupWindow mPopupWindow;
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
       
        /*ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
            @Override
            public void openMenu() {
                Toast.makeText(getBaseContext(), "Menu is opened!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void closeMenu() {
                Toast.makeText(getBaseContext(), "Menu is closed!", Toast.LENGTH_SHORT).show();
            }
        };
        resideMenu.setMenuListener(menuListener);*/
       

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
	
	private void findView(){
		mPopView=LayoutInflater.from(mContext).inflate(R.layout.app_exit, null);
		buttomBarGroup=(LinearLayout) findViewById(R.id.buttom_bar_group);
		mDeynaimic=(ImageButton) findViewById(R.id.buttom_deynaimic);
		mConstact=(ImageButton) findViewById(R.id.buttom_constact);
		mNews=(ImageButton) findViewById(R.id.buttom_news);
		mSetting=(ImageButton) findViewById(R.id.buttom_setting);
		
		app_cancle=(TextView) mPopView.findViewById(R.id.app_cancle);
		app_change=(TextView) mPopView.findViewById(R.id.app_change_user);
		app_exit=(TextView) mPopView.findViewById(R.id.app_exit);
	}
	
	private void init(){
		Intent intent=getIntent();
        Bundle u=intent.getExtras();
        user=(User)u.getSerializable("user");
        System.out.println(user.getLogId());
		mDeynaimic.setOnClickListener(deynaimicOnClickListener);
		mNews.setOnClickListener(newsOnClickListener);
		mConstact.setOnClickListener(constactOnClickListener);
		mSetting.setOnClickListener(settingOnClickListener);
		
		mDeynaimic.performClick();
		
		mPopupWindow=new PopupWindow(mPopView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
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
		
		app_cancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mPopupWindow.dismiss();
			}
		});
		
		app_change.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mContext, LoginActivity.class);
				startActivity(intent);
				((Activity)mContext).overridePendingTransition(R.anim.activity_up, R.anim.fade_out);
				//finish();
			}
		});
		
		app_exit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	private OnClickListener deynaimicOnClickListener=new OnClickListener() {
		@Override
		public void onClick(View v) {
			FragmentManager fm=getSupportFragmentManager();
			FragmentTransaction ft=fm.beginTransaction();
			DynamicFragment dynamicFragment=new DynamicFragment();
			ft.replace(R.id.fl_content, dynamicFragment,MainActivity.TAG);
			ft.commit();
			setButton(v);
			
		}
	};
	private OnClickListener newsOnClickListener=new OnClickListener() {
		@Override
		public void onClick(View v) {
			FragmentManager fm=getSupportFragmentManager();
			FragmentTransaction ft=fm.beginTransaction();
			NewsFatherFragment newsFatherFragment=new NewsFatherFragment();
			ft.replace(R.id.fl_content, newsFatherFragment,MainActivity.TAG);
			ft.commit();
			setButton(v);
			
		}
	};
	
	
	
	private OnClickListener constactOnClickListener=new OnClickListener() {
		@Override
		public void onClick(View v) {
			//FragmentManager fm=getSupportFragmentManager();
			//FragmentTransaction ft=fm.beginTransaction();
			//ConstactFatherFragment constactFatherFragment=new ConstactFatherFragment();
			//ft.replace(R.id.fl_content, constactFatherFragment,MainActivity.TAG);
			//ft.commit();
			//setButton(v);
			Intent intent=new Intent();
			intent.setClass(MainActivity.this, BBS_MainActivity.class);
			startActivity(intent);
			
		}
	};
	
	
	
	private OnClickListener settingOnClickListener=new OnClickListener() {
		@Override
		public void onClick(View v) {
			FragmentManager fm=getSupportFragmentManager();
			FragmentTransaction ft=fm.beginTransaction();
			SettingFragment settingFragment=new SettingFragment();
			ft.replace(R.id.fl_content, settingFragment,MainActivity.TAG);
			ft.commit();
			setButton(v);
			
		}
	};
	
	private void setButton(View v){
		if(currentButton!=null&&currentButton.getId()!=v.getId()){
			currentButton.setEnabled(true);
		}
		v.setEnabled(false);
		currentButton=v;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_MENU){
			mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#b0000000")));
			mPopupWindow.showAtLocation(buttomBarGroup, Gravity.BOTTOM, 0, 0);
			mPopupWindow.setAnimationStyle(R.style.app_pop);
			mPopupWindow.setOutsideTouchable(true);
			mPopupWindow.setFocusable(true);
			mPopupWindow.update();
		}
		return super.onKeyDown(keyCode, event);
		
	}

}
