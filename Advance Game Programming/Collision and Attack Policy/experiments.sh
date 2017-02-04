#!/bin/bash

blue_wins_A=0
red_wins_A=0
blue_wins_B=0
red_wins_B=0
blue_wins_C=0
red_wins_C=0
blue_wins_D=0
red_wins_D=0
blue_wins_E=0
red_wins_E=0
blue_wins_F=0
red_wins_F=0
numUNIT=20
echo  $numUNIT


for i in {1..25} 
do
	#echo "iteration: $i"
	var="$(./simul 700 700 0 $RANDOM $numUNIT $numUNIT 0 0 1)"
	
	if [ $var = "2" ]; then
      let "red_wins_D+=1"
   fi
  	if [ $var = "0" ]; then
      let "blue_wins_D+=1"
   fi

done

echo $red_wins_D
echo $blue_wins_D

for i in {1..25} 
do
	#echo "iteration: $i"
	var="$(./simul 700 700 0 $RANDOM $numUNIT $numUNIT 2 2 1)"
	
	if [ $var = "2" ]; then
      let "red_wins_E+=1"
   fi
  	if [ $var = "0" ]; then
      let "blue_wins_E+=1"
   fi

done

echo $red_wins_E
echo $blue_wins_E


