import java.util.ArrayDeque;

public class Main {
    public static void main(String[] args) {
        // 1) 40, 20, 60, 10, 30, 25
        ArvoreAVL tree1 = new ArvoreAVL();
        int[] seq1 = {40, 20, 60, 10, 30, 25};
        for (int v : seq1) tree1.adicionar(v);
        System.out.println("Arvore 1 - InOrder: ");
        tree1.printInOrder();
        System.out.println("Arvore 1 - Nós (BFS) com filhos, alturas e fator de balanceamento:");
        printBfsWithChildren(tree1.getRoot());

        // 2) 60, 40, 80, 35, 50, 90, 20, 38, 37
        ArvoreAVL tree2 = new ArvoreAVL();
        int[] seq2 = {60, 40, 80, 35, 50, 90, 20, 38, 37};
        for (int v : seq2) tree2.adicionar(v);
        System.out.println("\nArvore 2 - InOrder:");
        tree2.printInOrder();
        System.out.println("Arvore 2 - Nós (BFS) com filhos, alturas e fator de balanceamento:");
        printBfsWithChildren(tree2.getRoot());

        // 3) 30, 20, 10, 25, 40, 50, 5, 35, 45
        ArvoreAVL tree3 = new ArvoreAVL();
        int[] seq3 = {30, 20, 10, 25, 40, 50, 5, 35, 45};
        for (int v : seq3) tree3.adicionar(v);
        System.out.println("\nArvore 3 - InOrder:");
        tree3.printInOrder();
        System.out.println("Arvore 3 - Nós (BFS) com filhos, alturas e fator de balanceamento:");
        printBfsWithChildren(tree3.getRoot());
    }

    private static void printBfsWithChildren(No root) {
        if (root == null) {
            System.out.println("(vazia)");
            return;
        }
        ArrayDeque<No> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            No node = queue.poll();
            No left = node.left;
            No right = node.right;
            String leftStr = left == null ? "null" : (left.key + " (h = " + left.height + ")");
            String rightStr = right == null ? "null" : (right.key + " (h = " + right.height + ")");
            int fatorBalanceamento = node.calcularFatorBalanceamento();
            System.out.println(
                "no " + node.key + " [h = " + node.height + ", FB = " + fatorBalanceamento + "] -> " +
                "filho esquerdo: " + leftStr + ", " +
                "filho direito: " + rightStr
            );
            if (left != null) queue.add(left);
            if (right != null) queue.add(right);
        }
    }
}


