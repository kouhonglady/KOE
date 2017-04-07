package com.voice.activity;


import com.voice.R;
import com.voice.view.TitleBarView;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class NewsFatherFragment extends Fragment {
	private static final String TAG = "NewsFatherFragment";
	private Context mContext;
	private View mBaseView;	
	private RelativeLayout mCanversLayout;
	private TitleBarView mTitleBarView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext=getActivity();
		mBaseView=inflater.inflate(R.layout.fragment_news_father, null);
		findView();
		init();
		return mBaseView;
	}
	
	private void findView(){
		mCanversLayout=(RelativeLayout) mBaseView.findViewById(R.id.rl_canvers);
		mTitleBarView=(TitleBarView) mBaseView.findViewById(R.id.title_bar);
	}
	
	private void init(){
					mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE, View.GONE);
					mTitleBarView.setTitleText(R.string.wifi);
					FragmentTransaction ft=getFragmentManager().beginTransaction();
					NewsFragment newsFragment=new NewsFragment();
					ft.replace(R.id.child_fragment, newsFragment,NewsFatherFragment.TAG);
					ft.commit();

	}

}
