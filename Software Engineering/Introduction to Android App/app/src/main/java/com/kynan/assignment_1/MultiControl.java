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

public class MultiControl {

    private static MStat multiResult = null;
    private int currentplayers;

    //Singleton
    static public MStat getResult(){
        if (multiResult == null){
            multiResult = new MStat();
        }
        return multiResult;
    }


    //A bunch of getters and setters to get Strings
    public int getCurrentplayers() {return currentplayers;}

    public void setCurrentplayers(int currentplayers) {this.currentplayers = currentplayers;}

    public String getplayer1(){return getResult().getPlayer1();}

    public String getplayer2(){return getResult().getPlayer2();}

    public String getplayer3(){return getResult().getPlayer3();}

    public String getplayer4(){return getResult().getPlayer4();}

    public String getbuzz1(){return getResult().getBuzz1();}

    public String getbuzz2(){return getResult().getBuzz2();}

    public String getbuzz3(){return getResult().getBuzz3();}

    public String getbuzz4(){return getResult().getBuzz4();}


    public void saveResult(int players, int winner){
        //Svae the result of the game based on number of players
        if (players == 2){
            if (winner == 1){
                //get result that are already saved and add 1 to it
                int temp = getResult().findResult(0);
                temp = temp +1;
                getResult().addResult(0, temp);
            } else {
                int temp = getResult().findResult(1);
                temp = temp + 1;
                getResult().addResult(1, temp);
            }
        }//end 2 player if

        if (players == 3){
            if (winner == 1){
                int temp = getResult().findResult(2);
                temp = temp +1;
                getResult().addResult(2, temp);
            } else if (winner == 2){
                int temp = getResult().findResult(3);
                temp = temp + 1;
                getResult().addResult(3, temp);
            } else {
                int temp = getResult().findResult(4);
                temp = temp + 1;
                getResult().addResult(4, temp);
            }
        }//end 3 player if

        if (players == 4){
            if (winner == 1){
                int temp = getResult().findResult(5);
                temp = temp +1;
                getResult().addResult(5, temp);
            } else if (winner == 2){
                int temp = getResult().findResult(6);
                temp = temp + 1;
                getResult().addResult(6, temp);
            } else if (winner == 3){
                int temp = getResult().findResult(7);
                temp = temp + 1;
                getResult().addResult(7, temp);
            } else {
                int temp = getResult().findResult(8);
                temp = temp + 1;
                getResult().addResult(8, temp);
            }
        }//end 4 player if

    }//end Multicontrol





}
