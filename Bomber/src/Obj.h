/*
 * Obj.h
 *
 *  Created on: Dec 29, 2013
 *      Author: Alisson Oliveira
 */

#ifndef OBJ_H_
#define OBJ_H_

#include<GL/gl.h>
#include<GL/glu.h>


// Classe Base para todos os objetos.
class Obj3D {
	static GLUquadricObj *Quadro;    //Quadric Objeto
	GLfloat x, y, z, angle, angleX, angleY, angleZ;
	GLboolean pendingUpdate;
public:
	virtual ~Obj3D(){}
	virtual GLboolean isPendingUpdate();
	virtual GLvoid update();
	virtual GLvoid draw();
	virtual GLvoid mover(GLfloat x, GLfloat y, GLfloat z);
};

GLUquadricObj* Obj3D::Quadro = NULL;

class Camera : Obj3D {

};

class Luz : Obj3D {

};

class Bomba : Obj3D {

};

class Character : Obj3D {

};

class BomberMan : Character {

};

#endif /* OBJ_H_ */
