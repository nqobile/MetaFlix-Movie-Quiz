package edu.uci.ics.metaflix;

public class Quiz
{
	private long startTime;
	
	public Quiz()
	{
		startTime = System.currentTimeMillis();
	}
	
	public boolean isThereTimeLeft()
	{
		return (System.currentTimeMillis() - startTime) <= 18000;
	}
	
	public void startQuiz()
	{
		while(isThereTimeLeft())
		{
			// TODO Proceed with quiz
		}
	}
}
