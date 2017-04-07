package com.voice.study;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.voice.database.DataAccess;
import com.voice.model.BookList;
import com.voice.R;
import com.voice.model.WordList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.app.TabActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;

public class StudyChoice extends TabActivity implements TabHost.TabContentFactory{
	String info = "hey";
	public ArrayList<WordList> wordlist;
	private ArrayList<String> listShould ;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TabHost th = getTabHost();
        //数据读取
        DataAccess data = new DataAccess(this);
        wordlist=data.QueryList("BOOKID ='"+DataAccess.difficultyID+"'", null);
        listShould = new ArrayList<String>(wordlist.size());
		for(int i=0;i<wordlist.size();i++){
			if (wordlist.get(i).getLearned().equals("0")){
				listShould.add(wordlist.get(i).getList());
			}
		}
		BookList book =data.QueryBook("ID ='"+DataAccess.difficultyID+"'", null).get(0);
		this.setTitle("学习-"+book.getName());
        //
        LayoutInflater.from(this).inflate(R.layout.studychoice, th.getTabContentView(), true);
        
        
        th.addTab(th.newTabSpec("studid").setIndicator("未学",StudyChoice.this.getResources().getDrawable(R.drawable.not_learn)).setContent(this));
        th.addTab(th.newTabSpec("all").setIndicator("所有",StudyChoice.this.getResources().getDrawable(R.drawable.all)).setContent(this));
    }
	public View createTabContent(String tag) {
   	 ListView lv = new ListView(this);
   	 LinearLayout ll = new LinearLayout(this);
   	 ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
   	 ll.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.main_back));
        ll.setOrientation(LinearLayout.VERTICAL);
        TextView tv = new TextView(this);
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(40);
        tv.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.main_budget_lv_header));
        ll.addView(tv);
   	 ll.addView(lv);
   	 
   	 lv.setCacheColorHint(0);
   	 SimpleAdapter adapterAll = new SimpleAdapter(this, getData(tag), R.layout.list5, new String[]{"label","status","image"}, new int[]{R.id.label,R.id.status,R.id.list5_image});
   	 SimpleAdapter adapterStudid = new SimpleAdapter(this, getData(tag), R.layout.list4, new String[]{"label","image"}, new int[]{R.id.label,R.id.list4_image});
        
          if(tag.equals("all")){
       	   tv.setText("    所有");
       	   lv.setAdapter(adapterAll);
              lv.setOnItemClickListener(new OnItemClickListener(){

			     public void onItemClick(AdapterView<?> arg0, View v, final int arg2,
					  long id) {
				      // TODO Auto-generated method stub
		    	 
		    	       Intent intent = null;

		    	       if(wordlist.get(arg2).getLearned().equals("1")){
		    	    	startStudy(arg2,2,1);
		    	       }
		    	       else
		    	    	startStudy(arg2,2,0);
			      }
       	 
            });
              

              
          }
          else if(tag.equals("studid")){
       	   tv.setText("    未学");
           	 lv.setAdapter(adapterStudid);
                lv.setOnItemClickListener(new OnItemClickListener(){

 			     public void onItemClick(AdapterView<?> arg0, View v, int position,
 					  long id) {
 				      // TODO Auto-generated method stub
 			    	 
 			    	 
 		    	     startStudy(position,1,0);
 			      }
         	 
              });
                
                
       			lv.setOnItemLongClickListener(new OnItemLongClickListener(){

    				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
    						final int arg2, long arg3) {
    					// TODO Auto-generated method stub
    					Dialog dialog = new AlertDialog.Builder(StudyChoice.this)
    		            .setIcon(R.drawable.dialog_icon)
    		            .setTitle("操作")
    		            .setItems(new String[]{"标记为已学习"}, new DialogInterface.OnClickListener(){

    						public void onClick(DialogInterface dialog, int which) {
    							// TODO Auto-generated method stub
    							if (which==0){
    								DataAccess data = new DataAccess(StudyChoice.this);
    								WordList labelList = wordlist.get(Integer.parseInt(listShould.get(arg2))-1);
    								labelList.setLearned("1");
    								Calendar cal = Calendar.getInstance();
    							    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
    							    String date=f.format(cal.getTime());
    							    labelList.setLearnedTime(date);
    							    labelList.setReview_times("0");
    							    labelList.setReviewTime("");
    								data.UpdateList(labelList);
    								Intent intent = new Intent();
    								intent.setClass(StudyChoice.this, StudyChoice.class);
    								finish();
    								startActivity(intent);
    							}
    						}
    		            	
    		            })
    		            .setPositiveButton("取消", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
							}
						})
    		            .create();
    					dialog.show();
    					return false;
    				}
    				
    			});
                
            }
          
          return ll;
      }
	
	private List<Map<String, Object>> getData(String tag) {
 		// TODO Auto-generated method stub
 		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
 		
 		if (tag.equals("studid")){
 			
 			for (int i=0;i<wordlist.size();i++){
 				if (wordlist.get(i).getLearned().equals("0")){
 					Map<String,Object> map = new HashMap<String,Object>();
 					map.put("label","单元-"+wordlist.get(i).getList());
 					map.put("image", R.drawable.not_learn);
 					list.add(map);
 				}
 			}
 		}
 		else if(tag.equals("all")){
 			for (int i=0;i<wordlist.size();i++){
 				if (wordlist.get(i).getLearned().equals("0")){
 					Map<String,Object> map = new HashMap<String,Object>();
 					map.put("label","单元-"+wordlist.get(i).getList());
 					map.put("status", "未学习");
 					map.put("image",R.drawable.not_learn);
 					list.add(map);
 				}
 				else if(wordlist.get(i).getLearned().equals("1")){
 					Map<String,Object> map = new HashMap<String,Object>();
 					map.put("label"," 单元-"+wordlist.get(i).getList());
 					map.put("status", "已学习");
 					map.put("image", R.drawable.all);
 					list.add(map);
 				}
 			}
 		}

 		return list;
 	}
	
	private void startStudy(final int arg2,final int tag,final int check) {
 		if(tag==2){
 			if(check==1){
 		 		Dialog dialog = new AlertDialog.Builder(StudyChoice.this)
 		        .setIcon(R.drawable.qq_leba_list_seek_feeds)
 		        .setTitle("提示")
 		        .setMessage("此单元已学过，重新学习将清除学习进度，是否继续？\n单元-"+(arg2+1))
 		        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
 		            public void onClick(DialogInterface dialog, int whichButton) {
 		                /* User clicked OK so do some stuff */
 		            	Intent intent = new Intent();
 						Bundle bundle = new Bundle();
 		                bundle.putString("list", String.valueOf(arg2+1));
 						intent.setClass(StudyChoice.this, StudyActivity.class);
 						intent.putExtras(bundle);	
 						finish();
 						startActivity(intent);
 		            	}
 		        })
 		        .setNeutralButton("取消", new DialogInterface.OnClickListener() {
 		            public void onClick(DialogInterface dialog, int whichButton) {
 		                /* User clicked OK so do some stuff */
 		            	}
 		        }).create();
 				dialog.show();
 			}
 			else{
 		Dialog dialog = new AlertDialog.Builder(StudyChoice.this)
        .setIcon(R.drawable.qq_leba_list_seek_feeds)
        .setTitle("开始学习：")
        .setMessage("单元-"+(arg2+1))
        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                /* User clicked OK so do some stuff */
            	Intent intent = new Intent();
				Bundle bundle = new Bundle();
                bundle.putString("list", String.valueOf(arg2+1));
				intent.setClass(StudyChoice.this, StudyActivity.class);
				intent.putExtras(bundle);	
				finish();
				startActivity(intent);
            	}
        })
        .setNeutralButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                /* User clicked OK so do some stuff */
            	}
        }).create();
		dialog.show();
 			}

 		}
 		else if(tag==1)
 		{
 			Dialog dialog = new AlertDialog.Builder(StudyChoice.this)
 	        .setIcon(R.drawable.qq_leba_list_seek_feeds)
 	        .setTitle("开始学习：")
 	        .setMessage("单元-"+listShould.get(arg2))
 	        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
 	            public void onClick(DialogInterface dialog, int whichButton) {
 	                /* User clicked OK so do some stuff */
 	            	finish();
 	            	Intent intent = new Intent();
 					Bundle bundle = new Bundle();
 	            	bundle.putString("list", listShould.get(arg2));
 					intent.setClass(StudyChoice.this, StudyActivity.class);
 					intent.putExtras(bundle);	
 					
 					startActivity(intent);
 	            	}
 	        })
 	        .setNeutralButton("取消", new DialogInterface.OnClickListener() {
 	            public void onClick(DialogInterface dialog, int whichButton) {
 	                /* User clicked OK so do some stuff */
 	            	}
 	        }).create();
 			dialog.show();
 		}
		
	}
}
