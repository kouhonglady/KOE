package com.voice.activity;



import com.voice.Hornor;
import com.voice.MainActivity;
import com.voice.R;
import com.voice.Test;
import com.voice.study.StudyActivity;
import com.voice.wifi.foregin.Globals;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
public class HornorActivity extends Activity{
	private Context mContext;
	private TextView UsrName_h;
	private TextView Sign_h;
	private TextView cancle;
	private TextView studynum;
	private TextView date;
	private TextView grade;
	private TextView testnum;
	private TextView bbsnum;
	private TextView result;
	private Activity mActivity;
	public static Test test=new Test(1);
	final MainActivity m=new MainActivity();
	public HornorActivity() {};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hornor_new);
		mActivity = HornorActivity.this;
		// Set the application-wide context global, if not already set
        Context myContext = Globals.getContext();
        if (myContext == null) {
            myContext = mActivity.getApplicationContext();
            if (myContext == null) {
                throw new NullPointerException("Null context!?!?!?");
            }
            Globals.setContext(myContext);
        }

        
        cancle = (TextView) findViewById(R.id.cancle_new);
        studynum = (TextView) findViewById(R.id.studynum_new);
        testnum = (TextView) findViewById(R.id.testnum_new);
        bbsnum = (TextView) findViewById(R.id.bbsnum_new);
        date= (TextView) findViewById(R.id.date_new);
        grade = (TextView) findViewById(R.id.grade_new);
		UsrName_h=(TextView)findViewById(R.id.UsrName_h);
		Sign_h=(TextView)findViewById(R.id.Sign_h);
		mContext=mActivity.getApplicationContext();
		init();

	}
	void init(){

		studynum.setText(""+m.hornor.getstudynum());
		testnum.setText(""+m.hornor.gettestnum());
		bbsnum.setText(""+m.hornor.getbbsnum());
		grade.setText(""+m.hornor.getgrade());
		date.setText(""+m.hornor.getdate());
		UsrName_h.setText(""+m.user.getLogName());
		Sign_h.setText(""+m.user.getSign());

		//        studynum.setText("学习次数："+Hornor.getstudynum());
//        testnum.setText("练习次数："+Hornor.gettestnum());
//        bbsnum.setText("发帖数："+Hornor.getbbsnum());
//        grade.setText("最高成绩："+Hornor.getgrade());
//        date.setText("上次学习："+Hornor.getdate());


		cancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				HornorActivity.this.finish();
			}
		});
	}
}
