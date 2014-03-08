/*
 * Obj.h
 *
 *  Created on: Dec 29, 2013
 *      Author: Alisson Oliveira
 */

#ifndef OBJ_H_
#define OBJ_H_
#include <string>
#include <math.h>
#include <GL/gl.h>
#include <GL/glu.h>
#include <GL/glut.h>
using namespace std;


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
	virtual GLvoid setPosition(GLfloat x, GLfloat y, GLfloat z);
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
	GLuint * texture_id;

public:
	Textura();
	virtual GLvoid carregar_textura(char *, int, int, int,GLenum, GLenum);
	virtual GLvoid habilitarTextura();
	virtual GLvoid add_textura(string);
	virtual GLuint getTexture(int);

};

class Character : public Obj3D {
public:
	Character();
	Character(GLfloat x, GLfloat y, GLfloat z);
	GLvoid draw();
};

class BomberMan : Character {

};

#endif /* OBJ_H_ */