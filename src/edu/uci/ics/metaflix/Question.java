package edu.uci.ics.metaflix;

import java.util.ArrayList;

public class Question
{
	// Fields
	private String question;
	private int correctAnswerNumber;
	private ArrayList<String> answers;
	
	
	
	// Constructor
	public Question(String question, int correctAnswerNumber, ArrayList<String> answers)
	{
		this.question = question;
		this.correctAnswerNumber = correctAnswerNumber;
		this.answers = answers;
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
	public ArrayList<String> getAnswers()
	{
		return answers;
	}
}
