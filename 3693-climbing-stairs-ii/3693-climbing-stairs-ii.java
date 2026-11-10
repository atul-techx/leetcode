class Solution {
    public int climbStairs(int n, int[] costs) {
        int[] dp = new int[n + 1];
        dp[0] = 0;

        for (int i = 1; i <= n; i++) {
            int stepCost = costs[i - 1];
            
            // Jump from step i - 1 (distance 1, jump cost = 1^2 = 1)
            int minPrev = dp[i - 1] + 1;

            // Jump from step i - 2 (distance 2, jump cost = 2^2 = 4)
            if (i >= 2) {
                minPrev = Math.min(minPrev, dp[i - 2] + 4);
            }

            // Jump from step i - 3 (distance 3, jump cost = 3^2 = 9)
            if (i >= 3) {
                minPrev = Math.min(minPrev, dp[i - 3] + 9);
            }

            dp[i] = stepCost + minPrev;
        }

        return dp[n];
    }
}