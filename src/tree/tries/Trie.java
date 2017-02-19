package tree.tries;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

public class Trie<Value> {
    private static final int R = 256;
    private Node root = new Node();

    public static void main(String[] args) {

    }


    public void put(String key, Value value) {
        put(root, key, value, 0);
    }

    private Node put(Node x, String key, Value value, int d) {
        if (x == null)
            x = new Node();
        if (d == key.length()) {
            x.value = value;
        } else {
            char c = key.charAt(d);
            Node subNode = put(x.next[c], key, value, d + 1);
            x.next[c] = subNode;
            System.out.println("key" + c + x.next[c]);
        }
        return x;
    }

    public boolean contains(String key) {
        return get(key) != null;
    }

    private Value get(String key) {
        Node node = get(root, key, 0);
        return node == null ? null : (Value) node.value;
    }

    private Node get(Node node, String key, int d) {
        Node result = null;
        if (node != null) {
            {
                if (d == key.length()) {
                    result = node;
                } else {
                    char c = key.charAt(d);
                    result = get(node.next[c], key, d + 1);
                }
            }
        }
        return result;
    }

    //TODO
//    delete

    Iterable<String> keys() {
        Queue<String> queue = new LinkedList<>();
        collect(root, "", queue);
        return queue;
    }

    private void collect(Node node, String prefix, Queue<String> queue) {
        if (node != null) {
            if (node.value != null) {
                queue.add(prefix);
            }
            for (char c = 0; c < R; c++) {
                collect(node.next[c], prefix + c, queue);
            }
        }


    }

    Iterable<String> keyWithPrefix(String prefix) {
        return null;
    }

    Iterable<String> keysThatMatch(String key) {
        return null;
    }

    String longestPrefixOf(String string) {
        return null;
    }

    public class Node {
        private Object value;
        private Node[] next = new Trie.Node[R];
    }

    @Test
    public void testGetKeys() {
        Trie<String> trie = new Trie<String>();
        trie.put("test", "test");
        trie.put("toast", "toast");
        trie.put("toad", "toad");
        trie.put("hi", "hi");
        trie.put("hill", "hill");
        trie.put("hell", "hell");

        for (String str : trie.keys()) {
            System.out.println(str);
        }
    }

}
