package edu.uci.ics.metaflix;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbAdapter extends SQLiteOpenHelper
{

	private static final String DATABASE_NAME = "moviedb";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "movies";
	private static final String MOVIE_ID = "id";
	private static final String MOVIE_TITLE = "title";
	private static final String MOVIE_YEAR= "year";
	private static final String MOVIE_DIRECTOR = "director";
	
	private static final String FILE_NAME = "movies.csv";
	private static final String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME + "("+MOVIE_ID+" integer primary key autoincrement, "+MOVIE_TITLE+" text not null, "+MOVIE_YEAR+" integer not null, "+MOVIE_DIRECTOR+" text not null);";
	private SQLiteDatabase mDb;
	private Context mContext;
	
	public DbAdapter(Context ctx)
	{
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
		mContext = ctx;
		this.mDb = getWritableDatabase();
	}
	
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		/*
		 * Create table movies in sqlite db
		 */
		db.execSQL(CREATE_TABLE);
		System.out.println("Created table");
		/*
		 * Read from the csv file and import the data into the sqlite db.
		 */
		try
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(mContext.getAssets().open(FILE_NAME)));
			String line;
			line = in.readLine();
			while(line != null)
			{
				System.out.println("Trying to add data to db...");
				String[] fields = line.split(",");
				ContentValues values = new ContentValues();
				values.put(MOVIE_ID, fields[0]);
				values.put(MOVIE_TITLE, fields[1]);
				values.put(MOVIE_YEAR, fields[2]);
				values.put(MOVIE_DIRECTOR, fields[3]);
				db.insert(TABLE_NAME, null, values);
				line = in.readLine();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
	
	public Cursor fetchAll()
	{
		return mDb.query(TABLE_NAME, new String[] {MOVIE_ID, MOVIE_TITLE, MOVIE_YEAR, MOVIE_DIRECTOR}, null, null, null, null, null);
	}
}
