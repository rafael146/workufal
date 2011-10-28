# -*- coding: utf-8 -*-
# Autor: Alisson Oliveira
from math import sqrt

def Baskara(a,b,c):
    """
        Função que retorna as raizes da equação.
    """
    if(a==0): # Se a for 0 então temos uma equação do 1º grau logo dividimos o inverso de c por b.
        return (-c/b,)
    elif(b==0): # Se b for 0.
        try: # tentamos revolver a raiz quadrada do inverso de c dividido por a.
            result = sqrt((-c/a))
        except ValueError: # se o resultado da divisão de c por a for um número menor que 0, retornaremos um valor vazio, pois não existe raiz quadrada de números negativos.
            return ()
        return (result,) # se tudo for bem então retornaremos o resultado da expressão.
    elif(c==0): # if c for 0, então a primeira raiz sempre será 0 e a segunda será o inverso de b dividido por a.
        return (0, (-b/a))

    # se todos os números recebidos for diferente de 0 então calcularemos o delta.
    delta = (b**2) - (4*a*c)
    if delta == 0: # se delta for 0 então haverá apenas uma raiz, retornaremos o inverso de b dividido pelo dobro de a.
        return (-b/(2*a),)
    elif delta < 0: # delta for menor que zero, retornaremos um valor vazio pois não existe raiz quadrada de números negativos.
        return ()
    else: # se o delta for um número maior que 0 então teremos duas raizes.
        return ((-b + sqrt(delta))/(2*a), (-b - sqrt(delta))/(2*a))

def formataEquacao(a,b,c):
    """
        Funçao que formata os números recebidos do úsuario em forma de equação 
        e imprime na tela.
    """
    # a base da string. colocamos o valor de a já na base pois se a for menor ou maior que 0 não vai mudar não vai deformar a saida.
    saida = "a equação dada foi %dx²"
    if(b<0): # se b for menor que 0 não incluimos o sinal na string pois ele é incluido automaticamente.
        saida += "%dx"
    else: # caso contrario devemos incluir explicitamente o sinal.
        saida += "+%dx"
    if(c<0): # se c for menor que 0 não incluimos o sinal na string pois ele é incluido automaticamente.
        saida += "%d=0"
    else: # se c for menor que 0 não incluimos o sinal na string pois ele é incluido automaticamente.
        saida += "+%d=0"
    # agora só mostrar o resultado para o úsuario
    print (saida % (a,b,c))

def mostrarResultado(resultado):
    """
        Função que recebe o resultado da função formBaskara e formata a saida.
    """
    if(len(resultado) == 1): # se o resultado da função formBaskara for um tupla com tamanho 1, ou seja, contém apenas um elemento
        print("a equação tem apenas uma raiz %.1f" % (resultado))
    elif(len(resultado) == 2): # caso contenha dois elementos no resultado
        print("a equação tem duas raizes %.1f e %.1f" % (resultado))
    else: # se a equação não tem raiz o função formBaskara retornará uma tupla vazia
        print("a equação não tem raiz")

# Onde Começa o programa
#imprima na tela a informação.
print("Resovendo equação quadratica no formato ax²+bx+c=0")

# receba do úsuario os 3 coeficientes da equação
try:
    a = float(input("Entre com o valor de a: "))
    b = float(input("Entre com o valor de b: "))
    c = float(input("Entre com o valor de c: "))
except:
    print("use apenas números")

# agora mostre a equação resultante dos coeficientes recebidos.
formataEquacao(a,b,c)

# calcule e mostre qual o resultado da equação.
mostrarResultado(Baskara(a,b,c))
