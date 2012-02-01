# -*- coding: utf-8 -*-
# Autor: Alisson Oliveira
from variavel import *
import utils

class Expressao(object):
   """
      Classe que representa uma expressão.
   """
   def __init__(self, termo):
      """
         Construtor da classe.
      """
      self.termo = termo
      self.numero = 0.0
      self.variaveis = []
      self.organize()

   def __add__(self, val):
      """
         sobreescreve o operador '+'
      """
      if isinstance(val, (float,int,long)):
         if val == 0:
            return self
         if val < 0:
            return Expressao(self.termo+str(val))
         return Expressao(self.termo+"+"+str(val))
      elif isinstance(val, Variavel):
         if val == 0:
            return self
         if val.getCoeficiente() < 1:
            return Expressao(self.termo+str(val))
         return Expressao(self.termo+"+"+str(val))
      elif isinstance(val, Expressao):
         if val.termo.startswith("-"):
            return Expressao(self.termo+val.termo)
         return Expressao(self.termo+"+"+val.termo)
      raise TypeError("unsuported operand type(s) for +: <type 'Expressao'> and "+ str(type(val)))

   def __div__(self, val):
      """
         sobreescreve o operador '/'
      """
      if isinstance(val, (float,int,long, Variavel)):
         var = []
         num = float(self.numero) / val
         for i in self.variaveis:
            var.append(i/val)

         result = self.toString(var, num)
         return Expressao(result)
      raise NotImplementedError("unsuported operand type(s) for /: <type 'Expressao'> and "+ str(type(val)))

   def __mul__(self, val):
      """
         sobreescreve o operador "*"
      """
      if isinstance(val, (float,int,long, Variavel)):
         var = []
         num = self.numero * val
         for i in self.variaveis:
            var.append(i*val)

         result = self.toString(var, num)
         return Expressao(result)
      if isinstance(val, Expressao):
         var=[]
         num = 0
         for i in val.variaveis:
            for j in self.variaveis:
               var.append(i*j)

         if self.numero != 0:
            for i in val.variaveis:
               var.append(i*self.numero)

         if val.numero != 0:
            for i in self.variaveis:
               var.append(i*val.numero)

            if self.numero != 0:
               num = self.numero * val.numero

         result = self.toString(var, num)
         return Expressao(result)
      raise TypeError("unsuported operand type(s) for *: <type 'Expressao'> and "+ str(type(val)))
         
   def __neg__(self):
      """
         sobreescreve o operador unario "-"
      """
      var = []
      num = -self.numero
      for i in self.variaveis:
         var.append(-i)
      result = self.toString(var, num)
      return Expressao(result)

   def __ne__(self, val):
      """
         sobreescreve os operadores 'not' e '!'
      """
      if isinstance(val, Expressao):
         return not (val.variaveis == self.variaveis and self.numero == val.numero)
      if isinstance(val, (float,int,long)):
         if val == 0 and self.variaveis == [] and self.numero == 0:
            return False
      return True

   def __repr__(self):
      """
         sobreescreve a função 'repr'
      """
      return self.termo

   def __str__(self):
      """
         sobreescreve a função 'str' e 'print'
      """
      return self.termo
      
   def __sub__(self, val):
      """
         sobreescreve o operador '-'
      """
      if isinstance(val, (float,int,long)):
         if val == 0:
            return self
         if val < 0:
            val = -val
            return Expressao(self.termo+"+"+str(val))
         return Expressao(self.termo+"-"+str(val))
      if isinstance(val, Variavel):
         if val == 0:
            return self
         if val.getCoeficiente() < 0:
            val = -val
            return Expressao(self.termo+"+"+str(val))
         return Expressao(self.termo+"-"+str(val))
      if isinstance(val, Expressao):
         val = -val
         if val.termo.startswith("-"):
            return Expressao(self.termo+val.termo)
         return Expressao(self.termo+"+"+val.termo)
      raise TypeError("unsuported operand type(s) for -: <type 'Expressao'> and "+ str(type(val)))

   def organize(self):
      """
         organiza os membros da equação.
         e resolve todas as operações.
      """
      self.termo = utils.separeFromSignal(self.termo)
      result = self.termo.split(" ")
      result = self.prepare(result)
      for num in result:
         if num.count(".") > 0:
            tmp = num.split(".")
            if utils.isSignal(tmp[0][0]):
               char = tmp[0].replace(tmp[0][0],"",1)
               if char.isdigit() and tmp[1].isdigit():
                  self.numero += float(num)
                  result.insert(0,"")
                  result.remove(num)
                  continue
            elif tmp[0].isdigit() and tmp[1].isdigit():
               self.numero += float(num)
               result.insert(0,"")
               result.remove(num)
               continue
         if num.isdigit():
            self.numero += float(num)
            result.insert(0,"")
            result.remove(num)
            continue
         i = 0
         while i < len(num):
            if utils.isSignal(num[i]):
               char = num.replace(num[i], "", 1)
               if char.isdigit():
                  self.numero += float(num)
                  result.insert(0, "")
                  result.remove(num)
                  break
            i += 1

      if self.numero.is_integer():
         self.numero = int(self.numero)

      variaveis = []
      for var in result:
         if var != "":
            variaveis.append(Variavel.variavelFromStr(var))

      while 0 < len(variaveis):
         var = variaveis.pop(0)
         tmp = list(variaveis)
         for i in tmp:
            if var.isSummable(i):
               var += i
               variaveis.remove(i)
         self.variaveis.append(var)

      self.check()

      self.termo = self.toString(self.variaveis, self.numero)

   def prepare(self, termo):
      """
         resolve multiplicações e divisões da expressão.
      """
      i = 0
      while i < len(termo):
         if termo[i].startswith("*"):
            tmp = termo[i]
            tmp.replace("*","")
            tmp = Variavel.variavelFromStr(tmp)
            tmp *= Variavel.variavelFromStr(termo[i-1])
            termo.remove(termo[i])
            termo.remove(termo[i-1])
            if tmp >= 0:
               termo.insert(i-1, "+"+str(tmp))
            else: termo.insert(i-1, str(tmp))
            continue
         elif termo[i].startswith("/"):
            tmp = termo[i]
            tmp.replace("/","")
            tmp = Variavel.variavelFromStr(tmp)
            tmp = Variavel.variavelFromStr(termo[i-1]) / tmp
            termo.remove(termo[i])
            termo.remove(termo[i-1])
            if tmp >= 0:
               termo.insert(i-1, "+"+str(tmp))
            else: termo.insert(i-1, str(tmp))
            continue
         i += 1
      return termo

   @staticmethod
   def toString(var, num=0):
      """
         cria a representação em string da expressão.
      """
      term = ""
      for i in var:
         if i == 0: continue
         if i > 0 and len(term) > 1:
            term += "+" + str(i)
         else: term += str(i)

      if num == 0 and len(term) > 1:
         return term
      elif num > 0 and len(term) > 1:
         term += "+"+str(num)
      else:
         term += str(num)
      return term

   def check(self):
      var = ''
      for i in self.variaveis:
         if var != '' and i.var != var:
            raise InvalidArgumentException("A equação contém mais que uma variável")
         else: var = i.var
      
      
if __name__ == '__main__':
   e = Expressao("5x+5")
   print e.termo
   var = e / 2
   print var
   #print var.variaveis, var.numero
   #print var.termo
