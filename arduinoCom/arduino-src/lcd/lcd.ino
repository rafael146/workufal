#include <LiquidCrystal.h> //Inclui a biblioteca do LCD
 
LiquidCrystal lcd(12, 11, 5, 4, 3, 2); //Configura os pinos do Arduino para se comunicar com o LCD
 
int temp; //Inicia uma variável inteira(temp), para escrever no LCD a contagem do tempo
 
void setup()
{

lcd.begin(16, 2); //Inicia o LCD com dimensões 16x2(Colunas x Linhas)
lcd.setCursor(0, 0); //Posiciona o cursor na primeira coluna(0) e na primeira linha(0) do LCD
lcd.print("Chupa coco!"); //Escreve no LCD "Olá Garagista!"
lcd.setCursor(0, 1); //Posiciona o cursor na primeira coluna(0) e na segunda linha(1) do LCD
lcd.print("kkkkkkkkk"); //Escreve no LCD "LabdeGaragem"

}
 
void loop()
{
  
  if (Serial.available() > 1) {
    int length = Serial.read() | (Serial.read() << 8);
    while(Serial.available() < length) {
    	delay(100);
    }
    int opcode = Serial.read() | (Serial.read() << 8);
    switch(opcode) {
      case 0x01:
        lcdProcess();
        break;
      
    }
  }

}

void lcdProcess() {
  // clear lcd
  if(Serial.read()) {
    lcd.clear();
  }
  
  // positions
  byte x = Serial.read();
  byte y = Serial.read();
  lcd.setCursor(x, y);
  
  //text
  char c;
  while((c = Serial.read(), c != '\000')) {
    lcd.write(c);
  }
  
  //show cursor
  if(Serial.read()) {
    lcd.cursor();
  } else {
    lcd.noCursor();
  }
  
  // blink
  if(Serial.read()) {
     lcd.blink(); 
  } else {
    lcd.noBlink();
  }
  
  // display
  if(Serial.read()) {
     lcd.display(); 
  } else {
     lcd.noDisplay(); 
  }
  
  // direction
  if(Serial.read()) {
    lcd.leftToRight();
  } else {
    lcd.rightToLeft();
  }
  
  // autoscroll
  if(Serial.read()) {
    lcd.autoscroll();
  } else {
    lcd.noAutoscroll();
  }
  
}