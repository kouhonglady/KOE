/*package com.qq.activity;

import com.qq.MainActivity;
import com.qq.R;
import com.qq.login.MyDatabase;
import com.qq.view.TextURLView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private Context mContext;
	private RelativeLayout rl_user;
	private Button mLogin;
	private Button register;
	private TextURLView mTextViewURL;
	
	private EditText telenumber;
	private EditText passworded;
	
	private MyDatabase myHelper;
	private SQLiteDatabase db;
	Cursor cursor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mContext=this;
		findView();
		initTvUrl();
		init();
	}
	
	private void findView(){
		rl_user=(RelativeLayout) findViewById(R.id.rl_user);
		mLogin=(Button) findViewById(R.id.login);
		register=(Button) findViewById(R.id.register);
		mTextViewURL=(TextURLView) findViewById(R.id.tv_forget_password);
		telenumber=(EditText)findViewById(R.id.account);
		passworded=(EditText)findViewById(R.id.password);
		myHelper=new MyDatabase(this);
	}

	private void init(){
		Animation anim=AnimationUtils.loadAnimation(mContext, R.anim.login_anim);
		anim.setFillAfter(true);
		rl_user.startAnimation(anim);
		
		mLogin.setOnClickListener(loginOnClickListener);
		register.setOnClickListener(registerOnClickListener);
	}
	
	private void initTvUrl(){
		mTextViewURL.setText(R.string.forget_password);
	}
	
	private OnClickListener loginOnClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			String account =telenumber.getText().toString();
			String password=passworded.getText().toString();
			
			db = myHelper.getReadableDatabase();
			cursor = db.query("User", null, "tele=?", new String[]{account}, null, null, null);
			    		if(cursor.getCount()==0){
							Toast.makeText(getApplicationContext(),
									"账号不存在，请从新输入", Toast.LENGTH_SHORT).show();
							telenumber.setText("");
							passworded.setText("");
							cursor.close();
							db.close();
							myHelper.close();
						}
						else if(cursor.moveToFirst()&&!(cursor.getString(cursor.getColumnIndex("password")).equals(password))){
							Toast.makeText(getApplicationContext(),
									"输入密码错误，请从新输入", Toast.LENGTH_SHORT).show();
							telenumber.setText("");
							passworded.setText("");
							cursor.close();
							db.close();
							myHelper.close();
						}
						else{
							Toast.makeText(getApplicationContext(),
									"登录成功", Toast.LENGTH_SHORT).show();	
						Intent intent =new Intent(mContext,MainActivity.class);
						startActivity(intent);
						finish();
						}  
			
		}
	};
	
	private OnClickListener registerOnClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent intent=new Intent(mContext, RegisterPhoneActivity.class);
			startActivity(intent);
			
		}
	};
}
*/
package com.voice.activity;


import com.voice.MainActivity;
import com.voice.R;
import com.voice.User;
import com.voice.login_database.MyDatabase;
import com.voice.view.TextURLView;
import com.voice.register.RegisterPhoneActivity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private Context mContext;
	private RelativeLayout rl_user;
	private Button mLogin;
	private Button register;
	private TextURLView mTextViewURL;
	private EditText telenumber;
	private EditText passworded;
	
	private MyDatabase myHelper;
	private SQLiteDatabase db;
	Cursor cursor;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mContext=this;
		findView();
		initTvUrl();
		init();
	}
	
	private void findView(){
		rl_user=(RelativeLayout) findViewById(R.id.rl_user);
		mLogin=(Button) findViewById(R.id.login);
		register=(Button) findViewById(R.id.register);
		mTextViewURL=(TextURLView) findViewById(R.id.tv_forget_password);
		telenumber=(EditText)findViewById(R.id.account);
		passworded=(EditText)findViewById(R.id.password);
		myHelper=new MyDatabase(this,"dbName",null,1);
	}

	private void init(){
		Animation anim=AnimationUtils.loadAnimation(mContext, R.anim.login_anim);
		anim.setFillAfter(true);
		rl_user.startAnimation(anim);
		
		mLogin.setOnClickListener(loginOnClickListener);
		register.setOnClickListener(registerOnClickListener);
	}
	
	private void initTvUrl(){
		mTextViewURL.setText(R.string.forget_password);
	}
	
	private OnClickListener loginOnClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) 
		{
			String account =telenumber.getText().toString();
			String password=passworded.getText().toString();
			
			db = myHelper.getReadableDatabase();
			cursor = db.query("User", null, "tele=?", new String[]{account}, null, null, null);
			    		if(!cursor.moveToFirst()){
							Toast.makeText(getApplicationContext(),
									"账号不存在，请重新输入", Toast.LENGTH_SHORT).show();
							telenumber.setText("");
							passworded.setText("");
							cursor.close();
							db.close();
							myHelper.close();
						}
						else if(cursor.moveToFirst()&&!(cursor.getString(cursor.getColumnIndex("password")).equals(password))){
							Toast.makeText(getApplicationContext(),
									"输入密码错误，请从新输入", Toast.LENGTH_SHORT).show();
							telenumber.setText("");
							passworded.setText("");
							cursor.close();
							db.close();
							myHelper.close();
						}
						else{
							Toast.makeText(getApplicationContext(),
									"登录成功", Toast.LENGTH_SHORT).show();
							String UserName=cursor.getString(cursor.getColumnIndex("name"));
							String Sign=cursor.getString(cursor.getColumnIndex("sign"));
							String Sex=cursor.getString(cursor.getColumnIndex("sex"));
							String RegistDate=cursor.getString(cursor.getColumnIndex("regist_date"));
							User user=new User(account,UserName,"",Sign,Sex,RegistDate);
							cursor.close();
							db.close();
							myHelper.close();
							Intent intent =new Intent(mContext,MainActivity.class);
							Bundle u=new Bundle();
							u.putSerializable("user", user);
							intent.putExtras(u);
							startActivity(intent);
							finish();
						}
		}
	};
	
	
	private OnClickListener registerOnClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent intent=new Intent(mContext, RegisterPhoneActivity.class);
			startActivity(intent);
		
		}
	};
}

