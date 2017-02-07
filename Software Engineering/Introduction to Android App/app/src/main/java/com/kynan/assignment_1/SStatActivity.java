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

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SStatActivity extends ActionBarActivity {

    private static Calculator cal = new Calculator();
    private String currentmode;
    private String currenttype;
    private long   currentVal;
    private int    currentcount;
    private int    reCalMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sstat);
        StatList.loadFromFile(this);
        //Sets starting mode which is ALL average
        reCalMode = 0;
        currentcount = cal.Count();;
        currentVal = reCal(reCalMode);
        currenttype = cal.all;
        currentmode = cal.avg;
        updatetext(currenttype,currentmode,currentVal);
    }//end onCreate

    public void updatetext(String currenttype, String currentmode, long currentVal){
        //Updates the textfield with desired stat
        TextView StatVal = (TextView) findViewById(R.id.StatVal);
        TextView StatMsg = (TextView) findViewById(R.id.StatMes);

        StatMsg.setText(currenttype + currentmode);
        StatVal.setText(String.valueOf(currentVal) + "ms");
    }//end updateText

    public long reCal(int reCalMode){
        //ReCalculates desire Stat
        if (reCalMode == 0){
            return cal.avgCal(currentcount);
        } else if (reCalMode == 1){
            return cal.medCal(currentcount);
        } else if (reCalMode == 2){
            return cal.minCal(currentcount);
        } else {
            return cal.maxCal(currentcount);
        }
    }//reCal

    public void ten(View v){
        //Change display for "last 10"
        int currentcount = cal.Count();
        if (currentcount > 10){
            currentcount = 10;
        }
        currenttype = cal.ten;
        currentVal = reCal(reCalMode);
        updatetext(currenttype,currentmode,currentVal);
    }//end ten

    public void all(View v){
        //Change display for "all"
        currentcount = cal.Count();
        currenttype = cal.all;
        currentVal = reCal(reCalMode);
        updatetext(currenttype,currentmode,currentVal);

    }//end all

    public void hundred(View v){
        //Change display for "last 100"
        currentcount = cal.Count();
        if (currentcount > 100){
            currentcount = 100;
        }
        currenttype = cal.hundred;
        currentVal = reCal(reCalMode);
        updatetext(currenttype,currentmode,currentVal);
    }//end hundred

    public void min(View v){
        //Change display for new "min" of desired amount
        reCalMode = 2;
        currentVal = cal.minCal(currentcount);
        currentmode = cal.min;
        updatetext(currenttype,currentmode,currentVal);
    }//end min

    public void max(View v){
        //Change display for new "max" of desired amount
        reCalMode = 3;
        currentVal = cal.maxCal(currentcount);
        currentmode = cal.max;
        updatetext(currenttype,currentmode,currentVal);
    }//end max

    public void avg(View v){
        //Change display for new "avg" of desired amount
        reCalMode = 0;
        currentVal = cal.avgCal(currentcount);
        currentmode = cal.avg;
        updatetext(currenttype,currentmode,currentVal);
    }//end avg

    public void med(View v){
        reCalMode = 1;
        currentVal = cal.medCal(currentcount);
        currentmode = cal.med;
        updatetext(currenttype,currentmode,currentVal);
    }//end med

    public void clear(View v){
        StatList.clearFile(this);
        StatList.loadFromFile(this);
        reCalMode = 0;
        currentcount = cal.Count();;
        currentVal = reCal(reCalMode);
        currenttype = cal.all;
        currentmode = cal.avg;
        updatetext(currenttype, currentmode, currentVal);
    }//end clear


}//end SStatAcitivty
