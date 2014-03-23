/*
 * Obj.h
 *
 *  Created on: Dec 29, 2013
 *      Author: Alisson Oliveira
 */

#ifndef OBJ_H_
#define OBJ_H_
#include <string>
#include <iostream>
#include <vector>
#include <math.h>
#include <GL/gl.h>
#include <GL/glu.h>
#include <GL/glut.h>
#include <SOIL/SOIL.h>

using namespace std;

#define PAREDE 1

class World;


// Classe Base para todos os objetos.
class Obj3D {
	static GLint nextId;
	int id;
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
	virtual GLvoid setPosition(GLfloat x, GLfloat y, GLfloat z);
	virtual GLboolean isMoving();
	virtual GLvoid setDeltaMove(GLfloat offset);
	virtual GLfloat getX();
	virtual GLfloat getZ();
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

class Luz : public Obj3D {

	GLfloat luzAmbiente[4] = {0.2,0.2,0.2,1.0};
	GLfloat luzDifusa[4] = {0.7,0.7,0.7,1.0};
	GLfloat luzEspecular[4] = {0.1, 0.1, 0.1, 0.1};
	GLfloat especularidade[4] = {1.0,1.0,1.0,1.0};
	GLint luzId = GL_LIGHT0;
	GLfloat * posicaoLuz;
	GLint especMaterial = 60;

public:
	Luz();
	virtual GLvoid configure();
	virtual GLvoid propriedadesDeMaterias();
	virtual GLfloat * getPosition();
	virtual GLint getLuzId();
	virtual GLvoid enable(GLboolean val);

};

class Bomba : Obj3D {

};

class Textura{
public:
	GLuint texture_id;
	Textura(string);
	~Textura();
};

class Character : public Obj3D {
public:
	Character();
	Character(GLfloat x, GLfloat y, GLfloat z);
	static GLvoid draws(GLint x, GLint z);
};

class BomberMan : public Character {

};


class World {
	vector<Obj3D*> *characters;
	Textura * chao;
	Textura * fundo;
	Textura * parede;
	static GLUquadricObj *Quadro;    //Quadric Objeto
public:
	World();
	GLint matriz[20][20] ={
	{ 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , },
	{ 1 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 1 , },
	{ 1 , 0 , 2 , 0 , 0 , 1 , 0 , 2 , 0 , 0 , 1 , 0 , 0 , 0 , 0 , 1 , 0 , 2 , 0 , 1 , },
	{ 1 , 0 , 0 , 0 , 1 , 0 , 0 , 0 , 1 , 0 , 0 , 0 , 1 , 0 , 0 , 0 , 1 , 0 , 0 , 1 , },
	{ 1 , 0 , 0 , 1 , 0 , 1 , 1 , 0 , 0 , 1 , 1 , 0 , 1 , 0 , 0 , 1 , 0 , 0 , 1 , 1 , },
	{ 1 , 0 , 1 , 0 , 1 , 0 , 1 , 0 , 1 , 0 , 1 , 0 , 1 , 0 , 1 , 0 , 1 , 0 , 1 , 1 , },
	{ 1 , 0 , 0 , 0 , 1 , 1 , 0 , 0 , 1 , 0 , 1 , 0 , 1 , 0 , 0 , 1 , 1 , 0 , 0 , 1 , },
	{ 1 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 2 , 0 , 0 , 0 , 2 , 0 , 1 , },
	{ 1 , 0 , 2 , 1 , 0 , 1 , 1 , 0 , 0 , 1 , 1 , 0 , 1 , 0 , 0 , 1 , 0 , 0 , 1 , 1 , },
	{ 1 , 0 , 0 , 0 , 1 , 0 , 0 , 0 , 1 , 0 , 0 , 0 , 1 , 0 , 0 , 0 , 1 , 0 , 0 , 1 , },
	{ 1 , 0 , 1 , 0 , 1 , 1 , 1 , 0 , 1 , 0 , 1 , 0 , 1 , 0 , 1 , 1 , 1 , 0 , 1 , 1 , },
	{ 1 , 0 , 0 , 0 , 0 , 0 , 0 , 2 , 0 , 0 , 0 , 2 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 1 , },
	{ 1 , 0 , 0 , 1 , 1 , 1 , 1 , 0 , 1 , 1 , 1 , 0 , 1 , 0 , 0 , 1 , 1 , 0 , 1 , 1 , },
	{ 1 , 0 , 2 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 1 , },
	{ 1 , 0 , 0 , 0 , 0 , 1 , 0 , 0 , 0 , 0 , 1 , 0 , 0 , 0 , 0 , 1 , 0 , 0 , 0 , 1 , },
	{ 1 , 0 , 1 , 0 , 1 , 0 , 1 , 0 , 1 , 0 , 1 , 0 , 1 , 0 , 1 , 0 , 1 , 0 , 1 , 1 , },
	{ 1 , 0 , 0 , 1 , 0 , 1 , 1 , 0 , 0 , 1 , 1 , 0 , 1 , 0 , 0 , 1 , 0 , 0 , 1 , 1 , },
	{ 1 , 0 , 2 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 2 , 0 , 0 , 2 , 0 , 1 , },
	{ 1 , 0 , 0 , 0 , 1 , 1 , 0 , 0 , 1 , 0 , 1 , 0 , 1 , 0 , 0 , 1 , 1 , 0 , 0 , 1 , },
	{ 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , },
	};


	GLvoid draw();
};

#endif /* OBJ_H_ */
