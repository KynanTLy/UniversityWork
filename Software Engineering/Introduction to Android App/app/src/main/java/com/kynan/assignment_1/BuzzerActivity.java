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

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;

public class BuzzerActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buzzer);
    }//end Oncreate

    //Used to start the correct amount of player activity
    public void play2(View v){
        startActivity(new Intent(BuzzerActivity.this, twoplayerActivity.class));
    }//end play2
    public void play3(View v){
        startActivity(new Intent(BuzzerActivity.this, threeplayerActivity.class));
    }//end play3
    public void play4(View v){
        startActivity(new Intent(BuzzerActivity.this, fourplayerActivity.class));
    }//end play4
}//end BuzzerActivity
