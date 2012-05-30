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
        self.AI = PlayerAI(self)
        
    def addToKnown(self, character):
        self.AI.knownList.add(character)

    def getKnown(self):
        return self.AI.knownList

    def send(self, packet):
        self.client.writePacket(packet)

class PlayerAI(object):
    def __init__(self, owner):
        self.owner = owner
        self.knownList = set()
