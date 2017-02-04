#include <cmath>
#include <cassert>
#include <cstdlib>
#include <iostream>
#include <math.h> 
#include "World.H"
#include "Unit.H"


using namespace std;

/* helper that you need to implement
   
 - move unit by current_speed * heading

 - units only collide with the map border
 
 - when units hit a wall they stop at the collision point (current_speed=0)
   and collision_hook is called with the previous speed and a vector of bools
   encoding the walls that were hit. This vector is used in collision_hook to
   implement bouncing by only changing the unit's heading, i.e. even when
   bouncing the unit stays at the collision location for the remainder of the
   simulation frame and only starts moving again in the following frame.

 - it is essential that units are located inside the map at all times. There
   can be no overlap. Ensure this property by clipping coordinates after
   moving.

*/

void World::move_unit(Unit &u)
{

	//Varibles to hold their next location
	double newXpos = u.pos.x + (u.heading.x * u.current_speed);
	double newYpos = u.pos.y + (u.heading.y * u.current_speed);
	double prevSpeed = u.current_speed;
	
	//Check to see if their new position would result in them crashing into a wall
	//If there is a collision, call collision_hook
	//collision = right, left, top, bottom
	if(newXpos > (width - u.radius)){
		bool collisions[4] = {1,0,0,0};	
		u.collision_hook(prevSpeed, collisions);
	} else if (newXpos < u.radius){
		bool collisions[4] = {0,1,0,0};
		u.collision_hook(prevSpeed, collisions);
	} else if (newYpos > height - u.radius ){
		bool collisions[4] = {0,0,1,0};
		u.collision_hook(prevSpeed, collisions);
	} else if (newYpos < u.radius){
		bool collisions[4] = {0,0,0,1};
		u.collision_hook(prevSpeed, collisions);
	} else {
		//If there is no collision go to next position
		u.pos.x = newXpos;
		u.pos.y = newYpos;	
		return;
	}//end if statement
}//end move_unit


// returns policy name
const char *apol2str(AttackPolicy pol)
{
  switch (pol) {
    case ATTACK_WEAKEST:
      return "attack-weakest";
    case ATTACK_CLOSEST:
      return "attack-closest";
    case ATTACK_MOST_DANGEROUS:
      return "attack-most-dangerous";
  }//end switch
  return "?";
}//end return policy name


World::~World()
{
	for (size_t i=0; i < units.size(); ++i) {			
     	delete units[i];
  	}
}//end destructor


// deducts hit points and calls attack_hook

void World::attack(Unit &attacker, Unit &attacked)
{
	//Subtrack damage from hp and then call attack_hook
 	attacked.hp = attacked.hp - attacker.damage;
	attack_hook(attacker, attacked);
}//end attack


// return a random position at which a circle of that radius fits
Vec2 World::rnd_pos(double radius) const
{
  double slack = radius * 2;

  Vec2 p((width  - 2*slack) * rnd01() + slack,
         (height - 2*slack) * rnd01() + slack);
  
  // assert circle in rectangle
  assert(p.x > radius && p.x < width - radius);
  assert(p.y > radius && p.y < height - radius);
  return p;
}//end rnd_pos


// return uniform random heading
// length of vector = 1
Vec2 World::rnd_heading() const
{
	double x = rnd01();
	//Calculate y to ensure uniform random heading
	double y = sqrt(1 - pow(x, 2));
	//Return vector of uniform random heading
  	Vec2 p(x, y);
  	return p;
}//end rnd_heading

// mirror position with respect to map mid-point
Vec2 World::mirror(const Vec2 &pos) const
{
	double newX;
	double newY;

	//Determine where the position is and calculate the mirror
	if(pos.x > (width/2)){
		double dif = pos.x - (width/2);
		newX = (width/2) - dif;
	} else {
		double dif = (width/2) - pos.x;
		newX = (width/2) + dif;
	}//end Calculate x mirror 

	if(pos.y > (height/2)){
		double dif = pos.y - (height/2);
		newY = (height/2) - dif;
	} else {
		double dif = (height/2) - pos.y ;
		newY = dif + (height/2);
	}//end Calculate y mirror

	//Return Vec2 of mirror position
  	Vec2 p(newX, newY);
	return p;
}//end mirror


// return squared distance between two unit centers
double World::distance2(const Unit &u, const Unit &v)
{
	//Calculate distance equation
  	double d = sqrt( pow((u.pos.x - v.pos.x), 2) + pow((u.pos.y - v.pos.y), 2));
	return d; 
}//end distance 2

// return true iff u can attack v, i.e. distance of u's and v's centers is
// less than u's attack distance plus v's radius
bool World::can_attack(const Unit &u, const Unit &v)
{
	double d = distance2(u, v);
	//Determine if within attack range	
	if(d < (u.attack_radius + v.radius)){
		return true;
	}//end if 
	return false;
}

// populate a vector with enemy units that can be attacked by u;
// clears vector first
void World::enemies_within_attack_range(const Unit &u,
                                        vector<Unit*> &targets) const
{
  	targets.clear();
	//Interate through all units on map
	for(size_t i = 0; i < this->units.size(); ++i){
		//Ensure they are on different teams		
		if(this->units[i]->team != u.team){
			bool valid = can_attack(u, *this->units[i] );		
			//Check to see if enemy is within range				
			if (valid){
				targets.push_back(this->units[i]);
			}//end if
		}//end if check team
	}//end for loop
}//end enemies_within_attack_range

// return a random unit that can be attacked by u with minimal hp_old value,
// or 0 if none exists

Unit *World::random_weakest_target(Unit &u) const
{
	vector<Unit*> targets;
	enemies_within_attack_range(u , targets);
	//Check to see if there is any valid enemy
	if(targets.empty()){
		return 0;
	}
	//Default set the first enemy to weakest
	Unit *weakest = targets[0];
	for(size_t i = 0; i < targets.size(); ++i){
		Unit *enemy = targets[i];
		//if the new enemy is weaker set that to weakest		
		if (enemy->hp_old < weakest->hp_old){
			weakest = enemy;
		}//end if
	}//end for loop	

	//Now that we have the weakest target remove all target that is stronger
	for(size_t i = 0; i < targets.size(); ++i){
		Unit *enemy = targets[i];
		//Pop off stronger enemy
		if(enemy->hp_old > weakest->hp_old){
			targets[i] = targets[targets.size()-1];
      			targets.pop_back();
			i--;
		}//end if
	}//end for loop	

	//Return random target from remainder
	return targets[rnd_int(targets.size())];
}//end random_weakest_target


// return a random unit that can be attacked by u with minimal distance to
// u, or 0 if none exists

Unit *World::random_closest_target(Unit &u) const
{
  	vector<Unit*> targets;
	enemies_within_attack_range(u , targets);
	//Check to see if there is any valid enemy
	if(targets.empty()){
		return 0;
	}//end if
	//Set the 1st enemy to closest
	Unit *closest = targets[0];
	for(size_t i = 0; i < targets.size(); ++i){
		Unit *enemy = targets[i];
		//If the different between the enemy is small than current switch
		if (distance2(u, *enemy) < distance2(u, *closest)){
			closest = enemy;
		}//end if
	}//end for loop	

	//Now that you are closest remove all that are further
	for(size_t i = 0; i < targets.size(); ++i){
		Unit *enemy = targets[i];
		//If you are farther away pop off stack and account for machine error in floating
		//point calculations
		if (fabs(distance2(u, *enemy) - distance2(u, *closest)) > 1e-9){
			targets[i] = targets[targets.size()-1];
      			targets.pop_back();
			i--;
		}//end if
	}//end for loop	

	//return a random target from remainder
	return targets[rnd_int(targets.size())];
}//end random_cloest_target


// return a random unit that can be attacked by u with maximal damage/hp_old
// ratio, or 0 if none exists
Unit *World::random_most_dangerous_target(Unit &u) const
{
  	vector<Unit*> targets;
	enemies_within_attack_range(u , targets);
	//Check to see if there are any valid targets
	if(targets.empty()){
		return 0;
	}//end if
	//Set the 1st enemy to most dangerous
	Unit *danger = targets[0];
	for(size_t i = 0; i < targets.size(); ++i){
		Unit *enemy = targets[i];
		//If the new enemy is more dangerous than previous switcg
		if((enemy->damage/enemy->hp_old) > (danger->damage/danger->hp_old) ){
			danger = enemy;
		}//end if
	}//end for loop	

	//Now that we have the most dangerous remove all weaker
	for(size_t i = 0; i < targets.size(); ++i){
		Unit *enemy = targets[i];
		//If you are weaker pop you off stack
		if((enemy->damage/enemy->hp_old) < (danger->damage/danger->hp_old) ){
			targets[i] = targets[targets.size()-1];
      			targets.pop_back();
			i--;
		}//end if
	}//end for loop	

	//Random choose a target from remainder
	return targets[rnd_int(targets.size())];
}//end random_most_dangerous_target


// return score for red: 2 for win, 0 for loss, 1 for draw, -1 for game not
// over yet
int World::red_score() const
{
	//If there are no enemies left it is a tie
	if (units.empty()) {
    	return 1;
  	}//end if 

	//Set the first unit as the checking unit
	Unit * first = units[0];
  	for(size_t i = 0; i < units.size(); ++i){
		//If there is a unit from a different team game still going
		if(first->team != units[i]->team){
			return -1;
		}//end if
		//If we got through all the whole unit list check to see which side is alive
		if(i == units.size()-1){
			//If first unit is red then red wins
			if(first->team == RED){
				return 2;
			}//end if
			//Else blue wins
			return 0;
		}//end if
	}//end forloop

	return -1;
}//end red_score

