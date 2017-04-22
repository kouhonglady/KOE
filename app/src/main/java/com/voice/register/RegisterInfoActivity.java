package com.voice.register;


import com.voice.R;
import com.voice.User;
import com.voice.login_database.MyDatabase;
import com.voice.view.TitleBarView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterInfoActivity extends Activity {
	private Context mContext;
	private Button btn_complete;
	private TitleBarView mTitleBarView;
	private TextView passwordTextView;
	private TextView nameTextView;
	private String UserName;
	private String UserID;
	private String UserPassword;
	private final String dbName="dbName";
	private final String tableName="tableRegister";
	private MyDatabase dbHelper;
	private SQLiteDatabase db;

	String telephone;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_userinfo);

		Bundle bundle = this.getIntent().getExtras();
	         /*获取Bundle中的数据，注意类型和key*/
		telephone= bundle.getString("telephone");

		mContext=this;
		findView();
		initTitleView();
		init();
	}

	private void findView(){
		mTitleBarView=(TitleBarView) findViewById(R.id.title_bar);
		btn_complete=(Button) findViewById(R.id.register_complete);
		passwordTextView=(TextView) findViewById(R.id.password);
		nameTextView=(TextView) findViewById(R.id.name);
		dbHelper=new MyDatabase(this,dbName,null,1);
	}

	private void init(){
		btn_complete.setOnClickListener(completeOnClickListener);
	}

	private void initTitleView(){
		mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE,View.GONE, View.GONE);
		mTitleBarView.setTitleText(R.string.title_register_info);
		mTitleBarView.setBtnLeft(R.drawable.boss_unipay_icon_back, R.string.back);
		mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();

			}
		});
	}

	private OnClickListener completeOnClickListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			db=dbHelper.getReadableDatabase();
			String accountName =nameTextView.getText().toString();
			String passwordRegist=passwordTextView.getText().toString();
			UserName=nameTextView.getText().toString();
			UserID=telephone;
			UserPassword=passwordTextView.getText().toString();
			int temp1,temp2;
			temp1=checkpassword(passwordRegist);
			temp2=checkname(accountName);
			if(temp1==1){
				Toast.makeText(getApplicationContext(),
						"设置密码长度错误", Toast.LENGTH_SHORT).show();
				nameTextView.setText("");
				passwordTextView.setText("");
			}
			else if(temp1==2){
				Toast.makeText(getApplicationContext(),
						"密码不能为9位数以下数字", Toast.LENGTH_SHORT).show();
				nameTextView.setText("");
				passwordTextView.setText("");
			}
			else{
				if(temp2==0)
					Toast.makeText(getApplicationContext(),
							"用户名不能为空", Toast.LENGTH_SHORT).show();
				else
				{
					SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
					Date date=new Date(System.currentTimeMillis());
					String registDate=dateFormat.format(date);
					ContentValues values=new ContentValues();
					values.put("tele",telephone );
					values.put("password",passwordRegist);
					values.put("name", accountName);
					values.put("sign", "暂无");
					values.put("sex","");
					values.put("regist_date",registDate);
					db.insert("User",null,values);

					Intent intent=new Intent(mContext, RegisterResultActivity.class);
					User user=new User(UserID,UserName,UserPassword,"暂无","",registDate);
					Bundle u=new Bundle();
					u.putSerializable("user", user);
					intent.putExtras(u);
					startActivity(intent);
					finish();
				}
			}
		}
	};


	private int checkpassword(String tele){
		int flag=0;
		if(tele.length()<6||tele.length()>16){
			flag=1;
		}
		else if(tele.length()<9){
			for(int i=0;i<tele.length();i++)
			{
				if(Character.isDigit(tele.charAt(i))!=true)
				{
					flag=3;
					break;
				}
				flag=2;
			}
		}
		else flag=3;
		return flag;
	}
	private int checkname(String name){
		int flag=0;
		if(name.length()>0)
			flag=1;
		return flag;
	}
}
