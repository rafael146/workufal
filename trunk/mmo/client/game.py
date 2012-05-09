# -*- coding: utf-8 -*-
import sys, pygame
from codec import PacketWriter, PacketReader
from concurrent import ThreadPoolManager
from copy import copy
from packets import *
from pygame.locals import *
from pgu import gui, engine
from socket import socket

WIDTH, HEIGHT = 1200, 680
WHITE = (255,255,255)
pygame.font.init()
screen = pygame.display.set_mode((WIDTH,HEIGHT))
pygame.display.set_caption("PyBot War")
font = pygame.font.SysFont("default", 18)
_font = pygame.font.SysFont("helvetica",16)
form = gui.Form()

class GameState(object):
   CONNECTED=1
   CONNECTING=2
   WAIT=3

class Register(gui.Dialog):
   def __init__(self,**params):
      title = gui.Label("Register Form")
      t = gui.Table(vpadding=3)
      t.tr()
      t.td(gui.Label("Username: "))
      t.td(gui.Input(size=16, name="r_user"), colspan=2)
      t.tr()
      t.td(gui.Label("Password: "))
      t.td(gui.Password(size=16, name="r_pwd"), colspan=2)
      t.tr()
      t.td(gui.Label("Password: "))
      t.td(gui.Password(size=16, name="rc_pwd"), colspan=2)
      t.tr()
      bt= gui.Button("Register", width=40)
      bt.connect(gui.CLICK, self.register)
      t.td(bt,align=1, colspan=2)
      bt = gui.Button("Cancel", width=40)
      bt.connect(gui.CLICK, self.close)
      t.td(bt,align=0)
      t.tr()
      self.label = gui.Label("",color=(255,10,10))
      t.td(self.label, colspan=3, align=0)
      gui.Dialog.__init__(self,title,t)

   def register(self):
      user  = form['r_user'].value
      pwd   = form['r_pwd'].value
      c_pwd = form['rc_pwd'].value
      if pwd == c_pwd:
         if pwd != '' and user != '':
            try:
               s = socket()
               s.connect(('',7778))
               s.send(user+"\00"+pwd)
               r = s.recv(1024)
               s.shutdown(2)
               s.close()
               if r == '1':
                  self.label.set_text("User registered sucessful")
               elif r== '0':
                  self.label.set_text("User already exists")
            except Exception,e:
               print e
               self.label.set_text("Can't Resolve Hostname. Try Again later")
         else:
            self.label.set_text("Invalid User or Password!")
      else:
         self.label.set_text("Password not macht")
            
class Connection(socket):
   def __init__(self, game, user):
      self.game = game
      self.user = user
      socket.__init__(self)
      self.reader = PacketReader(self)
      self.writer = PacketWriter(self)
      self.connect('',7777)
      
   def connect(self, host, port):
      super(Connection, self).connect((host,port))
      ThreadPoolManager.getInstance().generalExecuter(self.handlerConnection,0)

   def writePacket(self, packet):
      self.send(self.writer.process(packet))

   def setKey(self, key):
      key2 = copy(key)
      self.reader.setKey(key)
      self.writer.setKey(key2)
      self.writer.isKeySetted = True
      self.game.State = GameState.CONNECTED
      
   def handlerConnection(self):
      while not self.game.quit:
         try:
            packet = self.recv(1024)
            if not packet:
               break
            self.reader.process(packet)
         except Exception, e:
            self.game.client = None
            print e
            break
      else:
         self.shutdown(2)
         self.close()
      self.game.GState = GameState.WAIT

class Game(engine.Game):
   def __init__(self,screen):
      self.screen= screen
      self.client = None
      self.GState = GameState.WAIT
      #self.run(Login(self),self.screen)
      self.run(World(self),self.screen)
      
   def connectToServer(self):
      if self.GState == GameState.WAIT:
         self.GState = GameState.CONNECTING
         self.client = Connection(self, self.state.user)

   def toCharScreen(self, hasPlayer, ID=None, name=None, model=None):
      self.state = CharacterScreen(self, hasPlayer, ID, name, model)

   def enterWorld(self):
      self.state = World(self)

   def toLoginScreen(self):
      self.state = Login(self)

   def close(self):
      self.quit = 1
      if self.client:
         self.client.writePacket(Logout())

   def event(self, evt):
      if evt.type is QUIT:
         self.close()

class Chat(gui.Container):
   def __init__(self):
      gui.Container.__init__(self, align=-1, valign=1)
      tb = gui.Table(width=240,height=260)
      tb.tr()
      lines = gui.Table(background=(100,20,100,0))
      box = gui.ScrollArea(lines,260,260,hscrollbar=False)
      tb.td(box)
      tb.tr()
      tb.td(gui.Input(size=28, background=(0,0,0,0)))
      self.add(tb,0,0)

class World(engine.State):
   def __init__(self, game):
      self.game = game
      self.app = gui.App()
      self.app.init(Chat(),screen)

   def update(self, screen):
      for x in xrange(WIDTH/40):
         for y in xrange(HEIGHT/40):
            screen.blit(pygame.image.load("tile1.png"),(x*40,y*40))
      self.app.paint()
      pygame.display.flip()

   def event(self, evt):
      self.app.event(evt)

class CharacterScreen(engine.State):
   """
      Model 1:
         normal speed, normal resist, normal force
      Model 2:
         less speed, normal resist, more force
      Model 3:
         more speed, less resist, normal force

   """
   def __init__(self,game,hasPlayer,ID,name,model):
      self.game = game
      self.txt = None
      self.tb = gui.Table(vpadding=3, hpadding=3)
      self.tb.tr()
      if not hasPlayer:
         self.tb.td(gui.Label("Choose your Style"), colspan=3, align=0)
         self.tb.tr()
         self.tb.td(gui.Label("Character:"))
         self.tb.td(gui.Input(size=16, name="pname"), colspan=2, align=-1)
         self.tb.tr()
         # These buttons must be changed for Images
         bt = gui.Button("First Model")
         bt.connect(gui.CLICK, self.create, 1)
         self.tb.td(bt)
         bt = gui.Button("Second Model")
         bt.connect(gui.CLICK, self.create, 2)
         self.tb.td(bt)
         bt = gui.Button("Third Model")
         bt.connect(gui.CLICK, self.create, 3)
         self.tb.td(bt)
      else:
         self.tb.td(gui.Label(name), colspan=3)
         self.tb.tr()
         self.tb.td(gui.Button("Model "+str(model)), colspan=3)
         self.tb.tr()
         bt = gui.Button("Enter in World")
         bt.connect(gui.CLICK, self.enterWorld, ID)
         self.tb.td(bt)
         bt = gui.Button("Delete Character")
         bt.connect(gui.CLICK, self.delChar, ID)
         self.tb.td(bt)
         bt = gui.Button("Logout")
         bt.connect(gui.CLICK, self.logout)
         self.tb.td(bt)
      self.app = gui.App()
      self.app.init(self.tb,screen)

   def event(self, evt):
      self.app.event(evt)

   def update(self, screen):
      screen.fill((0,145,120))
      self.app.paint()
      self.showTxt(screen)
      pygame.display.flip()

   def delChar(self, ID):
      self.game.client.writePacket(CharacterDelete(ID))

   def logout(self):
      self.game.client.writePacket(Logout())
      self.game.toLoginScreen()
      
   def create(self, model):
      charname = form['pname'].value
      if charname.isspace() or charname == '':
         self.write("Choose a name")
      else:
         self.game.client.writePacket(CharacterCreate(charname,model))

   def enterWorld(self, ID):
      self.write("entering world with "+str(ID))
      self.game.enterWorld()
         
   def write(self, txt):
      self.txt = pygame.Surface((WIDTH, 40), flags=SRCALPHA)
      self.txt.fill((0,0,0,100))
      x,y = _font.size(txt)
      self.txt.blit(_font.render(txt,1,WHITE),(WIDTH/2-x/2,y-9))

   def showTxt(self, screen):
      if self.txt:
         screen.blit(self.txt,(0,HEIGHT-60))

class Login(engine.State):
   def __init__(self,game):
      self.game = game
      self.txt =None
      self.ctn = gui.Table(width=WIDTH, height=HEIGHT)
      self.tb = gui.Table(vpadding=3)
      self.tb.tr()
      self.tb.td(gui.Label("Username: ",color=WHITE))
      self.tb.td(gui.Input(size=16, name="user"), colspan=2)
      self.tb.tr()
      self.tb.td(gui.Label("Password: ",color=WHITE))
      self.tb.td(gui.Password(size=16, name="pwd"), colspan=2)
      self.tb.tr()
      bt= gui.Button("Register")
      bt.connect(gui.CLICK, self.register)
      self.tb.td(bt,align=-1)
      bt= gui.Button("Login", width=40)
      bt.connect(gui.CLICK, self.onJoin)
      self.tb.td(bt, align=1)
      bt = gui.Button("Exit", width=40)
      bt.connect(gui.CLICK, self.onExit)
      self.tb.td(bt,align=0)

   def write(self, txt):
      self.txt = pygame.Surface((WIDTH, 40), flags=SRCALPHA)
      self.txt.fill((0,0,0,100))
      x,y = _font.size(txt)
      self.txt.blit(_font.render(txt,1,WHITE),(WIDTH/2-x/2,y-9))

   def showTxt(self, screen):
      if self.txt:
         screen.blit(self.txt,(0,HEIGHT-60))

   def paint(self, screen):
      self.app = gui.App()
      self.app.init(self.tb, screen)

   def event(self, evt):
      self.app.event(evt)

   def update(self, screen):
      screen.fill((0,145,120))
      self.app.paint()
      self.showTxt(screen)
      pygame.display.flip()

   def onExit(self):
      self.game.close()

   def register(self):
      r = Register()
      r.open()

   def onJoin(self):
      try:
         self.game.connectToServer()
      except Exception, e:
         print e
         self.write("Can't Resolve Hostname. Try again later")

   @property
   def user(self):
      return form['user'].value
   
   @property
   def pwd(self):
      return form['pwd'].value

Game(screen)
