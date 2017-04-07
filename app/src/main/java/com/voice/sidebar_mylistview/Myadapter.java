package com.voice.sidebar_mylistview;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.voice.R;
import com.voice.sidebar_switchoff.SwitchButton;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Myadapter extends BaseAdapter {
	protected static final String TAG = "MyAdapter";
	private Context mContext;
	private List<Map<String,Object>> lists;
	private LayoutInflater listContainer;
	private boolean[] hasChecked;
	
	
	class Holder {
		TextView tv_name;
		TextView tv_shuoming;
		SwitchButton switchbutton;
	}
	
	
	public Myadapter(Context context, List<Map<String, Object>> lists) {
		this.mContext = context;
		listContainer=LayoutInflater.from(context);
		this.lists = lists;
		hasChecked=new boolean[getCount()];
	}

	@Override
	public int getCount() {
		if (lists != null) {
			return lists.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private void checkedChange(int checkedID){
		hasChecked[checkedID]=!hasChecked[checkedID];
	}
	
	private boolean hasChecked(int checkedID){
		return hasChecked[checkedID];
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Holder holder;
		//Map<String, String> listItem = lists.get(position);
		
		
		if (convertView == null) {
			listContainer=LayoutInflater.from(mContext);
			convertView = listContainer.inflate(R.layout.sidebar_listitem,null);
			holder = new Holder();
			holder.tv_name = (TextView) convertView.findViewById(R.id.character_title);
			holder.tv_shuoming = (TextView) convertView.findViewById(R.id.character_shuoming);
			holder.switchbutton = (SwitchButton) convertView.findViewById(R.id.switchbotton);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

        
		holder.tv_name.setText((String)lists.get(position).get("character_name"));
		holder.tv_shuoming.setText((String)lists.get(position).get("character_shuoming"));
		//holder.switchbutton.setOnCheckedChangeListener();
		return convertView;
	}



}
