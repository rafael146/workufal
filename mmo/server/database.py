import MySQLdb

#Move this values to config
HOST = '127.0.0.1'
USER = 'root'
PWD  = 'root'
DB = 'mmo'

class DatabaseManager(object):
    def __init__(self):
        self.db = MySQLdb.Connect(HOST,USER,PWD,DB)
        
    @staticmethod
    def getInstance():
        return SingletonHolder.INSTANCE

    def query(self, sql, *params):
        cur = self.db.cursor()
        cur.execute(sql, *params)
        r = ResultSet(cur.fetchall())
        cur.close()
        return r
    
class ResultSet(object):
    def __init__(self, result):
        self.result = list(result)

    def hasNext(self):
        return len(self.result)

    def next(self):
        return self.result.pop(0)

    def empty(self):
        return len(self.result)==0
    
class SingletonHolder(object):
    INSTANCE = DatabaseManager()
            
