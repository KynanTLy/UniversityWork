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

import java.util.Random;

public class Timer {
    //Timer Stats
    private long startTime;
    private long endTime;
    private long randtime;
    private long delay;
    private long reaction;
    //Game Stats
    private boolean anyReaction;
    private boolean game;

    //Getters
    public boolean getGame() {return game;}

    public boolean getAnyReaction(){return anyReaction;}

    public long getRandtime() {return randtime;}

    public long getReaction() {return reaction;}

    public void startTimer(){
        //Changes game stats to start game and start timer
        game = true;
        anyReaction = false;
        startTime = System.currentTimeMillis();
        delay = startDelay(startTime);
    }//end startTimer

    public String endTimer(){
        //Changes game stats and gets end timer
        //Also determinds if player is too fast, game not started as well as reaction time calculation
        if (game == true){
            endTime = System.currentTimeMillis();
            if (delay >= endTime) {
                long result = delay - endTime;
                reaction = -result;
                game = false;
                return ("You reacted early by: " + result + " ms");
            } else {
                reaction = endTime - delay;
                game = false;
                anyReaction = true;
                return ("Your reaction speed is: " + reaction + " ms");
            }
        } else {
            return "The game did not start yet!";
        }
    }//end endtimer



    public long startDelay(long startTime){
        //Calculates total delay to check from (to see if react too early)
        long end = randtime + startTime;
        return end;
    }//end startdelay

    public void randomTime(){
        //Computes random time
        Random random = new Random();
        randtime = random.nextInt(1990) + 10;
    }//end randtime


}//End Class