//============================================================================
// Name        : Bomber.cpp
// Author      : Alisson Oliveira
// Version     :
// Copyright   : Your copyright notice
// Description : C++, Ansi-style
//============================================================================

#include <math.h>
#include "obj.h"
#include <iostream>

Camera * cam = new Camera();

// variáveis de movimentação da Câmera
GLint xOrigin = -1;

//Tratamento de dimenções de janela
GLvoid reshapeHandler(GLint w, GLint h) {
	//previne divisão por zero
	if (h == 0)
		h = 1;

	float ratio = 1.0 * w / h;

	// Use Matriz de projeção
	glMatrixMode(GL_PROJECTION);

	// Reseta Matriz
	glLoadIdentity();

	// viewport do tamanho da janela
	glViewport(0, 0, w, h);

	// coloca a perspectiva correta
	gluPerspective(45, ratio, 1, 1000);

	// volta pra ModelView (usa matriz de model)
	glMatrixMode(GL_MODELVIEW);
}

GLvoid Inicializa() {
	// Especifica que a cor de fundo da janela será preta
	glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
}

/*
 * key:
 GLUT_KEY_F1		F1 function key
 GLUT_KEY_F2		F2 function key
 GLUT_KEY_F3		F3 function key
 GLUT_KEY_F4		F4 function key
 GLUT_KEY_F5		F5 function key
 GLUT_KEY_F6		F6 function key
 GLUT_KEY_F7		F7 function key
 GLUT_KEY_F8		F8 function key
 GLUT_KEY_F9		F9 function key
 GLUT_KEY_F10		F10 function key
 GLUT_KEY_F11		F11 function key
 GLUT_KEY_F12		F12 function key
 GLUT_KEY_LEFT		Left function key
 GLUT_KEY_RIGHT		Right function key
 GLUT_KEY_UP		Up function key
 GLUT_KEY_DOWN		Down function key
 GLUT_KEY_PAGE_UP	Page Up function key
 GLUT_KEY_PAGE_DOWN	Page Down function key
 GLUT_KEY_HOME		Home function key
 GLUT_KEY_END		End function key
 GLUT_KEY_INSERT		Insert function key
 */

//Entradas do Teclado
GLvoid specialHandler(GLint key, GLint x, GLint y) {
	switch (key) {
	case GLUT_KEY_LEFT:
		break;
	case GLUT_KEY_RIGHT:
		break;
	case GLUT_KEY_UP:
		cam->setDeltaMove(0.5f);
		break;
	case GLUT_KEY_DOWN:
		cam->setDeltaMove(-0.5f);
		break;
	}
}

void specialUpHanler(int key, int x, int y) {

	switch (key) {
	case GLUT_KEY_LEFT:
	case GLUT_KEY_RIGHT:
		break;
	case GLUT_KEY_UP:
	case GLUT_KEY_DOWN:
		cam->setDeltaMove(0);
		break;
	}
}

/*
 * int glutGetModifiers(void);
 * Modificadores
 * GLUT_ACTIVE_SHIFT – Set if either you press the SHIFT key, or Caps Lock is on. Note that if they are both on then the constant is not set.
 GLUT_ACTIVE_CTRL – Set if you press the CTRL key.
 GLUT_ACTIVE_ALT – Set if you press the ALT key.
 * Use Bitwise OR para combinações
 *
 */
GLvoid keyboardHandler(GLubyte key, GLint x, GLint y) {
	glutPostRedisplay();
}

GLvoid drawSnowMan() {

	glColor3f(1.0f, 1.0f, 1.0f);

// Draw Body
	glTranslatef(0.0f, 0.75f, 0.0f);
	glutSolidSphere(0.75f, 20, 20);

// Draw Head
	glTranslatef(0.0f, 1.0f, 0.0f);
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
}

GLvoid displayHandler() {

	// Clear Color and Depth Buffers
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	// Reset transformations
	glLoadIdentity();

	cam->update();

	// Set the camera
	cam->draw();

	// Draw ground
	glColor3f(0.9f, 0.9f, 0.9f);
	glBegin(GL_QUADS);
	glVertex3f(-100.0f, 0.0f, -100.0f);
	glVertex3f(-100.0f, 0.0f, 100.0f);
	glVertex3f(100.0f, 0.0f, 100.0f);
	glVertex3f(100.0f, 0.0f, -100.0f);
	glEnd();

	// Draw 36 SnowMen
	for (int i = -3; i < 3; i++)
		for (int j = -3; j < 3; j++) {
			glPushMatrix();
			glTranslatef(i * 10.0, 0, j * 10.0);
			drawSnowMan();
			glPopMatrix();
		}

	glutSwapBuffers();
}

/*
 button:
 GLUT_LEFT_BUTTON
 GLUT_MIDDLE_BUTTON
 GLUT_RIGHT_BUTTON

 state:
 GLUT_DOWN
 GLUT_UP
 */
GLvoid mouseHandler(GLint button, GLint state, GLint x, GLint y) {
	// começa apenas quando o botão direito for clicado
	if (button == GLUT_RIGHT_BUTTON) {

		// Quando o botão for solto
		if (state == GLUT_UP) {
			cam->setMovimentOrigem(-1);
			cam->setDeltaAngle(0);
		} else {
			cam->setMovimentOrigem(x);
		}
	}
}

GLvoid motionHandler(GLint x, GLint y) {
	// Quando o botão direito estiver clicado
	if (cam->isMoving()) {
		// atualiza deltaAngle
		cam->calcDeltaAngle(x);
	}

}

GLvoid passiveMotionHandler(GLint x, GLint y) {

}

/*
 State:
 GLUT_LEFT
 GLUT_ENTERED

 */
GLvoid entryHandler(GLint state) {

}

GLvoid idleHandler() {

}

int main(int argc, char *argv[]) {
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGBA | GLUT_DEPTH);
	glutInitWindowSize(800, 600);
	glutCreateWindow("BomberMan");

	Inicializa();                                   //Função de Inicialização
	glutReshapeFunc(reshapeHandler);   //Função de Ajuste de Dimensões da Janela
	glutSpecialFunc(specialHandler);      //Entrada de Teclado, teclas especiais
	glutKeyboardFunc(keyboardHandler);                      //Entrada de Teclado
	glutDisplayFunc(displayHandler);                      //Renderização da Cena
	glutMouseFunc(mouseHandler);                               //função de mouse
	glutMotionFunc(motionHandler);                //função de movimento de mouse
	glutPassiveMotionFunc(passiveMotionHandler); //função de movimento passivo, mouse não clicado
	glutEntryFunc(entryHandler);     //função entrada e saida do mouse da janela
	glutIdleFunc(displayHandler);                      // função executa em idle

	// here are the new entries
	// 0 to enable repeat
	glutIgnoreKeyRepeat(1);

	glutSpecialUpFunc(specialUpHanler);

	glEnable(GL_DEPTH_TEST);

	glutMainLoop();

	return 0;
}
