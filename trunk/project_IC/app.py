# *-* coding: utf-8 -*-
# Autor: Alisson Oliveira

import pygtk
pygtk.require('2.0')
import gtk
import gtk.glade
from utils import *

class App(object):
   """
      Essa Aplicação basicamente calcula a equação quadratica de uma variável, embora também calcule equações do primeiro grau.

      >>> 2x²+4x+2=0
      A equação tem apenas uma raiz -1.0

      >>> 2x²+4x=-2
      A equação tem apenas uma raiz -1.0

   """

   # Cada instancia da classe começa aqui.
   def __init__(self):
      """
         Construtor da Classe.
      """
      self.mgsErr = "//!\\\ Função Invalida\n" # definindo a mensagem de função invalida.
      self.struct = gtk.glade.XML("app.glade") # ligação com o arquivo xml gerado pelo glade.
      self.window = self.struct.get_widget("Main") # obtenha a principal janela da aplicação.
      self.window.set_size_request(400, 200)  # estabeleça o tamanho inicial da aplicação.
      self.saida = self.struct.get_widget("text") # guarde uma referencia do TextView, onde será dado as saidas da aplicação.
      self.buffer = self.saida.get_buffer() #guarde uma referencia do buffer de texto da saida da aplicação.
      self.buffer.set_text("Resultados:") # estabeleça o texto inicial da saida.

      # um mapa contendo a relação entre os eventos e as funções.
      funcs = { "on_Main_destroy" : gtk.main_quit,
                "on_e_funcao_activate": self.calculate,
                "on_calcular_clicked": self.calculate,
                "on_e_funcao_press" : self.calculate
                }
      self.struct.signal_autoconnect(funcs) # ligue o mapa de funções para os objetos da aplicação

      self.window.show() # mostre a aplicação.

   def calculate(self, widget, sub_widget=None, event=None):
      """
         Função chamada quando úsuario solicita a resposta da equação.
      """
      self.imprimir("Calculando a função %s :" % widget.get_text()) # imprima a função dada pelo úsuario
      self.separar_membros(widget.get_text()) # separe os membros

      
   def imprimir(self, texto):
      """
         Imprima o texto na saida da aplicação.
         o texto é colocado ao final do texto já existente na saida.
      """
      self.buffer.insert(self.buffer.get_end_iter(), "\n %s" % texto)

   def separar_membros(self, funcao):
      """
         Esta função separa os membros.
         Deixando no segundo membro apenas o 0 (i.e dado a função 2x²+4x=-2 apos passar por essa função seu resultado será 2x²+4x+2=0)
         
      """
      if funcao.count("=") > 1: # caso haja mais que um caractere "=" será retornado e imprimirá ao úsuario uma mensagem de erro.
         self.imprimir(self.msgErr)
         return
      elif funcao.count("=") < 1: # caso a equação seja formada apenas pelo primeiro membro concatene a função com os caracteres "=0"
         funcao += "=0"
      
      funcao = funcao.lower().replace(" ","") # coloque todas as váriaveis em minusculo e retire todos os espaços existentes na função
      membros = funcao.split("=") # separe a função em dois membros
      
      if(len(membros[1]) ==1 and membros[1].isdigit() and float(membros[1]) == 0.0): # se a equação estiver na ordem correta i.e ax²+bx+c=0
         membro = separar_coeficientes(membros[0]) # separe os coeficientes, e concatene todos cujo as váriaveis são do mesmo tipo.
         try: # tente 
            a,b,c = somar_semelhantes(membro) # somar todos os números obtidos da separação dos coeficientes
         except: # caso haja algo errado na equação será retornado um valor Nulo
            self.imprimir(self.mgsErr) # imprima o erro na saida para o úsuario
            return # retorne a função
         # se tudo ocorrer bem
         # então calcule usando a formula de Baskara formatando de um modo legivel ao úsuario e imprima o resultado na saida.
         self.imprimir(mostrarResultado(Baskara(a,b,c)))
      elif(membros[1].isdigit()): # se no segundo membro estiver apenas um número
         numero = float(membros[1])
         numero *= -1  # obtenha o inverso do número
         membros[0] += str(numero) # concatene o inverso no primeiro membro
         membro = separar_coeficientes(membros[0]) # separe os coeficientes, e concatene todos cujo as váriaveis são do mesmo tipo.
         try: # tente 
            a,b,c = somar_semelhantes(membro) # somar todos os números obtidos da separação dos coeficientes
         except: # caso haja algo errado na equação será retornado um valor Nulo
            self.imprimir(self.msgErr) # imprima o erro na saida para o úsuario
            return # retorne a função
         # se tudo ocorrer bem
         # então calcule usando a formula de Baskara formatando de um modo legivel ao úsuario e imprima o resultado na saida.
         self.imprimir(mostrarResultado(Baskara(a,b,c)))
      else: # se no segundo membro houver variaveis ou sinais
         resultado = mesclar_membros(membros) # conjugue os membros.
         try: #tente
            a,b,c = resultado # separar a lista de valores obtidos em variaveis
         except: # caso haja algo errado na equação será retornado um valor Nulo
            self.imprimir(self.msgErr) # imprima o erro na saida para o úsuario
            return # retorne a função
         # se tudo ocorrer bem
         # então calcule usando a formula de Baskara formatando de um modo legivel ao úsuario e imprima o resultado na saida.
         self.imprimir(mostrarResultado(Baskara(a,b,c)))

   def main(self):
      """
         Chama o método main de gtk.
      """
      gtk.main()

# se o arquivo vou executado diretamente
if __name__ == "__main__":
   app = App() #crie uma instancia da class App
   app.main() # chame o metodo main 
