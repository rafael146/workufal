# Autor: Alisson Oliveira
import sys, os, hashlib, idFactory
from base64 import encodestring as encode, decodestring as decode
from concurrent import ThreadPoolManager
from database import DatabaseManager as DB
from exception import *
from socket import socket, SOL_SOCKET, SO_REUSEADDR

def passEncrypter(password):
    """
      @arg - password.
      @return - password encrypted.
    """
    tmp = ""
    for i, c in enumerate(str(password)): tmp += (chr(~(ord(c)+i)&0x7F^0xFF))
    md = hashlib.sha1(tmp)
    offset = os.urandom(9)
    md.update(offset)
    return encode(md.digest() + offset)

def checkPassword(password, expected):
    """
      Check if password is the expected.
      @return - True if is a same, otherwise False.
    """
    expected = decode(expected)
    offset = expected[20:]
    expected = expected[:20]
    tmp = ""
    for i, c in enumerate(str(password)): tmp += (chr(~(ord(c)+i)&0x7F^0xFF))
    password = hashlib.sha1(tmp)
    password.update(offset)
    return expected == password.digest()

def alreadyRegistered(user):
    return not DB.getInstance().query("SELECT ID FROM Accounts WHERE ID=%s",user).empty()

def tryLogin(user,pwd):
    result = DB.getInstance().query("SELECT ID, pass FROM Accounts WHERE ID=%s",user)
    if result.empty():
        return False
    result.next()
    return checkPassword(pwd, result.getString('pass'))

def updateIP(user, ip):
    DB.getInstance().query("UPDATE Accounts SET last_ip = %s WHERE ID = %s",(ip,user))

#must be moved to another 
def hasPlayer(name):
    return not DB.getInstance().query("SELECT ID FROM Players WHERE name = %s",name).empty()

#must be moved to another 
def saveCharacter(account, name, model):
    
    if hasPlayer(name):
        raise CharacterAlreadyExists()
    try:
        ID = idFactory.getInstance().allocate()
        # these values are only for test puporse, must be tested and fixed
        if model == 1:
            spd = 2
            df = 2
            fc = 2
            hp = 300
        elif model == 2:
            spd = 1
            df = 2
            fc = 3
            hp = 350
        elif model == 3:
            spd = 3
            df = 1
            fc = 2
            hp = 250
        DB.getInstance().query("INSERT INTO Players (ID, account, name, speed, defense, `force`, hp, model)" \
                               "Values (%s,%s,%s,%s,%s,%s,%s,%s)",(ID,account,name,spd,df,fc,hp,model))
    except Exception, e:
        return False
    return True

#must be moved to another 
def getPlayer(account):
    return DB.getInstance().query("SELECT * FROM Players WHERE account = %s",account)

#must be moved to another 
def deletePlayer(ID):
    try:
        DB.getInstance().query("DELETE FROM Players WHERE ID = %s",ID)
    except:
        return False
    return True

#must be moved to another
def loadPlayer(ID):
    return DB.getInstance().query("SELECT * FROM Players WHERE ID = %s",ID)

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
      
   def register(self, user, pwd, ip):
      pwd = passEncrypter(pwd)
      DB.getInstance().query("INSERT INTO Accounts VALUES (%s,%s,%s)",(user,pwd,ip))
