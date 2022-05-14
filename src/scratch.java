public class scratch {
    public int rank(int x, int[] a) {
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (a[mid] == x) {
                return mid;
            } else if (a[mid] < x) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return lo;// lo > hi
    }

    public static void main(String[] args) {
        scratch s = new scratch();
        int[] a = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        System.out.println(s.rank(5, a));
    }
}
