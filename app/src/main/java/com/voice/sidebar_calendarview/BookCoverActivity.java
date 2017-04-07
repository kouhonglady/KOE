package com.voice.sidebar_calendarview;

import com.voice.MainActivity;
import com.voice.R;
import com.voice.study.StudyActivity;
import com.voice.sidebar_calendarview.SetplanActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.Toast;

public class BookCoverActivity extends Activity implements OnClickListener{
	private ImageButton bookbutton1;
	private ImageButton bookbutton2;
	private ImageButton bookbutton3;
	private ImageButton bookbutton4;
	private ImageButton bookbutton5;
	private ImageButton bookbutton6;
	private ImageButton bookbutton7;
	private ImageButton bookbutton8;
	
	String bookname;
	
	
	protected void onCreate(Bundle savedInstanceState) {
 		super.onCreate(savedInstanceState);
    	setContentView(R.layout.sidebar_bookcover);
    	
    	SetplanActivity.flag=1;
    	
    	bookbutton1=(ImageButton)findViewById(R.id.bookcover1);
    	bookbutton2=(ImageButton)findViewById(R.id.bookcover2);
    	bookbutton3=(ImageButton)findViewById(R.id.bookcover3);
    	bookbutton4=(ImageButton)findViewById(R.id.bookcover4);
    	bookbutton5=(ImageButton)findViewById(R.id.bookcover5);
    	bookbutton6=(ImageButton)findViewById(R.id.bookcover6);
    	bookbutton7=(ImageButton)findViewById(R.id.bookcover7);
    	bookbutton8=(ImageButton)findViewById(R.id.bookcover8);
    	
    	this.bookbutton1.setOnClickListener(this);
    	this.bookbutton2.setOnClickListener(this);
    	this.bookbutton3.setOnClickListener(this);
    	this.bookbutton4.setOnClickListener(this);
    	this.bookbutton5.setOnClickListener(this);
    	this.bookbutton6.setOnClickListener(this);
    	this.bookbutton7.setOnClickListener(this);
    	this.bookbutton8.setOnClickListener(this);
    	}
	
	
	
	  public void onClick(View v) {
			if (v==bookbutton1){				
				bookname="《聋人百科词典》";
				
			}
			else if(v==bookbutton2){
				bookname="《中国手语日常会话》";
			}
			else if(v==bookbutton3){
				bookname="《银行服务常用手语》";
			}
			else if(v==bookbutton4){
				bookname="《浙江聋人自然手语》";
			}
			else if(v==bookbutton5){
				bookname="《美术专业手语》";
			}
           else if(v==bookbutton6){
        	   bookname="《理科专业手语》";
			}
           else if(v==bookbutton7){	
        	   bookname="《手势创造与语言起源》";
           }
           else if(v==bookbutton8){	
        	   bookname="《中国手语》";
           }
			
			
			Intent intent =new Intent(this,SetplanActivity.class);
			Bundle u=new Bundle();
            u.putSerializable("bookname", bookname);
            intent.putExtras(u);
			startActivity(intent);
			finish();
	  }

}


