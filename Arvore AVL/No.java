public class No {
    public int key;
    public int height;
    public No left;
    public No right;
    public No pai;

    public No(int key) {
        this.key = key;
        this.height = 1; // altura do nó inicial
    }

    public void calcularAltura() {
        int alturaEsquerda = (left == null) ? 0 : left.height;
        int alturaDireita = (right == null) ? 0 : right.height;
        this.height = 1 + Math.max(alturaEsquerda, alturaDireita);
    }

    public int calcularFatorBalanceamento() {
        int alturaEsquerda = (left == null) ? 0 : left.height;
        int alturaDireita = (right == null) ? 0 : right.height;
        return alturaEsquerda - alturaDireita;
    }
}



