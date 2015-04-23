/*
 * snake.cpp
 *
 *  Created on: 22/02/2014
 *      Author: rafael
 */
#include "snake.h"
#include<stdlib.h>

Snake::Snake(float nx, float ny){
	x=nx;
	y=ny;
	prox=NULL;
	is_right = false;
	is_left = false;
	is_down = false;
	is_up = false;
}

void Snake:: crescer(){
		Snake* temp=this;
		while(temp->getProx()!=NULL){
			temp=temp->getProx();
		}
		temp->setProx(new Snake(0,0));
	}
bool Snake::estaNasCordenadas(float x, float y){
		Snake* temp=this;
		while(temp->getProx()!=NULL){
			temp=temp->getProx();
			if(temp->getX()==x && temp->getY()==y)
				return true;
		}
		return false;
	}
void Snake::permutarPosicoes(float x,float y,Snake* temp){
		if(temp==NULL) temp=this;
		float i = temp->getX();
		float j = temp->getY();
		if(temp->getProx()!=NULL) permutarPosicoes(i,j,temp->getProx());
		temp->setX(x);
		temp->setY(y);

	}
void Snake::andar(){
	if(is_up) permutarPosicoes(x,y+0.5f,NULL); //ySnake += 0.5f;
	if(is_right) permutarPosicoes(x+0.5f,y,NULL); //xSnake += 0.5f;
	if(is_left) permutarPosicoes(x-0.5f,y,NULL); //xSnake -= 0.5f;
	if(is_down) permutarPosicoes(x,y-0.5f,NULL);
}
void Snake::right(){
	if(!is_left){
		is_right = true;
		is_left = false;
		is_down = false;
		is_up = false;
	}
}
void Snake::up(){
	if(!is_down){
		is_right = false;
		is_left = false;
		is_down = false;
		is_up = true;
	}
}
void Snake::left(){
	if(!is_right){
		is_right = false;
		is_left = true;
		is_down = false;
		is_up = false;
	}
}
void Snake::down(){
	if(!is_up){
		is_right = false;
		is_left = false;
		is_down = true;
		is_up = false;
	}
}

