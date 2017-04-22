package com.voice.fragment;

import com.voice.R;
import com.voice.activity.LoginActivity;
import com.voice.view.CircleImageView;
import com.voice.view.TitleBarView;
import com.voice.MainActivity;
import com.voice.activity.ChangeInfo;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SettingFragment extends Fragment {

	private Context mContext;
	private View mBaseView;
	private TitleBarView mTitleBarView;
	private TextView ID;
	private TextView Name;
	private TextView Sign;
	private ImageView Sex;
	private TextView RegistDate;
	private RelativeLayout Logout;
	private ImageView ChangeInfo;
	//private CircleImageView Fresh;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		mContext=getActivity();
		mBaseView=inflater.inflate(R.layout.fragment_mine, null);
		findView();
		init();
		return mBaseView;
	}
	@Override
	//public void onStart(){
	public void onResume(){
		init();
		super.onResume();
	}

	private void findView(){
		mTitleBarView=(TitleBarView) mBaseView.findViewById(R.id.title_bar);
		ID=(TextView)mBaseView.findViewById(R.id.UsrID);
		Name=(TextView)mBaseView.findViewById(R.id.UsrName);
		Sign=(TextView)mBaseView.findViewById(R.id.Sign);
		Logout=(RelativeLayout) mBaseView.findViewById(R.id.Logout);
		ChangeInfo=(ImageView) mBaseView.findViewById(R.id.ChangeInfo);
		Sex=(ImageView)mBaseView.findViewById(R.id.sex);
		RegistDate=(TextView)mBaseView.findViewById(R.id.regist_date);
		//Fresh=(CircleImageView)mBaseView.findViewById(R.id.fresh);
	}

	public void init(){
		final MainActivity m=new MainActivity();
		mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE, View.GONE);
		mTitleBarView.setTitleText(R.string.mime);
//		ID.setText("账号："+m.user.getLogId());
//		Name.setText("用户名："+m.user.getLogName());
		ID.setText(m.user.getLogId());
		Name.setText(m.user.getLogName());
		Sign.setText(m.user.getSign());
		String sex=m.user.getSex();
		RegistDate.setText("加入于"+m.user.getRegistDate());
		switch (sex){
			case "":
				Sex.setVisibility(View.GONE);
				break;
			case "female":
				Sex.setVisibility(View.VISIBLE);
				Sex.setImageDrawable(getResources().getDrawable(R.drawable.ic_female));
				break;
			case "male":
				Sex.setVisibility(View.VISIBLE);
				Sex.setImageDrawable(getResources().getDrawable(R.drawable.ic_male));
				break;
		}

		Logout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				m.finish();
				Intent intent = new Intent();
				intent.setClass(mContext, LoginActivity.class);
				mContext.startActivity(intent);
			}
		});
		ChangeInfo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(mContext, ChangeInfo.class);
				mContext.startActivity(intent);
			}
		});
//		Fresh.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				init();
//				Toast.makeText(mContext,
//						"已刷新", Toast.LENGTH_SHORT).show();
//			}
//		});

	}

}
