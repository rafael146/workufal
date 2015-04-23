#include <GL/glut.h>
#include <SDL/SDL.h>
#include <SDL/SDL_audio.h>
#include <SDL/SDL_mixer.h>
#include <iostream>
#include <cstdlib>//para o random da comida
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string>



#include "snake.h"
#include "camera.h"
#include "textura.h"

Snake snake(0,0);
Camera camera(0,-8,12,0,0,0,0,0,1);
//variaveis de texturas
Textura *chao;
Textura *cubo;

//variaveis da musica
SDL_Surface *screen = NULL;
Mix_Music *som = NULL;


//Variavel da comida
static GLfloat xComida = (rand() % 8)-4;
static GLfloat yComida = (rand() % 8)-4;
//variavel do cubo
static GLfloat movex=-4.5f;
static GLfloat movey=-4.5f;
void desenha_cubo() {

        static GLfloat vertice = 0.5f;

	glBegin ( GL_QUADS );

	        // Face frontal

		glTexCoord2f(0.0f, 0.0f); glVertex3f(-vertice, -vertice,  vertice);
		glTexCoord2f(1.0f, 0.0f); glVertex3f( vertice, -vertice,  vertice);
		glTexCoord2f(1.0f, 1.0f); glVertex3f( vertice,  vertice,  vertice);
		glTexCoord2f(0.0f, 1.0f); glVertex3f(-vertice,  vertice,  vertice);

		// Face posterior

		glTexCoord2f(1.0f, 0.0f); glVertex3f(-vertice, -vertice, -vertice);
		glTexCoord2f(1.0f, 1.0f); glVertex3f(-vertice,  vertice, -vertice);
		glTexCoord2f(0.0f, 1.0f); glVertex3f( vertice,  vertice, -vertice);
		glTexCoord2f(0.0f, 0.0f); glVertex3f( vertice, -vertice, -vertice);

		// Face superior

		glTexCoord2f(0.0f, 1.0f); glVertex3f(-vertice,  vertice, -vertice);
		glTexCoord2f(0.0f, 0.0f); glVertex3f(-vertice,  vertice,  vertice);
		glTexCoord2f(1.0f, 0.0f); glVertex3f( vertice,  vertice,  vertice);
		glTexCoord2f(1.0f, 1.0f); glVertex3f( vertice,  vertice, -vertice);
		// Face inferior

		glTexCoord2f(1.0f, 1.0f); glVertex3f(-vertice, -vertice, -vertice);
		glTexCoord2f(0.0f, 1.0f); glVertex3f( vertice, -vertice, -vertice);
		glTexCoord2f(0.0f, 0.0f); glVertex3f( vertice, -vertice,  vertice);
		glTexCoord2f(1.0f, 0.0f); glVertex3f(-vertice, -vertice,  vertice);

		// Face lateral direita

		glTexCoord2f(1.0f, 0.0f); glVertex3f( vertice, -vertice, -vertice);
		glTexCoord2f(1.0f, 1.0f); glVertex3f( vertice,  vertice, -vertice);
		glTexCoord2f(0.0f, 1.0f); glVertex3f( vertice,  vertice,  vertice);
		glTexCoord2f(0.0f, 0.0f); glVertex3f( vertice, -vertice,  vertice);

		// Face lateral esquerda

		glTexCoord2f(0.0f, 0.0f); glVertex3f(-vertice, -vertice, -vertice);
		glTexCoord2f(1.0f, 0.0f); glVertex3f(-vertice, -vertice,  vertice);
		glTexCoord2f(1.0f, 1.0f); glVertex3f(-vertice,  vertice,  vertice);
		glTexCoord2f(0.0f, 1.0f); glVertex3f(-vertice,  vertice, -vertice);
	glEnd();
}

//Tratamento de dimenções de janela
void TamanhoJanela(int w, int h) {
	GLfloat fAspect;

	// Previne divisão por 0
	if(h == 0)
		h = 1;
	// Viewport de acordo com as dimenssoes da janela
	glViewport(0, 0, w, h);
	fAspect = (GLfloat)w/(GLfloat)h;
	camera.setAspect(fAspect);
	camera.update();

}


void Inicializa (void)
{
	//LoadGLTextures();
	GLfloat luzAmbiente[4]={0.2,0.2,0.2,1.0};
	GLfloat luzDifusa[4]={0.7,0.7,0.7,1.0};	   // "cor"
	GLfloat luzEspecular[4]={1.0, 1.0, 1.0, 1.0};// "brilho"
	GLfloat posicaoLuz[4]={0.0, 50.0, 50.0, 1.0};

	// Capacidade de brilho do material
	GLfloat especularidade[4]={1.0,1.0,1.0,1.0};
	GLint especMaterial = 90;
	glPixelStorei (GL_UNPACK_ALIGNMENT, 1 );
	chao=new Textura("chao.png");
	cubo=new Textura("terra.png");

	glEnable(GL_TEXTURE_2D);

	// Especifica que a cor de fundo da janela será preta
	glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

	// Habilita o modelo de colorização de Gouraud
	glShadeModel(GL_SMOOTH);

	// Define a refletância do material
	glMaterialfv(GL_FRONT,GL_SPECULAR, especularidade);
	// Define a concentração do brilho
	glMateriali(GL_FRONT,GL_SHININESS,especMaterial);

	// Ativa o uso da luz ambiente
	glLightModelfv(GL_LIGHT_MODEL_AMBIENT, luzAmbiente);

	// Define os parâmetros da luz de número 0
	glLightfv(GL_LIGHT0, GL_AMBIENT, luzAmbiente);
	glLightfv(GL_LIGHT0, GL_DIFFUSE, luzDifusa );
	glLightfv(GL_LIGHT0, GL_SPECULAR, luzEspecular );
	glLightfv(GL_LIGHT0, GL_POSITION, posicaoLuz );

	// Habilita a definição da cor do material a partir da cor corrente
	glEnable(GL_COLOR_MATERIAL);
	//Habilita o uso de iluminação
	glEnable(GL_LIGHTING);
	// Habilita a luz de número 0
	glEnable(GL_LIGHT0);
	// Habilita o depth-buffering
	glEnable(GL_DEPTH_TEST);
}

//Entradas do Teclado (Movimentação da Camera)
void TeclasEspeciais(int key, int x, int y) {

	if (key == GLUT_KEY_F1)
		glutFullScreen();

	if (key == GLUT_KEY_F2)
		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

	if (key == GLUT_KEY_F3)
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

	if (key == GLUT_KEY_PAGE_UP)
		camera.setEyeY(camera.getEyeY()+0.5);

	if (key == GLUT_KEY_PAGE_DOWN)
		camera.setEyeY(camera.getEyeY()-0.5);

	//	MOVIMENTACAO DA COBRA PELO TECLADO
	if(key == GLUT_KEY_UP){
		snake.up();
	}
	if(key == GLUT_KEY_DOWN){
		snake.down();
	}
	if(key == GLUT_KEY_LEFT){
		snake.left();
	}
	if(key == GLUT_KEY_RIGHT){
		snake.right();
	}
	//ROTACIONAR A CAMERA (IMPLEMENTAR)
	if(key == GLUT_KEY_F7)
		camera.setEyeX(camera.getEyeX()+0.5);

	if(key == GLUT_KEY_F8)
		camera.setEyeX(camera.getEyeX()-0.5);

	if(key == GLUT_KEY_F9)
		camera.setEyeZ(camera.getEyeZ()+0.5);

	if(key == GLUT_KEY_F10)
		camera.setEyeZ(camera.getEyeZ()-0.5);

	//Atualiza Janela
	glutPostRedisplay();
}

void GerenciaTeclado(unsigned char key, int x, int y) {
	if (key==27) exit(0); //Sair ao apertar ESC
	return;
}

void Desenha(void) {

	GLUquadricObj *Quadro;// Novo Quadric Objeto
	//glClearColor(0.0f, 01.0f, 0.0f, 0.0f);
	glClearDepth(1.0);
	glDepthFunc(GL_LESS);
	glEnable(GL_DEPTH_TEST);
	glShadeModel(GL_SMOOTH);
	Quadro = gluNewQuadric();//Cria novo Quadric
	gluQuadricNormals(Quadro, GLU_SMOOTH);

	// Limpa a janela com a cor corrente
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	// Salva mudanças de estado e rotações na matrix
	glPushMatrix();

		//Desenhando a snake
		Snake* temp = &snake;
		while(temp!=NULL){//risco de erro aki
			glPushMatrix();
			glColor3f((rand()%10)/10.0, (rand()%10)/10.0, (rand()%10)/10.0);
			glTranslatef(temp->getX(), temp->getY(), 0.0f);
			glRotatef(90, 1.0f, 0.0f, 0.0f);
			gluSphere(Quadro, 0.26f ,30 ,30);
			glPopMatrix();
			temp=temp->getProx();
		}

		//Desenhando a comida
		glPushMatrix();
		glColor3f(0.0f, 0.0f, 0.0f);
		glTranslatef(xComida, yComida, 0.0f);
		glRotatef(90, 1.0f, 0.0f, 0.0f);
		gluSphere(Quadro, 0.26f ,30 ,30);
		glPopMatrix();

		//Desenhando do chão

		glPushMatrix();
		glColor3f(1.0f, 1.0f, 1.0f);
		glTranslatef(0.0f, 0.0f, -0.2f);
		glRotatef(90, 0.0f, 0.0f, 0.0f);
		glEnable(GL_TEXTURE_2D);//ativando as textura do chao
		glBindTexture(GL_TEXTURE_2D, chao->texture_id);

				    glBegin(GL_QUADS);
				    glTexCoord2f(0.0f, 0.0f); glVertex3f(-4.25f, -4.25f,  0.0f);
				    glTexCoord2f(1.0f, 0.0f); glVertex3f( 4.25f, -4.25f,  0.0f);
				    glTexCoord2f(1.0f, 1.0f); glVertex3f( 4.25f,  4.25f,  0.0f);
				    glTexCoord2f(0.0f, 1.0f); glVertex3f(-4.25f,  4.25f,  0.0f);

		glEnd();
		glDisable(GL_TEXTURE_2D);
		glPopMatrix();

		glPushMatrix();
		for(int i=0;i<4;i++){
			if(i==0){
				movey=-4.75f;
				movex=-4.75f;
			}
			if(i==1){
				movey=0;
				movex=0;
			}
			if(i==2){
				movex=-9;
				movey=0;
			}
			if(i==3){
				movex=-9;
				movey=-9;
			}

			for(int j=0;j<10;j++){
				glColor3f(1.0f, 1.0f, 1.0f);
				glTranslatef(movex,movey,0);
				glRotatef(90, 0.0f, 0.0f, 0.0f);
				glEnable(GL_TEXTURE_2D);
				glBindTexture(GL_TEXTURE_2D, cubo->texture_id);
				glScalef(1.0f, 1.0f, 1.0f);
				if(i==0||i==2){
					movex=1;
					movey=0;
				}else{
					movex=0;
					movey=1;
				}
				desenha_cubo();
			}
		}
		glDisable(GL_TEXTURE_2D);

		glPopMatrix();

	glPopMatrix();

	glutSwapBuffers();
}

void Timer(int value)
{
	//mudar cordenadas da comida e fazer a cobrinha crescer
	if(snake.getX()==xComida && snake.getY()==yComida){
		snake.crescer();
		do{
			xComida = (rand() % 8)-4;
			yComida = (rand() % 8)-4;
		}while(snake.estaNasCordenadas(xComida,yComida));

	}

	//Move a snake de acordo com as entradas do teclado
	snake.andar();
	//camera.setCenterX(snake.getX());
	//camera.setCenterY(snake.getY());

	if(!snake.estaNasCordenadas(snake.getX(),snake.getY()) && (snake.getX()<4&&snake.getX()>-4.5)
			&& (snake.getY()<4&&snake.getY()>-4.5)){
		glutPostRedisplay();
		glutTimerFunc(200,Timer, 1);
	}

}
void toca()
{

      Mix_OpenAudio( 22050, MIX_DEFAULT_FORMAT, 2, 1024 );
      atexit(Mix_CloseAudio);

      som = Mix_LoadMUS("audio.mp3");
      Mix_VolumeMusic(128);
      if(som == NULL)
      {
             system("pause");
      }

      Mix_PlayMusic(som, 100 );

}


int main(int argc, char *argv[]) {

	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);
	glutInitWindowSize(1100, 700);
	glutCreateWindow("Snake");
	Inicializa();//Função de Inicialização
	glutReshapeFunc(TamanhoJanela);//Função de Ajuste de Dimensões da Janela
	glutSpecialFunc(TeclasEspeciais);//Entrada de Teclado, teclas especiais
	glutKeyboardFunc(GerenciaTeclado);//Entrada de Teclado
	glutDisplayFunc(Desenha);//Renderização da Cena
	glutTimerFunc(200, Timer, 1);//Temporizador para a animação da cobrinha

	SDL_Init (SDL_INIT_AUDIO);
	SDL_WM_SetCaption ("SDL MultiMedia Application", NULL);
	toca();

	glutMainLoop();


	  return 0;


}

