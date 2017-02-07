package ca.ualberta.cs.lonelytwitter;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import junit.framework.TestCase;

/**
 * Created by wz on 14/09/15.
 */
public class LonelyTwitterActivityTest extends ActivityInstrumentationTestCase2 {

    private String tweetText;
    private EditText bodyText;
    private View saveButton;
    private String editTweetText;
    private EditText EditbodyText;
    private Button EditsaveButton;


    public LonelyTwitterActivityTest() {
        super(ca.ualberta.cs.lonelytwitter.LonelyTwitterActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();

    }

    public void testEditTweet(){
        LonelyTwitterActivity activity = (LonelyTwitterActivity) getActivity();

       // activity.getTweets().clear();

        tweetText = "Hello!";

        bodyText = activity.getBodyText();
        activity.runOnUiThread(new Runnable() {
            public void run() {
                bodyText.setText(tweetText);
            }
        });
        //Tell program to wait for Sync
        getInstrumentation().waitForIdleSync();

        saveButton = activity.getSaveButton();
        activity.runOnUiThread(new Runnable() {
            public void run() {
                saveButton.performClick();
            }
        });
        //Tell program to wait for Sync
        getInstrumentation().waitForIdleSync();

        //Get the list of tweets from the view!
        final ListView oldTweetList = activity.getOldTweetsList();
        Tweet newestTweet = (Tweet) oldTweetList.getItemAtPosition(0);
        assertEquals(tweetText, newestTweet.getText());

        //Code from:https://developer.android.com/training/activity-testing/activity-functional-testing.html
        //Date: Oct 16
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(EditTweetActivity.class.getName(),
                        null, false);


        //assertTrue(activity.getTweets().get(0).getText().equals(tweetText));

        //Click a tweet
        activity.runOnUiThread(new Runnable() {
            public void run() {
                View v = oldTweetList.getChildAt(0);
                oldTweetList.performItemClick(v, 0, v.getId());
            }
        });
        //Tell program to wait for Sync
        getInstrumentation().waitForIdleSync();

        //Code from:https://developer.android.com/training/activity-testing/activity-functional-testing.html
        //Date: Oct 16
        // Validate that ReceiverActivity is started
        EditTweetActivity receiverActivity = (EditTweetActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                EditTweetActivity.class, receiverActivity.getClass());


        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        editTweetText = "Goodbye!";

        EditbodyText = receiverActivity.getBodyText();


        receiverActivity.runOnUiThread(new Runnable() {
            public void run() {
                EditbodyText.setText(editTweetText);
            }
        });
        //Tell program to wait for Sync
        getInstrumentation().waitForIdleSync();


        EditsaveButton = receiverActivity.getSaveButton();
        activity.runOnUiThread(new Runnable() {
            public void run() {
                EditsaveButton.performClick();
            }
        });
        //Tell program to wait for Sync
        getInstrumentation().waitForIdleSync();
        // assertNotNull(receiverActivity.getTweets().);

        //assertFalse(receiverActivity.getTweets().get(0).getText().equals(tweetText));
        //assertTrue(receiverActivity.getTweets().get(0).getText().equals(editTweetText));



       // Tweet EditTweet = (Tweet) TweetController.getInstance().getTweets().get(0);
      //  assertEquals(editTweetText, EditTweet.getText());
        //nothing

        //activity.finish();

        //  Tweet editor starts up with the correct tweet
        //  We can Edit tweet
        //  We can Push a save button for editied Tweet
        //  Modified tweet was saved
        //  Modified tweet is in tweetlist

    }



}//end lonleytwitteractivitytest