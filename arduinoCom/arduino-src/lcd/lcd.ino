#include <LiquidCrystal.h> //Inclui a biblioteca do LCD
 
LiquidCrystal lcd(12, 11, 5, 4, 3, 2); //Configura os pinos do Arduino para se comunicar com o LCD
 
int temp; //Inicia uma variável inteira(temp), para escrever no LCD a contagem do tempo
 
void setup()
{
Serial.begin(9600);
lcd.begin(16, 2); //Inicia o LCD com dimensões 16x2(Colunas x Linhas)
lcd.setCursor(0, 0); //Posiciona o cursor na primeira coluna(0) e na primeira linha(0) do LCD
lcd.print("Esperando..."); //Escreve no LCD "Olá Garagista!"

}
 
void loop()
{
  
  if (Serial.available() > 1) {
    Serial.println("ok");
    int length = Serial.read() | (Serial.read() << 8);
    Serial.print("received tamanho :");
    Serial.println(length);
    while(Serial.available() < length) {
      // wait all packet
      Serial.println("esperando o restante");
      delay(100);
    }
     int opcode = Serial.read() | (Serial.read() << 8);
     Serial.print("received code :");
     Serial.println(opcode);
     switch(opcode) {
       case 0x01:
         lcdProcess();
         break;
     }
     delay(1000); 
  }
  delay(500);
  Serial.println("Esperando packet");
  Serial.println(Serial.available());
}

void lcdProcess() {
  Serial.println("processing");
  
  // clear lcd
  if(Serial.read()) {
    lcd.clear();
    Serial.println("limpando lcd");
  } else {
     Serial.println("no limpando lcd"); 
  }
  
  // autoscroll
   int scroll = Serial.read();
  if(scroll) {
    Serial.println("autoscrooling");
    lcd.autoscroll();
  } else {
    Serial.println("no scrooling");
    lcd.noAutoscroll();
  }
  //show cursor
  if(Serial.read()) {
    lcd.cursor();
    Serial.println("mostrando cursor");
  } else {
    Serial.println("nao mostrar cursor");
    lcd.noCursor();
  }
  
  // blink
  if(Serial.read()) {
     lcd.blink(); 
     Serial.println("blinking");
  } else {
    Serial.println("no blinking");
    lcd.noBlink();
  }
  
  // display
  if(Serial.read()) {
    Serial.println("Displaying");
     lcd.display(); 
  } else {
    Serial.println("no displaying");
     lcd.noDisplay(); 
  }
  
  // direction
  if(Serial.read()) {
    Serial.println("left to right");
    lcd.leftToRight();
  } else {
    Serial.println("right to left");
    lcd.rightToLeft();
  }

// positions
  byte x = Serial.read();
  byte y = Serial.read();
  Serial.print("localizacao ");
  Serial.print(x);
  Serial.println(y);
  lcd.setCursor(x, y);
  
  //text
  String st = "";
  char c;
  Serial.println("pegando string");
  while((c = Serial.read()) != 0) {
     st += c;
     lcd.print(c);
     if(scroll)
       delay(350);  
  }
  Serial.read(); // ler o byte nulo;
  Serial.print(" Texto: ");
  Serial.println(st);
  
}