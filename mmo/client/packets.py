from network import SendablePacket, ReadablePacket
from security import SecurityManager

# ** Sendable Packetes **
class AuthRequest(SendablePacket):
   OPCODE = 0x01
   def write(self, conn=None):
      self.writeString(conn.user)
      self.writeString(conn.game.login.pwd)

class Close(SendablePacket):
   OPCODE = 0x00
   def write(self, conn=None):
      #Do Nothing, only send exit info to server
      pass

# ** Readable Packet **
class Connect(ReadablePacket):
   #OPCODE: 0x01
   def __init__(self, packet):
      super(Connect,self).__init__(packet)
      
   def read(self):
      length = self.readInt()
      self.key = self.readBytes(length)
   
   def process(self, conn):
      conn.setKey(self.key)

class ProtocolReceiver(ReadablePacket):
   #OPCODE: 0x02
   def __init__(self, packet):
      super(ProtocolReceiver,self).__init__(packet)
      
   def read(self):
      self.protocol = self.readInt()

   def process(self, conn):
      if self.protocol != SecurityManager.PROTOCOL:
         conn.shutdown(2)
         conn.close()
         #set wait state
         conn.game.State = 3
         #Show wrong protocol to client
         conn.game.login.writeTxt("Wrong Protocol Revision")
      else:
         conn.writePacket(AuthRequest())

class LoginFail(ReadablePacket):
   #OPCODE: 0x03
   msgs = ["","User or Password wrong"]
   def __init__(self, packet):
      super(LoginFail, self).__init__(packet)

   def read(self):
      self.reason = self.readInt()

   def process(self, conn):
      #set wait state
      conn.game.State = 3
      #Show wrong protocol to client
      conn.game.login.writeTxt(self.msgs[self.reason])

class LoginOk(ReadablePacket):
   #OPCODE: 0x04
   def __init__(self, packet):
      super(LoginOk, self).__init__(packet)

   def read(self):
      # dummy packet
      pass

   def process(self, conn):
      #set Connected State
      conn.game.State = 1
      conn.game.login.writeTxt("Login Sucessful")
      conn.game.change()
