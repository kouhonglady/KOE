package com.voice.activity;


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

public class DifficultychoseActivity extends Activity {
    private Context mContext;
    public RadioGroup difficulty;
    public RadioButton easy, middle, hard;
    private ImageButton sure;
    private ImageButton cancle;
    private TextView result;
    private Activity mActivity;
    private int difficultyId;
    public static Test test = new Test(1);

    public DifficultychoseActivity() {
    }

    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulitychoice);
        mActivity = DifficultychoseActivity.this;
        // Set the application-wide context global, if not already set
        Context myContext = Globals.getContext();
        if (myContext == null) {
            myContext = mActivity.getApplicationContext();
            if (myContext == null) {
                throw new NullPointerException("Null context!?!?!?");
            }
            Globals.setContext(myContext);
        }

        difficulty = (RadioGroup) findViewById(R.id.difficultygroup);
        easy = (RadioButton) findViewById(R.id.easy);
        middle = (RadioButton) findViewById(R.id.middle);
        hard = (RadioButton) findViewById(R.id.hard);
        sure = (ImageButton) findViewById(R.id.sure);
        cancle = (ImageButton) findViewById(R.id.cancle);
        result = (TextView) findViewById(R.id.difficult);
        if (test.getDifficulty() == 1) {
            easy.setChecked(true);
            result.setText("简单");
        } else if (test.getDifficulty() == 2) {
            middle.setChecked(true);
            result.setText("一般");
        } else if (test.getDifficulty() == 3) {
            hard.setChecked(true);
            result.setText("困难");
        }
        mContext = mActivity.getApplicationContext();
        difficulty.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (checkedId == easy.getId()) {
                    result.setText("简单");
                    difficultyId = 1;
                } else if (checkedId == middle.getId()) {
                    result.setText("一般");
                    difficultyId = 2;
                } else if (checkedId == hard.getId()) {
                    result.setText("困难");
                    difficultyId = 3;

                }
            }
        });
        sure.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                test.setDifficulty(difficultyId);
                Intent intent = new Intent(mContext, TestActivity.class);
                startActivity(intent);
                DifficultychoseActivity.this.finish();
            }
        });
        cancle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                DifficultychoseActivity.this.finish();
            }
        });
    }
}
