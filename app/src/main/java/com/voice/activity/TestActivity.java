package com.voice.activity;

import java.util.ArrayList;

import com.voice.Hornor;
import com.voice.R;
import com.voice.wifi.foregin.Globals;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageButton;

public class TestActivity extends Activity implements OnClickListener {
    private Context mContext;
    private ImageButton last;//beforeone
    private ImageButton next;
    private int currentnum;
    private TextView num;
    private TextView diff;
    private TextView pinyin;
    private TextView hanzi;
    private int numoflist = 7;
    private Activity mActivity;
    int testnum = Hornor.gettestnum();
    private ImageButton fayinBotton;
    private ImageView picture;
    private String resultarray[] = {"菠萝", "草莓", "橘子", "梨子", "芒果", "柠檬", "苹果"};
    private String pinyinarray[] = {"bō luó", "cǎo méi", "jú zǐ", "lí zǐ", "máng guǒ", "níng méng", "píng guǒ"};
    private String picturename[] = {"boluo", "caomeng", "juzi", "lizi", "mangguo", "ningmeng", "pingguo"};
    private String result;
    private TextView score;
    int num_socre = 0;

    public TestActivity() {
    }

    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        Hornor.settestnum(testnum + 1);
        mActivity = TestActivity.this;
        currentnum = 0;
        initWidgets();
        UpdateView();
        // Set the application-wide context global, if not already set
        Context myContext = Globals.getContext();
        if (myContext == null) {
            myContext = mActivity.getApplicationContext();
            if (myContext == null) {
                throw new NullPointerException("Null context!?!?!?");
            }
            Globals.setContext(myContext);
        }

        mContext = mActivity.getApplicationContext();

    }

    private void initWidgets() {
        // TODO Auto-generated method stub
        this.last = (ImageButton) this.findViewById(R.id.practice_back);
        last.setOnClickListener(this);
        this.next = (ImageButton) this.findViewById(R.id.practice_test);
        next.setOnClickListener(this);

        this.picture = (ImageView) this.findViewById(R.id.testimg);


        this.num = (TextView) this.findViewById(R.id.num);
        this.diff = (TextView) this.findViewById(R.id.diff);
        this.score = (TextView) this.findViewById(R.id.point);
        this.pinyin = (TextView) this.findViewById(R.id.pinyin);
        this.hanzi = (TextView) this.findViewById(R.id.hanzi);
        //	this.spelling=(TextView) this.findViewById(R.id.spelling);


        fayinBotton = (ImageButton) findViewById(R.id.fayin);


        fayinBotton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.baidu.action.RECOGNIZE_SPEECH");
                //intent.putExtra("sample", 16000); // 离线仅支持16000采样率
                intent.putExtra("language", "cmn-Hans-CN"); // 离线仅支持中文普通话
                //intent.putExtra("prop", 20000); // 输入

                //intent.putExtra(EXTRA_SOUND_START, R.raw.bdspeech_recognition_start);
                //intent.putExtra(EXTRA_SOUND_END, R.raw.bdspeech_speech_end);
                //intent.putExtra(EXTRA_SOUND_SUCCESS, R.raw.bdspeech_recognition_success);
                //intent.putExtra(EXTRA_SOUND_ERROR, R.raw.bdspeech_recognition_error);
                //intent.putExtra(EXTRA_SOUND_CANCEL, R.raw.bdspeech_recognition_cancel);

                //  intent.putExtra("grammer", "asset:///baidu_speech_grammar.bsg"); // 设置离线的授权文件(离线模块需要授权), 该语法可以用自定义语义工具生成, 链接http://yuyin.baidu.com/asr#m5
                //intent.putExtra("slot-data", your slots); // 设置grammar中需要覆盖的词条,如联系人名
                startActivityForResult(intent, 1);
            }
        });


        DisplayMetrics dm = new DisplayMetrics();

        dm = getApplicationContext().getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        //add.setWidth(screenWidth/3);
        last.setMaxWidth(screenWidth / 3);
        next.setMaxWidth(screenWidth / 3);
    }

    public void onClick(View v) {
        Log.i("3", "3");
        this.UpdateView();
        Log.i("3", "3");
        if (v == next) {
            if (currentnum < numoflist) {
                currentnum++;
                if (currentnum > 6)
                    currentnum = 0;
                this.UpdateView();
                setItem(currentnum);
            }
        } else if (v == last) {
            currentnum--;
            if (currentnum < 0)
                currentnum = 6;
            this.UpdateView();
            setItem(currentnum);
        }

        Log.i("3", "3");
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Dialog dialog = new AlertDialog.Builder(this)
                    .setIcon(R.drawable.qq_leba_list_seek_individuation)
                    .setTitle("测试未完成")
                    .setMessage("你确定现在结束测试吗？这将导致本次测试无效！")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        /* User clicked OK so do some stuff */
                            TestActivity.this.finish();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        /* User clicked OK so do some stuff */
                        }
                    }).create();
            dialog.show();
        }

        return true;
    }

    private void UpdateView() {
        if (currentnum == 0) {
            last.setEnabled(false);
        } else if (currentnum > 0) {
            last.setEnabled(true);
        }
        if (currentnum == 6) {
            next.setEnabled(false);
        } else if (currentnum > 0) {
            next.setEnabled(true);
        }
        SharedPreferences setting = getSharedPreferences("wordroid.model_preferences", MODE_PRIVATE);
        if (setting.getBoolean("iftts", false)) {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }


        // TODO Auto-generated method stub
        if (currentnum < numoflist) {
            num.setText("第 " + (currentnum + 1) + " 个 / 共 " + numoflist + " 个");
            if (DifficultychoseActivity.test.getDifficulty() == 1) {
                diff.setText("难度：简单");
            } else if (DifficultychoseActivity.test.getDifficulty() == 2) {
                diff.setText("难度：一般");
            } else if (DifficultychoseActivity.test.getDifficulty() == 3) {
                diff.setText("难度：困难");
            }
        } else if (currentnum >= numoflist) {
            currentnum--;
            Dialog dialog = new AlertDialog.Builder(this)
                    .setIcon(R.drawable.qq_leba_list_seek_individuation)
                    .setTitle("测试已完成")
                    .setMessage("您可以依照复习计划进行本单元的复习")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            /* User clicked OK so do some stuff */
                            TestActivity.this.finish();
                        }
                    }).create();
            dialog.show();
        }


    }

    private void setItem(int num) {
        result = resultarray[num];
        StringBuffer sb = new StringBuffer(result);
        sb.insert(1, "  ");
        switch (num) {
            case 0:
                picture.setImageResource(R.drawable.boluo);
                pinyin.setText(pinyinarray[num]);
                hanzi.setText(sb);
                break;
            case 1:
                picture.setImageResource(R.drawable.caomei);
                pinyin.setText(pinyinarray[num]);
                hanzi.setText(sb);
                break;
            case 2:
                picture.setImageResource(R.drawable.juzi);
                pinyin.setText(pinyinarray[num]);
                hanzi.setText(sb);
                break;
            case 3:
                picture.setImageResource(R.drawable.lizi);
                pinyin.setText(pinyinarray[num]);
                hanzi.setText(sb);
                break;
            case 4:
                picture.setImageResource(R.drawable.mangguo);
                pinyin.setText(pinyinarray[num]);
                hanzi.setText(sb);
                break;
            case 5:
                picture.setImageResource(R.drawable.ningmeng);
                pinyin.setText(pinyinarray[num]);
                hanzi.setText(sb);
                break;
            case 6:
                picture.setImageResource(R.drawable.pingguo);
                pinyin.setText(pinyinarray[num]);
                hanzi.setText(sb);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Bundle results = data.getExtras();
            ArrayList<String> results_recognition = results.getStringArrayList("results_recognition");
            if (isright(results_recognition) == true) {
                //score.setText(""+results_recognition);
                num_socre++;
                score.setText("" + num_socre);
            }
        }
    }

    private boolean isright(ArrayList<String> results_recognition) {
        //score.setText(""+results_recognition+"=="+result);
        //score.setText("=="+result);
        String heightArray[] = (String[]) results_recognition.toArray(new String[0]);
        //score.setText("=="+result+"=="+heightArray[0]);
        //return false;
        return heightArray[0].equals(result);
        //return results_recognition.equals(Arrays.asList(result));

    }


}
