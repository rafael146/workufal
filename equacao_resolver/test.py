# -*- coding: utf-8 -*-
import utils
from variavel import *

#variavel("2x⁴x²")
if __name__ == '__main__':
   result = []
   a = ['2x', '/2', "*2", "+2", "*2", "*4", "/2"]
   i = 0
   while i < len(a):
      if a[i].startswith("*"):
         tmp = a[i]
         tmp.replace("*","")
         tmp = Variavel.variavelFromStr(tmp)
         tmp *= Variavel.variavelFromStr(a[i-1])
         a.remove(a[i])
         a.remove(a[i-1])
         if tmp >= 0:
            a.insert(i-1, "+"+str(tmp))
         else: a.insert(i-1, str(tmp))
         continue
      elif a[i].startswith("/"):
         tmp = a[i]
         tmp.replace("/","")
         tmp = Variavel.variavelFromStr(tmp)
         tmp = Variavel.variavelFromStr(a[i-1]) / tmp
         a.remove(a[i])
         a.remove(a[i-1])
         if tmp >= 0:
            a.insert(i-1, "+"+str(tmp))
         else: a.insert(i-1,str(tmp))
         continue
      i += 1
   a.extend(result)
   print a
