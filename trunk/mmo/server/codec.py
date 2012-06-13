import cPickle
from network import NetworkBuffer
from packets import *
from weakref import proxy

class PacketWriter(object):
    def __init__(self, conn):
        self.conn = proxy(conn)
        self.key = None
        self.isKeySetted = False

    def process(self, packet):
        packet.writeShort(packet.OPCODE)
        print 'sending opcode', hex(packet.OPCODE)
        packet.write(self.conn)
        if self.isKeySetted:
            pass
            #self.crypt(packet)
        else:
            self.isKeySetted = True
        return self.encoder(packet)
        
    def crypt(self, packet):
        size = packet.readableBytes()
        tmp = tmp2 = 0
        for i in xrange(size):
            tmp2 = packet.getByte(i) &0xFF
            tmp = tmp2^self.key[i&15]^tmp
            packet.setByte(i, tmp)

        old  = self.key[8]  &0xFF
        old |= self.key[9]  << 0x08 &0xFF00
        old |= self.key[10] << 0x10 &0xFF0000
        old |= self.key[11] << 0x18 &0xFF000000

        old += size

        self.key[8]  = old &0xFF
        self.key[9]  = old >> 0x08 &0xFF
        self.key[10] = old >> 0x10 &0xFF
        self.key[11] = old >> 0x18 &0xFF

    def encoder(self, packet):
        return cPickle.dumps(packet.array)

    def setKey(self, key):
        self.key = key

class PacketReader(object):
    def __init__(self, conn):
        self.conn = proxy(conn)
        self.key = None
    
    def process(self, packet):
        packet = NetworkBuffer.wrap(self.decoder(packet))
        if self.key:
            pass
            #self.decrypt(packet)
        self.handlePacket(packet)

    def decrypt(self,packet):
        size = packet.readableBytes()
        tmp=tmp2=0
        for i in xrange(size):
            tmp2 = packet.getByte(i) &0xFF
            packet.setByte(i, (tmp2^self.key[i&15]^tmp))
            tmp=tmp2

        old = self.key[8]   &0xFF
        old |= self.key[9]  << 0x08 &0xFF00
        old |= self.key[10] << 0x10 &0xFF0000
        old |= self.key[11] << 0x18 &0xFF000000

        old += size

        self.key[8]  = old &0xFF
        self.key[9]  = old >> 0x08 &0xFF
        self.key[10] = old >> 0x10 &0xFF
        self.key[11] = old >> 0x18 &0xFF 

    def decoder(self,packet):
        return cPickle.loads(packet)
    
    def setKey(self, key):
        self.key = key

    def handlePacket(self, packet):
        opcode = packet.readShort() &0xFF
        print 'received opcode', hex(opcode)
        if opcode == 0x00:
            readable = Logout(packet)
        elif opcode == 0x01:
            readable = AuthRequest(packet)
        elif opcode == 0x02:
            readable = CharacterCreate(packet)
        elif opcode == 0x03:
            readable = CharacterDelete(packet)
        elif opcode == 0x04:
            readable = EnterWorld(packet)
        elif opcode == 0x05:
            readable = Action(packet)
        elif opcode == 0x06:
            readable = Move(packet)
        elif opcode == 0x07:
            readable = CancelTarget(packet)
        else:
            print "Invalid Packet opcode:", hex(opcode)
            return
        readable.read()
        readable.process(self.conn)
