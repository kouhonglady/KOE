package com.voice.database;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;


import com.voice.model.BookList;
import com.voice.R;
import com.voice.model.Word;
import com.voice.model.WordList;

import java.io.InputStream;
import java.text.*;

public class DataAccess {
	public  static String difficultyID="";           //ѡ������
	public  static String listID;           //ѡ����List
	public  static int NumOfAttention;   //���ʱ�Ĵ���
	public  Context context;
	private SqlHelper helper;
	public static boolean ifContinue;
	
	public  DataAccess(Context context){
		helper = new SqlHelper();
		Cursor cursor =null;
		cursor=helper.Query(context, SqlHelper.ATTENTION_TABLE, null, null, null, null, null, null);
		if (cursor==null)NumOfAttention=0;
		else if(cursor.getCount()==0)
			NumOfAttention=0;
		else if(cursor.moveToLast())
		NumOfAttention=Integer.parseInt(cursor.getString(0));
		
		cursor.close();
		this.context = context;
		
	}
	
	/*
	 * ��ʼ���ʿ�
	 * 
	 * ������
	 * bookname���ʿ�����
	 * list�����е���
	 * numOfList:list������
	 */
	public boolean initBook(String bookname,ArrayList<Word> list,String numOfList,String NewName) throws Exception{
		boolean success = false;
		String bookName = null;
		 bookName=NewName;
		Cursor cursor=helper.Query(context, SqlHelper.BOOKLIST_TABLE, null, null, null, null, null, null);
		String bookID="pinyin";
		if (cursor.moveToLast()){
			StringTokenizer st = new StringTokenizer(cursor.getString(0), "book");
			bookID="book"+(Integer.parseInt(st.nextToken())+1);
		}
		cursor.close();
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
	    String date=f.format(cal.getTime());
	    ContentValues cv =new ContentValues();
	    helper.CreateTable(context, bookID);
	    for (int i=0;i<list.size();i++){
	    	if (ifContinue){
	    		cv.put("ID",list.get(i).getID() );
		    	cv.put("SPELLING", list.get(i).getSpelling());
		    	cv.put("MEANNING", list.get(i).getMeanning());
		    	cv.put("PHONETIC_ALPHABET", list.get(i).getPhonetic_alphabet());
		    	cv.put("LIST", list.get(i).getList());
		    	helper.Insert(context, bookID, cv);
		    	Log.i("Inserting word:",list.get(i).getSpelling());
	    	}
	    	else return false;
	    }
	    WordList wordList = new WordList();
	     f = new SimpleDateFormat("yyyy-MM-dd");
	     
		for (int i=0;i<Integer.parseInt(numOfList);i++){
			if (ifContinue){
				wordList.setBestScore("");
				wordList.setDifficultyID(bookID);
				wordList.setLearned("0");
				wordList.setLearnedTime("");
				wordList.setList(String.valueOf(i+1));
				wordList.setReview_times("0");
				wordList.setReviewTime("");
				wordList.setShouldReview("0");
				this.InsertIntoList(wordList);
			}
			else return false;
			
//			Log.i("Inserting list", wordList.getList());
		}
		DataAccess.difficultyID=bookID;
		cv.clear();
		cv.put("ID", bookID);
	    cv.put("NAME", bookName);
	    cv.put("GENERATE_TIME", date);
	    cv.put("NUMOFLIST", numOfList);
	    cv.put("NUMOFWORD", list.size());
	    helper.Insert(context, SqlHelper.BOOKLIST_TABLE, cv);
		Log.i("select", DataAccess.difficultyID);
		success=true;
	    return success;
	}
	
	/*
	 *��ѯ�ʿ�
	 *����
	 *selection����ѯ������where��䣩
	 *selectionArgs:where�а����ġ�����
	 */
	public ArrayList<BookList> QueryBook(String selection,String[] selectionArgs){

		Cursor cursor=helper.Query(context, SqlHelper.BOOKLIST_TABLE, null, selection, selectionArgs, null, null, null);
		ArrayList<BookList> list= new ArrayList<BookList>();;
        if (cursor.moveToFirst()){
        	do{
        		BookList bl=new BookList();
        		bl.setID(cursor.getString(0));
        		bl.setName(cursor.getString(1));
        		bl.setGenerateTime(cursor.getString(2));
        		bl.setNumOfList(cursor.getString(3));
        		bl.setNumOfWord(cursor.getInt(4));
        		list.add(bl);
        		}
            while(cursor.moveToNext());
        }
        cursor.close();
		return list;
		
	}
   
	/*
     * ��ѯ����
     * ����
     *selection����ѯ������where��䣩
	 *selectionArgs:where�а����ġ�����
     */
	public ArrayList<Word> QueryWord(String selection,String[] selectionArgs){
		Cursor cursor =helper.Query(context, difficultyID, null, selection, selectionArgs, null, null, null);
        ArrayList<Word> list = new ArrayList<Word>();
        if (cursor.moveToFirst()){
        	
        	
        	do{
        		Word word=new Word();
        		word.setID(cursor.getString(0));
        		word.setSpelling(cursor.getString(1));
        		word.setMeanning(cursor.getString(2));
        		word.setPhonetic_alphabet(cursor.getString(3));
        		word.setList(cursor.getString(4));
        		list.add(word);
    //    		Log.i("Querying word:",word.getSpelling());
        		}
            while(cursor.moveToNext());
        }
        cursor.close();
		return list;
		
	}
	
	/*
	 * ��ѯlist������
	 * ����
	 * selection����ѯ������where��䣩
	 *selectionArgs:where�а����ġ�����
	 */
	public ArrayList<WordList> QueryList(String selection,String[] selectionArgs){
		Cursor cursor=helper.Query(context, SqlHelper.WORDLIST_TABLE, null, selection, selectionArgs, null, null, null);
		ArrayList<WordList> list = new ArrayList<WordList>();
		if (cursor.moveToFirst()){
        	
        	
        	do{
        		WordList wl=new WordList();
        		wl.setDifficultyID(cursor.getString(0));
        		wl.setList(cursor.getString(1));
        		wl.setLearned(cursor.getString(2));
        		wl.setLearnedTime(cursor.getString(3));
        		wl.setReview_times(cursor.getString(4));
        		wl.setReviewTime(cursor.getString(5));
        		wl.setBestScore(cursor.getString(6));
        		wl.setShouldReview(cursor.getString(7));
        		list.add(wl);
        		}
            while(cursor.moveToNext());
        }
		cursor.close();

		return list;
		
	}
	
	/*
	 * ����list������
	 * ����
	 * list��Ҫ���и��µ�list�������ݣ�
	 */
	public void UpdateList(WordList list){
		ContentValues cv = new ContentValues();
		cv.put("BOOKID", list.getDifficultyID());
		cv.put("LIST", list.getList());
		cv.put("LEARNED", list.getLearned());
		cv.put("LEARN_TIME", list.getLearnedTime());
		cv.put("REVIEW_TIMES", list.getReview_times());
		cv.put("REVIEWTIME", list.getReviewTime());
		cv.put("BESTSCORE", list.getBestScore());
		cv.put("SHOULDREVIEW", list.getShouldReview());
		
		helper.Update(context, SqlHelper.WORDLIST_TABLE, cv, " BOOKID ='"+list.getDifficultyID()+"'AND LIST ='"+list.getList()+"'", null);
	}
	
	/*
	 * ɾ��list������
	 * ����
	 * list��Ҫɾ����list
	 */
	public void DeleteBook(){
		helper.Delete(context, SqlHelper.WORDLIST_TABLE, "BOOKID ='"+DataAccess.difficultyID+"'", null);
		helper.Delete(context, SqlHelper.BOOKLIST_TABLE, "ID ='"+DataAccess.difficultyID+"'", null);
		helper.DeleteTable(context, DataAccess.difficultyID);
	}
	
	/*
	 * �������ʱ�
	 * ����
	 * word��Ҫ��ӵĵ���
	 */
	public void InsertIntoAttention(Word word){
		ContentValues cv = new ContentValues();
		cv.put("ID", String.valueOf(DataAccess.NumOfAttention+1));
		cv.put("SPELLING", word.getSpelling());
		cv.put("MEANNING", word.getMeanning());
		cv.put("PHONETIC_ALPHABET", word.getPhonetic_alphabet());
		cv.put("LIST", "Attention");
		helper.Insert(context, SqlHelper.ATTENTION_TABLE, cv);
		
	}
	
	/*
	 * ɾ�����ʱ��еĵ���
	 * ����
	 * word��Ҫ��ӵĵ���
	 */
	public void DeleteFromAttention(Word word){
		helper.Delete(context, SqlHelper.ATTENTION_TABLE, "ID ='"+word.getID()+"'", null);
	}
	
	/*
	 * ��ѯ���ʱ�
	 * selection����ѯ������where��䣩
	 * selectionArgs:where�а����ġ�����
	 */
	
	public ArrayList<Word> QueryAttention(String selection,String[] selectionArgs){
		Cursor cursor =helper.Query(context, SqlHelper.ATTENTION_TABLE, null, selection, selectionArgs, null, null, null);
        ArrayList<Word> list = new ArrayList<Word>();
        if (cursor.moveToFirst()){
        	
        	
        	do{
        		Word word=new Word();
        		word.setID(cursor.getString(0));
        		word.setSpelling(cursor.getString(1));
        		word.setMeanning(cursor.getString(2));
        		word.setPhonetic_alphabet(cursor.getString(3));
        		word.setList(cursor.getString(4));
        		list.add(word);
      //  		Log.i("Querying word:",word.getSpelling());
        		}
            while(cursor.moveToNext());
        }
        cursor.close();
		return list;
	}
	
	/*
	 * ����list������
	 * ����
	 * list:Ҫ�����list��Ŀ
	 */
	public void InsertIntoList(WordList list){
		ContentValues cv = new ContentValues();
		cv.put("BOOKID", list.getDifficultyID());
		cv.put("LIST", list.getList());
		cv.put("LEARNED", list.getLearned());
		cv.put("LEARN_TIME", list.getLearnedTime());
		cv.put("REVIEW_TIMES", list.getReview_times());
		cv.put("REVIEWTIME", list.getReviewTime());
		cv.put("BESTSCORE", list.getBestScore());
		cv.put("SHOULDREVIEW", list.getShouldReview());
		helper.Insert(context, SqlHelper.WORDLIST_TABLE, cv);
		
	}
	
	public void ResetBook(){
		ArrayList<WordList> list = new ArrayList<WordList>();
		list=this.QueryList("DifficultyID ='"+DataAccess.difficultyID+"'", null);
		for (int i=0;i<list.size();i++){
			WordList newlist = list.get(i);
			newlist.setBestScore("");
			newlist.setLearned("0");
			newlist.setLearnedTime("");
			newlist.setReview_times("0");
			newlist.setReviewTime("");
			newlist.setShouldReview("0");
			this.UpdateList(newlist);
			
		}
	}
	
	public void UpdateAttention (Word word){
		ContentValues cv = new ContentValues();
		cv.put("ID", word.getID());
		cv.put("SPELLING", word.getSpelling());
		cv.put("MEANNING", word.getMeanning());
		cv.put("PHONETIC_ALPHABET", word.getPhonetic_alphabet());
		cv.put("LIST", word.getList());
		helper.Update(context, SqlHelper.ATTENTION_TABLE, cv, "ID ='"+word.getID()+"'", null);
	}
 }
