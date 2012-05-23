from database import DatabaseManager as DB
import idFactory
def hasPlayer(name):
    return not DB.getInstance().query("SELECT ID FROM Players WHERE name = %s",name).empty()

def createCharacter(account, name, model):
    
    if hasPlayer(name):
        raise CharacterAlreadyExists()
    try:
        ID = idFactory.createID()
        # these values are only for test puporse, must be tested and fixed
        # all players stats must be saved on DB
        if model == 1:
            spd = 2
            df = 2
            fc = 2
            hp = 300
        elif model == 2:
            spd = 1
            df = 2
            fc = 3
            hp = 350
        elif model == 3:
            spd = 3
            df = 1
            fc = 2
            hp = 250
        DB.getInstance().query("INSERT INTO Players (ID, account, name, speed, defense, `force`, maxHp, hp, model)" \
                               "Values (%s,%s,%s,%s,%s,%s,%s,%s,%s)",(ID,account,name,spd,df,fc,hp,hp,model))
    except Exception, e:
        print e
        return False
    return True

def getPlayer(account):
    return DB.getInstance().query("SELECT * FROM Players WHERE account = %s",account)

def deletePlayer(ID):
    try:
        DB.getInstance().query("DELETE FROM Players WHERE ID = %s",ID)
    except:
        return False
    return True

def loadPlayer(ID):
    rs = DB.getInstance().query("SELECT * FROM Players WHERE ID = %s",ID)
    if rs.next():
        player = Player()
        player.ID = rs.getInt('ID')
        player.name = rs.getString('name')
        player.speed = rs.getInt('speed')
        player.defense = rs.getInt('defense')
        player.force = rs.getInt('force')
        player.maxHp = rs.getInt('maxHp')
        player.hp = rs.getInt('hp')
        player.level = rs.getInt('level')
        player.x = rs.getInt('x')
        player.y = rs.getInt('y')
        player.exp = rs.getInt('exp')
        player.model = rs.getInt('model')
        return player   

class Player(object):
    def __init__(self):
        self.target = None
