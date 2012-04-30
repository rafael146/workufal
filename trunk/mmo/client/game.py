# -*- coding: utf-8 -*-
import sys, pygame
from codec import PacketWriter, PacketReader
from concurrent import ThreadPoolManager
from copy import copy
from packets import *
from pygame.locals import *
from pgu import text, gui, timer
from socket import socket

WIDTH, HEIGHT = 1200, 680
WHITE = (255,255,255)
pygame.font.init()
screen = pygame.display.set_mode((WIDTH,HEIGHT))
pygame.display.set_caption("PyMMO")
font = pygame.font.SysFont("default", 24)

class GameState(object):
   CONNECTED=1
   CONNECTING=2
   WAIT=3

class Register(gui.Dialog):
   def __init__(self,**params):
      title = gui.Label("Register Form")
      self.form = gui.Form()
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
      user = self.form['r_user'].value
      pwd  = self.form['r_pwd'].value
      c_pwd= self.form['rc_pwd'].value
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
      

class Login(gui.Table):
   def __init__(self,game):
      gui.Table.__init__(self, width=WIDTH, height=HEIGHT)
      self.game = game
      font = pygame.font.SysFont("helvetica",12)
      self.txt = gui.Label("",font=font,color=WHITE)
      self.form = gui.Form()
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
      self.add(self.tb,WIDTH/2-110,HEIGHT/2-60)
      self.add(self.txt,WIDTH/2-self.txt.style.width/2,HEIGHT-self.txt.style.height-10)
      
   def onExit(self):
      print "exiting"
      self.game.running = False
      if self.game.client:
         self.game.client.writePacket(Close())

   def register(self):
      r = Register()
      r.open()

   def onJoin(self):
      self.writeTxt("Requesting Login")
      try:
         self.game.connectToServer()
      except Exception, e:
         print e
         self.writeTxt("Can't Resolve Hostname. Try again later")

   def writeTxt(self, txt):
      self.txt.set_text(txt)

   @property
   def user(self):
      return self.form['user'].value
   
   @property
   def pwd(self):
      return self.form['pwd'].value

class Game(gui.App):
   def __init__(self):
      gui.App.__init__(self)
      self.running = 0
      self.client = None
      self.ctn = gui.Container(height=HEIGHT,width=WIDTH)
      self.login = Login(self)
      self.ctn.add(self.login,0,0)
      self.State = GameState.WAIT

   def init(self, screen=None, area=None):
      self.running = 1
      super(Game, self).init(self.ctn,screen,area)
      
   def connectToServer(self):
      if self.State == GameState.WAIT:
         self.State = GameState.CONNECTING
         self.client = Client(self, self.login.user)
      
class Client(socket):
   def __init__(self, game, user):
      self.game = game
      self.user = user
      socket.__init__(self)
      self.connect('',7777)
      self.reader = PacketReader(self)
      self.writer = PacketWriter(self)
      
   def connect(self, host, port):
      super(Client, self).connect((host,port))
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
      while self.game.running:
         try:
            packet = self.recv(1024)
            if not packet:
               break
            self.reader.process(packet)
         except Exception, e:
            print e
            break
      self.shutdown(2)
      self.close()
      self.game.State = GameState.WAIT
      self.client = None
      
game = Game()
game.init(screen)
clock = timer.Timer(7)

while game.running:
   for ev in pygame.event.get():
      if ev.type is QUIT: game.running = False
      game.event(ev)

   clock.tick()
   screen.fill((0,145,120))
   game.paint()
   pygame.display.flip()
