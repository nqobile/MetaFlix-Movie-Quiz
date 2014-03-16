package edu.uci.ics.metaflix;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbAdapter extends SQLiteOpenHelper{

	public static final String DATABASE_NAME = "moviedb";
	public static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "movies";
	private static final String MOVIE_ID = "id";
	private static final String MOVIE_TITLE = "title";
	private static final String MOVIE_YEAR= "year";
	private static final String MOVIE_DIRECTOR = "director";
	
	private static final String FILE_NAME = "movies.csv";
	private static final String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME + "("+MOVIE_ID+" integer primary key autoincrement, "+MOVIE_TITLE+" text not null, "+MOVIE_YEAR+" integer not null, "+MOVIE_DIRECTOR+" text not null);";
	private static final String CREATE_TABLE2 = "CREATE TABLE stars(id integer primary key autoincrement, first_name text, last_name text not null);";
	private static final String CREATE_TABLE3 = "CREATE TABLE stars_in_movies(star_id integer NOT NULL, movie_id integer NOT NULL, PRIMARY KEY (star_id, movie_id), FOREIGN KEY (star_id) REFERENCES stars(id), FOREIGN KEY (movie_id) REFERENCES movies(id))";
	private SQLiteDatabase mDb;
	private Context mContext;
	
	public DbAdapter(Context ctx){
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
		mContext = ctx;
		this.mDb = getWritableDatabase();
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);
		db.execSQL(CREATE_TABLE2);
		db.execSQL(CREATE_TABLE3);
		// populate database
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(mContext.getAssets().open(FILE_NAME)));
			String line;
			while((line=in.readLine())!=null) {
				String[] fields = line.split(",");
				ContentValues values = new ContentValues();
				values.put(MOVIE_ID, fields[0]);
				values.put(MOVIE_TITLE, fields[1]);
				values.put(MOVIE_YEAR, fields[2]);
				values.put(MOVIE_DIRECTOR, fields[3]);
				db.insert(TABLE_NAME, null, values);
			}
			BufferedReader in2 = new BufferedReader(new InputStreamReader(mContext.getAssets().open("stars.csv")));
			String line2;
			while((line2=in2.readLine())!=null) {
				String[] fields2 = line2.split(",");
				ContentValues values2 = new ContentValues();
				values2.put("id", fields2[0]);
				values2.put("first_name", fields2[1]);
				values2.put("last_name", fields2[2]);
				db.insert("stars", null, values2);
			}
			BufferedReader in3 = new BufferedReader(new InputStreamReader(mContext.getAssets().open("stars_in_movies.csv")));
			String line3;
			while((line3=in3.readLine())!=null) {
				String[] fields3 = line3.split(",");
				ContentValues values3 = new ContentValues();
				values3.put("star_id", fields3[0]);
				values3.put("movie_id", fields3[1]);
				db.insert("stars_in_movies", null, values3);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS stars");
		db.execSQL("DROP TABLE IF EXISTS stars_in_movies");
		onCreate(db);	
	}
	
	public Cursor fetchAll() {
		return mDb.query(TABLE_NAME, new String[] {MOVIE_ID, MOVIE_TITLE, MOVIE_YEAR, MOVIE_DIRECTOR}, null, null, null, null, null);
	}

}
