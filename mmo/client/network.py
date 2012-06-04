# Autor: Alisson Oliveira
from abc import *
from struct import pack, unpack
_BYTE  = 1
_SHORT = 2
_INT = 4
_LONG = 8
    
class NetworkBuffer(object):
    """
        A Byte Buffer using Little Endian.
    """
    def __init__(self,packet=None):
        if packet:
            self.readIndex = packet.readIndex
            self.writeIndex = packet.writeIndex
            self.array = packet.array
        else:
            self.readIndex = 0
            self.writeIndex = 0
            self.array = []
            

    def __len__(self):
        return len(self.array)

    def fill(self, index, byte=None):
        if index <= self.writeIndex:
            return
        if byte == None:
            byte = 0
        self.writeBytes([byte for x in xrange(index - self.writeIndex)])

    def setByte(self, index, byte):
        """
            Set the byte at index.
            raise index error if index not in range of array's length.
        """
        self.array[index] = byte

    def getByte(self, index):
        """
            Return the byte at index.
        """
        return self.array[index]

    def putByte(self, index, byte):
        """
            Put the byte at index.
            will be filled with 0 until index -1
        """
        if self.writeIndex < index:
            self.fill(index)
        self.array.insert(index, byte)
        self.writeIndex += _BYTE

    def putChar(self, index, char):
        """
            Put Char at index.
            if index greather than writeIndex fill with 0 until index.
        """
        if self.writeIndex < index:
            self.fill(index)
        self.array.insert(index, ord(char))
        self.writeIndex += _BYTE
        
    def readByte(self):
        """
            return the byte in the readerIndex and increase readerIndex by 1.
        """
        byte = self.array[self.readIndex]
        self.readIndex += _BYTE
        return byte

    def readBytes(self, length):
        """
            Return a array of bytes from readerIndex to readerIndex + lenght.
        """
        if length + self.readIndex > self.writeIndex:
            raise IndexError
        byte = self.array[self.readIndex:self.readIndex+length]
        self.readIndex += len(byte)
        return byte

    def readChar(self):
        """
            return the char in the readerIndex and increase readerIndex by 1.
        """
        char = chr(self.array[self.readIndex])
        self.readIndex += _BYTE
        return char

    def readShort(self):
        """
            return the short in the readerIndex and readerIndex+1
            and increase readerIndex by 2.
        """
        short = self.array[self.readIndex] | (self.array[self.readIndex+1]<<8)
        self.readIndex += _SHORT
        if short & 1<<_SHORT*8-1:
            return short - (1<<_SHORT*8)
        return short

    def readInt(self):
        """
            return the short in the readerIndex to readerIndex+3
            and increase readerIndex by 4.
        """
        value = self.array[self.readIndex] | (self.array[self.readIndex+1]<<8
        ) |(self.array[self.readIndex+2]<<16) | (self.array[self.readIndex+3]<<24)
        self.readIndex += _INT
        if value & 1<<_INT*8-1:
            return value - (1<<_INT*8)
        return value

    def readFloat(self):
        """
            return the float in the readerIndex to readerIndex+7
            and increase readerIndex by 8.
        """
        return unpack('d', pack('q',self.readLong()))[0]

    def readLong(self):
        """
            return the long in the readerIndex to readerIndex+7
            and increase readerIndex by 8.
        """
        value = self.array[self.readIndex] | (self.array[self.readIndex+1]<<8
        ) | (self.array[self.readIndex+2]<<16) | (self.array[self.readIndex+3]<<24
        ) | (self.array[self.readIndex+4]<<32) | (self.array[self.readIndex+5]<<40
        ) | (self.array[self.readIndex+6]<<48) | (self.array[self.readIndex+7]<<56)
        self.readIndex += _LONG
        if value & 1<<_LONG*8-1:
            return value - (1<<_LONG*8)
        return value

    def readString(self):
        """
            return the characters in the readerIndex to
            readerIndex + length of string
        """
        string = ""
        while True:
            char = self.readChar()
            if char == '\000':
                break
            string += char
        return string

    def writeByte(self, byte):
        """
            appends the byte at end of the array.
            If the argument given has more than 8 bits,
            will be used only the 8 bits less **sign and
            increase writerIndex by 1.

            raise ValueError if argument not in range 256,
            to avoid mistakes.
        """
        if not byte in range(256):
            raise ValueError
        self.array.append(byte & 0xFF)
        self.writeIndex += _BYTE

    def writeBytes(self, byte_array):
        """
            Appends the byte array at end and increase writerIndex by length byte_array
        """
        self.array.extend(byte_array)
        self.writeIndex += len(byte_array)

    def writeChar(self, char):
        """
            appends the char at end of the array.
            If the argument given has more than 8 bits,
            will be used only the 8 bits less **sign and
            increase writerIndex by 1.

            Raise ValueError if argument not in range 256,
            to avoid mistakes.
        """
        self.array.append(ord(char) & 0xFF)
        self.writeIndex += _BYTE

    def writeShort(self, short):
        """
            appends the short argument at end of the array.
            and increase writerIndex by 2.
        """
        self.array.append(short & 0xFF)
        self.array.append((short >> 8) & 0xFF)
        self.writeIndex += _SHORT

    def writeInt(self, integer):
        """
            appends the integer argument at end of the array.
            and increase writerIndex by 4.
        """
        self.array.append(integer & 0xFF)
        self.array.append((integer >>  8) &0xFF)
        self.array.append((integer >> 16) &0xFF)
        self.array.append((integer >> 24) &0xFF)
        self.writeIndex += _INT

    def writeFloat(self, value):
        """
            appends the value argument at end of the array.
            and increase writerIndex by 8.
        """
        self.writeLong(unpack('q', pack('d', value))[0])

    def writeLong(self, lon):
        """
            appends the long argument at end of the array.
            and increase writerIndex by 8.
        """
        self.array.append(lon & 0xFF)
        self.array.append((lon >>  8) &0xFF)
        self.array.append((lon >> 16) &0xFF)
        self.array.append((lon >> 24) &0xFF)
        
        self.array.append((lon >> 32) &0xFF)
        self.array.append((lon >> 40) &0xFF)
        self.array.append((lon >> 48) &0xFF)
        self.array.append((lon >> 56) &0xFF)
        self.writeIndex += _LONG

    def writeString(self, string):
        """
            appends the string at end of the array.
            strings always is terminated with char '\x00'
            and increase writerIndex by length of string.
        """
        for char in string:
            self.writeChar(char)
        self.writeChar('\000')

    @staticmethod
    def wrap(data):
        buf = NetworkBuffer()
        buf.array = data
        buf.writeIndex = len(data)
        return buf

    def readableBytes(self):
        """
            Return the quantity of bytes which can be read.
        """
        return self.writeIndex - self.readIndex

    def readerIndex(self):
        """
            Return the reader index
        """
        return self.readIndex

    def writerIndex(self):
        """
            Return the write index
        """
        return self.writeIndex

class NetworkPacket(NetworkBuffer):
    __metaclass__ = ABCMeta

class ReadablePacket(NetworkPacket):
    @abstractmethod
    def read(self):
        pass
    @abstractmethod
    def process(self,conn=None):
        pass
    
class SendablePacket(NetworkPacket):
    @abstractmethod
    def write(self,conn=None):
        pass
