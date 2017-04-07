package com.voice.sidebar;





import com.voice.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;



public class Feedback extends Activity{
	private Button v_home;
	
	protected void onCreate(Bundle savedInstanceState) {
 		super.onCreate(savedInstanceState);
    	setContentView(R.layout.sidebar_feedback);
    	v_home=(Button)findViewById(R.id.homepage);
    	v_home.setOnClickListener(new OnClickListener(){
    		public void onClick(View v){
    			Uri uri = Uri.parse("http://115.159.215.125/");
    			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
    			startActivity(intent);
    		}
    	});
    	}
	
}
