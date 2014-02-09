/*
 * Obj.cpp
 *
 *  Created on: Feb 9, 2014
 *      Author: Alisson Oliveira
 */

#include "obj.h"
#include <math.h>

GLUquadricObj* Obj3D::Quadro = NULL;


Obj3D::Obj3D() {
	pendingUpdate = false;
	y = 1.0f;
	x = 0.0f;
	z = 5.0f;
	dMove = 0.0f;
}

GLvoid Obj3D::draw() {

}

GLvoid Obj3D::setDeltaMove(GLfloat offset) {
	dMove = offset;
}

GLboolean Obj3D::isPendingUpdate() {
	return pendingUpdate;
}

GLvoid Obj3D::update() {

}

GLboolean Obj3D::isMoving() {
	return false;
}

GLvoid Obj3D::mover(GLfloat x, GLfloat y, GLfloat z) {

}

// Camera
Camera::Camera() {
	velocidade = 0.00005f;
	angle = 0.0f;
	angleX = 0.0f;
	angleY = 1.0f;
	angleZ = -1.0f;
	dAngle = 0.0f;
	mOrigem = -1;
}

GLvoid Camera::setDeltaAngle(GLfloat angle) {
	dAngle = angle;
}

GLvoid Camera::setMovimentOrigem(GLfloat x) {
	mOrigem = x;
}

GLboolean Camera::isMoving() {
	return mOrigem >= 0;
}

GLvoid Camera::update() {
	angle += dAngle;
	angleX = sin(angle);
	angleZ = -cos(angle);
	x += dMove * angleX * 0.1f;
	z += dMove * angleZ * 0.1f;
}

GLvoid Camera::calcDeltaAngle(GLfloat offset) {
	dAngle = velocidade * (offset - mOrigem);
}

GLvoid Camera::draw() {
	gluLookAt(x, y, z, x + angleX, angleY, z + angleZ, 0.0f, 1.0f, 0.0f);
}

