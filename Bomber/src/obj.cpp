/*
 * Obj.cpp
 *
 *  Created on: Feb 9, 2014
 *      Author: Alisson Oliveira
 */

#include "obj.h"

GLint Obj3D::nextId = 2;
GLUquadricObj* World::Quadro = gluNewQuadric();

World::World() {
	 gluQuadricNormals(Quadro, GLU_SMOOTH);
	 gluQuadricDrawStyle(Quadro, GLU_FILL);
	 gluQuadricTexture(Quadro, GL_TRUE);
	 characters = new vector<Obj3D*>();
	 characters->push_back(new Character(4,0,4));
	 characters->push_back(new Character(2.5,0,17.5));
	 characters->push_back(new Character(17,0,17));
	 characters->push_back(new Character(17,0,2));
	 characters->push_back(new Character(17,0,2));
	 grama = new Textura("chao.png");
	 fundo = new Textura("monti.png");
	 parede = new Textura("2.png");
}

GLvoid World::draw() {

	glEnable(GL_TEXTURE_2D);
	glBindTexture(GL_TEXTURE_2D, grama->texture_id);
	glColor3f(1.0f, 1.0f, 1.0f);
	glBegin(GL_QUADS);
	glTexCoord2f(0.0f, 0.0f);
	glVertex3f(0.0f, 0.0f, 0.0f);
	glTexCoord2f(0.0f, 1.0f);
	glVertex3f(0.0f, 0.0f, 40.0f);
		glTexCoord2f(1.0f, 1.0f);
		glVertex3f(40.0f, 0.0f, 40.0f);
		glTexCoord2f(1.0f, 0.0f);
		glVertex3f(40.0f, 0.0f, 0.0f);
		glEnd();
	glDisable(GL_TEXTURE_2D);


	glEnable(GL_TEXTURE_2D);
		glDisable(GL_COLOR_MATERIAL);
		glBindTexture(GL_TEXTURE_2D, fundo->texture_id);
		glPushMatrix();
			glTranslatef(20,5,20);
			glRotatef(-152, 1.0f,  0.0f, 1.0f);
			gluSphere(Quadro,30,30,30);
			glRotatef(85, 0.0f, 1.0f, 0.0f);

		glPopMatrix();
		glDisable(GL_TEXTURE_2D);
		glEnable(GL_COLOR_MATERIAL);

		glPushMatrix();
	float movex = 0.0;
	float movey = 0.0;
	for (int linha = 0; linha < 20; linha++) {

		for (int coluna = 0; coluna < 20; coluna++) {
			if (matriz[linha][coluna] == PAREDE) {
				glEnable(GL_TEXTURE_2D);
				glBindTexture(GL_TEXTURE_2D, parede->texture_id);
				glPushMatrix();
				glColor3f(1.0f, 1.0f, 1.0f);
				glScalef(1.0f, 1.5f, 1.0f);
				glTranslatef(movex, 0.0f, movey);
				glutSolidCube(2.0);
				glPopMatrix();
				glDisable(GL_TEXTURE_2D);
			} else if (matriz[linha][coluna] > PAREDE) {
				Character::draws(movex,movey);
			}
			movex = movex + 2.0f;
		}
		movey = movey + 2.0f;
		movex = 0.0;
	}
	movey = 0.0;
	glPopMatrix();

	for(unsigned int i = 0; i < characters->size(); i++) {
		characters->at(i)->draw();
	}


}

Obj3D::Obj3D() {
	pendingUpdate = false;
	y = 1.0f;
	x = 0.0f;
	z = 5.0f;
	dMove = 0.0f;
	id = nextId++;
}

GLvoid Obj3D::draw() {

}

GLfloat Obj3D::getX() {
	return x;
}

GLfloat Obj3D::getZ() {
	return z;
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
	this->x += x;
	this->y += y;
	this->z += z;

}

GLvoid Obj3D::setPosition(GLfloat x, GLfloat y, GLfloat z) {
	this->x = x;
	this->y = y;
	this->z = z;
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
	x = 2;
	z = 2;
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
	GLfloat oldX = x;
	GLfloat oldZ = z;
	angle += dAngle;
	angleX = sin(angle);
	angleZ = -cos(angle);

	x += dMove * angleX * 0.1f;
	z += dMove * angleZ * 0.1f;
}

GLvoid Camera::calcDeltaAngle(GLfloat offset) {
	dAngle = velocidade * (offset - mOrigem);
}


// x -38 z = -38
//
GLvoid Camera::draw() {
	gluLookAt(x, y, z, x + angleX, angleY, z + angleZ, 0.0f, 1.0f, 0.0f);
	//gluLookAt(0, 30, 44.9, 0, 0, 0, 0.0f, 1.0f, 0.0f);
}

Character::Character() {

}
Character::Character(GLfloat x, GLfloat y, GLfloat z) {
	this->x = x;
	this->y = y;
	this->z = z;
}

GLvoid Character::draws(GLint x, GLint z) {
	glPushMatrix();
	glTranslatef(x, 0, z);
	glColor3f(1.0f, 1.0f, 1.0f);

	// Draw Body
	glTranslatef(0.0f, 0.35f, 0.0f);
	glutSolidSphere(0.45f, 20, 20);

	// Draw Head
	glTranslatef(0.0f, 0.55f, 0.0f);
	glutSolidSphere(0.25f, 20, 20);

	// Draw Eyes
	glPushMatrix();
	glColor3f(0.0f, 0.0f, 0.0f);
	glTranslatef(0.05f, 0.10f, 0.18f);
	glutSolidSphere(0.05f, 10, 10);
	glTranslatef(-0.1f, 0.0f, 0.0f);
	glutSolidSphere(0.05f, 10, 10);
	glPopMatrix();

	// Draw Nose
	glColor3f(1.0f, 0.5f, 0.5f);
	glRotatef(0.0f, 1.0f, 0.0f, 0.0f);
	glutSolidCone(0.08f, 0.5f, 10, 2);
	glPopMatrix();
}

Luz::Luz() {
	posicaoLuz = new GLfloat[4];
	posicaoLuz[3] = 0;

}
GLvoid Luz::configure() {

	glLightModelfv(GL_LIGHT_MODEL_AMBIENT, luzAmbiente);
	glLightfv(luzId, GL_AMBIENT, luzAmbiente);
	glLightfv(luzId, GL_DIFFUSE, luzDifusa);
	glLightfv(luzId, GL_SPECULAR, luzEspecular);

}
GLvoid Luz::propriedadesDeMaterias() {

	glMaterialfv(GL_FRONT, GL_SPECULAR, especularidade);
	glMateriali(GL_FRONT, GL_SHININESS, especMaterial);

}

GLvoid Luz::enable(GLboolean val) {
	val ? glEnable(luzId) : glDisable(luzId);

}

GLfloat * Luz::getPosition() {
	posicaoLuz[0] = x;
	posicaoLuz[1] = y;
	posicaoLuz[2] = z;
	return posicaoLuz;
}

GLint Luz::getLuzId() {
	return luzId;
}

Textura::Textura(string file_name) {
	int img_width, img_height;
	unsigned char* img = SOIL_load_image(file_name.c_str(), &img_width, &img_height, NULL,0);
	glGenTextures(1, &texture_id);
	glBindTexture(GL_TEXTURE_2D, texture_id);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, img_width, img_height, 0, GL_RGB, GL_UNSIGNED_BYTE, img);
	SOIL_free_image_data(img);
}

Textura::~Textura() {
	glDeleteTextures(1, &texture_id);
}
