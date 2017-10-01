package com.voice.activity;


import com.voice.R;
import com.voice.Test;
import com.voice.study.StudyActivity;
import com.voice.wifi.foregin.Globals;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    private ImageView mDiffDescripImageView;
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
        mDiffDescripImageView= (ImageView) findViewById(R.id.diff_descrip_image_view);
        cancle = (ImageButton) findViewById(R.id.cancle);
        result = (TextView) findViewById(R.id.difficult);
        if (test.getDifficulty() == 1) {
            easy.setChecked(true);
            result.setText("简单");
        } else if (test.getDifficulty() == 2) {
            middle.setChecked(true);
            result.setText("普通");
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
                    result.setText("简单模式--学习内容为23个声母拼音发音，包括发音视频、拼音释义图、拼音手语图、标准声波图");
                    result.setMovementMethod(ScrollingMovementMethod.getInstance());
                    mDiffDescripImageView.setImageResource(R.drawable.diff_descrip_easy_128);
                    difficultyId = 1;
                } else if (checkedId == middle.getId()) {
                    result.setText("普通模式--学习内容为24个韵母发音，以及少数常用字。常用字包括数字、小学低年级汉字。");
                    mDiffDescripImageView.setImageResource(R.drawable.diff_descrip_common_128);
                    difficultyId = 2;
                } else if (checkedId == hard.getId()) {
                    result.setText("困难模式--学习内容为常用字，以及部分常用词汇。常用字包括小学高年级汉字、词汇。");
                    mDiffDescripImageView.setImageResource(R.drawable.diff_descrip_hard_128);
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
