package edu.uci.ics.metaflix;

import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

public class QuizActivity extends Activity
{
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            // Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        
        /*
         * Start quiz
         */
        QuestionAdapter qa = new QuestionAdapter(getApplicationContext());
        int numberOfQuestionsAnswered = 0;
		while(numberOfQuestionsAnswered < 4)
		{
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
			Random random = new Random();
			int questionType = random.nextInt(8);
			generateQuestion(questionType);
		}        
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
        	case android.R.id.home:
        		NavUtils.navigateUpFromSameTask(this);
        		return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
