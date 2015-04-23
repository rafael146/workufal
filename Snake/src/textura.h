/*
 * textura.h
 *
 *  Created on: 09/03/2014
 *      Author: rafael
 */

#ifndef TEXTURA_H_
#define TEXTURA_H_

#include <string>
#include <iostream>
#include <math.h>
#include <GL/gl.h>
#include <GL/glu.h>
#include <GL/glut.h>
#include <SOIL/SOIL.h>
using namespace std;

class Textura{
public:
	GLuint texture_id;
	Textura(string);

};




#endif /* TEXTURA_H_ */
