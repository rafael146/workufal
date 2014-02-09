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
#include <GL/glut.h>


// Classe Base para todos os objetos.
class Obj3D {
	static GLUquadricObj *Quadro;    //Quadric Objeto
protected:
	GLfloat x, y, z, dMove;
	GLboolean pendingUpdate;
public:
	Obj3D();
	virtual ~Obj3D(){}
	virtual GLboolean isPendingUpdate();
	virtual GLvoid update();
	virtual GLvoid draw();
	virtual GLvoid mover(GLfloat x, GLfloat y, GLfloat z);
	virtual GLboolean isMoving();
	virtual GLvoid setDeltaMove(GLfloat offset);
};

class Camera : public Obj3D {
	GLfloat velocidade, angle, angleX, angleY, angleZ, dAngle, mOrigem;
public:
	Camera();
	virtual GLvoid draw();
	virtual GLvoid update();
	virtual GLvoid setDeltaAngle(GLfloat angle);
	virtual GLvoid calcDeltaAngle(GLfloat offset);
	virtual GLvoid setMovimentOrigem(GLfloat x);
	virtual GLboolean isMoving();
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
