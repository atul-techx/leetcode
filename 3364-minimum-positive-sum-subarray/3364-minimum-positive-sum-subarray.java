import java.util.List;

class Solution {
    public int minimumSumSubarray(List<Integer> nums, int l, int r) {
        int n = nums.size();
        int[] prefix = new int[n + 1];
        
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums.get(i);
        }
        
        int minSum = Integer.MAX_VALUE;
        
        for (int i = 0; i <= n - l; i++) {
            for (int len = l; len <= r && i + len <= n; len++) {
                int currentSum = prefix[i + len] - prefix[i];
                if (currentSum > 0) {
                    minSum = Math.min(minSum, currentSum);
                }
            }
        }
        
        return minSum == Integer.MAX_VALUE ? -1 : minSum;
    }
}