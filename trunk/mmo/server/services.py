class World(object):
    def __init__(self):
        self.players = set()

    # Add to players' knownlist in same area
    def onEnter(self, player):
        for p in self.players:
            dx = p.x - player.x
            dy = p.y - player.y
            # these values must be changed to a constant
            # according with config of game.
            # must be player added to his own knowlist ?
            if -600 <= dx <= 600 and -340 <= dy <= 340:
                p.addToKnown(player)
                player.addToKnown(p)
        self.players.add(player)
        print 'players in game', self.players

    @property
    def KnownPlayers(self):
        return self.players

    def find(self, Id):
        for player in self.players:
            if player.ID == Id:
                return player
        return None

    def onLogout(self, player):
        self.players.remove(player)
        print 'players in game ',self.players
  
SingleWorld = World()
    
def getWorldInstance():
    return SingleWorld
