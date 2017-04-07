package com.voice.study;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import com.voice.R;

import com.voice.business.*;
import com.voice.database.DataAccess;
import com.voice.database.SqlHelper;
import com.voice.model.BookList;
import com.voice.model.WordList;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.content.*;

public class StudyMain extends Activity{
	public  static String SETTING_BOOKID="bookID";
	public  static String BOOKNAME = "BOOKNAME";
	private Spinner pickBook;
	private TextView info;
	private RelativeLayout studybegin;
	private ProgressBar learn_progress;
	private ProgressBar review_progress;
	private TextView learn_text;
	private TextView review_text;
	public static final int MENU_SETTING = 1;
	public static final int MENU_ABOUT = MENU_SETTING+1;
	public static final int MENU_CONTACT = MENU_SETTING+2;
	View myView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.setTitle("学习");
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.fragment_dynamic);
		LayoutInflater mInflater = LayoutInflater.from(this);
		myView = mInflater.inflate(R.layout.fragment_dynamic, null);
		
		Thread thread = new Thread(){
			public void run(){
				try {
					Thread.sleep(2000);
					Message m = new Message();
					m.what=1;
					StudyMain.this.mHandler.sendMessage(m);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		};
		thread.start();
		
		OperationOfBooks OOB = new OperationOfBooks();
		SharedPreferences setting = getSharedPreferences("wordroid.model_preferences", MODE_PRIVATE);
		OOB.setNotify(setting.getString("time", "18:00 下午"),StudyMain.this);
		File dir = new File("data/data/com.qq/databases");    
	    if (!dir.exists()) 
	        dir.mkdir(); 
	    if (!(new File(SqlHelper.DB_NAME)).exists()) {    
	    	FileOutputStream fos;
	    	try {
				fos = new FileOutputStream(SqlHelper.DB_NAME);
				
				byte[] buffer = new byte[8192]; 
	            int count = 0; 
	            InputStream is = getResources().openRawResource(R.raw.wordorid); 
	            while ((count = is.read(buffer)) > 0) { 
	                fos.write(buffer, 0, count); 
	            }

	            fos.close(); 
	            is.close(); 
			} catch (Exception e) {
					// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	    }

	    SharedPreferences settings=getSharedPreferences(SETTING_BOOKID, 0);
		DataAccess.difficultyID=settings.getString(BOOKNAME, "");
		studybegin=(RelativeLayout)myView.findViewById(R.id.ll_dynamic);
		pickBook=(Spinner)myView.findViewById(R.id.pickBook);
		initSpinner();
		
	}
	private void initSpinner() {
		pickBook.setVisibility(View.VISIBLE);
		DataAccess data = new DataAccess(this);
		final ArrayList<BookList> bookList = data.QueryBook(null, null);
		//Log.i("size", String.valueOf(bookList.size()));
		String[] books = new String[bookList.size()];
		int i=0;
		for (;i<bookList.size();i++){
			books[i]=bookList.get(i).getName();
		}
		
		ArrayAdapter< CharSequence > adapter = new ArrayAdapter< CharSequence >(this, android.R.layout.simple_spinner_item, books);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		pickBook.setAdapter(adapter);
		pickBook.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (arg2<bookList.size()){
					DataAccess.difficultyID=bookList.get(arg2).getID();
					//info.setText("\n词库名称:\n    "+bookList.get(arg2).getName()+"\n总词汇量:\n    "+bookList.get(arg2).getNumOfWord()+"\n创建时间：\n    "+bookList.get(arg2).getGenerateTime());
					
					studybegin.setEnabled(true);
					
					DataAccess data2 = new DataAccess(StudyMain.this);
					ArrayList<WordList> lists = data2.QueryList("BOOKID ='"+DataAccess.difficultyID+"'", null);
					//learn_progress.setMax(lists.size());
					//review_progress.setMax(lists.size());
					//learn_progress.setProgress(0);
					//review_progress.setProgress(0);
					//review_progress.setSecondaryProgress(0);
					int learned=0,reviewed=0;
					for (int k=0;k<lists.size();k++){
						if (lists.get(k).getLearned().equals("1")){
							//learn_progress.incrementProgressBy(1);
							learned++;
						}
						
						if (Integer.parseInt(lists.get(k).getReview_times())>=5){
							//review_progress.incrementProgressBy(1);
							reviewed++;
						}
						
						//if (Integer.parseInt(lists.get(k).getReview_times())>0)
						//review_progress.incrementSecondaryProgressBy(1);
						//review_text.setText("已复习"+reviewed+"/"+lists.size());
						//learn_text.setText("已学习"+learned+"/"+lists.size());
					}
				}
				studybegin.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent=new Intent(StudyMain.this, StudyChoice.class);
						startActivity(intent);
						
					}
				});
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		if (bookList.size()==0) {
			pickBook.setSelection(1);
			//info.setText("请先从上方选择一个词库！");
			this.studybegin.setEnabled(true);
			//this.learn_progress.setProgress(0);
			//this.review_progress.setProgress(0);
			return;
		}
		int j=0;
		Log.i("BookID", DataAccess.difficultyID);
		for (;j<bookList.size();j++){
			if (DataAccess.difficultyID.equals(bookList.get(j).getID())){
				pickBook.setSelection(j);	
				break;
			}
				
		}
	}
	protected void onDestroy() {
		// TODO Auto-generated method stub
		SharedPreferences settings=getSharedPreferences(SETTING_BOOKID, 0);
		settings.edit()
		.putString(BOOKNAME, DataAccess.difficultyID)
		.commit();
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Log.i("In Resume", DataAccess.difficultyID);
		this.initSpinner();
		super.onResume();
	}

	private Handler mHandler = new Handler(){ 
        public void handleMessage(Message msg) {
        	if (msg.what==1)
        	setContentView(myView);
         } 
     }; 
}
