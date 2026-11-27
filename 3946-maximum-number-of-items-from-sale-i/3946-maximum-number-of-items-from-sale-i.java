class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int maxFactor = 0;
        for (int[] item : items) {
            maxFactor = Math.max(maxFactor, item[0]);
        }

        // Frequency of each factor
        int[] freq = new int[maxFactor + 1];
        for (int[] item : items) {
            freq[item[0]]++;
        }

        // multiplesCount[f] = total number of items whose factor is a multiple of f
        int[] multiplesCount = new int[maxFactor + 1];
        for (int f = 1; f <= maxFactor; f++) {
            if (freq[f] == 0) continue;
            for (int m = f; m <= maxFactor; m += f) {
                multiplesCount[f] += freq[m];
            }
        }

        int[] dp = new int[budget + 1];

        for (int[] item : items) {
            int factor = item[0];
            int price = item[1];
            if (price > budget) continue;

            // Free items gained when purchasing at least 1 copy of this item
            int freeCopies = multiplesCount[factor] - 1;

            // 1. 0/1 Knapsack step: Buying the first copy gives (1 + freeCopies)
            for (int w = budget; w >= price; w--) {
                dp[w] = Math.max(dp[w], dp[w - price] + 1 + freeCopies);
            }

            // 2. Unbounded Knapsack step: Subsequent copies give +1 item each
            for (int w = price; w <= budget; w++) {
                dp[w] = Math.max(dp[w], dp[w - price] + 1);
            }
        }

        int ans = 0;
        for (int w = 0; w <= budget; w++) {
            ans = Math.max(ans, dp[w]);
        }
        return ans;
    }
}