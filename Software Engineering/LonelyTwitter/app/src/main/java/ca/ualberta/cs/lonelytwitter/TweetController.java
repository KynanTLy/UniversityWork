package ca.ualberta.cs.lonelytwitter;

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

/**
 * Created by kynan on 10/16/15.
 */
public class TweetController {
    //private static TweetController ourInstance = new TweetController();
    private static TweetList tweets = null;
    //Singleton

    static public TweetList getInstance(){
        if (tweets == null){
            tweets = new TweetList();
        }
        return tweets;
    }

    /*public static TweetController getInstance() {
        return ourInstance;
    }*/

    //private ArrayList<Tweet> tweets = new ArrayList<Tweet>();

    public ArrayList<Tweet> getTweets() {

        return getInstance().getTweets();
    }
    //Replace whole list
    public void setTweets(int tweetsPosition) {
        getInstance().setTweetArrayPosition(tweetsPosition);
    }

    //==Del Tweet==//
    public void delTweet(int tweetPositon){
        getInstance().delete(tweetPositon);
    }

    public void addTweet(Tweet tweet){
        getInstance().add(tweet);
    }


    public int getTweetArrayPosition() {
        return getInstance().getTweetArrayPosition();
    }

    public void setOver(Tweet tweet){
        getInstance().setTweet(tweet);
    }


}
