package edu.uci.ics.metaflix;
/*
 * I'm planning to make this class oversee creating a quiz, maintaing the quiz stats
 * and all of the previous stats. This should act like "main" for the first part of 
 * the assignment (the quiz and the stats on the quizzes).
 */
public class QuizHandler
{
	private Statistic stats;
	private Quiz quiz;
	
	
	
	public QuizHandler()
	{
		stats = new Statistic();
		quiz = new Quiz();
	}
	
	
	
	// Returns the Statistic object which contains all of the stats required across all
	// quizzes taken.
	public Statistic getStatisticObject()
	{
		return stats;
	}
	
	// Returns the quiz.
	public Quiz getQuiz()
	{
		return quiz;
	}
}
