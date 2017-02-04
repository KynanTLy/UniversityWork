#include "Tank.H"
#include <iostream>
#include <string.h>
using namespace std;

//Construct a Tank
Tank::Tank(Team team, const Vec2 & pos, AttackPolicy pol, bool bounce)
	:Unit(team, pos, 20, 80, 15, 4, 100){
	this->policy = pol;
	this->bounce = bounce;
}//end Constructor

//Tank attacking valid target if able
void Tank::act(World &w){
	Unit * valid;
	//Check the current attack policy and determine a target
	if(this->policy == ATTACK_WEAKEST){
		valid =  w.random_weakest_target(*this); 
	} else if (this->policy == ATTACK_CLOSEST){
		valid =   w.random_closest_target(*this); 
	} else if (this->policy == ATTACK_MOST_DANGEROUS){
		valid =   w.random_most_dangerous_target(*this); 
	} else {
		valid = 0;
	}//end if
	//Check to see if there was a target (valid != 0), if there is attack it
	if(valid){
		w.attack(*this, *valid);
	}//end if
}//end act

//Reverse x or y heading when unit crashes into wall
void Tank::collision_hook(double prev_speed, bool collisions[4]){
	//If there is no bounce return
	if(!bounce){
		current_speed = 0.0;
		return;
	}//end if

	//Check the corresponding collision to determind if x or y heading has to be reversed
	if(collisions[CollDir::RIGHT] == 1){
		this->heading.x *= -1;
	} else if(collisions[CollDir::LEFT] == 1){
		this->heading.x *= -1;
	} else if(collisions[CollDir::TOP] == 1){
		this->heading.y *= -1;
	} else if(collisions[CollDir::BOTTOM] == 1){
		this->heading.y *= -1;
	} else {
		return; 
	}//end if
	this->current_speed = prev_speed;	

}//end collision_hook

// ... implement

