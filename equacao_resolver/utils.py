# -*- coding: utf-8 -*-
# Autor: Alisson Oliveira
from excessoes import *
from math import sqrt

"""
   dicionario contendo os expoentes usando utf-8
"""
expoentes = {"-":'⁻',  "0":'⁰', "1":'¹', "2":'²', "3":'³', "4":'⁴',  "5":'⁵',
             "6":'⁶',  "7":'⁷',  "8":'⁸', "9":'⁹', "(": '⁽', ")":'⁾'}

def expoenteFromChar(char):
   char = str(char)
   result = ""
   if char == "1": return result 
   for i in char:
      result += expoentes.get(i)
   return result

def charFromExpoent(expoente):
   for i in expoentes.items():
      if expoente == i[1]:
         return i[0]
   return ""

def isExpoent(char):
   return char in expoentes.values()
            
signals = ["+", "-", "*", "/"]

def isSignal(char):
   return char in signals   

def separeFromSignal(termo):
   i = 0
   while i < len(termo):
      if isSignal(termo[i]):
         termo = termo[:i] + " " + termo[i:]
         i += 2
         continue
      i += 1
   return termo

def Bhaskara(a,b,c):
   """
      Função que retorna as raizes da equação.
   """
   if(a==b==c==0):
      raise EmptyEquacaoException("Os valores da equação são 0\n")
   if(a==0):
      if(b==0):
         raise ExpressaoException("O resultado da expressão dada é %.1f\n" % c)
      return (-c/b,)
   elif(b==0):
      try:
         result = sqrt((-c/a))
      except ValueError, e:
         raise InvalidOperationError("A equação não tem raiz:\n  raiz de %0.f é indeterminada\n" % (-c/a))
      return (-result,result)
   elif(c==0):
      return (0, (-b/a))

   delta = (b**2) - (4*a*c)
   if delta == 0:
      return (-b/(2*a),)
   elif delta < 0:
      return ()
   else:
      return ((-b + sqrt(delta))/(2*a), (-b - sqrt(delta))/(2*a))
