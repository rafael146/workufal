class Player(object):
    def __init__(self):
        self.ID = 0
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
        self.target = None
        self.AI = PlayerAI(self)

class PlayerAI(object):
    def __init__(self, owner):
        self.owner = owner
        self.knownList = set()
