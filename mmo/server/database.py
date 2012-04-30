#Autor: Alisson Oliveira
from MySQLdb import Connect, escape_string
from MySQLdb.cursors import DictCursor

#Move this values to config
HOST = '127.0.0.1'
USER = 'root'
PWD  = 'root'
DB = 'mmo'

class DatabaseManager(object):
    def __init__(self):
        self.db = Connect(host=HOST,user=USER,passwd=PWD,db=DB,cursorclass=DictCursor)
        self.db.autocommit(True)
        
    @staticmethod
    def getInstance():
        return SingletonHolder.INSTANCE

    def query(self, sql, *params):
        cur = self.db.cursor()
        sql= escape_string(sql)
        cur.execute(sql, *params)
        r = ResultSet(cur.fetchall())
        cur.close()
        return r
    
class ResultSet(object):
    def __init__(self, result):
        self.result = list(result)
        self.row = None

    def next(self):
        if len(self.result):
            self.row = self.result.pop(0)
            print self.row
            return True
        self.row = None
        return False

    def empty(self):
        return len(self.result)==0

    def getInt(self,column):
        return int(self.row[column])

    def getLong(self,column):
        return long(self.row[column])

    def getfloat(self,column):
        return float(self.row[column])

    def getString(self, column):
        return self.row[column]
    
class SingletonHolder(object):
    INSTANCE = DatabaseManager()
            
