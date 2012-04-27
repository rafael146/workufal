import socket, thread, copy
from codec import PacketWriter, PacketReader
from concurrent import Runnable, ThreadPoolManager
from packets import *
from socket import socket, SOL_SOCKET, SO_REUSEADDR

class Server(socket):
   def openConnection(self,host='',port=7777):
      self.clients = []
      self.setsockopt(SOL_SOCKET, SO_REUSEADDR, 1)
      self.bind((host,port))
      self.listen(7)
      self.handlerConnections()

   def accept(self):
      con, addr =super(Server, self).accept()
      connection = Connection(con,addr,self)
      return connection
      
   def close(self):
      super(Server, self).close()

   def broadcastToAll(self, packet):
      for client in self.clients:
         client.send(packet)

   def handlerConnections(self):
      while 1:
         connection = self.accept()
         ThreadPoolManager.getInstance().scheduleGeneral(ConnectionHandler(connection),0)

class BroadcastService(object):
   @staticmethod
   def broadcastToAll(packet):
      for player in World.getInstance().getKnownPlayers():
         player.send(packet)
         
class Connection(object):
   def __init__(self, con, addr, serv):
      self.con = con
      self.addr = addr
      self.serv = serv
      self.writer = PacketWriter(self)
      self.reader = PacketReader(self)

   def recv(self, lenght):
      return self.con.recv(lenght)

   def writePacket(self, packet):
      self.con.send(self.writer.process(packet))

   def readPacket(self, packet):
      self.reader.process(packet)

   def close(self):
      self.con.close()

   def broadcastToAll(self, packet):
      self.serv.broadcastToAll(packet)

   def setKey(self,key):
      key2 = copy.copy(key)
      self.writer.setKey(key)
      self.reader.setKey(key2)

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
            print 'incoming',self.connection.addr
            if not packet:
               break
            self.connection.readPacket(packet)
         except Exception, e:
            print e
            break
      self.connection.close()
      
server = Server()
server.openConnection()
