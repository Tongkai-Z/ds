import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Queue;

public class Tree {
    private Node root;

    private static class Node {
        int val;
        Node left, right;

        Node(int val) {
            this.val = val;
        }
    }

    // iterative height BFS
    public int heightBFS() {
        int h = 0;
        Queue<Node> q = new ArrayDeque<>();
        q.offer(root);
        int size = q.size();
        while (q.size() > 0) {
            Node n = q.poll();
            q.offer(n.left);
            q.offer(n.right);
            size--;
            if (size == 0) {
                h++;
            }
        }
        return h;
    }

    public int heightDFS() {
        Deque<Node> stack = new ArrayDeque<>();
        pushLeft(root, stack);
        int h = stack.size();

        while (stack.size() > 0) {
            pushLeft(stack.pollFirst().right, stack);
            h = Math.max(h, stack.size());
        }
        return h - 1;
    }

    // tree traverse
    public void levelOrder() {
        Queue<Node> q = new ArrayDeque<>();
        q.offer(root);
        while (q.size() > 0) {
            Node curr = q.poll();
            System.out.println(curr.val);
            if (curr.left != null) {
                q.offer(curr.left);
            }
            if (curr.right != null) {
                q.offer(curr.right);
            }
        }
    }

    public void dfsRecursion() {
        dfs(root);
    }

    private void dfs(Node root) {
        if (root == null) {
            return;
        }
        // preOrder System.out.println(root.val);
        dfs(root.left);
        // inOrder System.out.println(root.val);
        dfs(root.right);
        // postOrder System.out.println(root.val);
    }

    // iterative pattern的关键在于从第一个node开始不停的找successor

    public void preOrderIterative() {
        // root -> left -> right
        Deque<Node> stack = new ArrayDeque<>();
        stack.offerFirst(root);
        while (stack.size() > 0) {
            Node curr = stack.pollFirst();
            System.out.println(curr.val);
            if (curr.left != null) {
                stack.offerFirst(curr.left);
            }
            if (curr.right != null) {
                stack.offerFirst(curr.right);
            }
        }
    }

    public void inOrderIterative() {
        // left - root - right
        Deque<Node> stack = new ArrayDeque<>();
        pushLeft(root, stack);
        while (stack.size() > 0) {
            Node curr = stack.pollFirst();
            System.out.println(curr.val);
            pushLeft(curr.right, stack);
        }
    }

    public void postOrderIterative() {
        // left -right - root
        Deque<Node> stack = new ArrayDeque<>();
        pushLeft(root, stack);
        while (stack.size() > 0) {
            Node curr = stack.pollFirst();
            // case1 left child
            if (stack.isEmpty() || stack.peekFirst().left == curr) {
                System.out.println(curr.val);
                if (!stack.isEmpty()) {
                    pushLeft(stack.peekFirst().right, stack);
                }
            } else { // right child
                System.out.println(curr.val);
            }
        }
    }

    private void pushLeft(Node root, Deque<Node> stack) {
        if (root == null) {
            return;
        }
        stack.offerFirst(root);
        while (root.left != null) {
            stack.offerFirst(root.left);
            root = root.left;
        }
    }

    // find closest k node in a BST
    public List<Node> findClosestK(int x, int k) {
        List<Node> res = new ArrayList<>();
        Deque<Node> succesor = new ArrayDeque<>();
        Deque<Node> pred = new ArrayDeque<>();
        Node curr = root;
        // push the path
        while (curr != null) {
            if (x == curr.val) {
                res.add(curr);
                k--;
                break;
            }
            if (x < curr.val) {
                succesor.push(curr);
                curr = curr.left;
            } else {
                pred.push(curr);
                curr = curr.right;
            }
        }
        while (k > 0) {
            Node p = pred.peekFirst();
            Node s = succesor.peekFirst();
            if (p != null && s != null) {
                if (x - p.val > s.val - x) {
                    res.add(s);
                    succesor.pollFirst();
                    pushLeft(s.right, succesor); // succ
                } else {
                    res.add(p);
                    pred.pollFirst();
                    pushRight(p.left, pred); // pred
                }
            } else if (p == null) {
                res.add(succesor.pollFirst());
            } else {
                res.add(pred.pollFirst());
            }
            k--;
        }
        return res;

    }

    private void pushRight(Node root, Deque<Node> stack) {
        Node curr = root;
        while (curr != null) {
            stack.offerFirst(curr);
            curr = curr.right;
        }
    }

    public static void main(String[] args) {
        Tree tree = new Tree();
        tree.root = new Node(1);
        tree.root.left = new Node(2);
        tree.root.right = new Node(3);
        tree.root.left.left = new Node(4);
        tree.root.left.right = new Node(5);
        tree.root.right.left = new Node(6);
        tree.root.right.right = new Node(7);
        tree.postOrderIterative();
    }

}