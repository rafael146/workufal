/*
 * Obj.h
 *
 *  Created on: Dec 29, 2013
 *      Author: Alisson Oliveira
 */

#ifndef OBJ_H_
#define OBJ_H_

#include<GL/gl.h>

class Obj3D {
	GLfloat x=0, y=0, z=0, angle=0, angleX=0, angleY=0, angleZ=0;
public:
	virtual ~Obj3D(){}
	virtual void update();
	virtual void draw();
	virtual void mover(GLfloat x, GLfloat y, GLfloat z);
};

class Bomba : Obj3D {

};

class Camera : Obj3D {

};

class Luz : Obj3D {

};

class Character : Obj3D {

};

class BomberMan : Character {

};

#endif /* OBJ_H_ */
