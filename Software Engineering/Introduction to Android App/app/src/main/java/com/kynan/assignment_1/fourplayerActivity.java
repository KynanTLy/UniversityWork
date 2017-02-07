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
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

public class fourplayerActivity extends ActionBarActivity {

    private static MultiControl mc = new MultiControl();
    private static GameController gc = new GameController();
    private Handler handle = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourplayer);
        //Set Text for buttons
        Button self = (Button) findViewById(R.id.play1);
        Button self2 = (Button) findViewById(R.id.play2);
        Button self3 = (Button) findViewById(R.id.play3);
        Button self4 = (Button) findViewById(R.id.play4);
        self.setText(mc.getplayer1());
        self2.setText(mc.getplayer2());
        self3.setText(mc.getplayer3());
        self4.setText(mc.getplayer4());
        mc.setCurrentplayers(4);
        MStat.loadFromFile(this);
    }

    //Calculates who won and saves it
    public void player1(View v){
        mc.saveResult(mc.getCurrentplayers(), 1);
        MStat.saveInFile(this);
        popMessage("Player 1 wins!");
    }//end player1

    public void player2(View v){
        mc.saveResult(mc.getCurrentplayers(), 2);
        MStat.saveInFile(this);
        popMessage("Player 2 wins!");
    }//end player2

    public void player3(View v){
        mc.saveResult(mc.getCurrentplayers(), 3);
        MStat.saveInFile(this);
        popMessage("Player 3 wins!");
    }//end player3

    public void player4(View v){
        mc.saveResult(mc.getCurrentplayers(), 4);
        MStat.saveInFile(this);
        popMessage("Player 4 wins!");
    }//end player4

    //Custom Popup window that displays who won and then send you back to BuzzerActivity
    public void popMessage(String message){
        // Code based off https://www.youtube.com/watch?v=fxVeFwtIpVc //
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
                finish();
            }//end but-exit Onclick
        }); //end but_exit listener

        popupWindow.showAsDropDown(popupmessage, 90, 650);
    }//end popmessage
}//end fourplayer activity
