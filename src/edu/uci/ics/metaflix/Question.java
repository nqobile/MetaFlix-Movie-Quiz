package edu.uci.ics.metaflix;

public class Question
{
	// Fields
	private String question;
	private int correctAnswerNumber;
	private String answerZero;
	private String answerOne;
	private String answerTwo;
	private String answerThree;
	
	
	
	// Constructor
	public Question(String question, 
			int correctAnswerNumber, 
			String answerZero, 
			String answerOne, 
			String answerTwo, 
			String answerThree)
	{
		this.question = question;
		this.correctAnswerNumber = correctAnswerNumber;
		this.answerZero = answerZero;
		this.answerOne = answerOne;
		this.answerTwo = answerTwo;
		this.answerThree = answerThree;
	}
	
	
	
	// Methods
	public String getQuestion()
	{
		return question;
	}
	public int getCorrectAnswerNumber()
	{
		return correctAnswerNumber;
	}
	public String getAnswerZero()
	{
		return answerZero;
	}
	public String getAnswerOne()
	{
		return answerOne;
	}
	public String getAnswerTwo()
	{
		return answerTwo;
	}
	public String getAnswerThree()
	{
		return answerThree;
	}
}
