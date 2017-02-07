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

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class mstatActivity extends ActionBarActivity {

    private static MultiControl mc = new MultiControl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mstat);
        MStat.loadFromFile(this);
        //Set default view to 2 player buzzer
        TextView result = (TextView) findViewById(R.id.multiResult);
        TextView resultTitle = (TextView) findViewById(R.id.MTitle);
        resultTitle.setText("2 Player");
        result.setText(mc.getbuzz1() + "\n" + String.valueOf(mc.getResult().findResult(0) + "\n")
                + mc.getbuzz2() + "\n" + String.valueOf(mc.getResult().findResult(1) + "\n"));
    }//end onCreate

    public void p2(View v){
        //Select 2 player stat
        TextView result = (TextView) findViewById(R.id.multiResult);
        TextView resultTitle = (TextView) findViewById(R.id.MTitle);
        resultTitle.setText("2 Player");
        result.setText(mc.getbuzz1() + "\n" + String.valueOf(mc.getResult().findResult(0) + "\n")
                + mc.getbuzz2() + "\n" + String.valueOf(mc.getResult().findResult(1) + "\n"));
    }//end p2

    public void p3(View v){
        //Select 3 player stat
        TextView result = (TextView) findViewById(R.id.multiResult);
        TextView resultTitle = (TextView) findViewById(R.id.MTitle);
        resultTitle.setText("3 Player");
        result.setText(mc.getbuzz1() + "\n" + String.valueOf(mc.getResult().findResult(2) + "\n")
                + mc.getbuzz2() + "\n" + String.valueOf(mc.getResult().findResult(3) + "\n")
                + mc.getbuzz3() + "\n" + String.valueOf(mc.getResult().findResult(4) + "\n"));
    }//end p3

    public void p4(View v){
        //Select 4 player stat
        TextView result = (TextView) findViewById(R.id.multiResult);
        TextView resultTitle = (TextView) findViewById(R.id.MTitle);
        resultTitle.setText("4 Player");
        result.setText(mc.getbuzz1() + "\n" + String.valueOf(mc.getResult().findResult(5) + "\n")
                + mc.getbuzz2() + "\n" + String.valueOf(mc.getResult().findResult(6) + "\n")
                + mc.getbuzz3() + "\n" + String.valueOf(mc.getResult().findResult(7) + "\n")
                + mc.getbuzz4() + "\n" + String.valueOf(mc.getResult().findResult(8) + "\n"));
    }//end p4

    public void clear (View v){
        //clear stat
        MStat.clearFile(this);
        //Set default view to 2 player buzzer
        TextView result = (TextView) findViewById(R.id.multiResult);
        TextView resultTitle = (TextView) findViewById(R.id.MTitle);
        resultTitle.setText("2 Player");
        result.setText(mc.getbuzz1() + "\n" + String.valueOf(mc.getResult().findResult(0) + "\n")
                + mc.getbuzz2() + "\n" + String.valueOf(mc.getResult().findResult(1) + "\n"));}

}//end mstatActivity
