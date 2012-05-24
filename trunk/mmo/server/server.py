import sys, idFactory, time
start = time.time()
from accountManager import RegisterManager
from codec import PacketWriter, PacketReader
from concurrent import Runnable, ThreadPoolManager
from copy import copy
from packets import *
from socket import socket, SOL_SOCKET, SO_REUSEADDR

class Server(socket):
   def openConnection(self,host='',port=7777):
      self.clients = dict()
      self.setsockopt(SOL_SOCKET, SO_REUSEADDR, 1)
      self.bind((host,port))
      self.listen(7)
      self.handlerConnections()

   def accept(self):
      con, addr =super(Server, self).accept()
      connection = Client(con,addr,self)
      return connection
      
   def close(self):
      super(Server, self).close()

   def handlerConnections(self):
      while 1:
         connection = self.accept()
         ThreadPoolManager.getInstance().scheduleGeneral(ConnectionHandler(connection),0)

   def register(self, client, conn):
      self.clients[client] = conn
      print self.clients

   def logout(self, client):
      del self.clients[client]

   def hasClient(self, client):
      if self.clients.has_key(client):
         self.clients[client].writePacket(Disconnected())
         return True
      return False

class Client(object):
   def __init__(self, con, addr, serv):
      self.con = con
      self.addr = addr
      self.serv = serv
      self.writer = PacketWriter(self)
      self.reader = PacketReader(self)
      self.user = None
      self.player = None

   def recv(self, lenght):
      return self.con.recv(lenght)

   def writePacket(self, packet):
      self.con.send(self.writer.process(packet))

   def readPacket(self, packet):
      self.reader.process(packet)

   def close(self):
      self.con.close()

   def setKey(self,key):
      key2 = copy(key)
      self.writer.setKey(key)
      self.reader.setKey(key2)

   def setPlayer(self, player):
      self.player = player
      player.client = self

   def clientConnected(self, user):
      self.serv.register(user,self)
      self.user = user

   def isConnected(self, user):
      return self.serv.hasClient(user)

   def logout(self):
      self.serv.logout(self.user)
      self.close()

class ConnectionHandler(Runnable):
   def __init__(self, connection):
      self.connection = connection
      Runnable.__init__(self)

   def initializeConnection(self):
      self.connection.writePacket(Connect())
      self.connection.writePacket(ProtocolSender())
      
   def run(self):
      self.initializeConnection()
      while True:
         try:
            packet = self.connection.recv(1024)
            if not packet:
               break
            self.connection.readPacket(packet)
         except Exception, e:
            print "client desconnected"
            print e
            break
      self.connection.close()

idFactory.getInstance().load()
RegisterManager()
server = Server()
print 'loaded in %.3f seconds'% (time.time()-start)
server.openConnection()
