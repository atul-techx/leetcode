import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int[] freq = new int[n + 1];
        long minPrice = Long.MAX_VALUE;

        for (int[] item : items) {
            freq[item[0]]++;
            minPrice = Math.min(minPrice, item[1]);
        }

        // Count how many items have factor as a multiple of x in O(N log N)
        int[] multiples = new int[n + 1];
        for (int x = 1; x <= n; x++) {
            if (freq[x] == 0) continue;
            for (int mult = x; mult <= n; mult += x) {
                multiples[x] += freq[mult];
            }
        }

        // Sort item types by price ascending so we only collect the cheapest 2-for-1 copies
        Arrays.sort(items, Comparator.comparingInt(a -> a[1]));

        // Max number of 2-for-1 purchases we could ever afford is budget / minPrice,
        // and also bounded by how many items can actually be purchased.
        long maxPossibleK = budget / minPrice;
        long prefixCost = 0;
        long k = 0;
        long best = budget / minPrice; // Case k = 0

        for (int[] item : items) {
            int factor = item[0];
            long price = item[1];
            int freeCopies = multiples[factor] - 1;

            if (freeCopies <= 0) continue;

            // How many 2-for-1 copies of this item can we afford?
            long canAfford = (budget - prefixCost) / price;
            if (canAfford <= 0) break;

            long take = Math.min(freeCopies, canAfford);

            // Check at each step (or simply observe prefixCost grows linearly for this item)
            // Since price >= minPrice, (budget - prefixCost - t * price) / minPrice drops by
            // at least t, while 2 * (k + t) rises by 2 * t -> non-decreasing as t increases!
            k += take;
            prefixCost += take * price;
            long remaining = budget - prefixCost;
            best = Math.max(best, 2 * k + (remaining / minPrice));
        }

        return (int) best;
    }
}