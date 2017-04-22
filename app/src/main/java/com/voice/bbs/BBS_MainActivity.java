package com.voice.bbs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.voice.MainActivity;
import com.voice.R;
import com.voice.bbs.XListView.IXListViewListener;


import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BBS_MainActivity extends Fragment implements IXListViewListener{
private ArrayList<Map<String, Object>> mlList,mmList,list;
private Context context;
private View mBaseView;
private LinearLayout ll_bo,ll_po;
private PopupWindow pw;
private RelativeLayout rl;
private EditText et_pop;
private TextView tv_qs,tv_hf;
private MyAdapter adapter;
private XListView mListView;
private ListView lv_hf;
private MyHFAdapter myHFAdapter;
private HolderView holder;
private boolean flag=false;
private ImageView head_series;

private int pos=0;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.activity_bbs_main);
//
//    }


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		context=this.getActivity();
		mBaseView=inflater.inflate(R.layout.activity_bbs_main, null);
		findView();
		addData();
		loadData();
		addListenr();
		return mBaseView;
	}


    private void addListenr() {
		// TODO Auto-generated method stub
    	tv_hf.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String content=et_pop.getText().toString();
				
			

				//----获取当前用户信息-----------------------------
				final MainActivity m=new MainActivity();
				//------------------------------------------------
				
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("name",m.user.getLogName()+":");
				map.put("content", content);
				
				if(flag){
					if(list!=null){
						mlList.remove(pos);
						//----------------------回复--------------------------------
						Map<String, Object> ap=new HashMap<String, Object>();
						ap.put("name", "凌小雪");
						ap.put("content", "学习遇到了瓶颈，不开心");
						
						ArrayList<Map<String,Object>> st1=new ArrayList<Map<String,Object>>();
						
							Map<String, Object> mp=new HashMap<String, Object>();
							mp.put("name", "火龙");
							mp.put("content", ": 你怎么了");
							st1.add(mp);
						
							Map<String, Object> mp5=new HashMap<String, Object>();
							mp5.put("name", "吾谁与归");
							mp5.put("content", ": 坚持每天学一个");
							st1.add(mp5);
							
							Map<String, Object> mp6=new HashMap<String, Object>();
							mp6.put("name", "口红小姐");
							mp6.put("content", ":遇到什么瓶颈了呀，也许我可以帮助你");
							st1.add(mp6);
						//	st.add(map);
						
							st1.add(map);
						ap.put("replys", st1);
						mlList.add(pos, ap);
						
						
					}
				}else{
					//---------------发布--------------------------------
					
					ArrayList<Map<String,Object>> st=new ArrayList<Map<String,Object>>();
					
				
						
						Map<String, Object> mp=new HashMap<String, Object>();
						mp.put("name", "");
						mp.put("content", "");
						st.add(mp);
					
					
					map.put("replys", st);
					
					mlList.add(map);
					adapter.notifyDataSetChanged();
				}
				
				et_pop.getText().clear();
				ll_po.setVisibility(View.GONE);
				ll_bo.setVisibility(View.VISIBLE);
			
				
			}
		});
    	tv_qs.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				flag=false;
				ll_po.setVisibility(View.GONE);
				ll_bo.setVisibility(View.VISIBLE);
			}
		});
    	ll_bo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			//	ll_po.setFocusable(true);
			//	tv_pop.setFocusable(true);
			//	tv_pop.setFocusableInTouchMode(true);
				//ll_po.requestFocus();
				showInput();
			}
		});
	}
    /*
     * ��ʾ�ظ������ݿ�
     */
    public void  showInput(){
    	ll_bo.setVisibility(View.GONE);
		ll_po.setVisibility(View.VISIBLE);
		et_pop.setFocusable(true);
		et_pop.requestFocus();
		((InputMethodManager)context.getSystemService(context.INPUT_METHOD_SERVICE)).
		showSoftInput(et_pop, 0);
    }

	private void findView() {
		// TODO Auto-generated method stub
    	
    	mlList=new ArrayList<Map<String,Object>>();
    	mListView=(XListView) mBaseView.findViewById(R.id.lv);
    	ll_bo=(LinearLayout) mBaseView.findViewById(R.id.ll_bo);
    	ll_po=(LinearLayout) mBaseView.findViewById(R.id.ll_po);
    	rl=(RelativeLayout) mBaseView.findViewById(R.id.rl);
    	et_pop=(EditText) mBaseView.findViewById(R.id.tv_pop);
    	tv_qs=(TextView) mBaseView.findViewById(R.id.tv_qs);
    	tv_hf=(TextView) mBaseView.findViewById(R.id.tv_hf);
		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(this);
		lv_hf=(ListView) mBaseView.findViewById(R.id.lv_hf);
	}


	private void addPop() {
		// TODO Auto-generated method stub
		pw=new PopupWindow(context);
		pw.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
		pw.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
		pw.setContentView(View.inflate(context, R.layout.ll_bottom, null));
		pw.setFocusable(true);
		pw.setOutsideTouchable(true);
		pw.showAtLocation(rl, Gravity.BOTTOM, 0, 0);
	}


	private void addData() {
		// TODO Auto-generated method stub
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("name", "凌小雪");
		map.put("content", "学习遇到了瓶颈，不开心");
		//map.put("head_num", "1");
		mmList=new ArrayList<Map<String,Object>>();
		
			Map<String, Object> mp=new HashMap<String, Object>();
			mp.put("name", "火龙");
			mp.put("content", ": 你怎么了");
			mmList.add(mp);
			
			Map<String, Object> mp5=new HashMap<String, Object>();
			mp5.put("name", "吾谁与归");
			mp5.put("content", ": 坚持每天学一个");
			mmList.add(mp5);
			
			Map<String, Object> mp6=new HashMap<String, Object>();
			mp6.put("name", "口红小姐");
			mp6.put("content", ":遇到什么瓶颈了呀，也许我可以帮助你");
			mmList.add(mp6);
		
		map.put("replys", mmList);
		mlList.add(map);
//  head_num=1;
	Log.i("test", ":"+mmList.size());
	
	//  head_series.setImageResource(R.drawable.head_2);
		Map<String, Object> map2=new HashMap<String, Object>();
		map2.put("name", "黄纯纯");
		map2.put("content", "不狗带就只有活着");
		//map2.put("head_num","2");
		mmList=new ArrayList<Map<String,Object>>();
	
			Map<String, Object> mp2=new HashMap<String, Object>();
			mp2.put("name", "姜阿雄");
			mp2.put("content", ": 厉害了");
			mmList.add(mp2);
			
			Map<String, Object> mp7=new HashMap<String, Object>();
			mp7.put("name", "Dare");
			mp7.put("content", ": 我竟无言以对");
			mmList.add(mp7);
			
			Map<String, Object> mp8=new HashMap<String, Object>();
			mp8.put("name", "刘桑");
			mp8.put("content", ": 为什么突然这样有哲理");
			mmList.add(mp8);
		
		map2.put("replys", mmList);
		mlList.add(map2);
   // head_num=2;
	Log.i("test", ":"+mmList.size());
	
	Map<String, Object> map3=new HashMap<String, Object>();
	map3.put("name", "姜昭雪");
	map3.put("content", "今天又学了好几个词 好开心呀");
	mmList=new ArrayList<Map<String,Object>>();

		Map<String, Object> mp3=new HashMap<String, Object>();
		mp3.put("name", "红龙");
		mp3.put("content", ": 继续加油哦");
		mmList.add(mp3);
	
		Map<String, Object> mp9=new HashMap<String, Object>();
		mp9.put("name", "Dare");
		mp9.put("content", ": 学到哪里了，我学到拼音n了，分不清l和n");
		mmList.add(mp9);
		
		Map<String, Object> mp10=new HashMap<String, Object>();
		mp10.put("name", "向阳花");
		mp10.put("content", ": 我们一起练习一下吧");
		mmList.add(mp10);
		
	map3.put("replys", mmList);
	mlList.add(map3);

Log.i("test", ":"+mmList.size());

 Map<String, Object> map4=new HashMap<String, Object>();
 map4.put("name", "高高人");
 map4.put("content", "今天学得内容有点难啊，要再练习啊");
 mmList=new ArrayList<Map<String,Object>>();

	Map<String, Object> mp4=new HashMap<String, Object>();
	mp4.put("name", "红龙");
	mp4.put("content", ": 继续加油哦");
	mmList.add(mp4);
	
	Map<String, Object> mp11=new HashMap<String, Object>();
	mp11.put("name", "人气呆毛学术");
	mp11.put("content", ": 我也遇到瓶颈了，互相鼓励一下");
	mmList.add(mp11);

 map4.put("replys", mmList);
 mlList.add(map4);

Log.i("test", ":"+mmList.size());
	
	}


	private void loadData() {
		// TODO Auto-generated method stub
		adapter=new MyAdapter();
		mListView.setAdapter(adapter);
	}


//	@Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        // Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
public class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mlList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return mlList.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@SuppressWarnings("unchecked")
		@Override
		public View getView(final int position, View convertview, ViewGroup arg2) {
			// TODO Auto-generated method stub
			holder=new HolderView();
			if(convertview==null){
				convertview=View.inflate(context, R.layout.tiezi_item, null);
				holder.tv_name=(TextView) convertview.findViewById(R.id.tv_name);
				holder.tv_hf=(TextView) convertview.findViewById(R.id.tv_hf);
				holder.tv_content=(TextView) convertview.findViewById(R.id.tv_content);
				holder.lv_hf=(ListView) convertview.findViewById(R.id.lv_hf);
				holder.head_series=(ImageView)convertview.findViewById(R.id.head_series);
				convertview.setTag(holder);
			}else{
				holder=(HolderView) convertview.getTag();
			}
			/////////////////////////////////////////////////////////
			if("凌小雪".equals(mlList.get(position).get("name").toString())) 
			holder.head_series.setImageResource(R.drawable.head_1);
			if("黄纯纯".equals(mlList.get(position).get("name").toString()))
			holder.head_series.setImageResource(R.drawable.head_2);
			if("姜昭雪".equals(mlList.get(position).get("name").toString()))
			holder.head_series.setImageResource(R.drawable.head3);
			if("高高人".equals(mlList.get(position).get("name").toString()))
			holder.head_series.setImageResource(R.drawable.head_4);
			
			holder.tv_name.setText(mlList.get(position).get("name").toString());
			if(!"".equals(mlList.get(position).get("content").toString())){
				holder.tv_content.setVisibility(View.VISIBLE);
				holder.tv_content.setText(mlList.get(position).get("content").toString());
			}
			 list=(ArrayList<Map<String, Object>>) mlList.get(position).get("replys");
				myHFAdapter=new MyHFAdapter();
				holder.lv_hf.setAdapter(myHFAdapter);
				setListViewHeightBasedOnChildren(holder.lv_hf);
				myHFAdapter.notifyDataSetChanged();
				Log.i("test", ""+list.size());
			holder.tv_hf.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					showInput();
					pos=position;
					flag=true;
					//Toast.makeText(context, pos+"", 1).show();
				}
			});
			return convertview;
		}
	
}private class HolderView{
	private TextView tv_name,tv_hf,tv_content;
	private ListView lv_hf;
	private ImageView head_series;
}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
				
				mlList.clear();
				addData();
				adapter.notifyDataSetChanged();
				onLoad();
		
	}


	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		
				// TODO Auto-generated method stub
				addData();
				adapter.notifyDataSetChanged();
				onLoad();
		
		
	}
	private void onLoad() {
		//----获取当前用户信息-----------------------------
				final MainActivity m=new MainActivity();
				//------------------------------------------------
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime(m.user.getLogName());
	}
	
	class MyHFAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return list.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View contentview, ViewGroup arg2) {
			// TODO Auto-generated method stub
			HolderView1 view1=new HolderView1();
			if(contentview==null){
				contentview=View.inflate(context, R.layout.hf_item, null);
				view1.tv_name1=(TextView) contentview.findViewById(R.id.tv_name1);
				view1.tv_content1=(TextView) contentview.findViewById(R.id.tv_content1);
				contentview.setTag(view1);
			}else{
				view1=(HolderView1) contentview.getTag();
			}
			view1.tv_name1.setText(list.get(arg0).get("name").toString());
			view1.tv_content1.setText(list.get(arg0).get("content").toString());
			return contentview;
		}
		
		class HolderView1{
			private TextView tv_name1,tv_content1;
		}	
	}
	public static void setListViewHeightBasedOnChildren(ListView listView) {  
        //��ȡListView��Ӧ��Adapter  
    ListAdapter listAdapter = listView.getAdapter();  
    if (listAdapter == null) {  
        // pre-condition  
        return;  
    }  

    int totalHeight = 0;  
    for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   //listAdapter.getCount()�������������Ŀ  
        View listItem = listAdapter.getView(i, null, listView);  
        listItem.measure(0, 0);  //��������View �Ŀ��  
        totalHeight += listItem.getMeasuredHeight();  //ͳ������������ܸ߶�  
    }  

    ViewGroup.LayoutParams params = listView.getLayoutParams();  
    params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));  
    //listView.getDividerHeight()��ȡ�����ָ���ռ�õĸ߶�  
    //params.height���õ�����ListView������ʾ��Ҫ�ĸ߶�  
    listView.setLayoutParams(params);  
}  
}
