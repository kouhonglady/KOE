package com.voice.fragment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.voice.AsyncTaskBase;
import com.voice.R;
import com.voice.adapter.NewsAdapter;
import com.voice.bean.RecentChat;
import com.voice.test.TestData;
import com.voice.view.CustomListView;
import com.voice.view.TitleBarView;
import com.voice.view.CustomListView.OnRefreshListener;
import com.voice.view.LoadingView;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SearchViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.SearchView;

public class NewsFragment extends Fragment {
	private static final String TAG = "NewsFragment";
	private Context mContext;
	private View mBaseView;
	private CustomListView mCustomListView;
	private LoadingView mLoadingView;
	private SearchView mSearchView;
	private TitleBarView mTitleBarView;
	private NewsAdapter adapter;
	private LinkedList<RecentChat> chats = new LinkedList<RecentChat>();



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		mContext = getActivity();
		mBaseView = inflater.inflate(R.layout.fragment_news, null);
		//mSearchView = inflater.inflate(R.layout.common_search_l, null);
		findView();
		init();
		return mBaseView;
	}

	private void findView() {
		mCustomListView = (CustomListView) mBaseView.findViewById(R.id.lv_news);
		mLoadingView = (LoadingView) mBaseView.findViewById(R.id.loading);
		mSearchView=(SearchView)mBaseView.findViewById(R.id.search_view);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void init() {
		adapter = new NewsAdapter(mContext, chats, mCustomListView);
		mCustomListView.setAdapter(adapter);
		//mCustomListView.addHeaderView(mSearchView);

		mCustomListView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				new AsyncRefresh().execute(0);
			}
		});
//		mSearchView.setOnClickListener(new OnClickListener(){
//			@Override
//			public void onClick(View v){
//				Intent intent = new Intent();
//		        intent.setAction("android.intent.action.VIEW");
//		        Uri content_url = Uri.parse("https://m.baidu.com/");
//		        intent.setData(content_url);
//		        mContext.startActivity(intent);
//			}
//			});
		mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String s) {
				Intent intent = new Intent();
				intent.setAction("android.intent.action.VIEW");
				Uri content_url = Uri.parse("https://m.baidu.com/s?wd="+s);
				intent.setData(content_url);
				mContext.startActivity(intent);
				return false;
			}

			@Override
			public boolean onQueryTextChange(String s) {
				return false;
			}
		});
		mCustomListView.setCanLoadMore(false);
		new NewsAsyncTask(mLoadingView).execute(0);
	}

	private class NewsAsyncTask extends AsyncTaskBase {
		List<RecentChat> recentChats = new ArrayList<RecentChat>();

		public NewsAsyncTask(LoadingView loadingView) {
			super(loadingView);
		}

		@Override
		protected Integer doInBackground(Integer... params) {
			int result = -1;
			recentChats = TestData.getRecentChats();
			if (recentChats.size() > 0) {
				result = 1;
			}
			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			chats.addAll(recentChats);
			adapter.notifyDataSetChanged();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

	}

	private class AsyncRefresh extends
			AsyncTask<Integer, Integer, List<RecentChat>> {
		private List<RecentChat> recentchats = new ArrayList<RecentChat>();

		@Override
		protected List<RecentChat> doInBackground(Integer... params) {
			recentchats = TestData.getRecentChats();
			return recentchats;
		}

		@Override
		protected void onPostExecute(List<RecentChat> result) {
			super.onPostExecute(result);
			if (result != null) {

				for (RecentChat rc : recentchats) {
					chats.addFirst(rc);
				}
				adapter.notifyDataSetChanged();
				mCustomListView.onRefreshComplete();
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

	}

}
