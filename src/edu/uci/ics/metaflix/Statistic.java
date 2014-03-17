package edu.uci.ics.metaflix;

import android.content.Context;
import android.content.SharedPreferences;

public class Statistic
{
	private int totalCorrectAnswers;
	private int totalWrongAnswers;
	private long totalTimePerQuestion; // long because System.currentTimeInMillis() returns a long
	private long averageTimePerQuestion;
	private int totalNumberOfQuizzesTaken;
	private double overallScore;
	private int totalNumberOfQuestions;
	

	
	public Statistic(Context mContext)
	{
		loadStats(mContext);
	}
	
	public void loadStats(Context mContext)
	{
		SharedPreferences sharedPref = mContext.getSharedPreferences("edu.uci.ics.metaflix.stats", (Context.MODE_PRIVATE));
		this.totalCorrectAnswers = sharedPref.getInt("CorrectAnswers", 0);
		this.totalWrongAnswers = sharedPref.getInt("WrongAnswers", 0);
		this.averageTimePerQuestion = sharedPref.getLong("AvgTime", 0);
		this.totalNumberOfQuizzesTaken = sharedPref.getInt("TotalQuizzes", 0);
		this.totalTimePerQuestion = sharedPref.getLong("TotalTime", 0);
	}
	
	public void saveStats(Context mContext)
	{
		SharedPreferences sharedPref = mContext.getSharedPreferences("edu.uci.ics.metaflix.stats", (Context.MODE_PRIVATE));
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putInt("CorrectAnswers", totalCorrectAnswers);
		editor.putInt("WrongAnswers", totalWrongAnswers);
		editor.putLong("AvgTime", averageTimePerQuestion);
		editor.putInt("TotalQuizzes", totalNumberOfQuizzesTaken);
		editor.putLong("TotalTime", totalTimePerQuestion);
		editor.commit();		
	}
	
	
	// Returns the total number of correctly answered questions across all quizzes taken.
	public int getTotalCorrectAnswers()
	{
		return totalCorrectAnswers;
	}
	
	// Returns the total number of wrongly answered questions across all quizzes taken.
	public int getTotalWrongAnswers()
	{
		return totalWrongAnswers;
	}
	
	// Returns the average time spent on each question across all quizzes taken.
	public long getAverageTimePerQuestion()
	{
		return averageTimePerQuestion;
	}
	
	// Returns the total number of quizzes taken.
	public int getTotalNumberOfQuizzesTaken()
	{
		return totalNumberOfQuizzesTaken;
	}
	
	// Returns the overall score from every correctly answered and wrongly answered questions
	// across all quizzes taken.
	public double getOverallScore()
	{
		return overallScore;
	}
	
	// Adds one to the total number of correctly answered questions across all quizzes. Also
	// it adds one to the total number of questions answered across all quizzes taken.
	public void addToCorrectAnswerTotal()
	{
		totalCorrectAnswers++;
		totalNumberOfQuestions++;
	}
	
	// Adds one to the total number of wrongly answered questions across all quizzes. Also
	// it adds one to the total number of question answered across all quizzes taken.
	public void addToWrongAnswerTotal()
	{
		totalWrongAnswers++;
		totalNumberOfQuestions++;
	}
	
	// Adds one to the total number of quizzes taken. This is to be called in the onDestroy() method.
	// We assume that a completed quiz occurs at the end of the app's lifecycle
	public void addToTotalNumberOfQuizzesTaken()
	{
		totalNumberOfQuizzesTaken++;
	}
	
	// Calculates the total score from all of the correctly answered questions across all quizzes
	// divided by the total number of questions answered across all quizzes.
	public void calculateOverallScore()
	{
		overallScore = totalCorrectAnswers / totalNumberOfQuestions;
	}
	
	// Calculates the average time spent answering each question by dividing the total time spent
	// answered all of the questions across all quizzess divided by the total number of questions
	// answered across all quizzes.
	public void calculateAverageTimePerQuestion(long timeInMilliseconds)
	{
		totalTimePerQuestion += timeInMilliseconds;
		averageTimePerQuestion = totalTimePerQuestion / totalNumberOfQuestions;
	}
}
