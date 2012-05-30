import services
from exception import *
from accountManager import tryLogin, updateIP
from charManager import createCharacter, getPlayer, deletePlayer, loadPlayer
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
   # 0x01 - User or Password Wrong
   # 0x02 - Account Already in use
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

class PlayerInfo(SendablePacket):
   OPCODE = 0x09
   def write(self, conn=None):
      player = conn.player
      self.writeByte(player.model)
      self.writeInt(player.ID)
      self.writeString(player.name)
      self.writeShort(player.level)
      self.writeShort(player.speed)
      self.writeInt(player.maxHp)
      self.writeInt(player.hp)
      self.writeInt(player.x)
      self.writeInt(player.y)
      self.writeInt(player.heading)
      self.writeInt(player.defense)
      self.writeInt(player.force)
      self.writeLong(player.exp)

class Appearing(SendablePacket):
   OPCODE = 0x0A
   def write(self, conn=None):
      #dummy packet
      pass

class TargetSelected(SendablePacket):
   OPCODE = 0x0B
   def __init__(self, Id):
      SendablePacket.__init__(self)
      self.Id = Id

   def write(self, conn=None):
      self.writeInt(self.Id)   

class Disconnected(SendablePacket):
   OPCODE = 0x0C
   def write(self, conn=None):
      #dummy packet
      pass

class CharInfo(SendablePacket):
   OPCODE = 0x0D
   def __init__(self, character):
      SendablePacket.__init__(self)
      self.character = character
      
   def write(self, conn=None):
      self.writeInt(self.character.ID)
      self.writeString(self.character.name)
      self.writeByte(self.character.model)
      self.writeInt(self.character.x)
      self.writeInt(self.character.y)
      self.writeInt(self.character.heading)

# ** Readable Packets **

class Logout(ReadablePacket):
   #OPCODE: 0x00
   def __init__(self, packet):
      super(Logout, self).__init__(packet)

   def read(self):
      #dummy packet
      pass

   def process(self, conn):
      if conn.player:
         services.getWorldInstance().onLogout(conn.player)
      conn.logout()

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
         if conn.isConnected(self.user):
            conn.writePacket(LoginFail(0x02))
            return
         conn.clientConnected(self.user)
         if rs.next():
            ID = rs.getInt('ID')
            name = rs.getString('name')
            model = rs.getInt('model')
            conn.writePacket(LoginOk(0x01, ID, name, model))
         else:
            conn.writePacket(LoginOk(0x00))
         updateIP(self.user, conn.addr[0])
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
         if createCharacter(conn.user, self.name, self.model):
            rs = getPlayer(conn.user)
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

class EnterWorld(ReadablePacket):
   #OPCODE: 0x04
   def __init__(self, packet):
      super(EnterWorld, self).__init__(packet)

   def read(self):
      self.ID = self.readInt()

   def process(self, conn):
      player = loadPlayer(self.ID)
      conn.setPlayer(player)
      services.getWorldInstance().onEnter(player)
      conn.writePacket(PlayerInfo())
      conn.writePacket(Appearing())
      # broadcast all players in same area
      broadcastService = services.getBroadcastInstance()
      broadcastService.broadcastToArea(player, CharInfo(player))
      broadcastService.broadcastAll(player, [CharInfo(char) for char in
                                             player.getKnown()])
      
class Action(ReadablePacket):
   #OPCODE: 0x05
   def __init__(self, packet):
      super(Action, self).__init__(packet)

   def read(self):
      self.targetId = self.readInt()

   def process(self, conn):
      target = services.getWorldInstance().find(self.targetId)
      if target and target != conn.player.target:
         conn.player.target = target
         conn.writePacket(TargetSelected(target.ID))
      else :
         conn.writePacket(StaticPacket())
