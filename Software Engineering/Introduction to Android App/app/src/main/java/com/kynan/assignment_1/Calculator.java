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

public class Calculator {
    private static StatList statList = null;

    //String to indicate why stat you are looking at
    final String min = "Minimum Value:";
    final String max = "Maximum Value:";
    final String avg = "Average Value:";
    final String med = "Median Value:";
    final String all = "Total ";
    final String ten = "Recent 10 ";
    final String hundred = "Recent 100 ";

    //Singleton
    static public StatList getstatList(){
        if (statList == null){
            statList = new StatList();
        }
        return statList;}


    public int Count(){
        return getstatList().getCount();
    }

    public void addStat(Long stat){
        getstatList().addStats(stat);
    }

    public Long minCal(int count){
        //Calculates min
        long empty = 0;
        if (getstatList().isEmpty() == true || count == -1){
            return empty;}
        if (count == 1){
            return getstatList().getElement(0);}
        long min = getstatList().getElement(0);
        for (int i = 0; i < count; i++) {
            long next = getstatList().getElement(i);
           if (min > next) {
                min = next;
           }
        }//end while
        return (min);
    }//end minCal

    public Long maxCal(int count){
        //Calculate long
        long empty = 0;
        if (getstatList().isEmpty() == true || count == -1){
            return empty;}
        if (count == 1){
            return getstatList().getElement(0);}
        long max = getstatList().getElement(0);
        for (int i = 0; i < count; i++) {
            long next = getstatList().getElement(i);
            if (max < next) {
                max = next;
            }
        }//end while
        return (max);
    }//end maxCal


    public Long avgCal(int count){
        //Calculate avg
        long empty = 0;
        if (getstatList().isEmpty() == true || count == -1){
            return empty;
        }
        if (count == 1){
            return getstatList().getElement(0);
        }
        long avg = 0;
        for (int i = 0; i < count; i++) {
            avg = avg + getstatList().getElement(i);
        }//end while
        avg = (avg / count);
        return avg;
    }//end avgCal

    public Long medCal(int count){
        //Calculates median
        //Used the Bubblesort;    // https://en.wikipedia.org/wiki/Bubble_sort
        long empty = 0;
        if (getstatList().isEmpty() == true || count == -1){
            return empty;}

        if (count == 1){
            return getstatList().getElement(0) ;}

        long temp;
        StatList tempstatList = getstatList();
        for(int i=0; i < count; i++){

            for(int j=1; j < count-i; j++){
                if(tempstatList.getElement(j-1) > tempstatList.getElement(j)){
                    temp=tempstatList.getElement(j-1);
                    tempstatList.setElement(j-1, tempstatList.getElement(j)); ;
                    tempstatList.setElement(j, temp);
                }
            }
        }
        return tempstatList.getElement(count / 2);
    }//end medCal


}//end Calculator
