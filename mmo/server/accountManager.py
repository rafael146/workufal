import copy, sys, os, hashlib
from base64 import encodestring as encode, decodestring as decode
from concurrent import ThreadPoolManager
from database import DatabaseManager as DB
from socket import socket, SOL_SOCKET, SO_REUSEADDR

def alreadyRegistered(user):
    return not DB.getInstance().query("SELECT ID FROM Accounts WHERE ID=%s",user).empty()

def tryLogin(user,pwd):
    return not DB.getInstance().query("SELECT ID FROM Accounts WHERE ID=%s AND pass=%s",(user,pwd)).empty()

class RegisterManager(socket):
   def __init__(self):
      socket.__init__(self)
      self.setsockopt(SOL_SOCKET, SO_REUSEADDR, 1)
      self.bind(('',7778))
      self.listen(5)
      ThreadPoolManager.getInstance().generalExecuter(self.start,0)

   def start(self):
      while 1:
         con, addr = self.accept()
         ThreadPoolManager.getInstance().generalExecuter(self.handler,0,(con,addr))

   def handler(self, con, addr):
      re = con.recv(1024)
      user, pwd = re.split('\00')
      if alreadyRegistered(user):
         con.send('0')
      else:
         self.register(user, pwd, addr[0])
         con.send('1')
      con.close()
      
   def passEncrypter(self, password):
      """
         @arg - password.
         @return - password encrypted.
      """
      tmp = ""
      for i, c in enumerate(str(password)):
         tmp += (chr(~(ord(c)+i)&0x7F^0xFF))
      md = hashlib.sha1(tmp)
      offset = os.urandom(9)
      md.update(offset)
      return encode(md.digest() + offset)

   def register(self, user, pwd, ip):
      pwd = self.passEncrypter(pwd)
      DB.getInstance().query("""INSERT INTO Accounts VALUES (%s,%s,%s)""",(user,pwd,ip))
