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

	GLfloat luzAmbiente[4] = {0.2,0.2,0.2,1.0};
	GLfloat luzDifusa[4] = {0.7,0.7,0.7,1.0};
	GLfloat luzEspecular[4] = {0.1, 0.1, 0.1, 0.1};
	GLfloat especularidade[4] = {1.0,1.0,1.0,1.0};
	GLint luzId = GL_LIGHT0;
	GLfloat * posicaoLuz;

	GLint especMaterial = 60;

public:
	Luz();
	virtual GLvoid propriedadesDaLuz();
	virtual GLvoid propriedadesDeMaterias();
	virtual GLvoid paramentrosDeIluminacao();
	virtual GLfloat * getPosition();
	virtual GLint getLuzId();

};

class Bomba : Obj3D {

};

class Character : Obj3D {

};

class BomberMan : Character {

};

#endif /* OBJ_H_ */
