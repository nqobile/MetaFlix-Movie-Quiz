package edu.uci.ics.metaflix;

import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class QuizActivity extends Activity
{
	private static int numberOfQuestionsAnswered = 0;
	private static int totalNumberOfQuestionsForThisQuiz = 0;
	private static int totalNumberOfQuestionsCorrectForThisQuiz = 0;
	
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
        
        QuestionAdapter qa = new QuestionAdapter(this.getApplicationContext());
        qa = qa.open();
		takeQuiz(qa);
		// qa.close();
    }
    
    private void takeQuiz(final QuestionAdapter qa)
    {
    	if(numberOfQuestionsAnswered >= 4)
    	{
    		return;
    	}
    	
    	TextView percentage = (TextView) findViewById(R.id.percentage);
    	if(totalNumberOfQuestionsForThisQuiz != 0)
    	{
    		percentage.setText(String.valueOf(Math.floor((((float)totalNumberOfQuestionsCorrectForThisQuiz/(float)totalNumberOfQuestionsForThisQuiz)*100))) + "%");
    	}
    	
    	TextView score = (TextView) findViewById(R.id.score);
    	score.setText(String.valueOf(totalNumberOfQuestionsCorrectForThisQuiz) + "/" + String.valueOf(totalNumberOfQuestionsForThisQuiz));
    	
    	
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
		
		/*
		 * Make the question
		 */
		Random random = new Random();
		final Question q = qa.generateQuestion(random.nextInt(3));
		
		/*
		 * Change the text of the quesiton
		 * NOTE: The labels are One-based in the layout.xml file, but are Zero-based in
		 * 		 Question.java
		 */
		final RadioGroup rg = (RadioGroup) findViewById(R.id.answerRadioButtonGroup);
		
		TextView questionText = (TextView) findViewById(R.id.questionText); 
	    questionText.setText(q.getQuestion());
	    
	    final RadioButton answerOne = (RadioButton) findViewById(R.id.answerOne);
	    answerOne.setText(q.getAnswers().get(0));
	    answerOne.setChecked(false);
	    
	    final RadioButton answerTwo = (RadioButton) findViewById(R.id.answerTwo);
	    answerTwo.setText(q.getAnswers().get(1));
	    answerTwo.setChecked(false);
	    
	    final RadioButton answerThree = (RadioButton) findViewById(R.id.answerThree);
	    answerThree.setText(q.getAnswers().get(2));
	    answerThree.setChecked(false);
	    
	    final RadioButton answerFour = (RadioButton) findViewById(R.id.answerFour);
	    answerFour.setText(q.getAnswers().get(3));
	    answerFour.setChecked(false);
	    
	    // Start measuring how long it takes for the user to answer the question
	    final long timeStart = System.currentTimeMillis();
	    rg.setOnCheckedChangeListener(
	    		new OnCheckedChangeListener()
				{
					public void onCheckedChanged(RadioGroup group, int checkedId)
					{
						// Change the colors to reflect which answer was correct
					    int selectedAnswer = rg.getCheckedRadioButtonId();
					    RadioButton rb = (RadioButton) findViewById(selectedAnswer);
					    long timeForUsersResponse;
						switch(rb.getId())
					    {
					    case R.id.answerOne:
					    	/*
					    	 * If the answer view selected is the correct answer, update the statistics
					    	 * to reflect the quiz statistics
					    	 */
					    	if((q.getCorrectAnswerNumber() + 1) == 1)
					    	{
					    		MainActivity.stats.addToCorrectAnswerTotal();
					    		totalNumberOfQuestionsCorrectForThisQuiz++;
					    	}
					    	MainActivity.stats.addToWrongAnswerTotal();
					    	timeForUsersResponse = System.currentTimeMillis() - timeStart;
					    	MainActivity.stats.calculateAverageTimePerQuestion(timeForUsersResponse);
					    	
					    case R.id.answerTwo:
					    	/*
					    	 * If the answer view selected is the correct answer, update the statistics
					    	 * to reflect the quiz statistics
					    	 */
					    	if((q.getCorrectAnswerNumber() + 1) == 2)
					    	{
					    		MainActivity.stats.addToCorrectAnswerTotal();
					    		totalNumberOfQuestionsCorrectForThisQuiz++;
					    	}
					    	MainActivity.stats.addToWrongAnswerTotal();
					    	timeForUsersResponse = System.currentTimeMillis() - timeStart;
					    	MainActivity.stats.calculateAverageTimePerQuestion(timeForUsersResponse);
					    	
					    case R.id.answerThree:
					    	/*
					    	 * If the answer view selected is the correct answer, update the statistics
					    	 * to reflect the quiz statistics
					    	 */
					    	if((q.getCorrectAnswerNumber() + 1) == 3)
					    	{
					    		MainActivity.stats.addToCorrectAnswerTotal();
					    		totalNumberOfQuestionsCorrectForThisQuiz++;
					    	}
					    	MainActivity.stats.addToWrongAnswerTotal();
					    	timeForUsersResponse = System.currentTimeMillis() - timeStart;
					    	MainActivity.stats.calculateAverageTimePerQuestion(timeForUsersResponse);
					    	
					    case R.id.answerFour:
					    	/*
					    	 * If the answer view selected is the correct answer, update the statistics
					    	 * to reflect the quiz statistics
					    	 */
					    	if((q.getCorrectAnswerNumber() + 1) == 4)
					    	{
					    		MainActivity.stats.addToCorrectAnswerTotal();
					    		totalNumberOfQuestionsCorrectForThisQuiz++;
					    	}
					    	MainActivity.stats.addToWrongAnswerTotal();
					    	timeForUsersResponse = System.currentTimeMillis() - timeStart;
					    	MainActivity.stats.calculateAverageTimePerQuestion(timeForUsersResponse);
					    }
						numberOfQuestionsAnswered++;
						totalNumberOfQuestionsForThisQuiz++;
						//try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
					    takeQuiz(qa);
					}
				});
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
