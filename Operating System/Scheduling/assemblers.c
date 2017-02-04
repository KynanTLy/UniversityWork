#include <stdio.h>
#include <stdlib.h>
#include <sys/shm.h>
#include <string.h>
#include <sys/wait.h>
#include <unistd.h>
#include <pthread.h>
#include "factory.h"


const char *COLORS[] = {"AliceBlue","AntiqueWhite","Aqua","Aquamarine","Azure","Beige","Bisque","Black","BlanchedAlmond","Blue","BlueViolet","Brown","BurlyWood","CadetBlue","Chartreuse","Chocolate","Coral",
"CornflowerBlue","Cornsilk","Crimson","Cyan","DarkBlue","DarkCyan","DarkGodenRod","DarkGray","DarkGrey","DarkGreen","DarkKhaki","DarkMagenta","DarkOliveGreen","Darkorange",
"DarkOrchid","DarkRed","DarkSalmon","DarkSeaGreen","DarkSlateBlue","DarkSlateGray","DarkSlateGrey","DarkTurquoise","DarkViolet","DeepPink","DeepSkyBlue","DimGray","DimGrey",
"DodgerBlue","FireBrick","FloralWhite","ForestGreen","Fuchsia","Gainsboro","GhostWhite","God","GodenRod","Gray","Grey","Green","GreenYellow","HoneyDew","HotPink","IndianRed",
"Indigo","Ivory","Khaki","Lavender","LavenderBlush","LawnGreen","LemonChiffon","LightBlue","LightCoral","LightCyan","LightGodenRodYellow","LightGray","LightGrey","LightGreen",
"LightPink","LightSalmon","LightSeaGreen","LightSkyBlue","LightSlateGray","LightSlateGrey","LightSteelBlue","LightYellow","Lime","LimeGreen","Linen","Magenta","Maroon",
"MediumAquaMarine","MediumBlue","MediumOrchid","MediumPurple","MediumSeaGreen","MediumSlateBlue","MediumSpringGreen","MediumTurquoise","MediumVioletRed","MidnightBlue",
"MintCream","MistyRose","Moccasin","NavajoWhite","Navy","OdLace","Olive","OliveDrab","Orange","OrangeRed","Orchid","PaleGodenRod","PaleGreen","PaleTurquoise","PaleVioletRed",
"PapayaWhip","PeachPuff","Peru","Pink","Plum","PowderBlue","Purple","Red","RosyBrown","RoyalBlue","SaddleBrown","Salmon","SandyBrown","SeaGreen","SeaShell","Sienna","Silver",
"SkyBlue","SlateBlue","SlateGray","SlateGrey","Snow","SpringGreen","SteelBlue","Tan","Teal","Thistle","Tomato","Turquoise","Violet","Wheat","White","WhiteSmoke","Yellow","YellowGreen"};
#define MAXCOLORS 147

/** @brief Insert Product (A colour and product number) into assembly line
 *         Wait on signal if the assembly line is full
 *
 *  @param A pointer to the stucture queVar
 *
 *  @return void.
 */
void *insertProduct(void *arg){

	//=====Init Variables====//
	queVar *qV = (queVar *)arg;
	int amountRemain = qV->amountProduceEach;
	int proNum = 1;
	const char *randcolour = COLORS[rand() % 147];

	//===If assembler didn't finish creating all product it needs===//	
	while(amountRemain != 0){
		pthread_mutex_lock(&(qV->info->mutex));

		//===If assembly line is full wait===//
		if ((qV->del == 0 && qV->insert == ((qV->count) -1)) || (qV->del == ((qV->insert) + 1)) ){
			pthread_cond_wait(&(qV->info->fcond), &(qV->info->mutex));
		} else {

			//===Update Index===//
			if (qV->insert == -1){
				qV->insert = 0;
				qV->del = 0;
			} else if (qV->insert == qV->count -1){
				qV->insert = 0;
			} else {
				qV->insert = qV->insert + 1;
			}//end if Index

			//===Insert Product===//
			product pro;
			pro.colour = randcolour;
			pro.productNum = proNum;
			qV->queArray[qV->insert] = pro;
			proNum += 1;
			amountRemain += -1;
			
		}
		pthread_mutex_unlock(&(qV->info->mutex));
	}//end while	
	return ((void *)0);
}//end insertProduct
