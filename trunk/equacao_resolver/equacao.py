#-*- coding: utf-8 -*-
# Autor: Alisson Oliveira

from main import Application
from excessoes import *
from expressao import Expressao
from utils import Bhaskara

class Equacao(object):

   def __init__(self, app, expr):
      expr = expr.replace(" ","").replace(",",".")
      self.app = app
      if expr.count("=") > 1:
         raise InvalidEquacaoException("Há mais que um '=' na equação")
      elif expr.count("=") == 0 :
         expr += "=0"
         
      term, term2 = expr.split("=")
      self.first = Expressao(term)
      self.last  = Expressao(term2)

   def resolve(self):
      print self.first
      if self.first != 0 :
         if self.last != 0:
            expressao = self.first + (-self.last)
         else : expressao = self.first
      elif self.last != 0:
         expressao = self.last
      
      if self.isValidExpressao(expressao):
         a, b, c = self.getCoeficientesFromExpressao(expressao)
         #print "a",a, "b",b, "c",c
         result = Bhaskara(a,b,c)

         self.showResult(result)
      else:
         raise InvalidEquacaoException("A equação dada não é do 2º grau:\n  "+str(expressao))

   def getCoeficientesFromExpressao(self, expr):
      a = b = c = 0
      for i in expr.variaveis:
         if i.getExpoente() == 2:
            a = i.getCoeficiente()
         elif i.getExpoente() == 1:
            b = i.getCoeficiente()
      c = expr.numero
      return a, b, c

   def isValidExpressao(expr):
      if isinstance(expr, Expressao):
         for i in expr.variaveis:
            if i.getExpoente() > 2 or i.getExpoente() < 0:
               return False
         return True
      return False

   isValidExpressao = staticmethod(isValidExpressao)

   def showResult(self, result):
      """
        Função que recebe o resultado da função Bhaskara e formata a saida.
      """
      if(len(result) == 1):
         self.app.mostrar("a equação tem apenas uma raiz %.1f\n" % (result))
      elif(len(result) == 2):
         self.app.mostrar("a equação tem duas raizes %.1f e %.1f\n" % (result))
      else:
         self.app.mostrar("a equação não tem raiz\n")
         
      
