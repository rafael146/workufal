class World(object):
    def __init__(self):
        self.players = []

    def onEnter(self, player):
        self.players.append(player)
        # All player in known area must be notified

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
