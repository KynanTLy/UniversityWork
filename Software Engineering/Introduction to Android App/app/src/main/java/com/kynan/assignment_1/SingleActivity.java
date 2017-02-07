/*
Copyright 2015 Kynan Ly

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.kynan.assignment_1;

import android.app.ActionBar;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class SingleActivity extends ActionBarActivity {

    private Handler handle = new Handler();
    private static GameController gc = new GameController();
    //http://developer.android.com/reference/android/os/Handler.html

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);
        TextView go = (TextView) findViewById(R.id.readytext);
        go.setText(gc.gameON());
        StatList.loadFromFile(this);

    }//end onCreate

    public void popMessage(String message){
        // Code based off https://www.youtube.com/watch?v=fxVeFwtIpVc //
        //Display custom popup message (takes in string to display)
        LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupmessage = layoutInflater.inflate(R.layout.pop_message,null);
        // Creates the popup window object
        final PopupWindow popupWindow = new PopupWindow(popupmessage, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT,true);
        // Enable the button in popup window
        Button but_okay = (Button)popupmessage.findViewById(R.id.okay);
        TextView pop_message = (TextView) popupmessage.findViewById(R.id.poptext);
        pop_message.setText(message);

        but_okay.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View pop_exit) {
                popupWindow.dismiss();
            }//end but-exit Onclick
        }); //end but_exit listener

        popupWindow.showAsDropDown(popupmessage, 90, 650);
    }//end popmessage

    public void beginGame(View v){
        //This starts the game
        TextView go = (TextView) findViewById(R.id.readytext);
        //Reset delay
        gc.restartDelay();
        long delay = gc.getrand();
        gc.starttime();
        handle.postDelayed(runGame, delay);
    }//end begingame

    public Runnable runGame = new Runnable() {
        @Override
        public void run() {
            //When delay is over change display to go (to show game started)
            TextView go = (TextView) findViewById(R.id.readytext);
            go.setText(gc.gameON());
        }
    };//end runGame

    public void HitGame(View v){
        //This deals with the reaction, both if you hit too early, and if the game didn't start;
        String result =   gc.endtimer();
        if (gc.saveData() == true){
            long time = gc.getReaction();
            Calculator cal = new Calculator();
            cal.addStat(time);
        }
        popMessage(result);
        TextView go = (TextView) findViewById(R.id.readytext);
        go.setText(gc.gameON());
        StatList.saveInFile(this);
    }//end Hitgame

    //http://www.tutorialspoint.com/android/android_sending_email.htm

}//end SingleActivity
