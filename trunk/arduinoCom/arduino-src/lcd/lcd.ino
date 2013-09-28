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
  
  if (Serial.available()) {
     //read serial as ascii integer
    short opcode = Serial.read() | (Serial.read() << 8);
    Serial.write("recebido ");
    Serial.write(opcode);
    switch(opcode) {
      case 0x01:
        lcdProcess();
      
    }
  }

}

lcdProcess() {
  // clear lcd
  if(Serial.read()) {
    lcd.clear();
  }
  
  // positions
  byte x = Serial.read();
  byte y = Serial.read();
  lcd.setCursor(x, y);
  
  //text
  string st= "";
  char c;
  while((c = Serial.read(), c != '\000')) {
    st += c;
  }
  lcd.write(st);
  
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
