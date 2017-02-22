package tree;

import org.testng.annotations.Test;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

public class RedBlackTree<T extends Comparable> {
    Node root;


    class Node {
        Node(T value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        Node left;
        Node right;
        T value;
    }

    public void add(T value) {
        if (value != null) {
            Node node = new Node(value);
            if (root == null) {
                root = node;
            } else {
                add(node, root);
            }
        }
    }

    private void add(Node node, Node parent) {
        if (node.value.compareTo(parent.value) < 0) {
            if (parent.left != null) {
                add(node, parent.left);
            } else {
                parent.left = node;
            }
        } else {
            if (parent.right != null) {
                add(node, parent.right);
            } else {
                parent.right = node;
            }
        }
    }


    public Collection<T> getInOrder() {
        Queue<Node> queue = new LinkedList<Node>();
        Collection result = new LinkedList();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            result.add(node.value);

            if (node.left != null)
                queue.add(node.left);
            if (node.right != null)
                queue.add(node.right);
        }
        return result;
    }


    public void printTree() {
        int nodesInCurrentLevel = 1;
        int nodesInNextLevel = 0;
        Queue<Node> queue = new LinkedList<Node>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            System.out.print(node.value);
            nodesInCurrentLevel--;
            if (node.left != null) {
                queue.add(node.left);
                nodesInNextLevel++;
            }
            if (node.right != null) {
                queue.add(node.right);
                nodesInNextLevel++;
            }
            if (nodesInCurrentLevel == 0) {
                System.out.println();
                nodesInCurrentLevel = nodesInNextLevel;
                nodesInNextLevel = 0;
            }
        }
    }

    @Test
    public void testAdd() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.add(6);
        tree.add(2);
        tree.add(8);
        tree.add(4);
        tree.add(9);
        tree.add(3);
        tree.add(7);
        tree.add(5);
        tree.add(1);
        tree.add(5);
        Collection<Integer> inOrder = tree.getInOrder();
        tree.printTree();
    }

}
