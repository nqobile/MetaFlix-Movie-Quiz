package edu.uci.ics.metaflix;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class StatisticsActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistics);
		
		// Display the correct values
		TextView overallScore = (TextView) findViewById(R.id.overallScoreValue);
		overallScore.setText(String.valueOf(Math.floor((((float)MainActivity.stats.getTotalCorrectAnswers()
					/(float)(MainActivity.stats.getTotalCorrectAnswers()+MainActivity.stats.getTotalWrongAnswers()))*100))) + "%");
		
		TextView totalQuizzesTaken = (TextView) findViewById(R.id.totalQuizzesTakenValue);
		totalQuizzesTaken.setText(String.valueOf(MainActivity.stats.getTotalNumberOfQuizzesTaken()));
		
		TextView totalCorrectAnswers = (TextView) findViewById(R.id.totalCorrectAnswersValue);
		totalCorrectAnswers.setText(String.valueOf(MainActivity.stats.getTotalCorrectAnswers()));
		
		TextView totalWrongAnswers = (TextView) findViewById(R.id.totalWrongAnswersValue);
		totalWrongAnswers.setText(String.valueOf(MainActivity.stats.getTotalWrongAnswers()));
		
		TextView averageTimePerQuestion = (TextView) findViewById(R.id.averageTimePerQuestionValue);
		averageTimePerQuestion.setText(String.valueOf(MainActivity.stats.getAverageTimePerQuestion()) + "ms");
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.statistics, menu);
		return true;
	}
    @Override
    protected void onResume()
    {
    	super.onResume();
    	MainActivity.stats.loadStats(this.getApplicationContext());
    }
}