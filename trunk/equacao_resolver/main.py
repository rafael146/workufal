#-*- coding: utf-8 -*-
# Alisson Oliveira
# Anderson Felipe
# Danylo Souza
# Marcus Aurélio

import pygtk
pygtk.require('2.0')
import gtk
import gtk.glade
from excessoes import *
from equacao import *

class Application(object):
   """
      Essa Aplicação basicamente calcula a equação quadratica de uma variável,
      embora também calcule equações do primeiro grau.

      >>> 2x²+4x+2=0
      ...A equação tem apenas uma raiz -1.0

      >>> 2x²+4x=-2
      ...A equação tem apenas uma raiz -1.0

   """

   def __init__(self):
      """
         Construtor da Classe.

         inicializa todos os campos da classe.
      """
      self.msgErr = "\n//!\\\ Equação Invalida\n" 
      self.struct = gtk.glade.XML("app.glade") 
      self.window = self.struct.get_widget("Main") 
      self.window.set_size_request(400, 200)  
      self.out = self.struct.get_widget("text") 
      self.buffer = self.out.get_buffer() 
      self.buffer.set_text("Resultados:") 

      funcs = { "on_Main_destroy" : gtk.main_quit,
                "on_e_funcao_activate" : self.calcular,
                "on_calcular_clicked": self.calcular,
                "on_e_funcao_press" : self.calcular,
                "on_sobre_activate" : self.mostrarSobre,
                "on_About_response" : self.sobreResponse
                }
      # Estabelece a conexão entre eventos e funções
      self.struct.signal_autoconnect(funcs)

      #mostre a aplicação para o úsuario
      self.window.show()

   def calcular(self, widget, arg0=None, arg1=None):
      """
         Recebe a string que representa a equação dada pelo úsuario, converte
         em uma instancia de equação e chama o método para resolver a equação.
         
      """
      if len(widget.get_text()) < 1:
         return
      self.mostrar(("calculando %s :" % widget.get_text()))

      try:
         equacao = Equacao(self, widget.get_text())
         equacao.resolve()
      except EmptyEquacaoException, e:
         self.mostrar(self.msgErr + str(e))
      except ExpressaoException, e:
         self.mostrar(str(e))
      except InvalidArgumentException, e:
         self.mostrar(e)
         self.mostrar(widget.get_text())
      except Exception, e:
         self.mostrar(self.msgErr)

   def mostrarSobre(self, widget):
      """
         Mostra a janela sobre ao úsuario.

      """
      widget.show()

   def sobreResponse(self, widget, arg0=None):
      """
         Manipula os eventos ocorrido na janela sobre.
         XXX Atualmente só implementado o fechar.
      """
      widget.hide()

   def mostrar(self, texto):
      """
         Imprime o texto na saida da aplicação.
         o texto é colocado ao final do texto já existente na saida.
      """
      self.buffer.insert(self.buffer.get_end_iter(), "\n %s" % texto)
      self.out.scroll_to_mark(self.buffer.get_mark("insert"), 0, False, 0, 0)
      
   def main(self):
      """
         Chama o método main de gtk.
      """
      gtk.main()

if __name__ == "__main__":
   aplic = Application()
   aplic.main()
