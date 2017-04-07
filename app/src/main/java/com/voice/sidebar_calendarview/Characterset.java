package com.voice.sidebar_calendarview;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.voice.R;
import com.voice.sidebar_mylistview.CharacterData;
import com.voice.sidebar_mylistview.Myadapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
public class Characterset extends Activity{
     private ListView listView;
     private Myadapter myadapter;
    // private CharacterData data;
     private List<Map<String,Object>> listItems;
     private static String[] function={"开启海报","智能模式","释义辅助","个性化单词","拼写加强"
 		,"释义加强"};
 	private static String[] detal={"开启就能享用精美海报了呢","根据你的学习进度，动态安排复习"
 		,"复习时在图片上显示字词对应意思","根据你的兴趣优化学习安排","帮助你记忆字词书写","帮助你记住字词释义"};

 	
	
	public static List<Map<String,Object>>  getFriends(){
		  List<Map<String,Object>> listItems=new ArrayList <Map<String,Object>>(); 
		for(int i=0;i<6;i++){
		HashMap<String, Object> maps=new HashMap<String, Object>();
		maps.put("character_name",function[i]);
		maps.put("character_shuoming",detal[i]);
		listItems.add(maps);
		}
		return listItems;
	}
     
 	@Override
 	protected void onCreate(Bundle savedInstanceState) {
 		super.onCreate(savedInstanceState);
    	setContentView(R.layout.sidebar_characterset);
       	listView=(ListView)findViewById(R.id.country_lvcountry);
    	listItems=(List<Map<String, Object>>) getFriends();
    	myadapter=new Myadapter(this,listItems);  
    	listView.setAdapter(myadapter);
 
     }
     
 	class ClickEvent implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			new AlertDialog.Builder(Characterset.this)
			    .setTitle("11111")
			    .setMessage("2222")
			    .setPositiveButton("33333",null)
			    .show();
		}
 		
 	}
}


