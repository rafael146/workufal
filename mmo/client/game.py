# -*- coding: utf-8 -*-
import sys, pygame, thread
from codec import PacketWriter, PacketReader
from copy import copy
from concurrent import ThreadPoolManager
from pygame.locals import *
from pgu import text, gui, timer
from socket import socket
from packets import *

WIDTH, HEIGHT = 800, 640
pygame.font.init()
screen = pygame.display.set_mode((WIDTH,HEIGHT))
pygame.display.set_caption("PyMMO")
font = pygame.font.SysFont("default", 24)

class GameState(object):
   CONNECTED=1
   CONNECTING=2
   WAIT=3

class Game(gui.App):
   def __init__(self):
      gui.App.__init__(self)
      self.running = 0
      self.client = None
      self.login = Login(self)
      self.State = GameState.WAIT

   def init(self, widget=None, screen=None, area=None):
      self.running = 1
      super(Game, self).init(widget,screen,area)
      
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
      print "setting key"
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

class Login(gui.Table):
   def __init__(self,game,**params):
      gui.Table.__init__(self,**params)
      self.game = game
      self.form = gui.Form()
      font = pygame.font.SysFont("helvetica",12)
      self.txt = gui.Label("",font=font)  
      fg = (145,200,220)
      self.tr()
      self.td(gui.Label("Username: "),color=fg)
      self.td(gui.Input(size=16, name="user"), colspan=2)
      self.tr()
      self.td(gui.Label("Password: "),color=fg)
      self.td(gui.Password(size=16, name="pwd"), colspan=2)
      self.tr()
      self.td(gui.Label(""))
      bt= gui.Button("join")
      bt.connect(gui.CLICK, self.onJoin)
      self.td(bt,align=1)
      bt = gui.Button("Exit")
      bt.connect(gui.CLICK, self.onExit)
      self.td(bt,align=0)
      self.tr()
      self.td(self.txt,align=0,colspan=5)

   def onExit(self):
      print "exiting"
      self.game.running = False
      if self.game.client:
         self.game.client.writePacket(Close())

   def onJoin(self):
      self.txt.set_text("Requesting Login")
      try:
         self.game.connectToServer()
      except Exception, e:
         print e
         self.writeTxt("Can't Resolve Hostname")
      #print "Requesting Login",self.form['user'].value,self.form['pwd'].value

   def writeTxt(self, txt):
      self.txt.set_text(txt)

   @property
   def user(self):
      return self.form['user'].value
   
   @property
   def pwd(self):
      return self.form['pwd'].value
      

game = Game()

ctn = gui.Container()
ctn.add(game.login,0,0)
game.init(ctn, screen)
clock = timer.Timer(7)

while game.running:
   for ev in pygame.event.get():
      if ev.type is QUIT: game.running = False
      game.event(ev)

   clock.tick()
   screen.fill((0,145,120))
   game.paint()
   pygame.display.flip()
