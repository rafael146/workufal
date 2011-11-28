#-*- coding: utf-8 -*-
# Autor: Alisson Oliveira

class ExpressaoException(Exception):
   pass

class InvalidOperationError(ExpressaoException):
   pass

class EquacaoException(ExpressaoException):
   pass

class InvalidEquacaoException(EquacaoException):
   pass

class EmptyEquacaoException(EquacaoException):
   pass

class InvalidArgumentException(Exception):
   pass
