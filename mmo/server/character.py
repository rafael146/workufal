from weakref import ref, proxy
class Player(object):
    def __init__(self):
        self.ID = 0
        self.client = None
        self.name = ''
        self.speed = 1
        self.defense = 1
        self.force = 1
        self.maxHp = 1
        self.hp = 1
        self.level =1
        self.x = 0
        self.y = 0
        self.heading = 0
        self.exp = 0
        self.model = 1
        self.target = None
        self.knownList = KnownList()
        
    def addToKnown(self, character):
        self.knownList.add(character)

    def getKnown(self):
        return self.knownList

    def send(self, packet):
        self.client.writePacket(packet)

    def updateLocation(self):
        pass

    def __del__(self):
        print 'deleting player'
    
class KnownList(list):
    def __init__(self, *args):
        list.__init__(self, *args)
        def remove(obj, selfref=ref(self)):
            self = selfref()
            if self is not None:
                self.remove(obj)
        self._remove = remove

    def add(self, obj):
        self.append(proxy(obj,self._remove))
