package com.voice.study;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.Manifest;
import android.app.TabActivity;
import android.graphics.PixelFormat;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;


import com.cokus.wavelibrary.draw.WaveCanvas;
import com.cokus.wavelibrary.view.WaveSurfaceView;
import com.cokus.wavelibrary.view.WaveformView;
import com.voice.util.MusicSimilarityUtil;
import com.voice.util.U;
import com.cokus.wavelibrary.utils.SamplePlayer;
import com.cokus.wavelibrary.utils.SoundFile;
import com.cokus.wavelibrary.view.WaveSurfaceView;
import com.cokus.wavelibrary.view.WaveformView;
import com.voice.Hornor;
import com.voice.R;
import com.voice.model.Word;
import com.voice.business.OperationOfBooks;
import com.voice.business.TTS;
import com.voice.database.DataAccess;
import com.voice.fragment.DynamicFragment;
import com.voice.model.WordList;
import com.voice.wifi.foregin.Globals;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.ImageButton;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

import butterknife.ButterKnife;

import static com.voice.util.FileUtil.getPronunciationPath;

//@RuntimePermissions
public class StudyActivity extends TabActivity implements OnClickListener {
    private Context mContext;
    //private TextView grade;
    private TextView title;
    private TextView mean;//info
    private ImageView hand;
    private ImageView word;
    //private ImageView mouth;
    private VideoView example;
    private VideoView you;
    private ImageButton last;//beforeone
    private ImageButton next;
    private Activity mActivity;
    private String listnum;
    private String filename;
    private int currentnum;
    private int numoflist;
    private ArrayList<Word> list = new ArrayList<Word>();
    private Button add;
    private ImageButton playyours;
    private ImageButton playexample;
    private ImageButton watchyou;
    private WaveSurfaceView waveSfv;
    private WaveformView waveView;
    private WaveSurfaceView waveSfvExm;
    private WaveformView waveViewExm;
    private ImageButton swichwavebtn;
    private TextView status;
    private ImageButton playwave;
    private String strVideoPath = "";// 视频文件的绝对路径
    //  private TextView spelling;
    private TextView info;
    ViewGroup viewGroup;
    VideoPlayer player;
    MediaController MediaCtrl;
    MediaController MediaCtrl1;
    private static final int FREQUENCY = 16000;// 设置音频采样率，44100是目前的标准，但是某些设备仍然支持22050，16000，11025
    private static final int CHANNELCONGIFIGURATION = AudioFormat.CHANNEL_IN_MONO;// 设置单声道声道
    private static final int AUDIOENCODING = AudioFormat.ENCODING_PCM_16BIT;// 音频数据格式：每个样本16位
    public final static int AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;// 音频获取源
    private int recBufSize;// 录音最小buffer大小3
    private AudioRecord audioRecord;
    private WaveCanvas waveCanvas;
    private String mFileName = "test";
    private File yourWaveFile;
    private File exampleWaveFile;
    SamplePlayer mPlayer;

    public StudyActivity() {
    }

    int num = Hornor.getstudynum();
    SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd    hh:mm:ss");
    String date = sDateFormat.format(new java.util.Date());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study);
        currentnum = 0;
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        String name = b.getString("list");
        listnum = name;
        Hornor.setstudynum(num + 1);
        Hornor.setdate(date);
        this.setTitle("学习LIST-" + name);
        DataAccess data = new DataAccess(this);
        list = data.QueryWord("LIST = '" + name + "'", null);
        numoflist = list.size();
        initWidgets();
        UpdateView();
        ButterKnife.bind(this);
        if(waveSfv != null) {
            waveSfv.setLine_off(42);
            //解决surfaceView黑色闪动效果
            waveSfv.setZOrderOnTop(true);
            waveSfv.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        }
        waveViewExm.setLine_offset(42);
        if(waveSfvExm != null) {
            waveSfvExm.setLine_off(42);
            //解决surfaceView黑色闪动效果
            waveSfvExm.setZOrderOnTop(true);
            waveSfvExm.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        }
        waveViewExm.setLine_offset(42);
        //initPermission();



        TabHost tabHost = getTabHost();

        TabSpec page1 = tabHost.newTabSpec("tab1")
                .setIndicator("口型教学")
                .setContent(R.id.tab1);
        tabHost.addTab(page1);

        TabSpec page2 = tabHost.newTabSpec("tab2")
                .setIndicator("我的口型")
                .setContent(R.id.tab2);
        tabHost.addTab(page2);
        TabSpec page3 = tabHost.newTabSpec("tab3")
                .setIndicator("波形图")
                .setContent(R.id.tab3);
        tabHost.addTab(page3);
        TabWidget tabWidget = tabHost.getTabWidget();
        for (int i = 0; i < tabWidget.getChildCount(); i++) {
            //修改显示字体大小
            TextView tv = (TextView) tabWidget.getChildAt(i).findViewById(android.R.id.title);
            tv.setTextSize(16);
            //tv.setTextColor(this.getResources().getColorStateList(android.R.color.white));
        }
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener(){
            public void onTabChanged(String tabId) {
                if (tabId.equals("tab1")) {   //第一个标签
                    //tab1
                    example.setVisibility(View.VISIBLE);
                    playexample.setVisibility(View.VISIBLE);
                    //tab2
                    you.setVisibility(View.     INVISIBLE);
                    playyours.setVisibility(View.INVISIBLE);
                    watchyou.setVisibility(View.INVISIBLE);
                    //tab3
                    waveSfv.setVisibility(View.INVISIBLE);
                    waveSfvExm.setVisibility(View.INVISIBLE);
                    swichwavebtn.setVisibility(View.INVISIBLE);
                    playwave.setVisibility(View.INVISIBLE);

                }
                if (tabId.equals("tab2")) {   //第二个标签
                    //tab1
                    example.setVisibility(View.INVISIBLE);
                    playexample.setVisibility(View.INVISIBLE);
                    //tab2
                    you.setVisibility(View. VISIBLE);
                    playyours.setVisibility(View.VISIBLE);
                    watchyou.setVisibility(View.VISIBLE);
                    //tab3
                    waveSfv.setVisibility(View.INVISIBLE);
                    waveSfvExm.setVisibility(View.INVISIBLE);
                    swichwavebtn.setVisibility(View.INVISIBLE);
                    playwave.setVisibility(View.INVISIBLE);
                }
                if (tabId.equals("tab3")) {   //第三个标签
                    //tab1
                    example.setVisibility(View.INVISIBLE);
                    playexample.setVisibility(View.INVISIBLE);
                    //tab2
                    you.setVisibility(View. INVISIBLE);
                    playyours.setVisibility(View.INVISIBLE);
                    watchyou.setVisibility(View.INVISIBLE);
                    //tab3
                    waveSfv.setVisibility(View.VISIBLE);
                    waveSfvExm.setVisibility(View.INVISIBLE);
                    swichwavebtn.setVisibility(View.VISIBLE);
                    playwave.setVisibility(View.VISIBLE);
                }
            }
        });

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener(){
            public void onTabChanged(String tabId) {
                if (tabId.equals("tab1")) {   //第一个标签
                    //tab1
                    example.setVisibility(View.VISIBLE);
                    playexample.setVisibility(View.VISIBLE);
                    //tab2
                    you.setVisibility(View.     INVISIBLE);
                    playyours.setVisibility(View.INVISIBLE);
                    watchyou.setVisibility(View.INVISIBLE);
                    //tab3
                    waveSfv.setVisibility(View.INVISIBLE);
                    swichwavebtn.setVisibility(View.INVISIBLE);
                    playwave.setVisibility(View.INVISIBLE);
                    waveSfvExm.setVisibility(View.INVISIBLE);

                }
                if (tabId.equals("tab2")) {   //第二个标签
                    //tab1
                    example.setVisibility(View.INVISIBLE);
                    playexample.setVisibility(View.INVISIBLE);
                    //tab2
                    you.setVisibility(View. VISIBLE);
                    playyours.setVisibility(View.VISIBLE);
                    watchyou.setVisibility(View.VISIBLE);
                    //tab3
                    waveSfv.setVisibility(View.INVISIBLE);
                    waveSfvExm.setVisibility(View.INVISIBLE);
                    swichwavebtn.setVisibility(View.INVISIBLE);
                    playwave.setVisibility(View.INVISIBLE);
                }
                if (tabId.equals("tab3")) {   //第三个标签
                    //tab1
                    example.setVisibility(View.INVISIBLE);
                    playexample.setVisibility(View.INVISIBLE);
                    //tab2
                    you.setVisibility(View. INVISIBLE);
                    playyours.setVisibility(View.INVISIBLE);
                    watchyou.setVisibility(View.INVISIBLE);
                    //tab3
                    waveSfv.setVisibility(View.VISIBLE);
                    waveSfvExm.setVisibility(View.VISIBLE);
                    swichwavebtn.setVisibility(View.VISIBLE);
                    playwave.setVisibility(View.VISIBLE);
                }
            }
        });

        mActivity = StudyActivity.this;
        // Set the application-wide context global, if not already set
        Context myContext = Globals.getContext();
        if (myContext == null) {
            myContext = mActivity.getApplicationContext();
            if (myContext == null) {
                throw new NullPointerException("Null context!?!?!?");
            }
            Globals.setContext(myContext);
        }

        //grade = (TextView) findViewById(R.id.yourpoint);
        hand = (ImageView) findViewById(R.id.examplehand);
        //mouth= (ImageView) findViewById(R.id.examplemouth);


        mContext = mActivity.getApplicationContext();


    }


    private void initWidgets() {
        // TODO Auto-generated method stub
        this.title = (TextView) this.findViewById(R.id.title);
        this.mean = (TextView) this.findViewById(R.id.mean);
        this.word = (ImageView) this.findViewById(R.id.word);
        this.hand = (ImageView) this.findViewById(R.id.examplehand);
        //this.mouth= (ImageView) this.findViewById(R.id.examplemouth);
        this.last = (ImageButton) this.findViewById(R.id.gotolast);
        last.setOnClickListener(this);
        this.next = (ImageButton) this.findViewById(R.id.gotonext);
        next.setOnClickListener(this);
        //	this.spelling=(TextView) this.findViewById(R.id.spelling);
        this.example = (VideoView) this.findViewById(R.id.examplevideo);
        this.you = (VideoView) this.findViewById(R.id.yourvideo);
        DisplayMetrics dm = new DisplayMetrics();
        this.watchyou = (ImageButton) this.findViewById(R.id.watchyours);
        this.playexample = (ImageButton) this.findViewById(R.id.playexample);
        this.waveSfv=(WaveSurfaceView)this.findViewById(R.id.wavesfv);
        this.waveView=(WaveformView)this.findViewById(R.id.waveview);
        this.waveSfvExm=(WaveSurfaceView)this.findViewById(R.id.wavesfv_exmple);
        this.waveViewExm=(WaveformView)this.findViewById(R.id.waveview_example);
        this.playwave=(ImageButton)this.findViewById(R.id.play_wave);
        this.status=(TextView)this.findViewById(R.id.status);
        this.swichwavebtn=(ImageButton)this.findViewById(R.id.switch_wave);


        dm = getApplicationContext().getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        //add.setWidth(screenWidth/3);
        last.setMaxWidth(screenWidth / 3);
        last.setMaxWidth(screenWidth / 3);
        next.setMaxWidth(screenWidth / 3);
        this.playyours = (ImageButton) this.findViewById(R.id.playyours);
        this.playyours.setOnClickListener(this);
        this.watchyou.setOnClickListener(this);
        this.playexample.setOnClickListener(this);
        this.swichwavebtn.setOnClickListener(this);
        this.playwave.setOnClickListener(this);

        title.setText("单元" + listnum);


    }



    public void onClick(View v) {
        Log.i("3", "3");
        this.UpdateView();
        Log.i("3", "3");
        if (v == next) {
            if (currentnum < numoflist) {
                currentnum++;
                this.UpdateView();
            }

        } else if (v == last) {
            currentnum--;
            this.UpdateView();
        } else if (v == playyours) {//录制口型
            videoMethod();
            //playyours.setVisibility(View.INVISIBLE);
        } else if (v == watchyou) {//你的口型
            MediaCtrl1 = new MediaController(StudyActivity.this, false);
            MediaCtrl1.setAnchorView(you);
            you.setMediaController(MediaCtrl);
            you.requestFocus();
            you.setVideoPath(strVideoPath);
            Toast.makeText(StudyActivity.this, "播放录像", Toast.LENGTH_SHORT).show();
            you.start();
        } else if (v == playexample) {
            int exampleid = R.raw.p0101 + currentnum;
            String VideoUri = "android.resource://" + getPackageName() + "/" + exampleid;
            VeidoPlay(VideoUri);
        }else if(v==playwave){
                onPlay(0);//播放 从头开始播放
            //onPlay(0,waveViewExm);
        }else if(v==swichwavebtn){
            if (waveCanvas == null || !waveCanvas.isRecording) {
                status.setText("录音中...");
                //swichwavebtn.setText("停止录音");
                swichwavebtn.setImageDrawable(getResources().getDrawable(R.drawable.wave_stop));
                waveSfv.setVisibility(View.VISIBLE);
                waveView.setVisibility(View.INVISIBLE);
                initAudio();
                startAudio();

            } else {
                status.setText("停止录音");
                //swichwavebtn.setText("开始录音");
                swichwavebtn.setImageDrawable(getResources().getDrawable(R.drawable.wave_start));
                waveCanvas.Stop();
                waveCanvas = null;
                yourWaveFile=new File(U.DATA_DIRECTORY + mFileName + ".wav");

                try {
                    initWaveView();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SoundFile.InvalidInputException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.i("3", "3");
    }
    //    public void initPermission(){
//        MainActivityPermissionsDispatcher.initAudioWithCheck(this);
//
//    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Dialog dialog = new AlertDialog.Builder(this)
                    .setIcon(R.drawable.qq_leba_list_seek_feeds)
                    .setTitle("学习未完成")
                    .setMessage("你确定现在结束学习吗？这将导致本次学习无效！")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            /* User clicked OK so do some stuff */
                            finish();
                            Intent intent = new Intent();
                            intent.setClass(StudyActivity.this, StudyChoice.class);
                            startActivity(intent);
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
        playyours.setVisibility(View.VISIBLE);
        if (currentnum == 0) {
            last.setEnabled(false);
        } else if (currentnum > 0) {
            last.setEnabled(true);
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
            mean.setText(list.get(currentnum).getID() + "." + list.get(currentnum).getSpelling());
            filename = list.get(currentnum).getPhonetic_alphabet();
            //	spelling.setText(list.get(currentnum).getMeanning());
            String imgUrl = "http://115.159.215.125/voicepicture/" + filename + ".jpg";
            String handUrl = "http://115.159.215.125/voicegesture/" + filename + ".JPG";
            String mouseUrl = "http://115.159.215.125/voicemouse/" + filename + ".jpg";
            String pronunciationUrl="http://115.159.215.125/voicepronunciation/" + filename + ".wav";
            downloadFile(imgUrl);
            downloadmouseFile(mouseUrl);
            downloadhandFile(handUrl);
            downloadpronunciationUrl(pronunciationUrl,filename);
        } else if (currentnum >= numoflist) {
            currentnum--;
            Dialog dialog = new AlertDialog.Builder(this)
                    .setIcon(R.drawable.qq_leba_list_seek_feeds)
                    .setTitle("学习已完成")
                    .setMessage("您可以依照复习计划进行本单元的复习")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
		                    /* User clicked OK so do some stuff */
                            finish();
                            Intent intent = new Intent();
                            intent.setClass(StudyActivity.this, StudyChoice.class);
                            startActivity(intent);

                        }
                    }).create();
            dialog.show();
        }


    }

    private void downloadFile(String imgUrl) {
        //word.setVisibility(View.VISIBLE);

        //Uri VideoUri=Uri.parse("http://115.159.215.125/side-"+filename+".3gp");

        new DownImgAsyncTask().execute(imgUrl);

        //player.playUrl(VideoUri);
				/*
			    Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/Test_Movie.m4v");
			    VideoView videoView = (VideoView)this.findViewById(R.id.video_view);
			    videoView.setMediaController(new MediaController(this));
			    videoView.setVideoURI(uri);
			    videoView.start();
			    videoView.requestFocus();
			         **/
    }

    private void downloadhandFile(String imgUrl) {

        new DownImgHandAsyncTask().execute(imgUrl);

    }

    private void downloadmouseFile(String imgUrl) {

        new DownImgMouseAsyncTask().execute(imgUrl);

    }

    private void VeidoPlay(String VideoUri) {
        // TODO Auto-generated method stub
        MediaCtrl = new MediaController(StudyActivity.this, false);
        MediaCtrl.setAnchorView(example);
        example.setMediaController(MediaCtrl);
        example.requestFocus();
        example.setVideoPath(VideoUri);
        Toast.makeText(StudyActivity.this, "播放", Toast.LENGTH_SHORT).show();
        example.start();
	    	 /*
	    	 example.setMediaController(new MediaController(this));
	    	 example.setVideoURI(VideoUri);
	    	 example.start();
	    	 example.requestFocus();
	    	 */
    }

    class DownImgAsyncTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            word.setImageBitmap(null);
            showProgressBar();//显示进度条提示框
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            // TODO Auto-generated method stub
            System.out.println(params[0]);
            Bitmap b = getImageBitmap(params[0]);
            return b;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (result != null) {
                word.setImageBitmap(result);
            }
        }
    }

    class DownImgHandAsyncTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            hand.setImageBitmap(null);
            showProgressBar();//显示进度条提示框
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            // TODO Auto-generated method stub
            System.out.println(params[0]);
            Bitmap b = getImageBitmap(params[0]);
            return b;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (result != null) {
                hand.setImageBitmap(result);
            }
        }


    }

    class DownImgMouseAsyncTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            //mouth.setImageBitmap(null);
            showProgressBar();//显示进度条提示框
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            // TODO Auto-generated method stub
            System.out.println(params[0]);
            Bitmap b = getImageBitmap(params[0]);
            return b;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (result != null) {
                //mouth.setImageBitmap(result);
            }
        }
    }
    public void downloadpronunciationUrl(final String furl, final String fname){

        new Thread(new Runnable() {
            @Override
            public void run(){
                try{
                    String path="";
                    path=getPronunciationPath();
                    String name=fname+".wav";
                    String filename=path+name;
                    OutputStream output=null;
                    URL url=new URL(furl);
                    HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                    //取得inputStream，并将流中的信息写入SDCard
                    exampleWaveFile=new File(filename);
                    //File file=new File(filename);
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.connect();
                    InputStream input=conn.getInputStream();
                    if(exampleWaveFile.exists()){
                        System.out.println(filename+"已存在！");
                        return;
                    }else {
                        exampleWaveFile.createNewFile();
                        output=new FileOutputStream(exampleWaveFile);
                        byte[] buffer=new byte[1024];
                        while (input.read(buffer)!=-1){
                            output.write(buffer);
                        }
                        output.flush();
                        input.close();
                        output.close();
                        SoundFile es=SoundFile.create(exampleWaveFile.getAbsolutePath(),null);
                        loadFromFile(es,waveSfvExm,waveViewExm);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SoundFile.InvalidInputException e) {
                    e.printStackTrace();
                }
            }
            // finally {
//            try{
//                output.close();
//                System.out.println("OutputStream closed success");
//            } catch (IOException e) {
//                System.out.println("OutputStream closed fail");
//                e.printStackTrace();
//            }
//
        }).start();

    }

    private Bitmap getImageBitmap(String url) {
        URL imgUrl = null;
        Bitmap bitmap = null;
        try {
            imgUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imgUrl.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private void showProgressBar() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        Context context = getApplicationContext();
        viewGroup = (ViewGroup) findViewById(R.id.parent_view);
    }
    private void  initWaveView() throws IOException, SoundFile.InvalidInputException {
        SoundFile ys=SoundFile.create(yourWaveFile.getAbsolutePath(),null);
        SoundFile es=SoundFile.create(exampleWaveFile.getAbsolutePath(),null);
        loadFromFile(ys,waveSfv,waveView);
        loadFromFile(es,waveSfvExm,waveViewExm);
        mPlayer = new SamplePlayer(ys);
    }
    //File mFile;
    Thread mLoadSoundFileThread;
    //SoundFile mSoundFile;
    boolean mLoadingKeepGoing;

    private void loadFromFile(final SoundFile mSoundFile,final WaveSurfaceView ws,final WaveformView wv) {
        try {
            Thread.sleep(300);//让文件写入完成后再载入波形 适当的休眠下
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //mFile = new File(U.DATA_DIRECTORY + mFileName + ".wav");
        mLoadingKeepGoing = true;
        // Load the sound file in a background thread
        mLoadSoundFileThread = new Thread() {
            public void run() {
                try {
                    //mSoundFile = SoundFile.create(mFile.getAbsolutePath(),null);
                    if (mSoundFile == null) {
                        return;
                    }
                    //mPlayer = new SamplePlayer(mSoundFile);
                } catch (final Exception e) {
                    e.printStackTrace();
                    return;
                }
                if (mLoadingKeepGoing) {
                    Runnable runnable = new Runnable() {
                        public void run() {
                            finishOpeningSoundFile(mSoundFile,wv);
                            ws.setVisibility(View.INVISIBLE);
                            wv.setVisibility(View.VISIBLE);
                        }
                    };
                    StudyActivity.this.runOnUiThread(runnable);
                }
            }
        };
        mLoadSoundFileThread.start();
    }


    float mDensity;
    /**waveview载入波形完成*/
    private void finishOpeningSoundFile(SoundFile mSoundFile,WaveformView wv) {
        wv.setSoundFile(mSoundFile);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mDensity = metrics.density;
        wv.recomputeHeights(mDensity);
    }
    /**
     * 开始录音
     */
    private void startAudio(){
        waveCanvas = new WaveCanvas();
        waveCanvas.baseLine = waveSfv.getHeight() / 2;
        waveCanvas.Start(audioRecord, recBufSize, waveSfv, mFileName, U.DATA_DIRECTORY, new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                return true;
            }
        });
    }
    @NeedsPermission({Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE})
    public void initAudio(){
        recBufSize = AudioRecord.getMinBufferSize(FREQUENCY,
                CHANNELCONGIFIGURATION, AUDIOENCODING);// 录音组件
        audioRecord = new AudioRecord(AUDIO_SOURCE,// 指定音频来源，这里为麦克风
                FREQUENCY, // 16000HZ采样频率
                CHANNELCONGIFIGURATION,// 录制通道
                AUDIO_SOURCE,// 录制编码格式
                recBufSize);// 录制缓冲区大小 //先修改
        U.createDirectory();
    }
    @OnShowRationale({Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE})
    void showRationaleForRecord(final PermissionRequest request){
        new android.support.v7.app.AlertDialog.Builder(this)
                .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage("是否开启录音权限")
                .show();
    }

    @OnPermissionDenied({Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE})
    void showRecordDenied(){
        Toast.makeText(StudyActivity.this,"拒绝录音权限将无法进行挑战",Toast.LENGTH_LONG).show();
    }

    @OnNeverAskAgain({Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE})
    void onRecordNeverAskAgain() {
        new android.support.v7.app.AlertDialog.Builder(this)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage("您已经禁止了录音权限,是否现在去开启")
                .show();
    }
    private int mPlayStartMsec;
    private int mPlayEndMsec;
    private final int UPDATE_WAV = 100;
    /**播放音频，@param startPosition 开始播放的时间*/
    private synchronized void onPlay(int startPosition) {
        if (mPlayer == null)
            return;
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.pause();
            updateTime.removeMessages(UPDATE_WAV);
        }
        mPlayStartMsec = waveView.pixelsToMillisecs(startPosition);
        mPlayEndMsec = waveView.pixelsToMillisecsTotal();
        mPlayer.setOnCompletionListener(new SamplePlayer.OnCompletionListener() {
            @Override
            public void onCompletion() {
                waveView.setPlayback(-1);
                updateDisplay();
                updateTime.removeMessages(UPDATE_WAV);
                Toast.makeText(getApplicationContext(),"播放完成",Toast.LENGTH_LONG).show();
            }
        });
        mPlayer.seekTo(mPlayStartMsec);
        mPlayer.start();
        Message msg = new Message();
        msg.what = UPDATE_WAV;
        updateTime.sendMessage(msg);
    }
    Handler updateTime = new Handler() {
        public void handleMessage(Message msg) {
            updateDisplay();
            updateTime.sendMessageDelayed(new Message(), 10);
        };
    };

    /**更新upd
     * ateview 中的播放进度*/
    private void updateDisplay() {
        int now = mPlayer.getCurrentPosition();// nullpointer
        int frames = waveView.millisecsToPixels(now);
        waveView.setPlayback(frames);//通过这个更新当前播放的位置
        if (now >= mPlayEndMsec ) {
            waveView.setPlayFinish(1);
            if (mPlayer != null && mPlayer.isPlaying()) {
                mPlayer.pause();
                updateTime.removeMessages(UPDATE_WAV);
            }
        }else{
            waveView.setPlayFinish(0);
        }
        waveView.invalidate();//刷新整个视图
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    private void videoMethod() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        intent.putExtra("camerasensortype", 2); // 调用前置摄像头
        intent.putExtra("autofocus", true); // 自动对焦
        intent.putExtra("fullScreen", false); // 全屏
        intent.putExtra("showActionIcons", false);

        startActivityForResult(intent, 0);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri uriVideo = data.getData();
            Cursor cursor = this.getContentResolver().query(uriVideo, null, null, null, null);
            if (cursor.moveToNext()) {
                                      /* _data：文件的绝对路径 ，_display_name：文件名 */
                strVideoPath = cursor.getString(cursor.getColumnIndex("_data"));
                Toast.makeText(this, strVideoPath, Toast.LENGTH_SHORT).show();
                watchyou.setVisibility(View.VISIBLE);

            }
        }
    }


}
