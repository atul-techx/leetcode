class Solution {
    public long minimumCost(int cost1, int cost2, int costBoth, int need1, int need2) {
        long c1 = cost1;
        long c2 = cost2;
        long cb = costBoth;
        long n1 = need1;
        long n2 = need2;

        // If buying type 1 and type 2 individually is cheaper than or equal to type 3
        if (cb >= c1 + c2) {
            return n1 * c1 + n2 * c2;
        }

        // Otherwise, use type 3 to satisfy both requirements simultaneously up to min(n1, n2)
        long common = Math.min(n1, n2);
        long totalCost = common * cb;

        // Satisfy any remaining requirement using either the specific item or type 3 (whichever is cheaper)
        if (n1 > n2) {
            totalCost += (n1 - n2) * Math.min(c1, cb);
        } else if (n2 > n1) {
            totalCost += (n2 - n1) * Math.min(c2, cb);
        }

        return totalCost;
    }
}