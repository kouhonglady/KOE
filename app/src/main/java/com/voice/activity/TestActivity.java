package com.voice.activity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.asr.SpeechConstant;
import com.voice.MainActivity;
import com.voice.R;
import com.voice.wifi.foregin.Globals;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.Toast;

import org.json.JSONObject;

public class TestActivity extends Activity implements EventListener, OnClickListener {
    private Context mContext;
    final MainActivity m=new MainActivity();
    private ImageButton last;//beforeone
    private ImageButton next;
    private int currentnum;
    private TextView num;
    private TextView diff;
    private TextView pinyin;
    private TextView hanzi;
    private TextView std_ans_text_view;
    private TextView user_ans_text_view;
    private ImageButton stop_test_image_button;
    private int numoflist = 7;
    private Activity mActivity;
    int testnum = m.hornor.gettestnum();
    private ImageButton fayinBotton;
    private ImageView picture;
    private String resultarray[] = {"菠萝", "草莓", "橘子", "梨子", "芒果", "柠檬", "苹果"};
    private String pinyinarray[] = {"bō luó", "cǎo méi", "jú zǐ", "lí zǐ", "máng guǒ", "níng méng", "píng guǒ"};
    private String picturename[] = {"boluo", "caomeng", "juzi", "lizi", "mangguo", "ningmeng", "pingguo"};
    private String result;
    private TextView score;
    int num_socre = 0;
    private EventManager asr;

    private String user_ans="";
    private boolean logTime = true;

    public TestActivity() {
    }

    public String getUser_ans() {
        return user_ans;
    }

    public void setUser_ans(String user_ans) {
        this.user_ans = user_ans;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_two);
        asr = EventManagerFactory.create(this, "asr");
        asr.registerListener(this); //  EventListener 中 onEvent方法

        m.hornor.settestnum(testnum + 1);
        mActivity = TestActivity.this;
        currentnum = 0;
        initWidgets();
        initPermission();


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
        this.std_ans_text_view= (TextView) this.findViewById(R.id.std_ans_text_view);
        this.user_ans_text_view= (TextView) this.findViewById(R.id.user_ans_text_view);
        this.stop_test_image_button= (ImageButton) this.findViewById(R.id.stop_test_image_button);
        this.hanzi = (TextView) this.findViewById(R.id.hanzi);



        fayinBotton = (ImageButton) findViewById(R.id.fayin);


        fayinBotton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"开始语音识别",Toast.LENGTH_SHORT).show();
                start();
            }
        });

        stop_test_image_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"停止语音识别",Toast.LENGTH_SHORT).show();
                stop();
            }
        });

        DisplayMetrics dm = new DisplayMetrics();

        dm = getApplicationContext().getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        //add.setWidth(screenWidth/3);
        last.setMaxWidth(screenWidth / 3);
        next.setMaxWidth(screenWidth / 3);
    }
    private void start() {
        System.out.println("MainActivity---start()--------开始");
        user_ans_text_view.setText("");
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        String event = null;
        event = SpeechConstant.ASR_START; // 替换成测试的event

        params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, false);
        params.put(SpeechConstant.VAD,SpeechConstant.VAD_DNN);
        String json = null; //可以替换成自己的json
        json = new JSONObject(params).toString(); // 这里可以替换成你需要测试的json
        asr.send(event, json, null, 0, 0);
        System.out.println("MainActivity---start-----结束---------json-----------"+json);
    }
    private void stop() {
        System.out.println("MainActivity---stop（）---------------------开始");
        asr.send(SpeechConstant.ASR_STOP, null, null, 0, 0); //
    }


    private void printLog(String text) {
        if (logTime) {
            //text += "  ;time=" + System.currentTimeMillis();
        }

        int strlen=text.length();
        System.out.println("printLog()-----------------text="+text+"------length="+strlen);
        //text += "\n";
        if(strlen==2){
            Log.i(getClass().getName(), text);
            user_ans_text_view.append(text);
            setUser_ans(text);
            judgePoint();
        }
    }

    private void judgePoint() {
        String tempAns=getUser_ans();

        switch(currentnum){
            case 0:
                if(tempAns.equals("菠萝")){
                    score.setText("100");
                    score.setTextColor(Color.parseColor("#EE2C2C"));
                }
                else{
                    score.setText("0");
                    score.setTextColor(Color.parseColor("#ffcf0a"));
                }
                break;
            case 1:
                if(tempAns.equals("草莓")){
                    score.setText("100");
                    score.setTextColor(Color.parseColor("#EE2C2C"));
                }
                else
                {
                    score.setText("0");
                    score.setTextColor(Color.parseColor("#ffcf0a"));
                }
                break;
            case 2:
                if(tempAns.equals("橘子")){
                    score.setText("100");
                    score.setTextColor(Color.parseColor("#EE2C2C"));
                }
                else
                {
                    score.setText("0");
                    score.setTextColor(Color.parseColor("#ffcf0a"));
                }
                break;
            case 3:
                if(tempAns.equals("梨子")){
                    score.setText("100");
                    score.setTextColor(Color.parseColor("#EE2C2C"));
                }
                else
                {
                    score.setText("0");
                    score.setTextColor(Color.parseColor("#ffcf0a"));
                }
                break;
            case 4:
                if(tempAns.equals("芒果")){
                    score.setText("100");
                    score.setTextColor(Color.parseColor("#EE2C2C"));
                }
                else
                {
                    score.setText("0");
                    score.setTextColor(Color.parseColor("#ffcf0a"));
                }
                break;
            case 5:
                if(tempAns.equals("柠檬")){
                    score.setText("100");
                    score.setTextColor(Color.parseColor("#EE2C2C"));
                }
                else
                {
                    score.setText("0");
                    score.setTextColor(Color.parseColor("#ffcf0a"));
                }
                break;
            case 6:
                if(tempAns.equals("苹果")){
                    score.setText("100");
                    score.setTextColor(Color.parseColor("#EE2C2C"));
                }
                else
                {
                    score.setText("0");
                    score.setTextColor(Color.parseColor("#ffcf0a"));
                }
                break;
        }
    }

    private void initPermission() {
        String permissions[] = {Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm :permissions){
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                //进入到这里代表没有权限.

            }
        }
        String tmpList[] = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()){
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        }

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
        score.setText("暂无");
        score.setTextColor(Color.parseColor("#ffcf0a"));
        user_ans_text_view.setText("");
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
                std_ans_text_view.setText(sb);
                break;
            case 1:
                picture.setImageResource(R.drawable.caomei);
                pinyin.setText(pinyinarray[num]);
                hanzi.setText(sb);
                std_ans_text_view.setText(sb);
                break;
            case 2:
                picture.setImageResource(R.drawable.juzi);
                pinyin.setText(pinyinarray[num]);
                hanzi.setText(sb);
                std_ans_text_view.setText(sb);
                break;
            case 3:
                picture.setImageResource(R.drawable.lizi);
                pinyin.setText(pinyinarray[num]);
                hanzi.setText(sb);
                std_ans_text_view.setText(sb);
                break;
            case 4:
                picture.setImageResource(R.drawable.mangguo);
                pinyin.setText(pinyinarray[num]);
                hanzi.setText(sb);
                std_ans_text_view.setText(sb);
                break;
            case 5:
                picture.setImageResource(R.drawable.ningmeng);
                pinyin.setText(pinyinarray[num]);
                hanzi.setText(sb);
                std_ans_text_view.setText(sb);
                break;
            case 6:
                picture.setImageResource(R.drawable.pingguo);
                pinyin.setText(pinyinarray[num]);
                hanzi.setText(sb);
                std_ans_text_view.setText(sb);
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


    @Override
    public void onEvent(String name, String params, byte[] data, int offset, int length) {

        System.out.println("MainActivity--------------onEvent()");
        String best_result="";
        String stdStr="asr.partial";
        if(name.equals(stdStr)){
            System.out.println("MainActivity-----onEvent----name.queals(stdStr)="+name.equals(stdStr));
            //String logTxt = "name: " + name;
            String logTxt = "";

            if (params != null && !params.isEmpty()) {
                System.out.println("MainActivity-----onEvent----params"+params);
                String[] temp=params.split("\"best_result\":\"");
                System.out.println("MainActivity-----onEvent----params=temp[1]="+params);
                String[] temp2=temp[1].split("\",\"results_recognition");
                params=temp2[0];
                System.out.println("MainActivity-----onEvent----params=temp2[0]="+params);
                logTxt +=params;
            }
            if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL)) {
                if (params.contains("\"nlu_result\"")) {
                    if (length > 0 && data.length > 0) {
                        logTxt += ", 语义解析结果：" + new String(data, offset, length);
                    }
                }
            } else if (data != null) {
                logTxt += " ;data length=" + data.length;
            }
            printLog(logTxt);
            System.out.println("MainActivity--------------onEvent()----logTxt"+logTxt);
        }
    }
}
