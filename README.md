# Arvore-AVL

          ÁRVORES AVL BALANCEADAS
=================================================

ÁRVORE 1 - Sequência: 40, 20, 60, 10, 30, 25

       30 (h=3, FB=0)
      /            \
   20 (h=2, FB=0)   40 (h=2, FB=-1)
   /      \              \
10 (h=1)  25 (h=1)       60 (h=1)

Fatores de Balanceamento:
- 30: FB = 0 (perfeitamente balanceado)
- 20: FB = 0 (perfeitamente balanceado)
- 40: FB = -1 (ligeiramente desbalanceado à direita)
- 10, 25, 60: FB = 0 (folhas)

=================================================

ÁRVORE 2 - Sequência: 60, 40, 80, 35, 50, 90, 20, 38, 37

           60 (h=4, FB=1)
          /              \
     38 (h=3, FB=0)       80 (h=2, FB=-1)
     /          \              \
35 (h=2, FB=0)  40 (h=2, FB=-1) 90 (h=1)
/       \              \
20 (h=1) 37 (h=1)       50 (h=1)

Fatores de Balanceamento:
- 60: FB = 1 (ligeiramente desbalanceado à esquerda)
- 38: FB = 0 (perfeitamente balanceado)
- 80: FB = -1 (ligeiramente desbalanceado à direita)
- 35: FB = 0 (perfeitamente balanceado)
- 40: FB = -1 (ligeiramente desbalanceado à direita)
- 20, 37, 50, 90: FB = 0 (folhas)

=================================================

ÁRVORE 3 - Sequência: 30, 20, 10, 25, 40, 50, 5, 35, 45

           30 (h=4, FB=0)
          /              \
     20 (h=3, FB=0)       40 (h=3, FB=0)
     /          \         /          \
10 (h=2, FB=0)  25 (h=1) 35 (h=1)    50 (h=2, FB=1)
/       \                              \
5 (h=1)  null                          45 (h=1)

Fatores de Balanceamento:
- 30: FB = 0 (perfeitamente balanceado)
- 20: FB = 0 (perfeitamente balanceado)
- 40: FB = 0 (perfeitamente balanceado)
- 10: FB = 0 (perfeitamente balanceado)
- 50: FB = 1 (ligeiramente desbalanceado à esquerda)
- 5, 25, 35, 45: FB = 0 (folhas)

=================================================

LEGENDA:
- h = altura do nó
- FB = Fator de Balanceamento (alturaEsquerda - alturaDireita)
- Todos os nós estão dentro do limite de balanceamento AVL

=======================
PSEUDOCÓDIGO - ÁRVORE AVL
=========================

ESTRUTURA DO NÓ
===============
Classe No {
    inteiro valor
    inteiro altura
    No filhoEsquerdo
    No filhoDireito
    No pai
    
    Construtor(inteiro valor) {
        this.valor = valor
        this.altura = 1
        this.filhoEsquerdo = null
        this.filhoDireito = null
        this.pai = null
    }
    
    Método calcularAltura() {
        inteiro alturaEsquerda = (filhoEsquerdo == null) ? 0 : filhoEsquerdo.altura
        inteiro alturaDireita = (filhoDireito == null) ? 0 : filhoDireito.altura
        this.altura = 1 + maximo(alturaEsquerda, alturaDireita)
    }
    
    Método calcularFatorBalanceamento() {
        inteiro alturaEsquerda = (filhoEsquerdo == null) ? 0 : filhoEsquerdo.altura
        inteiro alturaDireita = (filhoDireito == null) ? 0 : filhoDireito.altura
        retornar alturaEsquerda - alturaDireita
    }
}

=======================
ESTRUTURA DA ÁRVORE AVL
=======================
Classe ArvoreAVL {
    No raiz
    
    Método adicionar(inteiro valor) {
        raiz = adicionar(valor, raiz, null)
    }
    
    Método adicionar(inteiro valor, No noAtual, No noPai) {
        se (noAtual == null) {
            No novoNo = new No(valor)
            novoNo.pai = noPai
            retornar novoNo
        }
        
        se (valor > noAtual.valor) {
            No novoNo = adicionar(valor, noAtual.filhoDireito, noAtual)
            noAtual.filhoDireito = novoNo
            noAtual.calcularAltura()
        }
        senão se (valor < noAtual.valor) {
            No novoNo = adicionar(valor, noAtual.filhoEsquerdo, noAtual)
            noAtual.filhoEsquerdo = novoNo
            noAtual.calcularAltura()
        }
        senão {
            retornar noAtual // valor duplicado: ignora
        }
        
        retornar balancear(noAtual, valor)
    }
    
    Método balancear(No noAtual, inteiro valor) {
        inteiro fatorBalanceamento = noAtual.calcularFatorBalanceamento()
        
        // Caso LL (Left-Left)
        se (fatorBalanceamento > 1 E valor < noAtual.filhoEsquerdo.valor) {
            retornar rotacaoDireita(noAtual)
        }
        
        // Caso RR (Right-Right)
        se (fatorBalanceamento < -1 E valor > noAtual.filhoDireito.valor) {
            retornar rotacaoEsquerda(noAtual)
        }
        
        // Caso LR (Left-Right)
        se (fatorBalanceamento > 1 E valor > noAtual.filhoEsquerdo.valor) {
            noAtual.filhoEsquerdo = rotacaoEsquerda(noAtual.filhoEsquerdo)
            se (noAtual.filhoEsquerdo != null) {
                noAtual.filhoEsquerdo.pai = noAtual
            }
            retornar rotacaoDireita(noAtual)
        }
        
        // Caso RL (Right-Left)
        se (fatorBalanceamento < -1 E valor < noAtual.filhoDireito.valor) {
            noAtual.filhoDireito = rotacaoDireita(noAtual.filhoDireito)
            se (noAtual.filhoDireito != null) {
                noAtual.filhoDireito.pai = noAtual
            }
            retornar rotacaoEsquerda(noAtual)
        }
        
        retornar noAtual
    }
    
    Método rotacaoDireita(No noDesbalanceado) {
        No filhoEsquerdo = noDesbalanceado.filhoEsquerdo
        No filhoDireitoDoFilho = (filhoEsquerdo != null) ? filhoEsquerdo.filhoDireito : null
        
        // Atualiza ponteiros de pai
        se (filhoEsquerdo != null) {
            filhoEsquerdo.pai = noDesbalanceado.pai
        }
        se (filhoDireitoDoFilho != null) {
            filhoDireitoDoFilho.pai = noDesbalanceado
        }
        noDesbalanceado.pai = filhoEsquerdo
        
        // Realiza a rotação
        noDesbalanceado.filhoEsquerdo = filhoDireitoDoFilho
        se (filhoEsquerdo != null) {
            filhoEsquerdo.filhoDireito = noDesbalanceado
        }
        
        // Atualiza raiz se necessário
        se (filhoEsquerdo != null E filhoEsquerdo.pai == null) {
            raiz = filhoEsquerdo
        }
        senão se (filhoEsquerdo != null E filhoEsquerdo.pai != null) {
            se (filhoEsquerdo.pai.filhoEsquerdo == noDesbalanceado) {
                filhoEsquerdo.pai.filhoEsquerdo = filhoEsquerdo
            }
            senão se (filhoEsquerdo.pai.filhoDireito == noDesbalanceado) {
                filhoEsquerdo.pai.filhoDireito = filhoEsquerdo
            }
        }
        
        // Recalcula alturas
        noDesbalanceado.calcularAltura()
        se (filhoEsquerdo != null) {
            filhoEsquerdo.calcularAltura()
        }
        
        retornar (filhoEsquerdo != null) ? filhoEsquerdo : noDesbalanceado
    }
    
    Método rotacaoEsquerda(No noDesbalanceado) {
        No filhoDireito = noDesbalanceado.filhoDireito
        No filhoEsquerdoDoFilho = (filhoDireito != null) ? filhoDireito.filhoEsquerdo : null
        
        // Atualiza ponteiros de pai
        se (filhoDireito != null) {
            filhoDireito.pai = noDesbalanceado.pai
        }
        se (filhoEsquerdoDoFilho != null) {
            filhoEsquerdoDoFilho.pai = noDesbalanceado
        }
        noDesbalanceado.pai = filhoDireito
        
        // Realiza a rotação
        noDesbalanceado.filhoDireito = filhoEsquerdoDoFilho
        se (filhoDireito != null) {
            filhoDireito.filhoEsquerdo = noDesbalanceado
        }
        
        // Atualiza raiz se necessário
        se (filhoDireito != null E filhoDireito.pai == null) {
            raiz = filhoDireito
        }
        senão se (filhoDireito != null E filhoDireito.pai != null) {
            se (filhoDireito.pai.filhoEsquerdo == noDesbalanceado) {
                filhoDireito.pai.filhoEsquerdo = filhoDireito
            }
            senão se (filhoDireito.pai.filhoDireito == noDesbalanceado) {
                filhoDireito.pai.filhoDireito = filhoDireito
            }
        }
        
        // Recalcula alturas
        noDesbalanceado.calcularAltura()
        se (filhoDireito != null) {
            filhoDireito.calcularAltura()
        }
        
        retornar (filhoDireito != null) ? filhoDireito : noDesbalanceado
    }
    
    // Métodos de impressão
    Método printInOrder() {
        printInOrder(raiz)
        imprimir nova_linha
    }
    
    Método printInOrder(No no) {
        se (no == null) retornar
        printInOrder(no.filhoEsquerdo)
        imprimir no.valor + " "
        printInOrder(no.filhoDireito)
    }
}

==================
PROGRAMA PRINCIPAL
==================
Programa Main {
    Método main() {
        // Árvore 1: 40, 20, 60, 10, 30, 25
        ArvoreAVL arvore1 = new ArvoreAVL()
        inteiro[] sequencia1 = {40, 20, 60, 10, 30, 25}
        
        para cada valor em sequencia1 {
            arvore1.adicionar(valor)
        }
        
        imprimir "Arvore 1 - InOrder:"
        arvore1.printInOrder()
        imprimir "Arvore 1 - Nós (BFS) com filhos, alturas e fator de balanceamento:"
        printBfsWithChildren(arvore1.getRoot())
        
        // Árvore 2: 60, 40, 80, 35, 50, 90, 20, 38, 37
        ArvoreAVL arvore2 = new ArvoreAVL()
        inteiro[] sequencia2 = {60, 40, 80, 35, 50, 90, 20, 38, 37}
        
        para cada valor em sequencia2 {
            arvore2.adicionar(valor)
        }
        
        imprimir "Arvore 2 - InOrder:"
        arvore2.printInOrder()
        imprimir "Arvore 2 - Nós (BFS) com filhos, alturas e fator de balanceamento:"
        printBfsWithChildren(arvore2.getRoot())
        
        // Árvore 3: 30, 20, 10, 25, 40, 50, 5, 35, 45
        ArvoreAVL arvore3 = new ArvoreAVL()
        inteiro[] sequencia3 = {30, 20, 10, 25, 40, 50, 5, 35, 45}
        
        para cada valor em sequencia3 {
            arvore3.adicionar(valor)
        }
        
        imprimir "Arvore 3 - InOrder:"
        arvore3.printInOrder()
        imprimir "Arvore 3 - Nós (BFS) com filhos, alturas e fator de balanceamento:"
        printBfsWithChildren(arvore3.getRoot())
    }
    
    Método printBfsWithChildren(No raiz) {
        se (raiz == null) {
            imprimir "(vazia)"
            retornar
        }
        
        Fila<No> fila = new Fila<No>()
        fila.adicionar(raiz)
        
        enquanto (fila não está vazia) {
            No no = fila.remover()
            No esquerdo = no.filhoEsquerdo
            No direito = no.filhoDireito
            
            string esquerdoStr = (esquerdo == null) ? "null" : (esquerdo.valor + "(h=" + esquerdo.altura + ")")
            string direitoStr = (direito == null) ? "null" : (direito.valor + "(h=" + direito.altura + ")")
            inteiro fatorBalanceamento = no.calcularFatorBalanceamento()
            
            imprimir "no " + no.valor + " (h=" + no.altura + ", FB=" + fatorBalanceamento + ") -> " +
                    "filho esquerdo: " + esquerdoStr + ", " +
                    "filho direito: " + direitoStr
            
            se (esquerdo != null) fila.adicionar(esquerdo)
            se (direito != null) fila.adicionar(direito)
        }
    }
}

CONCEITOS
=====================

1. FATOR DE BALANCEAMENTO:
   - FB = altura_subarvore_esquerda - altura_subarvore_direita
   - Para árvore AVL: FB deve estar entre -1, 0 ou 1

2. ROTAÇÕES:
   - Rotação Direita: corrige desbalanceamento à esquerda (LL)
   - Rotação Esquerda: corrige desbalanceamento à direita (RR)
   - Rotação Dupla: LR (esquerda + direita) ou RL (direita + esquerda)

3. PROPRIEDADES AVL:
   - Altura máxima: O(log n)
   - Busca, inserção, remoção: O(log n)
   - Sempre balanceada automaticamente

4. CASOS DE DESBALANCEAMENTO:
   - LL: FB > 1 e inserção à esquerda da subárvore esquerda
   - RR: FB < -1 e inserção à direita da subárvore direita
   - LR: FB > 1 e inserção à direita da subárvore esquerda
   - RL: FB < -1 e inserção à esquerda da subárvore direita

