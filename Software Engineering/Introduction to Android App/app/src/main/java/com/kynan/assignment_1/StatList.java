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
import java.util.Collection;

public class StatList {

    //Statlist arrays
    protected static ArrayList<Long> statslist = new ArrayList<Long>();
    final static ArrayList<Long> clear = new ArrayList<>();

    //Computes basic function on Arraylist

    public void addStats(long stat){statslist.add(stat);}

    public long getElement(int i){return statslist.get(i);}

    public void setElement(int i, long value){statslist.set(i, value);}

    public int getCount(){
        //Get total elements in array
        if (statslist.isEmpty()){
            return -1;
        }
        return statslist.size();
    }//end getcount

    public boolean isEmpty(){
        //Checks if array is empty
        if (statslist.isEmpty()){
            return true;
        }
        return false;
    }//end is empty

    private static final String FILENAME = "file.sav";

    public static void loadFromFile(Context context) {
        //Code taken from lonelytwitter project done in lab and edited to take Arraylist<Long>
        //ArrayList<String> tweets = new ArrayList<String>();
        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            //Following line based pff https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html
            Type player2 = new TypeToken<ArrayList<Long>>(){}.getType();
            statslist = gson.fromJson(in,player2);
            System.out.println("load");
            System.out.println(statslist);


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            //throw new RuntimeException(e);
            statslist= new ArrayList<Long>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public static void saveInFile(Context context) {
        //Code taken from lonelytwitter project done in lab and edited to take Arraylist<Long>
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, 0);
            //It will start of the file, aka, it will delete the context and then write new stuff
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(statslist, writer);
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
        //Code taken from lonelytwitter project done in lab and edited to take Arraylist<Long>
        //Code based off https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, 0);
            //It will start of the file, aka, it will delete the context and then write new stuff
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(clear, writer);
            writer.flush(); //Tells it to write to file
            fos.close();
            //fos.write(new String(date.toString() + " | " + text)
            //		.getBytes())

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
}
