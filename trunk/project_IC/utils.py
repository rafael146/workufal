# -*- coding: utf-8 -*-
# Autor: Alisson Oliveira
from math import sqrt

def Baskara(a,b,c):
    """
        Função que retorna as raizes da equação.
    """
    if(a==b==c==0): # caso todos os coeficientes forem 0 então não teremos uma equação.
        return () # retorne um valor vazio.
    if(a==0): # Se a for 0 então temos uma equação do 1º grau logo dividimos o inverso de c por b.
        if(b==0): # caso b for 0 então retornaremos um valor vazio, pois é impossivel dividir por b.
            return ()
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
    
def separar_coeficientes(membro):
    """
        Essa função recebe um membro da equação esepara os coeficientes em um dicionario de listas onde os
        coeficientes de cada variavel são guardados.

        i.e dado a equação 2x²+2x+2-4x²+8x obteremos o o resultado:

        numeros = { "x2":[2,-4], "x":[2,+8], "var":[2]}
    """
    i=0 #indice que vai percorrer o tamanho do membro
    last = 0 #ultimo indice adicionado ao dicionario
    numeros = { "x2":[], "x" : [], "var":[]} #inicializar o dicionario
    signal = False # se o indice encontrado é um sinal
    while(i < len(membro)):  # enquanto o indice for menor que o tamanho do membro
        if membro[i].isalpha() and membro[i] != "x": # caso haja uma variavel diferente de X
            return  # retorne um valor vazio.
        if not membro[i].isalnum(): # se o membro não for número nem letra, provavelmente será um sinal
            if signal or (i > 0 and last == 0):  # se já tiver encontrado outro sinal antes ou o ultimo indice usado é 0
                numeros["var"].append(membro[last:i]) # add o valor na lista de números
                last = i # estabeleça o último indice usado
                signal = False # limpe o valor de signal encontrado
            else: # se for o primeiro sinal encontrado
                signal = True
        elif membro[i] == "x": #se o charactere no indice for a variavel x
            if membro[i+1:i+3] == "²": # se o proximo caractere for ²
                numeros["x2"].append(membro[last:i]) # add o número na lista de coeficientes de X²
                i +=3 # incremente o indice até o último caractere usado
                last = i # estabeleça o último indice usado
            else: # se o proximo caracterer não for ²
                numeros["x"].append(membro[last:i]) # add o número na lista de coeficientes de x
                last = i+1 # estabeleça o último indice usado
        elif i == len(membro)-1: # se for o último caractere e for um número
            numeros["var"].append(membro[last:]) # add o número na lista de números
        i+=1 # incremente o indice a cada loop.
    return numeros # retorne o dicionario de valor

def somar_semelhantes(membro):
    """
        Essa funçao recebe um dicionario no formato {"x2":[...], "x":[...], "var":[...]}
        e retorna uma lista com a soma dos números contidos no valor de cada chave do dicionario
    """
    a=b=c=0 # inicializa as variaveis com um valor 0
    x2 = membro["x2"] # guarde uma referencia da lista da chave x2
    x = membro["x"] # guarde uma referencia da lista da chave x
    var = membro["var"] #guarde uma referencia da lista da chave var

    # para cada número em cada lista incremente o valor da variavel a, b e c.
    for i in x2:
        a += (float(i)) # converte o número pra float e soma com o valor de a
    for i in x:
        b += (float(i)) # converte o número pra float e soma com o valor de a
    for i in var:
        c += (float(i)) # converte o número pra float e soma com o valor de a

    return [a,b,c] # retorne uma lista com os valores das variaveis.

def mesclar_membros(membros):
    """
        Essa função recebe um array de dois elementos, com os valores do membros da equação
        e retorna uma lista com a soma dos coeficientes.
    """
    membro = separar_coeficientes(membros[0]) # separe os coeficientes, e concatene todos cujo as váriaveis são do mesmo tipo.
    membro2 = separar_coeficientes(membros[1]) # separe os coeficientes, e concatene todos cujo as váriaveis são do mesmo tipo.
    try: # tente
        somado = somar_semelhantes(membro) # somar todos os números obtidos da separação dos coeficientes
        somado2 = somar_semelhantes(membro2) # somar todos os números obtidos da separação dos coeficientes
    except: # se houver algo errado retorne um valor vazio
        return
    resultado = organize(somado, somado2) # some os valores das listas
    return resultado # retorne o resultado
      
def organize(membro, membro2):
    """
        Essa função recebe os dois membros da equação, e retorna uma lista com a soma dos dois membros
    """
    resultado = [0,0,0] # inicialize uma variavel
    for i in range(len(membro2)): # calcule o inverso de cada elemento do segundo membro para coloca-los no primeiro.
        membro2[i] *= (-1)

    for i in range(3): # some os termos semelhantes nos dois membros.
        resultado[i] = membro[i] + (membro2[i])

    return resultado # retorne a lista com o valor resultante
    
def _formataEquacao(a,b,c):
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
        Função que recebe o resultado da função Baskara e formata a saida.
    """
    if(len(resultado) == 1): # se o resultado da função Baskara for um tupla com tamanho 1, ou seja, contém apenas um elemento
        return("a equação tem apenas uma raiz %.1f\n" % (resultado))
    elif(len(resultado) == 2): # caso contenha dois elementos no resultado
        return("a equação tem duas raizes %.1f e %.1f\n" % (resultado))
    else: # se a equação não tem raiz o função Baskara retornará uma tupla vazia
        return("a equação não tem raiz\n")

# isso só executado caso, este arquivo seja executado diretamente.
if __name__ == "__main__":
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
    _formataEquacao(a,b,c)

    # calcule e mostre qual o resultado da equação.
    mostrarResultado(Baskara(a,b,c))
