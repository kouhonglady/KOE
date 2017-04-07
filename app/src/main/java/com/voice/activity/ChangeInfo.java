package com.voice.activity;

//import com.qq.R;
import com.voice.R;
import com.voice.activity.LoginActivity;
import com.voice.fragment.SettingFragment;
import com.voice.login_database.MyDatabase;
import com.voice.view.TitleBarView;
import com.voice.MainActivity;
import com.voice.database.DataAccess;
import com.voice.study.StudyActivity;
import com.voice.study.StudyChoice;
import com.voice.study.StudyMain;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChangeInfo extends Activity {
	//
	private Context mContext;
	private View mBaseView;
	private TitleBarView mTitleBarView;
	private TextView ID;
	private EditText Name;
	private EditText Sign;
	public RelativeLayout ChangeFinish;
	private MyDatabase myHelper;
	private SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changeinfo);
		mContext=this;
		findView();
		init();
	}
	
	private void findView(){
		mTitleBarView=(TitleBarView)findViewById(R.id.title_bar);
		ID=(TextView)findViewById(R.id.UsrID);
		Name=(EditText)findViewById(R.id.UsrName);
		Sign=(EditText)findViewById(R.id.Sign);
		myHelper=new MyDatabase(this,"dbName",null,1);
		ChangeFinish=(RelativeLayout) findViewById(R.id.ChangeFinish);
	}
	
	private void init(){
		final MainActivity m=new MainActivity();
		mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE, View.GONE);
		mTitleBarView.setTitleText(R.string.mime);
		ID.setText("账号："+m.user.getLogId());
		Sign.setText(m.user.getSign());
		Name.setText(m.user.getLogName());
		final String OldName=Name.getText().toString();
		ChangeFinish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String NewName=Name.getText().toString();
				String NewSign=Sign.getText().toString();
				int check=checkname(NewName);
				if(check==0)
					Toast.makeText(getApplicationContext(),
							"用户名不能为空", Toast.LENGTH_SHORT).show();
				else{
					db = myHelper.getWritableDatabase();
					ContentValues values=new ContentValues();
					values.put("name", NewName);
					values.put("sign", NewSign);
					db.update("User", values, "tele=?", new String[]{m.user.getLogId()});	
					m.user.setLogName(NewName);
					m.user.setSign(NewSign);
					db.close();
					myHelper.close();
					m.finish();
					finish();
					
				}	
			}
		});
	}
	private int checkname(String name){
		int flag=0;
		if(name.length()>0)
			flag=1;
		return flag;
	}
}
