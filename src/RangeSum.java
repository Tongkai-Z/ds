import java.util.HashMap;

public class RangeSum {
    // leetcode 307
    // Segment tree, sumRange time O(lgN), update time O(lgN)
    private int[] a; // inner array for segment tree
    private HashMap<Integer, Integer> indexM;

    public RangeSum(int[] nums) {
        this.a = new int[nums.length * 4];
        indexM = new HashMap<>();
        // build the segment tree
        buildSegmentTree(nums);
    }

    public void update(int index, int val) {
        if (index >= a.length) {
            return;
        }
        // get index on a
        int indexA = indexM.get(index);
        a[indexA] = val;
        indexA = indexA / 2;
        while (indexA > 0) {
            a[indexA] = a[indexA * 2] + a[indexA * 2 + 1];
            indexA = indexA / 2;
        }
    }

    public int sumRange(int left, int right) {
        return find(1, 0, indexM.size() - 1, left, right);
    }

    private int find(int i, int nl, int nr, int left, int right) {
        if (left > right) {
            return 0;
        }

        if (nl >= left && nr <= right) {
            return a[i];
        }

        if (nl > right || nr < left) {
            return 0;
        }

        int mid = nl + (nr - nl) / 2;
        return find(i * 2, nl, mid, left, Math.min(right, nr))
                + find(i * 2 + 1, mid + 1, nr, Math.max(left, mid + 1), right);
    }

    private void buildSegmentTree(int[] nums) {
        buildHelper(1, nums, 0, nums.length - 1);
    }

    private void buildHelper(int i, int[] nums, int left, int right) {
        if (left == right) { // leaf
            a[i] = nums[left];
            indexM.put(left, i);
            return;
        }

        int leftChild = i * 2;
        int rightChild = i * 2 + 1;
        int mid = left + (right - left) / 2;
        buildHelper(leftChild, nums, left, mid);
        buildHelper(rightChild, nums, mid + 1, right);

        // segment sum
        a[i] = a[leftChild] + a[rightChild];
    }

    public static void main(String[] args) {
        RangeSum r = new RangeSum(new int[] { 1, 32, 2, 4, 6, 7, 8, 9 });
        System.out.println(r.sumRange(1, 2));
        r.update(1, 3);
        System.out.println(r.sumRange(1, 2));

    }

}