package edu.uci.ics.metaflix;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class QuestionAdapter {
	Random randomGen;

	    private DatabaseHelper mDbHelper;
	    private SQLiteDatabase mDb;

	    private final Context mCtx;

	    private static class DatabaseHelper extends SQLiteOpenHelper {

	        DatabaseHelper(Context context) {
	            super(context, DbAdapter.DATABASE_NAME, null, DbAdapter.DATABASE_VERSION);
	        }

	        @Override
	        public void onCreate(SQLiteDatabase db) {
	        }

	        @Override
	        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        }
	    }

	    /**
	     * Constructor - takes the context to allow the database to be
	     * opened/created
	     * 
	     * @param ctx
	     *            the Context within which to work
	     */
	    public QuestionAdapter(Context ctx) {
	        this.mCtx = ctx;
	        randomGen = new Random();
	    }

	    /**
	     * Open the cars database. If it cannot be opened, try to create a new
	     * instance of the database. If it cannot be created, throw an exception to
	     * signal the failure
	     * 
	     * @return this (self reference, allowing this to be chained in an
	     *         initialization call)
	     * @throws SQLException
	     *             if the database could be neither opened or created
	     */
	    public QuestionAdapter open() throws SQLException {
	        this.mDbHelper = new DatabaseHelper(this.mCtx);
	        this.mDb = this.mDbHelper.getWritableDatabase();
	        return this;
	    }

	    /**
	     * close return type: void
	     */
	    public void close() {
	        this.mDbHelper.close();
	    }

	    public Question generateQuestion(int questionType){
		/*
		 * 0 - Who directed the movie X?
		 * 1 - When was the movie X released?
		 * 2 - Which star (was/was not) in the movie X?
		 * 3 - In which movie the stars X and Y appear together?
		 * 4 - Who directed/did not direct the star X?
		 * 5 - Which star appears in both movies X and Y?
		 * 6 - Which star did not appear in the same movie with the star X?
		 * 7 - Who directed the star X in year Y?
		 */
	    	switch(questionType)
	    	{
	    	case 0:
	    		ArrayList<String> answers = new ArrayList<String>();
	    		String wrongAnsQuery = "SELECT director FROM movies ORDER BY RANDOM() LIMIT 3;";
	    		Cursor wrongResults = this.mDb.rawQuery(wrongAnsQuery,null);
	    		
	    		//Add wrong answers to Array of Answers
	    		answers.add(wrongResults.getString(0));
	    		answers.add(wrongResults.getString(1));
	    		answers.add(wrongResults.getString(2));
	    		
	    		String query = "SELECT title, director FROM movies ORDER BY RANDOM() LIMIT 1;";
	    		Cursor results = this.mDb.rawQuery(query, null);
	    		int correctAnswerIndex = randomGen.nextInt(3);
	    		answers.add(correctAnswerIndex, results.getString(0));
	    		
	    		return new Question("Who directed the movie "+results.getString(0)+"?", correctAnswerIndex, answers);
	    	case 1:
	    		
	    	case 2:
	    	case 3:
	    	case 4:
	    	case 5:
	    	case 6:
	    	case 7:
	    	case 8:
	    	default:
	    		return null;
	    	}

	    }
}
