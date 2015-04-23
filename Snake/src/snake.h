/*
 * snake.h
 *
 *  Created on: 22/02/2014
 *      Author: rafael
 */

#ifndef SNAKE_H_
#define SNAKE_H_

#include <stdlib.h>
class Snake{
private:
	float x;
	float y;
	Snake *prox;
	bool is_right;
	bool is_left;
	bool is_down;
	bool is_up;
public:

	Snake(float nx, float ny);
	void crescer();

	float getX(){return x;}
	float getY(){return y;}
	Snake* getProx(){return prox;}
	void setX(float nx){x=nx;}
	void setY(float ny){y=ny;}
	void setProx(Snake *p){prox=p;}
	bool estaNasCordenadas(float x, float y);

	void permutarPosicoes(float x,float y,Snake* temp=NULL);
	void andar();
	void right();
	void up();
	void down();
	void left();
};



#endif /* SNAKE_H_ */
