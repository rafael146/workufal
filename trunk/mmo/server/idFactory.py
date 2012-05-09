# Autor: Alisson Oliveira
#
# ID allocador based in BitSet
#
from database import DatabaseManager as DB

#Actually the word is a long which consists of 64bits,
#requires 6 address bits.
WORD_ADDRESS = 6
# bits size of long
BITS_WORD = 1 << WORD_ADDRESS
# max value of long, used to avoid overflows
MAX_VALUE = 0xFFFFFFFFFFFFFFFFL

FIRST_ID = 0x10000000
LAST_ID  = 0x7FFFFFFF
ALLOCABLES_ID = LAST_ID - FIRST_ID

class IDAllocador(object):
    def __init__(self, capacity):
        self.words = [0L]*(capacity/BITS_WORD+1)
        self.nextID = self.nextClearBit()

    def nextClearBit(self, start=0):
        """
            return the first index of the bit which is not used
        """
        start >>= WORD_ADDRESS
        idx = -1
        for i in xrange(start, len(self.words)-1):
            if self.words[i] < MAX_VALUE:
                idx = bin(self.words[i])[2:].zfill(64)[::-1].find('0')
                break
        if idx < 0:
            idx = self.nextClearBit()
        if idx < 0:
            self.ensureCapacity()
            idx = self.nextClearBit()
        return (i << WORD_ADDRESS) + idx

    def ensureCapacity(self, idx=None):
        """
            Increase the lenght of array of bits
        """
        if idx != None:
            length = len(self.words)-1
            if length < idx:
                self.words.extend([0L]*int((idx-length)*0.1+1))
        else:
            self.words.extend([0L]*int(len(self.words)*0.1+1))

    def set(self, ID):
        idx = ID>>WORD_ADDRESS
        self.ensureCapacity(idx)
        self.words[idx] |= (1L<<(ID%BITS_WORD))

    def get(self, ID):
        idx = ID >> WORD_ADDRESS
        return idx < len(self.words) and self.words[idx] & (1L<<(ID%BITS_WORD))
        
    def allocate(self, ID=None):
        if ID == None:
            newID = self.nextID
            self.set(newID)
            self.nextID = self.nextClearBit(newID)
            return newID + FIRST_ID
        else:
            if self.get(ID - FIRST_ID):
                print "ID already in use"
                return
            if ID < FIRST_ID:
                print "ID is less"
                return
            self.set(ID - FIRST_ID)
            self.nextID = self.nextClearBit()

    def loads(self):
        rs = DB.getInstance().query("SELECT ID FROM Players")
        while rs.next():
            self.allocate(rs.getInt('ID'))
        print "All IDs loaded"

class SingletonHolder(object):
    INSTANCE = IDAllocador(10000)

def getInstance():
    return SingletonHolder.INSTANCE
