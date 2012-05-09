from exception import *
from accountManager import tryLogin, updateIP, saveCharacter, getPlayer, \
     deletePlayer
from network import SendablePacket, ReadablePacket
from security import SecurityManager


# ** Sendable Packets **
class StaticPacket(SendablePacket):
   OPCODE = 0x00
   def write(self,conn=None):
      #dummy packet
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
      self.writeByte(self.reason)

class LoginOk(SendablePacket):
   OPCODE = 0x04
   def __init__(self, hasPlayer, ID=None, name=None, model=None):
      SendablePacket.__init__(self)
      self.hasPlayer = hasPlayer
      self.ID = ID
      self.name = name
      self.model = model
      
   def write(self, conn=None):
      self.writeByte(self.hasPlayer)
      if self.hasPlayer:
         self.writeInt(self.ID)
         self.writeString(self.name)
         self.writeByte(self.model)

class CharOk(SendablePacket):
   OPCODE = 0x05
   def __init__(self, ID, name, model):
      SendablePacket.__init__(self)
      self.ID = ID
      self.name = name
      self.model = model
      
   def write(self, conn=None):
      self.writeInt(self.ID)
      self.writeString(self.name)
      self.writeByte(self.model)

class CharFail(SendablePacket):
   OPCODE = 0x06
   def write(self, conn=None):
      #dummy packet
      pass

class ActionFailed(SendablePacket):
   OPCODE = 0x07
   #reasons:
   # 1 - Could not delete character
   # 2 - Char Creation Failed
   # 3 - Character name Already in use
   def __init__(self, reason):
      SendablePacket.__init__(self)
      self.reason = reason

   def write(self, conn=None):
      self.writeByte(self.reason)

class CharacterDeleted(SendablePacket):
   OPCODE = 0x08
   def write(self, conn=None):
      #dummy packet
      pass

# ** Readable Packets **
class AuthRequest(ReadablePacket):
   #OPCODE: 0x01
   def __init__(self, packet):
      super(AuthRequest, self).__init__(packet)

   def read(self):
      self.user = self.readString()
      self.pwd = self.readString()

   def process(self, conn):
      if tryLogin(self.user, self.pwd):
         rs = getPlayer(self.user)
         if rs.next():
            ID = rs.getInt('ID')
            name = rs.getString('name')
            model = rs.getInt('model')
            conn.writePacket(LoginOk(0x01, ID, name, model))
         else:
            conn.writePacket(LoginOk(0x00))
         updateIP(self.user, conn.addr[0])
         #this must be removed
         conn.setName(self.user)
      else:
         conn.writePacket(LoginFail(0x01))

class CharacterCreate(ReadablePacket):
   #OPCODE: 0x02
   def __init__(self, packet):
      super(CharacterCreate, self).__init__(packet)

   def read(self):
      self.model = self.readByte()
      self.name  = self.readString()

   def process(self, conn):
      try:
         if saveCharacter(conn.name, self.name, self.model):
            rs = getPlayer(conn.name)
            rs.next()
            conn.writePacket(CharOk(rs.getInt('ID'),self.name,self.model))
         else:
            conn.writePacket(ActionFailed(0x02))
      except CharacterAlreadyExists:
         conn.writePacket(ActionFailed(0x03))

class CharacterDelete(ReadablePacket):
   #OPCODE: 0x03
   def __init__(self, packet):
      super(CharacterDelete, self).__init__(packet)

   def read(self):
      self.ID = self.readInt()

   def process(self, conn):
      if deletePlayer(self.ID):
         conn.writePacket(CharacterDeleted())
      else:
         conn.writePacket(ActionFailed(0x01))
