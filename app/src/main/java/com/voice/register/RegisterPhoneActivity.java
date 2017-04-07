package com.voice.register;


import com.voice.R;
import com.voice.login_database.MyDatabase;
import com.voice.view.TextURLView;
import com.voice.view.TitleBarView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterPhoneActivity extends Activity {

	private Context mContext;
	private TitleBarView mTitleBarView;
	private TextURLView mTextViewURL;
	private Button next;
	private TextView et_phoneNumber;
    
	private String phonenumber;
	private String telenumber;
	
	private MyDatabase myHelper;
	private SQLiteDatabase db;
	Cursor cursor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_phone);
		mContext=this;
		findView();
		initTitleView();
		initTvUrl();
		init();
	}
	
	private void findView(){
		mTitleBarView=(TitleBarView) findViewById(R.id.title_bar);
		mTextViewURL=(TextURLView) findViewById(R.id.tv_url);
		next=(Button) findViewById(R.id.btn_next);
		et_phoneNumber=(TextView)findViewById(R.id.et_phoneNumber);
		myHelper=new MyDatabase(this,"dbName",null,1);
	}
	
	private void init(){
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				phonenumber=et_phoneNumber.getText().toString();
				int temp=checktele();
				int temp1=chenkIsTeleUsed();
				if(temp==5||temp==0){
					Toast.makeText(getApplicationContext(),
							"电话号码位数错误", Toast.LENGTH_SHORT).show();
					    
				}
				else if(temp==4){
					Toast.makeText(getApplicationContext(),
							"未知电话号码", Toast.LENGTH_SHORT).show();	     
				}
				else{
					if(temp1==1){
						Toast.makeText(getApplicationContext(),
						"电话号码已经存在，请从新输入", Toast.LENGTH_SHORT).show();
						et_phoneNumber.setText("");
					}
					else{
					switch(temp){
					case 1:Toast.makeText(getApplicationContext(),
							"中国移动", Toast.LENGTH_SHORT).show();
					      break;
					case 2:
						Toast.makeText(getApplicationContext(),
								"中国联通", Toast.LENGTH_SHORT).show();
						      break;
					case 3:
						Toast.makeText(getApplicationContext(),
								"中国电信", Toast.LENGTH_SHORT).show();
						      break;
						      }
				Intent intent=new Intent(mContext,RegisterInfoActivity.class);
				Bundle b = new Bundle();   
                b.putString("telephone", phonenumber);  
				  //此处使用putExtras，接受方就响应的使用getExtra  
			    intent.putExtras( b );    
				startActivity(intent);
					}
				}
				
			}
		});
	}
	
	private void initTitleView(){
		mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE,View.GONE, View.GONE);
		mTitleBarView.setBtnLeft(R.drawable.boss_unipay_icon_back, R.string.back);
		mTitleBarView.setTitleText(R.string.title_phoneNumber);
		mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	private void initTvUrl(){
		mTextViewURL.setText(R.string.tv_xieyi_url);
	}
	 private int checktele(){

   	  String YD = "^[1]{1}(([3]{1}[4-9]{1})|([5]{1}[012789]{1})|([8]{1}[2378]{1})|([4]{1}[7]{1}))[0-9]{8}$";  
   	  String LT = "^[1]{1}(([3]{1}[0-2]{1})|([5]{1}[56]{1})|([8]{1}[56]{1}))[0-9]{8}$";  
   	  String DX = "^[1]{1}(([3]{1}[3]{1})|([5]{1}[3]{1})|([8]{1}[09]{1}))[0-9]{8}$"; 
   	  
   	  
   	  /**
 		 * flag = 1 YD 2 LT 3 DX 
 		 */
 		int flag=0;// 存储匹配结果
 		// 判断手机号码是否是11位
 		if (phonenumber.length() == 11) {
 			// 判断手机号码是否符合中国移动的号码规则
 			if (phonenumber.matches(YD)) {
 				flag = 1;
 			}
 			// 判断手机号码是否符合中国联通的号码规则
 			else if (phonenumber.matches(LT)) {
 				flag = 2;
 			}
 			// 判断手机号码是否符合中国电信的号码规则
 			else if (phonenumber.matches(DX)) {
 				flag = 3;
 			}
 			// 都不合适 未知
 			else {
 				flag = 4;
 			}
 		}
 		// 不是11位
 		else {
 			flag = 5;
 		}
 		return flag;
   }
   private int chenkIsTeleUsed(){
   	int  flag=0;   	
   	db = myHelper.getReadableDatabase();
		cursor = db.query("User", null, "tele=?", new String[]{phonenumber}, null, null, null);
		for (int i = 0; i < cursor.getCount(); i++) {
			cursor.moveToPosition(i);
			if(phonenumber.equals(cursor.getString(cursor.getColumnIndex("tele")))){
				Toast.makeText(getApplicationContext(),
						"注册账号已经存在", Toast.LENGTH_SHORT)
						.show();
				et_phoneNumber.setText("");
				flag=1;
			}
		}
		cursor.close();
		db.close();
		myHelper.close();
		/*    		
		if(!cursor.moveToFirst()){
		    			if(cursor.getCount()!=0)
						flag=1;
						cursor.close();
						db.close();
						myHelper.close();
					}
		*/
   	return flag; 
   }

}
