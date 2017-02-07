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
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }//end onCreate

    public void Stat(View v){
        //Opens popup menu to select which type of stat you would like to look at
        popStat();
    }//end Stat

    public void MultiPlayer(View v){
        //Go to Buzz menu
        startActivity(new Intent(MainActivity.this, BuzzerActivity.class));
    }//end MultiPlayer

    public void singlePlayer(View v){
        //Display instruction for Single player
        GameController gc = new GameController();
        popinstruc(gc.getInstruc());

    }//end singlePlayer


    public void popStat(){
        //Display custom popup window to choose which type of stat you would like to see
        // https://www.youtube.com/watch?v=fxVeFwtIpVc //
        LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupstat = layoutInflater.inflate(R.layout.pop_stat,null);
        // Creates the popup window object
        final PopupWindow popupWindow = new PopupWindow(popupstat, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT,true);
        // Enable the button in popup window
        Button but_ss = (Button)popupstat.findViewById(R.id.singlestat);
        Button but_bs = (Button)popupstat.findViewById(R.id.buzzstat);

        //Changes to Single player Statistic
        but_ss.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View pop_exit) {
                startActivity(new Intent(MainActivity.this, SStatActivity.class));
                popupWindow.dismiss();
            }//end but-exit Onclick
        }); //end but_exit listener

        //Change to Multiplayer Statcistic
        but_bs.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View pop_exit) {
                startActivity(new Intent(MainActivity.this, mstatActivity.class));
                popupWindow.dismiss();
            }//end but-exit Onclick
        }); //end but_exit listener
        //Tell where to display the popup window at
        popupWindow.showAsDropDown(popupstat, 90, 350);
    }//end popStat

    //Sends email
    public void Email(View v){
        //Sends email
        String sendTo = "kynan@ualberta.ca";
        Intent emailIntent = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, sendTo);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Reaction Time Stat");
        emailIntent.putExtra(Intent.EXTRA_TEXT, convertToEmail());
        emailIntent.setType("text/plain");
        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));

        } catch (android.content.ActivityNotFoundException ex) {
        }
    }//End email

    public String convertToEmail(){
        //Convert the .sav files into text to be sent over email
        StatList.loadFromFile(this);
        MStat.loadFromFile(this);
        MultiControl mc = new MultiControl();
        Calculator cal = new Calculator();
        //Convert to readable text of multiplayer stat
        String email2 = "Multiplayer Stats: \n 2 Players:\n Player 1 Buzz Count: " + mc.getResult().findResult(0) + "\n Player 2 Buzz Count: " + mc.getResult().findResult(1) +
                "\n3 Players: Player 1 Buzz Count: " + mc.getResult().findResult(2) + "\n Player 2 Buzz Count: " + mc.getResult().findResult(3) +  "\n Player 3 Buzz Count: " + mc.getResult().findResult(4)
                + "\n4 Players: Player 1 Buzz Count: " + mc.getResult().findResult(5) + "\n Player 2 Buzz Count: " + mc.getResult().findResult(6) +  "\n Player 3 Buzz Count: " + mc.getResult().findResult(7) +
                "\n Player 4 Buzz Count: " + mc.getResult().findResult(8);

        //Convert to readable text of single player stat
        String email = "Single Player Stats: ";
        for (int i = 0; i < cal.Count(); i++){
        email = email + String.valueOf(cal.getstatList().getElement(i)) + "ms \n";
        }
        return email +email2 ;
    }//end convertToEmail

    //Create the custom popup window with instruction on how to play single player
    public void popinstruc(String instructions){
        // Code based off https://www.youtube.com/watch?v=fxVeFwtIpVc //
        LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupmessage = layoutInflater.inflate(R.layout.pop_message,null);
        // Creates the popup window object
        final PopupWindow popupWindow = new PopupWindow(popupmessage, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT,true);
        // Enable the button in popup window
        Button but_okay = (Button)popupmessage.findViewById(R.id.okay);
        TextView text = (TextView)popupmessage.findViewById(R.id.poptext);

        text.setText(instructions);

        but_okay.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View pop_exit) {
                startActivity(new Intent(MainActivity.this, SingleActivity.class));
                popupWindow.dismiss();
            }//end but-exit Onclick
        }); //end but_exit listener

        popupWindow.showAsDropDown(popupmessage,  90, 650);
    }//end popinstruc


}//endMainActivity
