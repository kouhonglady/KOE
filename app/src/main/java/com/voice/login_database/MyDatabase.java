package com.voice.login_database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabase extends SQLiteOpenHelper{
	public static final String CREATE_LEADER="create table if not exists User( "+
			"tele text, "+
			"password text,"+
			"name text,"+
			"sign text,"+
			"sex text,"+
			"regist_date)";


	private Context mContext;

	public MyDatabase(Context context,String name,CursorFactory factory,int version){
		super(context,name,factory,version);
		mContext=context;
	}
	public MyDatabase(Context context) {
		super(context, "dbName", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_LEADER);
		Toast.makeText(mContext, "Create secceeded", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
