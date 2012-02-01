#-*- coding: utf-8 -*-
# Autor: Alisson Oliveira

import utils
from expressao import *
from excessoes import *

class Variavel(object):

   def __init__(self, coeficiente=None, var=None, expoente=None):
      """
         Representação de uma variavel.

         coeficiente é a parte numerica da variavel
         var é a o valor da variavel, se não especificado o (x) será usado
         expoente o valor do expoente da variavel
      """
      
      if coeficiente == None: self.coef = float(1)
      else : self.coef = float(coeficiente)
      
      if var == None : self.var = "x"
      else: self.var = str(var)

      if expoente == None or expoente in ["", " "]: self.expoente = 1
      else: self.expoente = int(expoente)

      if(self.coef.is_integer()):
         self.valor = str(int(self.coef)) + self.var + utils.expoenteFromChar(self.expoente)
      else:  self.valor = str(self.coef) + self.var + utils.expoenteFromChar(self.expoente)

   def __add__(self, val):
      """
         Sobreescreve o operador '+'
      """
      if isinstance(val, Variavel):
         if self.var == val.var and self.expoente == val.expoente:
            return Variavel(self.coef + val.coef, val.var, val.expoente)
         if self.coef == 0 and val.coef == 0:
            return 0
         elif val.coef == 0:
            return self
         elif self.coef == 0:
            return val
         elif val.coef < 0:
            return Expressao(str(self) + str(val))
         return  Expressao(str(self) + "+" + str(val))
      if isinstance(val, (float, int, long)):
         if self.coef == 0:
            return val
         if val == 0:
            return self
         if val < 0:
            return Expressao(str(self) + str(val))
         return Expressao(str(self) + "+" + str(val))
      raise TypeError("unsuported operand type(s) for +: <type 'Variavel'> and "+ str(type(val)))

   def __div__(self, val):
      """
         Sobreescreve o operador '/'
      """
      if isinstance(val, Variavel):
         if val.var== self.var and val.expoente == self.expoente:
            return self.coef / val.coef
         elif val.var== self.var:
            return Variavel(self.coef/ val.coef, self.var, self.expoente - val.expoente)
         raise NotImplementedError("Unsuported operand type(s) for /:<type 'Variavel'> and <type 'Variavel'> with different variavel")
      elif isinstance(val, (int,float,long)):
         if val == 0:
            raise ZeroDivisionError
         return Variavel(self.coef/val, self.var, self.expoente)
      raise TypeError("unsuported operand type(s) for +: <type 'Variavel'> and "+ str(type(val)))

   def __eq__(self, val):
      """
         Sobreescreve o operador '=='
      """
      if isinstance(val, Variavel):
         return val.coef == self.coef and val.var== self.var and val.expoente == self.expoente
      elif isinstance(val, (float,int,long)):
         if val == 0 and self.coef == 0:
            return True
      return False

   def __ge__(self, val):
      """
         Sobreescreve o operador '>='
      """
      if isinstance(val, (int,long,float)):
         if val == 0:
            return self.coef >= 0
      return False

   def __gt__(self, val):
      """
         Sobreescreve o operador '>'
      """
      if isinstance(val, (int,long,float)):
         if val == 0:
            return self.coef > 0
      return False

   def __le__(self, val):
      """
         Sobreescreve o operador '<='
      """
      if isinstance(val, (int,long,float)):
         if val == 0:
            return self.coef <= 0
      return False
   
   def __lt__(self, val):
      """
         Sobreescreve o operador '<'
      """
      if isinstance(val, (int,long,float)):
         if val == 0:
            return self.coef < 0
      return False

   def __mul__(self, val):
      """
         Sobreescreve o operador '*'
      """
      if isinstance(val, Variavel):
         if val.var== self.var:
            return Variavel(self.coef * val.coef, self.var, self.expoente + val.expoente)
         # implementar algo parecido com:
         # return Expressao(self.coef * val.coef, self.var+utils.expoenteFromChar(self.expoente)+val.var+utils.expoenteFromChar(val.expoente))
         raise NotImplementedError("Unsuported operand type(s) for *:<type 'Variavel'> and <type 'Variavel'> with different variavel")
      elif isinstance(val, (int,float,long)):
         return Variavel(self.coef * val, self.var, self.expoente)
      raise TypeError("unsuported operand type(s) for *: <type 'Variavel'> and "+ str(type(val)))
         
   def __ne__(self, val):
      """
         Sobreescreve o operador '!='
      """
      if isinstance(val, Variavel):
         return not (val.coef == self.coef and val.var== self.var and val.expoente == self.expoente)
      elif isinstance(val, (float,int,long)):
         if val == 0 and self.coef == 0:
            return False
      return True

   def __neg__(self):
      """
         Sobreescreve o operador unario '-'
      """
      return Variavel(-self.coef, self.var, self.expoente)

   def __nonzero__(self):
      """
         Sobreescreve o operador '!= 0'
      """
      return self.coef != 0

   def __pow__(self, val, arg=None):
      """
         Sobreescreve o operador "**"
      """
      if arg != None:
         raise NotImplementedError("Variavel instance not support third argument yet")
      return Variavel(self.coef, self.var, self.expoente*val)

   def __radd__(self, val):
      """
         Sobreescreve o operador "+"
      """
      return self.__add__(val)

   def __rdiv__(self, val):
      """
         Sobreescreve o operador '/'
      """
      if isinstance(val, (float,int,long)):
         val /= self.coef
         return Variavel(val, self.var, -self.expoente)
      tmp = Variavel(self.coef, self.var, -self.expoente)
      return tmp.__div__(val)

   def __repr__(self):
      """
         Sobreescreve a função 'repr'
      """
      if self.coef == 0:
         return "0"
      if self.expoente == 0:
         return str(self.coef)
      if self.coef == 1:
         return self.var + utils.expoenteFromChar(self.expoente)
      if self.coef == -1:
         return "-"+self.var + utils.expoenteFromChar(self.expoente)
      return self.valor

   def __rmul__(self, val):
      """
         Sobreescreve o operador "*"
      """
      return self.__mul__(val)

   def __rsub__(self, val):
      """
         Sobreescreve o operador '-'
      """
      val = -val
      temp = - self
      return temp.__sub__(val)

   def __str__(self):
      """
         Sobreescreve a função 'str'
      """
      return self.__repr__()

   def __sub__(self, val):
      """
         Sobreescreve o operador '-'
      """
      if isinstance(val, Variavel):
         if self.var == val.var and self.expoente == val.expoente:
            return Variavel(self.coef - val.coef, val.var, val.expoente)
         if self.var == val.var == 0:
            return 0
         if self.var == 0:
            return val
         if val.coef == 0:
            return self
         elif val.coef < 0:
            val = -val
            return Expressao(str(self) + "+" + str(val))
         return Expressao(str(self) + "-" + str(val))
      elif isinstance(val, (float,int,long)):
         if self.var == 0:
            return val
         if val == 0:
            return self
         elif val < 0:
            val = -val
            return Expressao(str(self) + "+" + str(val))
         return Expressao(str(self) + "-" + str(val))
      raise TypeError("unsuported operand type(s) for -: <type 'Variavel'> and "+ str(type(val)))

   @staticmethod
   def variavelFromStr(arg):
      """
         Recebe uma string que representa uma variavel e retorna uma instancia de variavel
         caso a string recebida seja um int retorna um float com o valor da string.
      """
      if arg.isdigit():
         return float(arg)
      var = ""
      for i in arg:
         if i.isalpha():
            if var != "" and i != var:
               raise InvalidArgumentException("A equação contém mais que uma variável")
            else:
               var = i

      i = 0
      r = {"var":[], "num":[], "exp":[]}
      var =  num = ""
      isNegative = False
      while i < len(arg):
         if arg[i] == "-":
            isNegative = True
            i += 1
            continue
         if arg[i].isdigit():
            num = arg[i]
            tmp = arg[i+1:]
            p = tmp.find(".")
            if p >= 0:
               tmp = tmp.replace(".","")
               i+=1
            t = 0
            while t < len(tmp):
               if tmp[t].isdigit():
                  num += tmp[t]
                  i += 1
                  t += 1
               else : break
            t = 0
            tmp = arg[i+1:]
            exp = ""
            if utils.isExpoent(tmp[t:t+2]):
               exp = utils.charFromExpoent(tmp[t:t+2])
               i += 2
               tmp = tmp[t+2:]
               t = 0
               while t < len(tmp):
                  if utils.isExpoent(tmp[t:t+2]):
                     exp += utils.charFromExpoent(tmp[t:t+2])
                     t += 2
                     i += 2
                  elif utils.isExpoent(tmp[t:t+3]):
                     exp += utils.charFromExpoent(tmp[t:t+3])
                     t += 3
                     i += 3
                  else: break
            elif utils.isExpoent(tmp[t:t+3]):
               exp = utils.charFromExpoent(tmp[t:t+3])
               i += 3
               tmp = tmp[t+3:]
               t = 0
               while t < len(tmp):
                  if utils.isExpoent(tmp[t:t+2]):
                     exp += utils.charFromExpoent(tmp[t:t+2])
                     t += 2
                     i += 2
                  elif utils.isExpoent(tmp[t:t+3]):
                     exp += utils.charFromExpoent(tmp[t:t+3])
                     t += 3
                     i += 3
                  else: break
            if p >= 0:
               num = num[:p+1]+"."+num[p+1:]
            if exp != "" :
               num = str(pow(float(num),int(exp)))
               if int(exp) % 2 == 0: isNegative = False
            if isNegative:
               r["num"].append("-"+num)
               isNegative = False
            else: r["num"].append(num)
         elif arg[i].isalpha():
            if isNegative:
               r["var"].append("-"+arg[i])
               isNegative = False
            else : r["var"].append(arg[i])
            tmp = arg[i+1:]
            t = 0
            exp = ""
            if utils.isExpoent(tmp[t:t+2]):
               exp = utils.charFromExpoent(tmp[t:t+2])
               i += 2
               tmp = tmp[t+2:]
               t = 0
               while t < len(tmp):
                  if utils.isExpoent(tmp[t:t+2]):
                     exp += utils.charFromExpoent(tmp[t:t+2])
                     t += 2
                     i += 2
                  elif utils.isExpoent(tmp[t:t+3]):
                     exp += utils.charFromExpoent(tmp[t:t+3])
                     t += 3
                     i += 3
                  else: break
            elif utils.isExpoent(tmp[t:t+3]):
               exp = utils.charFromExpoent(tmp[t:t+3])
               i += 3
               tmp = tmp[t+3:]
               t = 0
               while t < len(tmp):
                  if utils.isExpoent(tmp[t:t+2]):
                     exp += utils.charFromExpoent(tmp[t:t+2])
                     t += 2
                     i += 2
                  elif utils.isExpoent(tmp[t:t+3]):
                     exp += utils.charFromExpoent(tmp[t:t+3])
                     t += 3
                     i += 3
                  else: break
            r["exp"].append(exp)
         i += 1
      variavel = 1
      for i in range(len(r["var"])):
         if r["var"][i].startswith("-"):
            variavel *= Variavel(-1, r["var"][i].replace("-",""), r["exp"][i])
         else : variavel *= Variavel(var=r["var"][i], expoente=r["exp"][i])

      coef = 1
      for i in r["num"]:
         coef *= float(i)

      variavel *= coef 

      return variavel

   def getExpoente(self):
      """
         @return o expoente da variavel
      """
      return self.expoente

   def getCoeficiente(self):
      """
         @return o Coeficiente da variavel
      """
      return self.coef

   def getVar(self):
      """
         @return o character que representa a variavel (i.e "x")
      """
      return self.var

   def isSummable(self, val):
      """
         Verifica se o argumento é somavel com a variavel.
      """
      if isinstance(val, Variavel):
         return self.var == val.var and self.expoente == val.expoente
      return False
         
if __name__ == "__main__":
   var = Variavel.variavelFromStr("4x")
   var1 = Variavel.variavelFromStr("2")
   var2 = Variavel("-20.0", "y")
   var3 = Variavel(30, "z", 4)
   var4 = Variavel("2", "t", "5")
   var5 = Variavel(9, expoente=10)

   print var
   print var1
   print var2
   print var3
   print var4
   print var5
   var4 =  var1 / var
   print  var4

