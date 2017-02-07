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

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MStat {

    private static ArrayList<Integer> multiResult = new ArrayList<Integer>(9);
    private static final String FILENAME = "Multi.sav";

    //String use to set stat
    final String player1 = "\n Player 1\n";
    final String player2 = "\n Player 2\n";
    final String player3 = "\n Player 3\n";
    final String player4 = "\n Player 4\n";

    final String buzz1 = "Player 1 Buzzed: ";
    final String buzz2 = "Player 2 Buzzed: ";
    final String buzz3 = "Player 3 Buzzed: ";
    final String buzz4 = "Player 4 Buzzed: ";


    public MStat() {
        //ensure there is data in the Array
        //Data is saved as so
        //Elements 0,1 are for 2 players win (1st player = element 0, etc)
        //Elements 2,3,4 are for 3 players win (1st player = element 2, etc)
        //Elements 5,6,7,8 are for 4 players win (1st player = element 5, etc)
        for (int i = 0; i < 9; i++) {
            multiResult.add(0);
        }
    }


    //A bunch of getters and setters
    public String getPlayer1() {return player1;}

    public String getPlayer2() {return player2;}

    public String getPlayer3() {return player3;}

    public String getPlayer4() {return player4;}

    public String getBuzz4() {
        return buzz4;
    }

    public String getBuzz1() {
        return buzz1;
    }

    public String getBuzz2() {
        return buzz2;
    }

    public String getBuzz3() {
        return buzz3;
    }

    public int findResult(int position) {
        return multiResult.get(position);
    }

    public void addResult(int position, int score) {
        multiResult.set(position, score);
    }


    public static void loadFromFile(Context context) {
        //Code taken from lonelytwitter project done in lab and edited to take Arraylist<Integers>
        //ArrayList<String> tweets = new ArrayList<String>();

        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            //Following line based pff https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html
            Type player2 = new TypeToken<ArrayList<Integer>>() {
            }.getType();
            multiResult = gson.fromJson(in, player2);


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            //throw new RuntimeException(e);
            multiResult = new ArrayList<Integer>(9);
            for (int i = 0; i < 9; i++) {
                multiResult.add(0);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            throw new RuntimeException(e);
        }
    }//end loadinfile

    public static void saveInFile(Context context) {
        //Code taken from lonelytwitter project done in lab and edited to take Arraylist<Integers>
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, 0);
            //It will start of the file, aka, it will delete the context and then write new stuff
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(multiResult, writer);
            writer.flush(); //Tells it to write to file
            fos.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            throw new RuntimeException(e);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            throw new RuntimeException(e);
        }
    }//end Saveinfile

    public static void clearFile(Context context) {
        //Code taken from lonelytwitter project done in lab and edited to take Arraylist<Integers>
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, 0);
            //It will start of the file, aka, it will delete the context and then write new stuff
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            for (int i = 0; i < 9; i++){
                multiResult.set(i, 0);
            }
            gson.toJson(multiResult, writer);
            writer.flush(); //Tells it to write to file
            fos.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            throw new RuntimeException(e);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            throw new RuntimeException(e);
        }
    }//end clearfile


}//end Mstat


