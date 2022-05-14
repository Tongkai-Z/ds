class MaxPQ {
	private int[] pq;
	private int N = 0; // O(1) size

	public MaxPQ(int maxN) {
		pq = new int[maxN + 1];
	}

	public int size() {
		return N;
	}

	public boolean isEmpty() {
		return N == 0;
	}

	public void insert(int v) {
		// insert at tail and percolate up
		pq[++N] = v;
		percolateUp(N);
	}

	public int peek() {
		return pq[1];
	}

	public int removeMax() {
		int ret = pq[1];
		pq[1] = pq[N--]; // 如果是object需要置空防止loitering

		percolateDown(1);
		return ret;
	}

	private void percolateUp(int i) {
		while (i > 1 && pq[i] > pq[i / 2]) {
			swap(i, i / 2);
			i = i / 2;
		}
	}

	private void percolateDown(int i) {
		while (2 * i <= N) {
			int j = 2 * i;
			// swap with larger child
			if (j < N && pq[j] < pq[j + 1]) {
				j++;
			}
			if (pq[i] < pq[j]) {
				swap(i, j);
				i = j;
			} else {
				return;
			}
		}
	}

	private void swap(int i, int j) {
		int tmp = pq[i];
		pq[i] = pq[j];
		pq[j] = tmp;
	}

	public static void main(String[] args) {
		MaxPQ pq = new MaxPQ(100);
		System.out.println(pq.size());
		pq.insert(1);
		pq.insert(2);
		System.out.println(pq.size());
		System.out.println(pq.peek());
		System.out.println(pq.removeMax());
		System.out.println(pq.peek());
	}
}