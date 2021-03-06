package edu.uci.ics.metaflix;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity
{
	public static Statistic stats;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		/*
		 * Create the databases and populate it
		 */
		stats = new Statistic(this.getApplicationContext());
		@SuppressWarnings("unused")
		DbAdapter dba = new DbAdapter(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void startQuiz(View view)
	{
		Intent intent = new Intent(MainActivity.this, QuizActivity.class);
		startActivity(intent);
	}
	public void accessStats(View view)
	{
		Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
		startActivity(intent);
	}
}
