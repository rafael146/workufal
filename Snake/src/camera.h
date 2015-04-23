/*
 * camera.h
 *
 *  Created on: 22/02/2014
 *      Author: henrique
 */

#ifndef CAMERA_H_
#define CAMERA_H_
#include <GL/glut.h>

class Camera{
private:
	GLdouble eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ, aspect, angle;
public:
	Camera();
	Camera(GLdouble eyeX, GLdouble eyeY,GLdouble eyeZ,GLdouble centerX,GLdouble centerY,GLdouble centerZ,GLdouble upX,GLdouble upY,GLdouble upZ
);
	GLdouble getAngle();
	void setAngle(GLdouble angle);
	GLdouble getAspect();
	void setAspect(GLdouble aspect);
	GLdouble getCenterX();
	void setCenterX(GLdouble centerX);
	GLdouble getCenterY();
	void setCenterY(GLdouble centerY);
	GLdouble getCenterZ();
	void setCenterZ(GLdouble centerZ);
	GLdouble getEyeX();
	void setEyeX(GLdouble eyeX);
	GLdouble getEyeY();
	void setEyeY(GLdouble eyeY);
	GLdouble getEyeZ();
	void setEyeZ(GLdouble eyeZ);
	GLdouble getUpX();
	void setUpX(GLdouble upX);
	GLdouble getUpY();
	void setUpY(GLdouble upY);
	GLdouble getUpZ();
	void setUpZ(GLdouble upZ);
	void update();
};

#endif /* CAMERA_H_ */
