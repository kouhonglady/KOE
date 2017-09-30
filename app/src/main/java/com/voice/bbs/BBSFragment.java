package com.voice.bbs;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.voice.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by EXcalibur on 2017/5/20.
 */

public class BBSFragment extends Fragment{
    private Context mContext;
    private static final String TAG = "BBSFragment";
    private RecyclerView mBBSRecyclerView;
    private List<BBS> mBBSList = new ArrayList<>();

    public static BBSFragment newInstance() {
        return new BBSFragment();
    }

//    public BBSFragment(RecyclerView mRcycle, Context context) {
//
//        mBBSRecyclerView=mRcycle;
//        mContext=context;
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new FetchItemsTask().execute();
    }

    private class FetchItemsTask extends AsyncTask<Void,Void,List<BBS>> {
        /**
         * @param params
         * @return 这里的返回值是返回给谁了呢
         */
        @Override
        protected List<BBS> doInBackground(Void... params) {
//            List<BBS> mBBSLab= new ArrayList<>();

//            try {
//
//                String result = new Fetchr()
//                        .getUrlString("http://115.159.215.125/bbs/api.php?mod=js&bid=6");
//                Document document=Jsoup.parse(result);
//                Elements content= document.getElementsByTag("li");
//                for(Element thing:content){
//                    String bbsTime=thing.getElementsByTag("em").text();
//                    String bbsUrl=thing.select("a").attr("href");
//                    String bbsTitle=thing.select("a").attr("title");
//
//                    BBS bbs=new BBS(bbsTime,bbsTitle,bbsUrl);
//                    mBBSLab.add(bbs);
//                    Log.i(TAG, "bbs: " + bbsTime+"------"+bbsTitle+"------"+bbsUrl);
//                }
//                Log.i(TAG, "Fetched contents of URL: " + result);
//            } catch (IOException ioe) {
//                Log.e(TAG, "Failed to fetch URL: ", ioe);
//            }

            return new BBSFetchr().fetchBBS();
        }





        @Override
        protected void onPostExecute(List<BBS> bbses) {
            mBBSList=bbses;
            setupAdapter();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_bbs,container,false);

        mBBSRecyclerView= (RecyclerView) v.findViewById(R.id.fragment_bbs_recycler_view);
        mBBSRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        setupAdapter();

        return v;
    }
    private void setupAdapter(){
        if (isAdded()){
            mBBSRecyclerView.setAdapter(new BBSAdapter(mBBSList));
        }
    }
    private class BBSHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView mBBSHeadImageView;
        private ImageView mBBSLikeImageView;
        private ImageView mBBSLineImageView;
        private ImageView mBBSMailImageView;
        private TextView mBBSTitleTextView;
        //private TextView mBBSUrlTextView;
        private TextView mBBSTimeTextView;
        private TextView mBBSAuthorTextView;
        private TextView mBBSAbstractTextView;
        private TextView mBBSHotTextView;
        private TextView mBBSReplyTextView;
        private EditText mBBSAddComEditView;
        private BBS thisBBS;
        private WebView mWebView;
        private String BBS_URL ="BBS_URL";



        public BBSHolder(View itemView){
            super(itemView);
            mBBSHeadImageView= (ImageView) itemView.findViewById(R.id.bbs_head_iamge_view);
            mBBSLikeImageView=(ImageView) itemView.findViewById(R.id.bbs_like_image_view);
            mBBSLineImageView=(ImageView) itemView.findViewById(R.id.bbs_line_image_view);
            mBBSMailImageView=(ImageView) itemView.findViewById(R.id.bbs_reply_image_view);

            mBBSTitleTextView= (TextView) itemView.findViewById(R.id.bbs_title_text_view);
           // mBBSUrlTextView= (TextView) itemView.findViewById(R.id.bbs_href_text_view);
            mBBSTimeTextView= (TextView) itemView.findViewById(R.id.bbs_time_text_view);

            mBBSAuthorTextView= (TextView) itemView.findViewById(R.id.bbs_author_text_view);
            mBBSAbstractTextView= (TextView) itemView.findViewById(R.id.bbs_abstract_text_View);
            mBBSHotTextView= (TextView) itemView.findViewById(R.id.bbs_hot_text_view);
            mBBSReplyTextView= (TextView) itemView.findViewById(R.id.bbs_reply_text_view);
            mBBSAddComEditView= (EditText) itemView.findViewById(R.id.bbs_comment_edit_text);
        }

        public void bindBBS(BBS bbs){
            Context theContext;
            thisBBS=bbs;
            String bbs_head_name;
            int max=15;
            int min=0;
            Random random=new Random();
            int i=random.nextInt(max)%(max-min+1)+min;
            bbs_head_name="bbs_head_"+i;
            System.out.println("bbs_head_name-------------"+i);
            System.out.println("bbs_head_name-----------------"+bbs_head_name);

            Class drawable=R.drawable.class;
            try {
                Field field=drawable.getField(bbs_head_name);
                int resId=field.getInt(field.getName());
                mBBSHeadImageView.setImageResource(resId);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            //mBBSHeadImageView.setImageResource(getResources().getIdentifier(bbs_head_name,"drawable",theContext.getPackageName()));
            mBBSLikeImageView.setImageDrawable(getResources().getDrawable(R.drawable.bbs_like));
            mBBSLineImageView.setImageDrawable(getResources().getDrawable(R.drawable.bbs_line));
            mBBSMailImageView.setImageDrawable(getResources().getDrawable(R.drawable.bbs_mail));
            mBBSTimeTextView.setText(bbs.getBBSTime());
            mBBSTitleTextView.setText(bbs.getBBSTitle());
            //mBBSUrlTextView.setText(bbs.getBBSUrl());
            mBBSAuthorTextView.setText(bbs.getBBSAuthor());
            mBBSAbstractTextView.setText(bbs.getBBSAbstract());
            mBBSHotTextView.setText(bbs.getBBSHot());
            mBBSReplyTextView.setText(bbs.getBBSReply());
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            Intent intent = new Intent(mContext, WebViewActivity.class);
            //这个context要初始化，需要这个Fragment有一个 实例，当社区被点击后，就会实例一个BBSFragmetn,然后传入contex和RV
            intent.putExtra(BBS_URL,thisBBS.getBBSUrl());
            mContext.startActivity(intent);

        }
    }

    private class BBSAdapter extends RecyclerView.Adapter<BBSHolder>{
        private List<BBS> mBBSList;

        public BBSAdapter(List<BBS> BBSList) {
            mBBSList=BBSList;
        }

        @Override
        public BBSHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.bbs_list, parent, false);
            return new BBSHolder(view);
        }

        @Override
        public void onBindViewHolder(BBSHolder holder, int position) {

            BBS mBBS=mBBSList.get(position);
            holder.bindBBS(mBBS);

        }

        @Override
        public int getItemCount() {
            return mBBSList.size();
        }
    }
}
