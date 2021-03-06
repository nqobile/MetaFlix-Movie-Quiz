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
	    private ArrayList<String> answers;

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
	    public QuestionAdapter(Context ctx)
	    {
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
	    public QuestionAdapter open() throws SQLException
	    {
	        this.mDbHelper = new DatabaseHelper(this.mCtx);
	        this.mDb = this.mDbHelper.getWritableDatabase();
	        return this;
	    }

	    /**
	     * close return type: void
	     */
	    public void close()
	    {
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
	    	String wrongAnsQuery;
	    	String query;
	    	String query2;
	    	Cursor wrongResults;
	    	Cursor results;
	    	Cursor results2;
	    	int correctAnswerIndex;
	    	switch(questionType)
	    	{
	    	case 0:
	    		answers = new ArrayList<String>();
	    		wrongAnsQuery = "SELECT director FROM movies ORDER BY RANDOM() LIMIT 3";
	    		wrongResults = this.mDb.rawQuery(wrongAnsQuery,null);
	    		
	    		//Add wrong answers to Array of Answers
	    		while(wrongResults.moveToNext())
	    		{
	    			answers.add(wrongResults.getString(0));
	    		}
	    		
	    		query = "SELECT title, director FROM movies ORDER BY RANDOM() LIMIT 1";
	    		results = this.mDb.rawQuery(query, null);
	    		results.moveToFirst();
	    		correctAnswerIndex = randomGen.nextInt(4);
	    		answers.add(correctAnswerIndex, results.getString(1));
	    		
	    		return new Question("Who directed the movie "+results.getString(0)+"?", correctAnswerIndex, answers);
	    	case 1:
	    		answers = new ArrayList<String>();
	    		query = "SELECT title, year FROM movies ORDER BY RANDOM() LIMIT 1";
	    		results = this.mDb.rawQuery(query, null);
	    		results.moveToFirst();
	    		
	    		answers.add(String.valueOf(results.getInt(1)-1));
	    		answers.add(String.valueOf(results.getInt(1)-2));
	    		answers.add(String.valueOf(results.getInt(1)+1));
	    		
	    		correctAnswerIndex = randomGen.nextInt(4);
	    		answers.add(correctAnswerIndex, String.valueOf(results.getInt(1)));
	    		
	    		return new Question("When was the movie "+results.getString(0)+" released?", correctAnswerIndex, answers);	
	    	case 2:
	    		answers = new ArrayList<String>();
	    		query = "SELECT s.first_name, s.last_name, m.title FROM stars s, stars_in_movies sm, movies m WHERE (s.id = sm.star_id AND m.id = sm.movie_id) ORDER BY RANDOM() LIMIT 1";
	    		results = this.mDb.rawQuery(query, null);
	    		results.moveToFirst();
	    		
	    		wrongAnsQuery = "SELECT first_name, last_name FROM stars ORDER BY RANDOM() LIMIT 3";
	    		wrongResults = this.mDb.rawQuery(wrongAnsQuery, null);
	    		
	    		while(wrongResults.moveToNext())
	    		{
	    			answers.add(wrongResults.getString(0) + " " + wrongResults.getString(1));
	    		}	    		
	    		
	    		correctAnswerIndex = randomGen.nextInt(4);
	    		answers.add(correctAnswerIndex, results.getString(0) + " " + results.getString(1));
	    		return new Question("Which star was in the movie "+results.getString(2)+"?", correctAnswerIndex, answers);	
	    	case 3:
	    		answers = new ArrayList<String>();
	    		query = "SELECT movies.id, movies.title FROM stars, stars_in_movies, movies WHERE (stars.id = stars_in_movies.star_id AND movies.id = stars_in_movies.movie_id) GROUP BY movies.id HAVING count(stars.id)>1 ORDER BY RANDOM() LIMIT 1";
	    		results = this.mDb.rawQuery(query, null);
	    		results.moveToFirst();
	    		
	    		wrongAnsQuery = "SELECT title FROM movies ORDER BY RANDOM() LIMIT 3";
	    		wrongResults = this.mDb.rawQuery(wrongAnsQuery, null);
	    		
	    		while(wrongResults.moveToNext())
	    		{
	    			answers.add(wrongResults.getString(0));
	    		}
	    		
	    		query2 = "SELECT first_name, last_name FROM stars, stars_in_movies WHERE (stars.id = stars_in_movies.star_id) AND movie_id = " + results.getInt(0) + " ORDER BY RANDOM() LIMIT 2";
	    		results2 = this.mDb.rawQuery(query2, null);
	    		results2.moveToFirst();
	    		
	    		correctAnswerIndex = randomGen.nextInt(4);
	    		answers.add(correctAnswerIndex, results.getString(1));
	    		String question = "In which movie did stars " + results2.getString(0) + " " + results2.getString(1);
	    		results2.moveToNext();
	    		question += " and " + results2.getString(0) + " " + results2.getString(1) + " appear together?";
	    		
	    		return new Question(question, correctAnswerIndex, answers);
	    	case 4:
	    		answers = new ArrayList<String>();
	    		query = "SELECT s.id, s.first_name, s.last_name FROM stars s, stars_in_movies sm WHERE s.id=sm.star_id ORDER BY RANDOM() LIMIT 1";
	    		results = this.mDb.rawQuery(query, null);
	    		results.moveToFirst();
	    		
	    		query2 = "SELECT m.director FROM stars s, movies m, stars_in_movies sm WHERE s.id=sm.star_id AND m.id=sm.movie_id" +
	    				" AND s.id=" + results.getInt(0) + " ORDER BY RANDOM() LIMIT 1";
	    		results2 = this.mDb.rawQuery(query2, null);
	    		results2.moveToFirst();	    		
	    		
	    		wrongAnsQuery = "SELECT director FROM movies ORDER BY RANDOM() LIMIT 3";
	    		wrongResults = this.mDb.rawQuery(wrongAnsQuery, null);
	    		
	    		while(wrongResults.moveToNext())
	    		{
	    			answers.add(wrongResults.getString(0));
	    		}
	    		correctAnswerIndex = randomGen.nextInt(4);
	    		answers.add(correctAnswerIndex, results2.getString(0));
	    		return new Question("Who directed the star " + results.getString(1) + " " + results.getString(2) + "?", correctAnswerIndex, answers);
	    		
	    	case 5:
	    		answers = new ArrayList<String>();
	    		query = "SELECT sm.star_id, s.first_name, s.last_name FROM stars_in_movies sm, stars s WHERE sm.star_id = s.id GROUP BY star_id HAVING count(movie_id) > 1 ORDER BY RANDOM() LIMIT 1";
	    		results = this.mDb.rawQuery(query, null);
	    		results.moveToFirst();
	    		
	    		query2 = "SELECT m.title FROM stars_in_movies sm, movies m WHERE sm.movie_id = m.id AND sm.star_id=" + results.getInt(0)+ " ORDER BY RANDOM() LIMIT 2";
	    		results2 = this.mDb.rawQuery(query2, null);
	    		results2.moveToFirst();
	    		
	    		wrongAnsQuery = "SELECT first_name, last_name FROM stars ORDER BY RANDOM() LIMIT 3";
	    		wrongResults = this.mDb.rawQuery(wrongAnsQuery,null);
	    		
	    		while(wrongResults.moveToNext())
	    		{
	    			answers.add(wrongResults.getString(0) + " " + wrongResults.getString(1));
	    		}
	    		correctAnswerIndex = randomGen.nextInt(4);
	    		answers.add(correctAnswerIndex, results.getString(1) + " " + results.getString(2));
	    		question = "Which star appears in both movies " + results2.getString(0) + " and ";
	    		results2.moveToNext();
	    		question += results2.getString(0) + "?";
	    		return new Question(question, correctAnswerIndex, answers);
	    		
	    	case 6:
	    		answers = new ArrayList<String>();
	    		query = "SELECT movies.id FROM stars, stars_in_movies, movies WHERE (stars.id = stars_in_movies.star_id AND movies.id = stars_in_movies.movie_id) GROUP BY movies.id HAVING count(stars.id)>3 ORDER BY RANDOM() LIMIT 1";
	    		results = this.mDb.rawQuery(query, null);
	    		results.moveToFirst();
	    		
	    		//First answer is the question person, next 3 are wrong answers.
	    		query2 = "SELECT s.first_name, s.last_name FROM stars_in_movies sm, stars s WHERE sm.star_id = s.id AND sm.movie_id=" + results.getInt(0);
	    		results2 = this.mDb.rawQuery(query2, null);
	    		
	    		   		
	    		for(int i = 0; i < 3; i++)
	    		{
	    			results2.moveToNext();
	    			answers.add(results2.getString(0) + " " + results2.getString(1));
	    		}
	    		
	    		//This one is actually right.
	    		wrongAnsQuery = "SELECT first_name, last_name FROM stars ORDER BY RANDOM() LIMIT 1";
	    		wrongResults = this.mDb.rawQuery(wrongAnsQuery, null);	 
	    		wrongResults.moveToFirst();
	    		
	    		correctAnswerIndex = randomGen.nextInt(4);
	    		results2.moveToNext();
	    		answers.add(correctAnswerIndex, results2.getString(0) + " " + results2.getString(1));
	    		question = "Which star did not appear in the same movie with the star " + wrongResults.getString(0) + " " + wrongResults.getString(1) + "?";
	    		return new Question(question, correctAnswerIndex, answers);
	    	case 7:
	    		answers = new ArrayList<String>();
	    		query = "SELECT s.id, s.first_name, s.last_name FROM stars s, stars_in_movies sm WHERE s.id=sm.star_id ORDER BY RANDOM() LIMIT 1";
	    		results = this.mDb.rawQuery(query, null);
	    		results.moveToFirst();
	    		
	    		query2 = "SELECT m.director, m.year FROM stars s, movies m, stars_in_movies sm WHERE s.id=sm.star_id AND m.id=sm.movie_id" +
	    				" AND s.id=" + results.getInt(0) + " ORDER BY RANDOM() LIMIT 1";
	    		results2 = this.mDb.rawQuery(query2, null);
	    		results2.moveToFirst();	    		
	    		
	    		wrongAnsQuery = "SELECT director FROM movies ORDER BY RANDOM() LIMIT 3";
	    		wrongResults = this.mDb.rawQuery(wrongAnsQuery, null);
	    		
	    		while(wrongResults.moveToNext())
	    		{
	    			answers.add(wrongResults.getString(0));
	    		}
	    		correctAnswerIndex = randomGen.nextInt(4);
	    		answers.add(correctAnswerIndex, results2.getString(0));
	    		return new Question("Who directed the star " + results.getString(1) + " " + results.getString(2) + " in the year " + results2.getInt(1) + "?", correctAnswerIndex, answers);
	    		
	    	default:
	    		return null;
	    	}

	    }
}
