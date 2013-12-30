//============================================================================
// Name        : Bomber.cpp
// Author      : Alisson Oliveira
// Version     :
// Copyright   : Your copyright notice
// Description : C++, Ansi-style
//============================================================================

#include <GL/glut.h>

//Variaveis de Rotação
static GLfloat xRot = 20.0f;
static GLfloat yRot = 0.0f;

//Variavel de Distancia da Camera
static GLfloat dist = -5.0f;

//Tratamento de dimenções de janela
void atualizaJanela(int w, int h) {
	GLfloat fAspect;

	// Previne divisão por 0
	if (h == 0)
		h = 1;
	// Viewport de acordo com as dimenssoes da janela
	glViewport(0, 0, w, h);
	fAspect = (GLfloat) w / (GLfloat) h;
	// Reseta cordenadas do Sistema
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	// Produz a perspectiva da camera
	gluPerspective(60.0, fAspect, 1.0, 40.0);
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
}

void Inicializa(void) {
	// Especifica que a cor de fundo da janela será preta
	glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
}

//Entradas do Teclado
void TeclasEspeciais(int key, int x, int y) {
	//Atualiza Janela
	glutPostRedisplay();
}

void GerenciaTeclado(unsigned char key, int x, int y) {
	glutPostRedisplay();
}


void Desenha(void) {

	GLUquadricObj *Quadro;    // Novo Quadric Objeto

	Quadro = gluNewQuadric();  //Cria novo Quadric
	gluQuadricNormals(Quadro, GLU_SMOOTH);

	// Limpa a janela com a cor corrente
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	// Salva mudanças de estado e rotações na matrix
	glPushMatrix();

	//Move os Objetos e Rotaciona de acordo com as entradas do teclado
	glTranslatef(0.0f, 0.0f, dist);
	glRotatef(xRot, 1.0f, 0.0f, 0.0f);
	glRotatef(yRot, 0.0f, 1.0f, 0.0f);

	glPushMatrix();
	glColor3f(1.0f, 1.0f, 1.0f);
	glTranslatef(0.0f, 0.2f, 0.0f);
	glRotatef(90, 1.0f, 0.0f, 0.0f);
	gluCylinder(Quadro, 1.02f, 1.02f, 0.5f, 30, 15); //quad, top, base, height, slices, stack
	glPopMatrix();

	glPopMatrix();

	// Buffer swap
	glutSwapBuffers();
}

int main(int argc, char *argv[]) {
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);
	glutInitWindowSize(800, 600);
	glutCreateWindow("BomberMan");
	Inicializa();                                      //Função de Inicialização
	glutReshapeFunc(atualizaJanela);   //Função de Ajuste de Dimensões da Janela
	glutSpecialFunc(TeclasEspeciais);     //Entrada de Teclado, teclas especiais
	glutKeyboardFunc(GerenciaTeclado);                    //Entrada de Teclado
	glutDisplayFunc(Desenha);                          //Renderização da Cena
	glutMainLoop();

	return 0;
}
