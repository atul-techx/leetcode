import java.util.*;

class Solution {
    // Helper structure to maintain a sliding window's median and distance sum
    static class SlidingMedianCost {
        private final int x;
        private final int smallTargetSize;
        
        // Value -> count
        private final TreeMap<Integer, Integer> small = new TreeMap<>();
        private final TreeMap<Integer, Integer> large = new TreeMap<>();
        
        private int smallCount = 0;
        private int largeCount = 0;
        private long smallSum = 0;
        private long largeSum = 0;

        public SlidingMedianCost(int x) {
            this.x = x;
            this.smallTargetSize = (x + 1) / 2;
        }

        private void add(TreeMap<Integer, Integer> map, int val) {
            map.put(val, map.getOrDefault(val, 0) + 1);
        }

        private void remove(TreeMap<Integer, Integer> map, int val) {
            int count = map.get(val);
            if (count == 1) {
                map.remove(val);
            } else {
                map.put(val, count - 1);
            }
        }

        public void insert(int val) {
            if (small.isEmpty() || val <= small.lastKey()) {
                add(small, val);
                smallCount++;
                smallSum += val;
            } else {
                add(large, val);
                largeCount++;
                largeSum += val;
            }
            balance();
        }

        public void erase(int val) {
            if (small.containsKey(val)) {
                remove(small, val);
                smallCount--;
                smallSum -= val;
            } else {
                remove(large, val);
                largeCount--;
                largeSum -= val;
            }
            balance();
        }

        private void balance() {
            while (smallCount < smallTargetSize && largeCount > 0) {
                int moveVal = large.firstKey();
                remove(large, moveVal);
                largeCount--;
                largeSum -= moveVal;

                add(small, moveVal);
                smallCount++;
                smallSum += moveVal;
            }

            while (smallCount > smallTargetSize) {
                int moveVal = small.lastKey();
                remove(small, moveVal);
                smallCount--;
                smallSum -= moveVal;

                add(large, moveVal);
                largeCount++;
                largeSum += moveVal;
            }

            // Ensure max(small) <= min(large)
            while (!small.isEmpty() && !large.isEmpty() && small.lastKey() > large.firstKey()) {
                int valFromSmall = small.lastKey();
                int valFromLarge = large.firstKey();

                remove(small, valFromSmall);
                smallSum -= valFromSmall;
                smallCount--;

                remove(large, valFromLarge);
                largeSum -= valFromLarge;
                largeCount--;

                add(small, valFromLarge);
                smallSum += valFromLarge;
                smallCount++;

                add(large, valFromSmall);
                largeSum += valFromSmall;
                largeCount++;
            }
        }

        public long getCost() {
            long median = small.lastKey();
            long costSmall = median * smallCount - smallSum;
            long costLarge = largeSum - median * largeCount;
            return costSmall + costLarge;
        }
    }

    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;
        long[] windowCost = new long[n];
        SlidingMedianCost window = new SlidingMedianCost(x);

        for (int i = 0; i < n; i++) {
            window.insert(nums[i]);
            if (i >= x) {
                window.erase(nums[i - x]);
            }
            if (i >= x - 1) {
                windowCost[i] = window.getCost();
            }
        }

        // dp[j][i]: min cost to pick j subarrays from prefix nums[0...i]
        final long INF = (long) 1e18;
        long[] prevDp = new long[n];
        long[] currDp = new long[n];

        // Base case: j = 1
        long runningMin = INF;
        for (int i = 0; i < n; i++) {
            if (i >= x - 1) {
                runningMin = Math.min(runningMin, windowCost[i]);
            }
            prevDp[i] = runningMin;
        }

        // Transitions for j = 2 to k
        for (int j = 2; j <= k; j++) {
            Arrays.fill(currDp, INF);
            for (int i = j * x - 1; i < n; i++) {
                // Option 1: Do not pick a subarray ending at i
                currDp[i] = currDp[i - 1];

                // Option 2: Pick a subarray ending at i (starts at i - x + 1)
                long prevCost = prevDp[i - x];
                if (prevCost != INF) {
                    currDp[i] = Math.min(currDp[i], prevCost + windowCost[i]);
                }
            }
            System.arraycopy(currDp, 0, prevDp, 0, n);
        }

        return prevDp[n - 1];
    }
}