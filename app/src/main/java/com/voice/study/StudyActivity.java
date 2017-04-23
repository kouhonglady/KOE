package com.voice.study;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.app.TabActivity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;


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
    private String strVideoPath = "";// 视频文件的绝对路径
    //  private TextView spelling;
    private TextView info;
    ViewGroup viewGroup;
    VideoPlayer player;
    MediaController MediaCtrl;
    MediaController MediaCtrl1;

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


        TabHost tabHost = getTabHost();

        TabSpec page1 = tabHost.newTabSpec("tab1")
                .setIndicator("口型教学")
                .setContent(R.id.tab1);
        tabHost.addTab(page1);

        TabSpec page2 = tabHost.newTabSpec("tab2")
                .setIndicator("我的口型")
                .setContent(R.id.tab2);
        tabHost.addTab(page2);
        TabWidget tabWidget = tabHost.getTabWidget();
        for (int i = 0; i < tabWidget.getChildCount(); i++) {
            //修改显示字体大小
            TextView tv = (TextView) tabWidget.getChildAt(i).findViewById(android.R.id.title);
            tv.setTextSize(16);
            //tv.setTextColor(this.getResources().getColorStateList(android.R.color.white));
        }

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
        } else if (v == playyours) {
            videoMethod();
            //playyours.setVisibility(View.INVISIBLE);
        } else if (v == watchyou) {
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
        }
        Log.i("3", "3");
    }

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
            downloadFile(imgUrl);
            downloadmouseFile(mouseUrl);
            downloadhandFile(handUrl);
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
