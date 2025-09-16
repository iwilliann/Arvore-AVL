public class ArvoreAVL {
    private No root;

    public void insert(int key) {
        root = adicionar(key, root, null);
    }

    public void adicionar(int valor) {
        root = adicionar(valor, root, null);
    }

    public No getRoot() {
        return root;
    }

    private No adicionar(int valor, No noAtual, No noPai) {
        if (noAtual == null) {
            No novoNo = new No(valor);
            novoNo.pai = noPai;
            return novoNo;
        }

        if (valor > noAtual.key) {
            No novoNo = adicionar(valor, noAtual.right, noAtual);
            noAtual.right = novoNo;
            noAtual.calcularAltura();
        } else if (valor < noAtual.key) {
            No novoNo = adicionar(valor, noAtual.left, noAtual);
            noAtual.left = novoNo;
            noAtual.calcularAltura();
        } else {
            return noAtual; // duplicado: ignora
        }

        return balancear(noAtual, valor);
    }

    private No balancear(No noAtual, int valor) {
        int fatorBalanceamento = noAtual.calcularFatorBalanceamento();

        // LL
        if (fatorBalanceamento > 1 && valor < (noAtual.left != null ? noAtual.left.key : Integer.MIN_VALUE)) {
            return rotacaoDireita(noAtual);
        }
        // RR
        if (fatorBalanceamento < -1 && valor > (noAtual.right != null ? noAtual.right.key : Integer.MAX_VALUE)) {
            return rotacaoEsquerda(noAtual);
        }
        // LR
        if (fatorBalanceamento > 1 && valor > (noAtual.left != null ? noAtual.left.key : Integer.MIN_VALUE)) {
            noAtual.left = rotacaoEsquerda(noAtual.left);
            if (noAtual.left != null) noAtual.left.pai = noAtual;
            return rotacaoDireita(noAtual);
        }
        // RL
        if (fatorBalanceamento < -1 && valor < (noAtual.right != null ? noAtual.right.key : Integer.MAX_VALUE)) {
            noAtual.right = rotacaoDireita(noAtual.right);
            if (noAtual.right != null) noAtual.right.pai = noAtual;
            return rotacaoEsquerda(noAtual);
        }

        return noAtual;
    }

    private No rotacaoDireita(No noDesbalanceado) {
        No filhoEsquerdo = noDesbalanceado.left;
        No filhoDireitoDoFilho = (filhoEsquerdo != null) ? filhoEsquerdo.right : null;

        // atualiza pais
        if (filhoEsquerdo != null) filhoEsquerdo.pai = noDesbalanceado.pai;
        if (filhoDireitoDoFilho != null) filhoDireitoDoFilho.pai = noDesbalanceado;
        noDesbalanceado.pai = filhoEsquerdo;

        // rota
        noDesbalanceado.left = filhoDireitoDoFilho;
        if (filhoEsquerdo != null) filhoEsquerdo.right = noDesbalanceado;

        // se raiz mudou
        if (filhoEsquerdo != null && filhoEsquerdo.pai == null) {
            root = filhoEsquerdo;
        } else if (filhoEsquerdo != null && filhoEsquerdo.pai != null) {
            if (filhoEsquerdo.pai.left == noDesbalanceado) {
                filhoEsquerdo.pai.left = filhoEsquerdo;
            } else if (filhoEsquerdo.pai.right == noDesbalanceado) {
                filhoEsquerdo.pai.right = filhoEsquerdo;
            }
        }

        // recalcula alturas
        noDesbalanceado.calcularAltura();
        if (filhoEsquerdo != null) filhoEsquerdo.calcularAltura();
        return filhoEsquerdo != null ? filhoEsquerdo : noDesbalanceado;
    }

    private No rotacaoEsquerda(No noDesbalanceado) {
        No filhoDireito = noDesbalanceado.right;
        No filhoEsquerdoDoFilho = (filhoDireito != null) ? filhoDireito.left : null;

        // atualiza pais
        if (filhoDireito != null) filhoDireito.pai = noDesbalanceado.pai;
        if (filhoEsquerdoDoFilho != null) filhoEsquerdoDoFilho.pai = noDesbalanceado;
        noDesbalanceado.pai = filhoDireito;

        // rota
        noDesbalanceado.right = filhoEsquerdoDoFilho;
        if (filhoDireito != null) filhoDireito.left = noDesbalanceado;

        // se raiz mudou
        if (filhoDireito != null && filhoDireito.pai == null) {
            root = filhoDireito;
        } else if (filhoDireito != null && filhoDireito.pai != null) {
            if (filhoDireito.pai.left == noDesbalanceado) {
                filhoDireito.pai.left = filhoDireito;
            } else if (filhoDireito.pai.right == noDesbalanceado) {
                filhoDireito.pai.right = filhoDireito;
            }
        }

        // recalcula alturas
        noDesbalanceado.calcularAltura();
        if (filhoDireito != null) filhoDireito.calcularAltura();
        return filhoDireito != null ? filhoDireito : noDesbalanceado;
    }

    // Impressão
    public void printInOrder() {
        printInOrder(root);
        System.out.println();
    }

    private void printInOrder(No node) {
        if (node == null) return;
        printInOrder(node.left);
        System.out.print(node.key + " ");
        printInOrder(node.right);
    }

    public void printTree() {
        printTree(root, "", true);
    }

    private void printTree(No node, String prefix, boolean isTail) {
        if (node == null) return;
        System.out.println(prefix + (isTail ? "└── " : "├── ") + node.key + " (h=" + node.height + ")");
        if (node.left != null || node.right != null) {
            printTree(node.left, prefix + (isTail ? "    " : "│   "), node.right == null);
            printTree(node.right, prefix + (isTail ? "    " : "│   "), true);
        }
    }
}


