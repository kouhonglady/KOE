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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ChangeInfo extends Activity {
	//
	private Context mContext;
	private View mBaseView;
	private TitleBarView mTitleBarView;
	public static final String[] SexChoice={"未知","男","女"};
	private Spinner SexSpiner;
	private ArrayAdapter<String> arrayAdapter;
	private TextView ID;
	private EditText Name;
	private EditText Sign;
	public RelativeLayout ChangeFinish;
	private MyDatabase myHelper;
	private SQLiteDatabase db;
	private String Sex;
	private TextView SexShow;
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
		SexShow=(TextView)findViewById(R.id.sex_show);
		myHelper=new MyDatabase(this,"dbName",null,1);
		ChangeFinish=(RelativeLayout) findViewById(R.id.ChangeFinish);
		SexSpiner=(Spinner)findViewById(R.id.sex_choice);
		arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,SexChoice);
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		SexSpiner.setAdapter(arrayAdapter);

	}

	private void init(){
		final MainActivity m=new MainActivity();
		mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE, View.GONE);
		mTitleBarView.setTitleText(R.string.mime);
		ID.setText("账号："+m.user.getLogId());
		Sign.setText(m.user.getSign());
		Name.setText(m.user.getLogName());
		switch (m.user.getSex()){
			case "":
				SexSpiner.setSelection(0,true);
				SexShow.setText("您的性别：未知");
				break;
			case "male":
				SexSpiner.setSelection(1,true);
				SexShow.setText("您的性别：男");
				break;
			case "female":
				SexSpiner.setSelection(2,true);
				SexShow.setText("您的性别：女");
				break;
		}
//		if(!m.user.getSex().equals("")){
//			SexShow.setText("您的性别："+m.user.getSex());
//		}
//		SexSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
//			@Override
//			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//				Sex=SexChoice[i];
//				SexShow.setText("您的性别："+Sex);
//			}
//		});
		SexSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				Sex=SexChoice[i];
				SexShow.setText("您的性别："+Sex);
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {
			}
		});
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
					switch (Sex){
						case "未知":
							values.put("sex","");
							m.user.setSex("");
							break;
						case "男":
							values.put("sex","male");
							m.user.setSex("male");
							break;
						case  "女":
							values.put("sex","female");
							m.user.setSex("female");
							break;
					}
					db.update("User", values, "tele=?", new String[]{m.user.getLogId()});
					m.user.setLogName(NewName);
					m.user.setSign(NewSign);

					//SettingFragment sf=new SettingFragment();
					//sf.init();
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
