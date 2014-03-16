package edu.uci.ics.metaflix;

public class Statistic
{
	private int totalCorrectAnswers;
	private int totalWrongAnswers;
	private long averageTimePerQuestion; // long because System.currentTimeInMillis() returns a long
	private int totalNumberOfQuizzesTaken;
	private double overallScore;
	private int totalNumberOfQuestions;
	

	
	public Statistic()
	{
		totalCorrectAnswers = 0;
		totalWrongAnswers = 0;
		averageTimePerQuestion = 0;
		totalNumberOfQuizzesTaken = 0;
		overallScore = 0;
		totalNumberOfQuestions = 0;
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
		averageTimePerQuestion = ((averageTimePerQuestion + timeInMilliseconds) / totalNumberOfQuestions);
	}
}
