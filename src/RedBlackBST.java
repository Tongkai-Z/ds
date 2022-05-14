public class RedBlackBST<Key extends Comparable<Key>, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private Node root;

    private class Node {
        Key key;
        Value val;
        Node left, right;
        int N;
        boolean color;

        Node(Key key, Value val, int N, boolean color) {
            this.key = key;
            this.val = val;
            this.N = N;
            this.color = color;
        }
    }

    private boolean isRed(Node x) {
        if (x == null)
            return false;
        return x.color == RED;
    }

    private int size(Node x) {
        if (x == null) {
            return 0;
        }
        return x.N;
    }

    // 注意这个是针对右边为红的时候，所以右子树的左子树一定不是红
    private Node rotateLeft(Node h) {
        if (h == null || h.right == null) {
            return h;
        }

        // put right to root
        Node tmp = h.right;
        h.right = tmp.left;
        tmp.left = h;
        // sync color
        tmp.color = h.color;
        h.color = RED;
        tmp.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        return tmp;
    }

    // 左边为红
    private Node rotateRight(Node h) {
        if (h == null || h.left == null) {
            return h;
        }

        Node tmp = h.left;
        h.left = tmp.right;
        tmp.left = h;
        tmp.color = h.color;
        h.color = RED;
        tmp.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        return tmp;
    }

    private void flipColors(Node h) {
        if (h == null) {
            return;
        }
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
        h.color = !h.right.color;
    }

    public void put(Key key, Value val) {
        root = put(root, key, val);
        root.color = BLACK;
    }

    private Node put(Node root, Key key, Value val) {
        // always insert with red link
        if (root == null) {
            return new Node(key, val, 1, RED);
        }

        // search
        int cmp = key.compareTo(root.key);
        if (cmp == 0) {
            root.val = val;
            return root;
        }
        if (cmp < 0) {
            root.left = put(root.left, key, val);
        } else {
            root.right = put(root.right, key, val);
        }

        // check three different cases
        if (isRed(root.right) && isRed(root.left)) {
            flipColors(root);
            return root;
        }
        if (isRed(root.right)) {
            root = rotateLeft(root);
        }
        if (isRed(root.left) && isRed(root.left.left)) {
            root = rotateRight(root);
        }
        root.N = 1 + size(root.left) + size(root.right);
        return root;
    }

}