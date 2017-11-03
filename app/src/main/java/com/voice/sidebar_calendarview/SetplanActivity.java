package com.voice.sidebar_calendarview;

import java.util.ArrayList;
import java.util.List;

import com.voice.R;
import com.voice.MainActivity;
import com.voice.User;
import com.voice.sidebar_datepicker.MonthDateView;
import com.voice.sidebar_datepicker.MonthDateView.DateClick;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class SetplanActivity extends FragmentActivity {
	private ImageView iv_left;
	private ImageView iv_right;
	private TextView tv_date;
	private TextView tv_week;
	private TextView tv_today;
	private MonthDateView monthDateView;
	private ImageButton choosebook;
	private TextView bookname;
	private TextView finishtime;
	private TextView totaltime;
	private String booknameStr;
	
	public static int flag;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		List<Integer> list = new ArrayList<Integer>();
		list.add(10);
		list.add(12);
		list.add(15);
		list.add(16);
		setContentView(R.layout.sidebar_setplan);
		
		iv_left = (ImageView) findViewById(R.id.iv_left);
		iv_right = (ImageView) findViewById(R.id.iv_right);
		monthDateView = (MonthDateView) findViewById(R.id.monthDateView);
		tv_date = (TextView) findViewById(R.id.date_text);
		tv_week  =(TextView) findViewById(R.id.week_text);
		tv_today = (TextView) findViewById(R.id.tv_today);
		choosebook=(ImageButton)findViewById(R.id.choosebookbutton);
		finishtime = (TextView) findViewById(R.id.finishtime);
		totaltime = (TextView) findViewById(R.id.totaltime);
		bookname=(TextView)findViewById(R.id.bookname);
		
		
		if(flag!=0){
		Intent intent=getIntent();
        Bundle u=intent.getExtras();
        booknameStr=(String)u.getSerializable("bookname");
		}
        if(booknameStr==null){
        	booknameStr="《基础拼音》";
        	
        }	
        
        bookname.setText(booknameStr);
		monthDateView.setTextView(tv_date,tv_week);
		monthDateView.setDaysHasThingList(list);
		monthDateView.setDateClick(new DateClick() {
			
			@Override
			public void onClickOnDate() {
				//Toast.makeText(getApplication(), "点击了：" + monthDateView.getmSelDay(), Toast.LENGTH_SHORT).show();
				bookname.setText(booknameStr);
				finishtime.setText(+monthDateView.getmSelYear()+"年"+(monthDateView.getmSelMonth()+1)+"月"+monthDateView.getmSelDay()+"日") ;
				//totaltime.setText("共需要。。天  每天需学习。。字") ;
			}
		});
	
		setOnlistener();
	}
	
	private void setOnlistener(){
		iv_left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				monthDateView.onLeftClick();
			}
		});
		
		iv_right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				monthDateView.onRightClick();
			}
		});
		
		tv_today.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				monthDateView.setTodayToView();
			}
		});
		choosebook.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(), BookCoverActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
}
