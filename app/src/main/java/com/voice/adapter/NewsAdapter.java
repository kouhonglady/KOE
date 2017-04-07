package com.voice.adapter;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.List;

import com.voice.MainActivity;
import com.voice.R;
import com.voice.bean.RecentChat;
import com.voice.util.ImgUtil;
import com.voice.util.ImgUtil.OnLoadBitmapListener;
import com.voice.util.SystemMethod;
import com.voice.view.CustomListView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.Activity;

public class NewsAdapter extends BaseAdapter{
	protected static final String TAG = "NewsAdapter";
	private Context mContext;
	private List<RecentChat> lists;
	private CustomListView mCustomListView;
	public OnClickListener listener;  
	private HashMap<String, SoftReference<Bitmap>> hashMaps = new HashMap<String, SoftReference<Bitmap>>();
	public NewsAdapter(Context context, List<RecentChat> lists,
			CustomListView customListView) {
		this.mContext = context;
		this.lists = lists;
		this.mCustomListView = customListView;
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
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Holder holder;
		RecentChat chat = lists.get(position);
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.fragment_news_item,
					null);
			holder = new Holder();
			holder.iv = (ImageView) convertView.findViewById(R.id.user_picture);
			holder.tv_name = (TextView) convertView
					.findViewById(R.id.user_name);
			holder.tv_feel = (TextView) convertView
					.findViewById(R.id.user_feel);
			holder.tv_time = (TextView) convertView
					.findViewById(R.id.user_time);
			holder.info = (RelativeLayout) convertView
					.findViewById(R.id.info);
			
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		String path = chat.getImgPath();
		final String add=chat.getUserFeel();
		if (hashMaps.containsKey(path)) {
			holder.iv.setImageBitmap(hashMaps.get(path).get());
			//另一个地方缓存释放资源
			ImgUtil.getInstance().reomoveCache(path);
		} else {
			holder.iv.setTag(chat.getImgPath());
			ImgUtil.getInstance().loadBitmap(chat.getImgPath(),
					new OnLoadBitmapListener() {
						@Override
						public void loadImage(Bitmap bitmap, String path) {
							ImageView iv = (ImageView) mCustomListView
									.findViewWithTag(path);
							if (bitmap != null && iv != null) {
								bitmap = SystemMethod.toRoundCorner(bitmap, 15);
								iv.setImageBitmap(bitmap);

								if (!hashMaps.containsKey(path)) {
									hashMaps.put(path,
											new SoftReference<Bitmap>(bitmap));
								}
							}
						}
					});

		}
		holder.info.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Intent intent = new Intent();
		        intent.setAction("android.intent.action.VIEW");
		        
		        Uri content_url = Uri.parse(add);
		        intent.setData(content_url);
		        mContext.startActivity(intent);
			}
			});
		holder.tv_name.setText(chat.getUserName());
		holder.tv_feel.setText(chat.getIntroduction());
		holder.tv_time.setText(chat.getUserTime());
		
		return convertView;
	}

	class Holder {
		ImageView iv;
		TextView tv_name;
		TextView tv_feel;
		TextView tv_time;
		RelativeLayout info;  
	}

}
