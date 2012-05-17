class World(object):
    def __init__(self):
        self.players = []

    def onEnter(self, player):
        self.players.append(player)
        # All player in known area must be notified

    @property
    def KnownPlayers(self):
        return self.players

class BroadcastService(object):
    @staticmethod
    def broadcastToAll(packet):
        for player in getInstance().KnownPlayers:
            player.send(packet)
            
class SingleWorld(object):
    INSTANCE = World()
    
def getInstance():
    return SingleWorld.INSTANCE
