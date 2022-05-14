import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class BST<Key extends Comparable<Key>, Value> {

    private class Node {
        private Key key;
        private Value val;
        private Node left, right;
        private int N;

        public Node(Key key, Value val, int N) {
            this.key = key;
            this.val = val;
            this.N = N;
        }
    }

    private Node root; // root of BST

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) {
            return 0;
        }
        return x.N;
    }

    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node root, Key key) {
        if (root == null) {
            return null;
        }
        int cmp = key.compareTo(root.key);
        if (cmp == 0) {
            return root.val;
        }
        if (cmp < 0) {
            return get(root.left, key);
        }
        return get(root.right, key);
    }

    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    private Node put(Node root, Key key, Value val) {
        if (root == null) {
            return new Node(key, val, 1);
        }
        int cmp = key.compareTo(root.key);
        if (cmp == 0) {
            root.val = val;
        } else if (cmp < 0) {
            root.left = put(root.left, key, val);
        } else {
            root.right = put(root.right, key, val);
        }
        root.N = size(root.left) + size(root.right);
        return root;
    }

    public Key min() {
        return min(root).key;
    }

    private Node min(Node root) {
        if (root.left == null) {
            return root;
        }
        return min(root.left);
    }

    public Key max() {
        return max(root).key;
    }

    private Node max(Node root) {
        if (root.right == null) {
            return root;
        }
        return max(root.right);
    }

    public Key floor(Key key) {
        Node n = floor(root, key);
        if (n == null) {
            return null;
        }
        return n.key;
    }

    private Node floor(Node root, Key key) {
        if (root == null) {
            return null;
        }
        int cmp = key.compareTo(root.key);
        if (cmp == 0) {
            return root;
        }
        if (cmp > 0) {
            Node larger = floor(root.right, key);
            if (larger == null) {
                return root; // no larger
            } else {
                return larger;
            }
        }
        return floor(root.left, key);
    }

    public Key ceiling(Key key) {
        Node n = ceiling(root, key);
        return n == null ? null : n.key;
    }

    private Node ceiling(Node root, Key key) {
        if (root == null) {
            return null;
        }
        int cmp = key.compareTo(root.key);
        if (cmp == 0) {
            return root;
        }
        if (cmp < 0) { // root > key
            Node smaller = ceiling(root.left, key);
            if (smaller == null) {
                return root; // no larger
            } else {
                return smaller;
            }
        }
        // root < key
        return floor(root.right, key);
    }

    // return key at rank k start from 0
    // quick select
    public Key select(int k) {
        if (k >= size(root)) {
            return null;
        }
        return select(root, k).key;
    }

    private Node select(Node root, int k) {
        if (root == null) {
            return null;
        }
        int t = size(root.left);
        if (t > k) {
            return select(root.left, k);
        }
        if (t < k) {
            return select(root.right, k - t - 1);
        }
        return root;
    }

    // return num of keys smaller than input
    public int rank(Key key) {
        return rank(root, key);
    }

    private int rank(Node root, Key key) {
        if (root == null) {
            return 0;
        }
        int cmp = key.compareTo(root.key);
        if (cmp == 0) {
            return size(root.left);
        }
        if (cmp < 0) {
            return rank(root.left, key);
        }
        return size(root.left) + 1 + rank(root.right, key);
    }

    public void deleteMin() {
        root = deleteMin(root);
    }

    private Node deleteMin(Node root) {
        if (root == null) {
            return null;
        }
        if (root.left == null) {
            return root.right;
        }
        root.left = deleteMin(root.left);
        return root;
    }

    public void delete(Key key) {
        root = delete(root, key);
    }

    private Node delete(Node root, Key key) {
        if (root == null) {
            return null;
        }
        int cmp = key.compareTo(root.key);
        if (cmp == 0) {
            // delete current Node
            // let min_right be root
            Node tmp = min(root.right);
            if (tmp != null) {
                tmp.right = deleteMin(root.right);
                tmp.left = root.left;
                return tmp;
            } else {
                return root.left;
            }
        } else if (cmp < 0) {
            root.left = delete(root.left, key);
            return root;
        } else {
            root.right = delete(root.right, key);
            return root;
        }

    }

}
