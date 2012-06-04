# -*- coding: utf-8 -*-
import sys, pygame
from codec import PacketWriter, PacketReader
from concurrent import ThreadPoolManager
from copy import copy
from packets import *
from pygame.locals import *
from pygame.sprite import Sprite
from pgu import gui, engine
from re import match
from socket import socket

# XXX: Externalize all strings

# initialize only necessary modules making so speed up.
pygame.font.init()

# Constants
WIDTH, HEIGHT = 1200, 680
CENTER = WIDTH/2, HEIGHT/2
WHITE = (255,255,255)

screen = pygame.display.set_mode((WIDTH,HEIGHT),SRCALPHA)
pygame.display.set_caption("PyBot War")
font = pygame.font.SysFont("default", 18)
_font = pygame.font.SysFont("helvetica",14)
form = gui.Form()

# temporary
model1 = pygame.image.load('model.png').convert_alpha()

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
         self.label.set_text("Passwords not macht")
            
class Connection(socket):
   def __init__(self, game):
      socket.__init__(self)
      self.game = game
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
      
   def handlerConnection(self):
      while not self.game.quit:
         try:
            packet = self.recv(1024)
            if not packet:
               break
            self.reader.process(packet)
         except Exception, e:
            print e
            break
      else:
         self.shutdown(2)
         self.close()
      self.game.GState = GameState.WAIT

   @property
   def player(self):
      return self.game.player

class Game(engine.Game):
   def __init__(self,screen):
      self.screen= screen
      self.connection = None
      self.player = None
      self.GState = GameState.WAIT
      self.run(Login(self),self.screen)
      
   def connectToServer(self):
      if self.GState == GameState.WAIT:
         self.GState = GameState.CONNECTING
         self.connection = Connection(self)

   def toCharScreen(self, hasPlayer, ID=None, name=None, model=None):
      self.player = None
      self.state = CharacterScreen(self, hasPlayer, ID, name, model)

   def enterWorld(self):
      self.state = World(self)

   def toLoginScreen(self):
      self.player = None
      self.state = Login(self)

   def close(self):
      self.quit = 1
      if self.connection:
         self.writePacket(Logout())

   def event(self, evt):
      if evt.type is QUIT:
         self.close()

   def writePacket(self, packet):
      self.connection.writePacket(packet)

   def updatePlayer(self, model, ID, name, level, speed, maxHp, hp, x, y, heading, defense, force, exp):
      if self.player:
         self.player.speed = speed
         self.player.maxHp = maxHp
         self.player.hp = hp
         self.player.x = x
         self.player.y = y
         self.player.heading = heading
         self.player.defense = defense
         self.player.force = force
         self.player.exp = exp
         self.level = level
         self.player.turn(self.player.heading)
      else:
         self.player = Player(model, ID, name, level, speed, maxHp, hp, x, y, heading, defense, force, exp)

   def showCharacter(self, ID, name, model, x, y, heading):
      # if char is in knowlist only update location
      for char in self.player.knownlist:
         if char.ID == ID:
            char.x = x
            char.y = y
            char.heading = heading
            char.turn(heading)
            break
      else:
         self.player.knownlist.add(Character(ID,name,model,x,y,heading))
         
class Character(Sprite):
   def __init__(self, ID, name, model, x, y, heading):
      self.ID = ID
      self.name = name
      self.model = model
      #make others models and initialize rotation with heading
      #self.image = pygame.transform.rotate(eval("model"+str(model)), heading)
      self.image = model1
      self.rect = self.image.get_rect()
      self.x = x
      self.y = y
      self.heading = heading

   def turn(self, degree):
      self.heading = degree
      center = self.rect.center
      #self.image = pygame.transform.rotate(eval("model"+str(self.model)), self.heading)
      self.image = pygame.transform.rotate(model1, self.heading)
      self.rect = self.image.get_rect(center=center)
      
class Player(Character):
   def __init__(self, model, ID, name, level, speed, maxHp, hp, x, y, heading, defense, force, exp):
      Character.__init__(self, ID, name, model, x, y, heading)
      self.level = level
      self.maxHp = maxHp
      self.hp = hp
      self.speed = speed
      # must be persistent date
      self.target = None
      self.knownlist = set()

   def fire(self):
      if self.target:
         print 'firing'

   def cancelTarget(self):
      self.target = None
         
class Bar(object):
   def __init__(self, player=None):
      self.player = player
      self.surf = pygame.Surface((160, 60), flags=SRCALPHA)
      # for target porpuse
      self.t_surf = pygame.Surface((100, 40), flags=SRCALPHA)

   def update(self):
      self.surf.fill((0,0,0,75))
      self.surf.blit(_font.render("level: "+str(self.player.level),1,WHITE),(4,0))
      self.surf.blit(_font.render("name: "+self.player.name,1,WHITE),(4,20))
      self.surf.blit(_font.render("hp: %s / %s"%(str(self.player.hp), str(self.player.maxHp)),1,WHITE),(4,40))
      screen.blit(self.surf, (5,0))
      if self.player.target:
         self.t_surf.fill((0,0,0,75))
         name = self.player.target.name
         dx,dy = _font.size(name)
         self.t_surf.blit(_font.render(name,1,WHITE), (50-dx/2,20-dy/2))
         screen.blit(self.t_surf,(WIDTH/2-50,0))

class World(engine.State):
   def __init__(self, game):
      self.game = game
      self.player = game.player
      self.bar = Bar(self.player)

   def update(self, screen):
      for x in xrange(WIDTH/40):
         for y in xrange(HEIGHT/40):
            screen.blit(pygame.image.load("tile1.png"),(x*40,y*40))
      self.blitOwner(screen)
      for char in self.player.knownlist:
         self.blitChar(char)
      self.bar.update()
      pygame.display.flip()

   def blitOwner(self,screen):
      # player must always appear on center screen
      name = self.player.name
      size = _font.size(name)
      screen.blit(_font.render(name,1,WHITE),(CENTER[0]-size[0]/2,CENTER[1]-40))
      self.player.rect.center = (CENTER)
      screen.blit(self.player.image, (CENTER[0]-20,CENTER[1]-25))

   def blitChar(self, char):
      dx = CENTER[0] - self.player.x + char.x-20
      dy = CENTER[1] - self.player.y + char.y-25
      size = _font.size(char.name)
      screen.blit(_font.render(char.name,1,WHITE),(dx-size[0]/2+20,dy-15))
      screen.blit(char.image, (dx, dy))
      char.rect.center = dx+20,dy+25

   def event(self, evt):
      if evt.type == MOUSEBUTTONDOWN:
         for char in self.player.knownlist:
            if char.rect.collidepoint(evt.pos):
               self.onAction(char)
               break
      if evt.type == KEYDOWN:
         if evt.key == K_w:
            self.game.writePacket(Move(1))
         elif evt.key == K_s:
            self.game.writePacket(Move(2))
         elif evt.key == K_d:
            self.game.writePacket(Move(3))
         elif evt.key == K_a:
            self.game.writePacket(Move(4))
         elif evt.key == K_f:
            self.player.fire()
         elif evt.key == K_ESCAPE:
            self.game.writePacket(CancelTarget())
            self.player.cancelTarget()

   def onAction(self, character):
      self.game.writePacket(Action(character.ID))

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
         # These buttons must be changed for Images of models
         bt = gui.Button("First Model")
         bt.connect(gui.CLICK, self.create, 1)
         self.tb.td(bt)
         bt = gui.Button("Second Model")
         bt.connect(gui.CLICK, self.create, 2)
         self.tb.td(bt)
         bt = gui.Button("Third Model")
         bt.connect(gui.CLICK, self.create, 3)
         self.tb.td(bt)
         self.tb.tr()
         bt = gui.Button("Logout")
         bt.connect(gui.CLICK, self.logout)
         self.tb.td(bt, colspan=3)
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
      self.game.writePacket(CharacterDelete(ID))

   def logout(self):
      self.game.writePacket(Logout())
      self.game.toLoginScreen()
      
   def create(self, model):
      # I don't like this, this check must be server side.
      charname = form['pname'].value
      m = match('\w{3,16}', charname)
      if m and m.group() == charname:
         self.game.writePacket(CharacterCreate(charname,model))
      else:
         self.write("Invalid Name! Your Charname must be only alphanumeric with 3 to 16 chars")

   def enterWorld(self, ID):
      self.game.writePacket(EnterWorld(ID))
         
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
         self.game.connection = None
         self.game.GState = GameState.WAIT

   @property
   def user(self):
      return form['user'].value
   
   @property
   def pwd(self):
      return form['pwd'].value

Game(screen)
