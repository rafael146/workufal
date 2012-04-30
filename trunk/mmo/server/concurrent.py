# Autor: Alisson Oliveira
from abc import ABCMeta, abstractmethod
from threading import Timer, Thread
from abc import *

class Runnable():
   __metaclass__ = ABCMeta
   @abstractmethod
   def run(self):
      pass

class ScheduledThread(Thread):
   def __init__(self, runnable, delay):
      self.runnable = runnable
      self.delay = delay
      self.scheduler = Timer
      Thread.__init__(self)
      self.setDaemon(True)
      
   def run(self):
      self.scheduler(self.delay, self.runnable.run).start()

class ScheduledThreadAtFixedRate(Thread):
   def __init__(self, runnable, initial, delay):
      self.active = None
      self._canceled=False
      self.delay = delay
      self.runnable = runnable
      self.scheduler = Timer
      self.scheduler(initial, self.run).start()
      Thread.__init__(self)
      self.setDaemon(True)

   def run(self):
      if not self._canceled:
         self.runnable.run()
         self.active = self.scheduler(self.delay, self.run)
         self.active.start()

   def cancel(self):
      self._canceled = True
      if self.active:
         self.active.cancel()
      
class ThreadPoolManager(object):
   def __init__(self):
      self.Scheduler = ScheduledThread
      self.SchedulerAtRate = ScheduledThreadAtFixedRate
      self.Executor = Timer

   @staticmethod
   def getInstance():
      return SingletonHolder.INSTANCE

   def generalExecuter(self, func, delay, *args, **kargs):
      t = self.Executor(delay, func, *args, **kargs)
      t.setDaemon(True)
      t.start()

   def scheduleGeneral(self, runnable, delay=0, *args):
      return self.Scheduler(runnable, delay).start()

   def scheduleAtFixedRate(self, runnable, initial, delay):
      return self.SchedulerAtRate(runnable,initial,delay)


class SingletonHolder(object):
   INSTANCE = ThreadPoolManager()

class Mytest(Runnable):
   def run(self):
      print "running runnable"

if __name__ == '__main__':
   c = Mytest()
   a = ThreadPoolManager.getInstance().scheduleAtFixedRate(c,5,10)
   b = ThreadPoolManager.getInstance().generalExecuter(a.cancel, 30)

