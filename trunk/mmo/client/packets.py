from network import SendablePacket, ReadablePacket
from security import SecurityManager

# ** Sendable Packets **

class Logout(SendablePacket):
   OPCODE = 0x00
   def write(self, conn=None):
      #dummy packet
      #Do Nothing, only send exit info to server
      pass

class AuthRequest(SendablePacket):
   OPCODE = 0x01
   def write(self, conn=None):
      self.writeString(conn.game.state.user)
      self.writeString(conn.game.state.pwd)

class CharacterCreate(SendablePacket):
   OPCODE = 0x02
   def __init__(self, name, model):
      self.model = model
      self.name  = name
      SendablePacket.__init__(self)

   def write(self, conn=None):
      self.writeByte(self.model)
      self.writeString(self.name)

class CharacterDelete(SendablePacket):
   OPCODE = 0x03
   def __init__(self, ID):
      SendablePacket.__init__(self)
      self.ID = ID

   def write(self, conn=None):
      self.writeInt(self.ID)

class EnterWorld(SendablePacket):
   OPCODE = 0x04
   def __init__(self, ID):
      SendablePacket.__init__(self)
      self.ID = ID

   def write(self, conn=None):
      self.writeInt(self.ID)

class Action(SendablePacket):
   OPCODE = 0x05
   def __init__(self, ID):
      SendablePacket.__init__(self)
      self.ID = ID

   def write(self, conn):
      self.writeInt(self.ID)
      

# ** Readables Packet **

class Connect(ReadablePacket):
   #OPCODE: 0x01
   def __init__(self, packet):
      super(Connect,self).__init__(packet)
      
   def read(self):
      length = self.readInt()
      self.key = self.readBytes(length)
   
   def process(self, conn):
      conn.setKey(self.key)
      #set state Connected
      conn.game.State = 1

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
         conn.game.GState = 3
         #Show wrong protocol to client
         conn.game.state.write("Wrong Protocol Revision")
      else:
         conn.writePacket(AuthRequest())

class LoginFail(ReadablePacket):
   #OPCODE: 0x03
   msgs = ["","User Or Password Wrong", "Account Already In Use"]
   def __init__(self, packet):
      super(LoginFail, self).__init__(packet)

   def read(self):
      self.reason = self.readByte()

   def process(self, conn):
      #set wait state
      conn.game.GState = 3
      #Show wrong protocol to client
      conn.game.state.write(self.msgs[self.reason])
      conn.game.connection.close()

class LoginOk(ReadablePacket):
   #OPCODE: 0x04
   def __init__(self, packet):
      super(LoginOk, self).__init__(packet)
      self.hasPlayer = 0
      self.ID = None
      self.name = None
      self.model = None

   def read(self):
      self.hasPlayer = self.readByte()
      if self.hasPlayer:
         self.ID = self.readInt()
         self.name = self.readString()
         self.model = self.readByte()

   def process(self, conn):
      #set Connected State
      conn.game.GState = 1
      conn.game.toCharScreen(self.hasPlayer,self.ID,self.name,self.model)

class CharOk(ReadablePacket):
   #OPCODE: 0x05
   def __init__(self, packet):
      super(CharOk, self).__init__(packet)

   def read(self):
      self.ID = self.readInt()
      self.name = self.readString()
      self.model = self.readByte()

   def process(self, conn):
      conn.game.toCharScreen(1,self.ID,self.name,self.model)

class CharFail(ReadablePacket):
   #OPCODE: 0x06
   def __init__(self, packet):
      super(CharFail, self).__init__(packet)

   def read(self):
      #dummy packet
      pass

   def process(self, conn):
      conn.game.state.write("Char creation Failed")

class ActionFailed(ReadablePacket):
   #OPCODE: 0x07
   reasons = ["","Coldn't delete Character","Char Creation Failed",
              "Character name Already in use"]
   def __init__(self, packet):
      super(ActionFailed, self).__init__(packet)

   def read(self):
      self.reason = self.readByte()

   def process(self, conn):
      if self.reason:
         conn.game.state.write(self.reasons[self.reason])

class CharacterDeleted(ReadablePacket):
   #OPCODE: 0x08
   def __init__(self, packet):
      super(CharacterDeleted, self).__init__(packet)

   def read(self):
      #dummy packet
      pass

   def process(self, conn):
      conn.game.toCharScreen(0)

class PlayerInfo(ReadablePacket):
   #OPCODE: 0x09
   def __init__(self, packet):
      super(PlayerInfo, self).__init__(packet)

   def read(self):
      self.model = self.readByte()
      self.ID = self.readInt()
      self.name = self.readString()
      self.level = self.readShort()
      self.speed = self.readShort()
      self.maxHp = self.readInt()
      self.hp = self.readInt()
      self.x = self.readInt()
      self.y = self.readInt()
      self.defense = self.readInt()
      self.force = self.readInt()
      self.exp = self.readLong()

   def process(self, conn):
      conn.game.updatePlayer(self.model, self.ID, self.name, self.level, self.speed, \
                             self.maxHp, self.hp, self.x, self.y, self.defense, \
                             self.force, self.exp)

class Appearing(ReadablePacket):
   #OPCODE: 0x0A
   def __init__(self, packet):
      super(Appearing, self).__init__(packet)

   def read(self):
      #dummy packet
      pass

   def process(self, conn):
      conn.game.enterWorld()

class TargetSelected(ReadablePacket):
   #OPCODE : 0x0B
   def __init__(self, packet):
      super(TargetSelected, self).__init__(packet)

   def read(self):
      targetId = self.readInt()

   def process(self, conn):
      # implement discover player system
      pass

class Disconnected(ReadablePacket):
   #OPCODE: 0x0C
   def read(self):
      #dummy packet
      pass

   def process(self, conn):
      conn.game.toLoginScreen()
      conn.game.state.write("Disconnected From Server")
      conn.writePacket(Logout())
