package com.voice.register;


import com.voice.R;
import com.voice.User;
import com.voice.activity.LoginActivity;
import com.voice.view.TextURLView;
import com.voice.view.TitleBarView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RegisterResultActivity extends Activity {
	private Context mContext;
	private TitleBarView mTitleBarView;
	private TextURLView url;
	private Button complete;
	private TextView ID;
	private TextView Name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_result);
		
		mContext=this;
		findView();
		initTitleView();
		initTvUrl();
		init();
	}
	
	private void findView(){
		mTitleBarView=(TitleBarView) findViewById(R.id.title_bar);
		url=(TextURLView) findViewById(R.id.tv_tiaokuan);
		complete=(Button) findViewById(R.id.register_success);
	}

	private void init(){
		ID=(TextView)findViewById(R.id.UserID);
		Name=(TextView)findViewById(R.id.UserName);
		Intent intent=getIntent();
        Bundle u=intent.getExtras();
        User user=(User)u.getSerializable("user");
        ID.setText("您的账号："+user.getLogId());
        Name.setText("您的用户名："+user.getLogName());
		complete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mContext, LoginActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
	
	private void initTitleView(){
		mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE,View.GONE, View.GONE);
		mTitleBarView.setTitleText(R.string.tv_register_success);
		mTitleBarView.setBtnLeft(R.drawable.boss_unipay_icon_back, R.string.back);
		mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
	}
	
	private void initTvUrl(){
		url.setText(R.string.tv_tiaokuan);
		url.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				
				
			}
		});
	}
}
