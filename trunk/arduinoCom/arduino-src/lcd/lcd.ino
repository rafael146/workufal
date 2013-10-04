#include <LiquidCrystal.h> //Inclui a biblioteca do LCD
 
LiquidCrystal lcd(12, 11, 5, 4, 3, 2); //Configura os pinos do Arduino para se comunicar com o LCD
 
int temp; //Inicia uma variável inteira(temp), para escrever no LCD a contagem do tempo
 
void setup()
{
Serial.begin(9600);
lcd.begin(16, 2); //Inicia o LCD com dimensões 16x2(Colunas x Linhas)
lcd.setCursor(0, 0); //Posiciona o cursor na primeira coluna(0) e na primeira linha(0) do LCD
lcd.print("Chupa coco!"); //Escreve no LCD "Olá Garagista!"
lcd.setCursor(0, 1); //Posiciona o cursor na primeira coluna(0) e na segunda linha(1) do LCD
lcd.print("kkkkkkkkk"); //Escreve no LCD "LabdeGaragem"

}
 
void loop()
{
  
  if (Serial.available() > 1) {
    Serial.println("ok");
    int length = Serial.read() | (Serial.read() << 8);
    while(Serial.available() < length) {
      // wait all packet
    }
     int opcode = Serial.read() | (Serial.read() << 8);
     switch(opcode) {
       case 0x01:
         lcdProcess();
         break;
     }
     delay(1000); 

  }
}

void lcdProcess() {
  Serial.println("processing");
  
  // clear lcd
  if(Serial.read()) {
    lcd.clear();
    Serial.println("limpando lcd");
    delay(1000);
  } else {
     Serial.println("no limpando lcd"); 
  }
  
  // positions
  byte x = Serial.read();
  byte y = Serial.read();
  Serial.print("localizacao ");
  Serial.print(x);
  Serial.println(y);
  lcd.setCursor(x, y);
  delay(1000);
  
  //text
  String st = "";
  char c;
  Serial.println("pegando string");
  while((c = Serial.read()) != 0) {
     st += c;
  }
  Serial.print(" Texto: ");
  Serial.println(st);
  lcd.print(st);
  delay(1000);

  //show cursor
  if(Serial.read()) {
    lcd.cursor();
    Serial.println("mostrando cursor");
    delay(1000);
  } else {
    Serial.println("nao mostrar cursor");
    lcd.noCursor();
  }
  
  // blink
  if(Serial.read()) {
     lcd.blink(); 
     Serial.println("blinking");
     delay(1000);
  } else {
    Serial.println("no blinking");
    lcd.noBlink();
  }
  
  // display
  if(Serial.read()) {
    Serial.println("Displaying");
     lcd.display(); 
     delay(1000);
  } else {
    Serial.println("no displaying");
     lcd.noDisplay(); 
  }
  
  // direction
  if(Serial.read()) {
    Serial.println("left to right");
    lcd.leftToRight();
    delay(1000);
  } else {
    Serial.println("right to left");
    lcd.rightToLeft();
  }
  
  // autoscroll
  if(Serial.read() == 1) {
    Serial.println("autoscrooling");
    lcd.autoscroll();
    delay(1000);
  } else {
    Serial.println("no scrooling");
    lcd.noAutoscroll();
  }
  
}