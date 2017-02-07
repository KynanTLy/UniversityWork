package ca.ualberta.cs.lonelytwitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class EditTweetActivity extends Activity {

    private EditText bodyText;
    private Button saveButton;
    //private ArrayList<Tweet> tweets = TweetController.getInstance().getTweets();
    private static final String FILENAME = "file.sav"; // model
    private EditTweetActivity activity = this;
    private static TweetController TC = new TweetController();


    public Button getSaveButton() {
        return saveButton;
    }

    public EditText getBodyText(){
        return bodyText;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tweet);
        bodyText = (EditText) findViewById(R.id.EDITTWEET);
        saveButton = (Button) findViewById(R.id.SAVE);
        loadFromFile();

        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                String text = bodyText.getText().toString();
                System.out.println(String.valueOf(TC.getTweetArrayPosition()));
                TC.setOver(new NormalTweet(text));
                saveInFile(); // model
                finish();
                //Intent intent = new Intent(activity, LonelyTwitterActivity.class);
                //startActivity(intent);
                //tweets.get(0).setText(text);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_tweet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadFromFile() {
        try {

            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            // https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html, 2015-09-23
            Type arraylistType = new TypeToken<ArrayList<NormalTweet>>() {}.getType();
            ArrayList<Tweet> temp = gson.fromJson(in, arraylistType);
            TC.getInstance().setTweets(temp);
        } catch (FileNotFoundException e) {
            ArrayList<Tweet> tweets = TC.getInstance().getTweets();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveInFile() {
        try {
            ArrayList<Tweet> tweets = TC.getInstance().getTweets();
            FileOutputStream fos = openFileOutput(FILENAME, 0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(tweets, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }
}
