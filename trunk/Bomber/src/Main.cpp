//============================================================================
// Name        : Bomber.cpp
// Author      : Alisson Oliveira
// Version     :
// Copyright   : Your copyright notice
// Description : C++, Ansi-style
//============================================================================

#include "obj.h"
#include "utils.h"
#include <iostream>
#include <vector>
#include <SOIL/SOIL.h>

World *world;
Camera * cam = new Camera();
Luz *luz = new Luz();

GLvoid displayHandler() {

	// Clear Color and Depth Buffers
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	// Reset transformations
	glLoadIdentity();
	cam->update();

	// Set the camera
	cam->draw();
	glLightfv(luz->getLuzId(), GL_POSITION, luz->getPosition());
	world->draw();
	glutSwapBuffers();
}


GLvoid Inicializa() {
	glShadeModel(GL_SMOOTH);

	glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

	glEnable(GL_LIGHTING);
	glEnable(GL_COLOR_MATERIAL);
	glEnable(GL_DEPTH_TEST);

	glEnable(GL_TEXTURE_2D);

	luz->enable(true);

	luz->setPosition(20, 75, 10);
	luz->configure();

	//todo isso deve ser dos materiais não da luz
	luz->propriedadesDeMaterias();

	glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

	world = new World();
	//initCharacters();
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
		cam->setDeltaMove(1.1f);
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
	switch (key) {
	case 'w':
	case 'W':
		cam->setDeltaMove(0.5f);
		break;
	case 's':
	case 'S':
		cam->setDeltaMove(-0.5f);
		break;

	case 'a':
	case 'A':
		cam->setMovimentOrigem(-2);
		break;

	}
	glutPostRedisplay();
	std::cout << " pressed key " << key << " code " << (int) key << std::endl;
}

void keyboardUpHandler(unsigned char key, int x, int y) {
	switch (key) {
	case 'w':
	case 'W':
	case 's':
	case 'S':
		cam->setDeltaMove(0);
		break;

	}
	glutPostRedisplay();
}

/*
 button:
 GLUT_LEFT_BUTTON
 GLUT_MIDDLE_BUTTON
 GLUT_RIGHT_BUTTON
 glutKeyboardUpFunc
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
	glutSpecialUpFunc(specialUpHanler); // função de teclado teclas especiais solta
	// here are the new entries
	// 0 to enable repeat
	glutIgnoreKeyRepeat(1);
	glutSpecialUpFunc(specialUpHanler);
	glutKeyboardUpFunc(keyboardUpHandler);

	glutMainLoop();

	return 0;
}
