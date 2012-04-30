from network import SendablePacket, ReadablePacket
from security import SecurityManager
from accountManager import tryLogin

# ** Sendable Packets **
class StaticPacket(SendablePacket):
   OPCODE = 0x00
   def write(self,conn=None):
      #Do nothing
      pass

class Connect(SendablePacket):
   OPCODE = 0x01
   def write(self,conn=None):
      key = SecurityManager.generateKey()
      conn.setKey(key)
      self.writeInt(len(key))
      self.writeBytes(key)

class ProtocolSender(SendablePacket):
   OPCODE = 0x02
   def write(self,conn=None):
      self.writeInt(SecurityManager.PROTOCOL)

class LoginFail(SendablePacket):
   OPCODE = 0x03
   # reasons
   # 0x01 - USER OR PASSWORD WRONG
   def __init__(self, reason):
      SendablePacket.__init__(self)
      self.reason = reason

   def write(self, conn=None):
      self.writeInt(self.reason)

# ** Readable Packets **
class AuthRequest(ReadablePacket):
   #OPCODE: 0x01
   def __init__(self, packet):
      super(AuthRequest, self).__init__(packet)

   def read(self):
      self.user = self.readString()
      self.pwd = self.readString()

   def process(self, conn):
      if not tryLogin(self.user, self.pwd):
         conn.writePacket(LoginFail(0x01))
      else:
         conn.writePacket(StaticPacket())


