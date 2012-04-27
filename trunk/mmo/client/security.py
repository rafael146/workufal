from random import Random
class SecurityManager(object):
   PROTOCOL = 0x10

   @staticmethod
   def generateKey():
      src = []
      rnd = Random()
      for i in xrange(16):
         src.append(rnd.randint(0,255))
      return src
