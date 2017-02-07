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

public class GameController {

    private static Timer timer = null;
    final String instruc = "When you are ready to begin, press the begin button \n When the text changes from 'Ready' to 'Go' press to 'Hit Button' \n";

    //Sington
    static public Timer getTimer(){
        if (timer == null){
            timer = new Timer();
        }
        return timer;
    }

    public String gameON(){
        //Determines if the game has started
        if (getTimer().getGame() == true){
            return "Go";
        } else {
            return "Ready.";
        }
    }

    //A bunch of Getters and Setters
    public String getInstruc(){
        return instruc;
    }

    public boolean saveData(){return getTimer().getAnyReaction();}

    public void starttime(){getTimer().startTimer();}

    public String endtimer(){return getTimer().endTimer();}

    public long getrand(){ return getTimer().getRandtime();}

    public void restartDelay(){ getTimer().randomTime();}

    public long getReaction(){return getTimer().getReaction();}


}
