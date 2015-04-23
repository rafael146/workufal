#include "camera.h"

Camera::Camera(){}
Camera::Camera(GLdouble eyeX, GLdouble eyeY,GLdouble eyeZ,GLdouble centerX,GLdouble centerY,GLdouble centerZ,GLdouble upX,GLdouble upY,GLdouble upZ
){
	this->eyeX=eyeX;
	this->eyeY=eyeY;
	this->eyeZ=eyeZ;
	this->centerX=centerX;
	this->centerY=centerY;
	this->centerZ=centerZ;
	this->upX=upX;
	this->upY=upY;
	this->upZ=upZ;
}
GLdouble  Camera::getAngle() {
		return angle;
	}

void Camera::setAngle(GLdouble angle) {
		this->angle = angle;
	}

GLdouble Camera::getAspect() {
		return aspect;
	}

void Camera::setAspect(GLdouble aspect) {
		this->aspect = aspect;
	}

GLdouble Camera::getCenterX(){
		return centerX;
	}

void Camera::setCenterX(GLdouble centerX) {
		this->centerX = centerX;
		update();
	}

GLdouble Camera::getCenterY() {
		return centerY;
	}

void Camera::setCenterY(GLdouble centerY) {
		this->centerY = centerY;
		update();
	}

GLdouble Camera::getCenterZ() {
		return centerZ;
	}

void Camera::setCenterZ(GLdouble centerZ) {
		this->centerZ = centerZ;
		update();
	}

GLdouble Camera::getEyeX()  {
		return eyeX;
	}

void Camera::setEyeX(GLdouble eyeX) {
		this->eyeX = eyeX;
		update();
	}

GLdouble Camera::getEyeY() {
		return eyeY;
	}

void Camera::setEyeY(GLdouble eyeY) {
		this->eyeY = eyeY;
		update();
	}

GLdouble Camera::getEyeZ()  {
		return eyeZ;
	}

void Camera::setEyeZ(GLdouble eyeZ) {
		this->eyeZ = eyeZ;
		update();
	}

GLdouble Camera::getUpX()  {
		return upX;
	}

void Camera::setUpX(GLdouble upX) {
		this->upX = upX;
		update();
	}

GLdouble Camera::getUpY()  {
		return upY;
	}

void Camera::setUpY(GLdouble upY) {
		this->upY = upY;
		update();
	}

GLdouble Camera::getUpZ()  {
		return upZ;
	}

void Camera::setUpZ(GLdouble upZ) {
		this->upZ = upZ;
		update();
	}

void Camera::update(){
	       glMatrixMode(GL_PROJECTION);
	       glLoadIdentity();
	       gluPerspective(45,aspect,0.1,80);
	       glMatrixMode(GL_MODELVIEW);
	       glLoadIdentity();
	       gluLookAt(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
	       glutPostRedisplay();
	}


