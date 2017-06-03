package com.voice.bbs;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by EXcalibur on 2017/5/20.
 */

public class BBSFetchr {
    private static final String TAG = "BBSFetchr";
    private List<BBS> mBBSList;


    public List<BBS> fetchBBS(){
        mBBSList=new ArrayList<>();

        fetchBBSTime(mBBSList);
        fetchBBSAuthor(mBBSList);
        fetchBBSHot(mBBSList);
        fetchBBSReply(mBBSList);

        return mBBSList;
    }

    /**
     * @param mBBSList
     * @return time+ href + title
     */
    public void fetchBBSTime(List<BBS> mBBSList){
        try {
            String result=new Fetchr().getUrlString("http://115.159.215.125/bbs/api.php?mod=js&bid=6");
            Document document= Jsoup.parse(result);
            Elements content= document.getElementsByTag("li");
            for(Element thing:content){
                String bbsTime=thing.getElementsByTag("em").text();
                String bbsUrl=thing.select("a").attr("href");
                String bbsTitle=thing.select("a").attr("title");

                BBS bbs=new BBS(bbsTime,bbsTitle,bbsUrl);
                mBBSList.add(bbs);
                Log.i(TAG, "bbs: " + bbsTime+"------"+bbsTitle+"------"+bbsUrl);
            }
            Log.i(TAG, "Fetched contents of URL: 66666" + result);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Failed to fetch URL6666: ", e);
        }

    }

    /**
     * @param mBBSList
     * @return author + abstract
     */
    public void fetchBBSAuthor(List<BBS> mBBSList){
        try {
            String result=new Fetchr().getUrlString("http://115.159.215.125/bbs/api.php?mod=js&bid=8");
            Document document= Jsoup.parse(result);
            Elements content= document.getElementsByTag("dl");
            int i=0;
            for(Element thing:content){

                Elements bbsAuthors=thing.getElementsByTag("em");
                String bbsAuthor="default";
                for(Element time:bbsAuthors){

                    bbsAuthor=time.select("a").text();

                }
                String bbsAbstract=Jsoup.clean(thing.select("a").text(), Whitelist.none());

                mBBSList.get(i).setBBSAuthor(bbsAuthor);
                mBBSList.get(i).setBBSAbstract(bbsAbstract);

                i++;
                Log.i(TAG, "bbs: "+"------" +bbsAuthor+"------"+bbsAbstract);
            }
            Log.i(TAG, "Fetched contents of URL8888: " + result);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Failed to fetch URL8888: ", e);
        }

    }

    /**
     * @param mBBSList
     * @return hot
     */
    public void fetchBBSHot(List<BBS> mBBSList){

        try {
            String result=new Fetchr().getUrlString("http://115.159.215.125/bbs/api.php?mod=js&bid=10");
            Document document= Jsoup.parse(result);
            Elements content= document.getElementsByTag("li");
            int i=0;
            for(Element thing:content){
                String bbsHot=thing.getElementsByTag("em").text();

                mBBSList.get(i).setBBSHot(bbsHot);
                i++;
                Log.i(TAG, "bbs: " + bbsHot);
            }
            Log.i(TAG, "Fetched contents of URL10 10 10 10: " + result);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Failed to fetch URL10 10 10 10: ", e);
        }
    }

    /**
     * @param mBBSList
     * @return reply
     */
    public void fetchBBSReply(List<BBS> mBBSList){
        try {
            String result=new Fetchr().getUrlString("http://115.159.215.125/bbs/api.php?mod=js&bid=9");
            Document document= Jsoup.parse(result);
            Elements content= document.getElementsByTag("li");
            int i=0;
            for(Element thing:content){
                String bbsReply=thing.getElementsByTag("em").text();

                mBBSList.get(i).setBBSReply(bbsReply);
                i++;
                Log.i(TAG, "bbs: " + bbsReply);
            }
            Log.i(TAG, "Fetched contents of URL: 9999" + result);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Failed to fetch URL9999: ", e);
        }
    }
}
