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
    def broadcastToArea(player, packet):
        for char in player.getKnown():
            char.send(packet)

    #this sends all packets to player
    @staticmethod
    def broadcastAll(player, packets):
        for packet in packets:
            player.send(packet)
            
SingleWorld = World()
SingleBroadcast = BroadcastService()
    
def getWorldInstance():
    return SingleWorld

def getBroadcastInstance():
    return SingleBroadcast
