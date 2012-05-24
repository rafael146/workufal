class World(object):
    def __init__(self):
        self.players = set()

    def onEnter(self, player):
        self.players.add(player)
        print len(self.players)
        for p in self.players:
            print p.name

    def onLogout(self, player):
        self.players.remove(player)

    @property
    def KnownPlayers(self):
        return self.players

    def find(self, Id):
        for player in self.players:
            if player.ID == Id:
                return player
        return None
            

class BroadcastService(object):
    @staticmethod
    def broadcastToAll(packet):
        for player in getInstance().KnownPlayers:
            player.send(packet)
            
class SingleWorld(object):
    INSTANCE = World()
    
def getInstance():
    return SingleWorld.INSTANCE
