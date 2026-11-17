import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public int minimumChanges(String s, int k) {
        int n = s.length();
        int INF = 1000000000;
        
        // Precompute proper divisors for each possible length from 1 to n
        List<Integer>[] divisors = new List[n + 1];
        for (int i = 1; i <= n; i++) {
            divisors[i] = new ArrayList<>();
            for (int d = 1; d < i; d++) {
                if (i % d == 0) {
                    divisors[i].add(d);
                }
            }
        }

        // cost[i][j] = min changes to make s[i...j] a semi-palindrome
        int[][] cost = new int[n][n];
        for (int len = 1; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                cost[i][j] = INF;
                
                // If length has no proper divisors (e.g., length 1), it cannot be a semi-palindrome
                if (divisors[len].isEmpty()) continue;

                // Try all proper divisors of len
                for (int d : divisors[len]) {
                    int currentCost = 0;
                    // Check each of the d groups
                    for (int r = 0; r < d; r++) {
                        currentCost += getPalindromeCost(s, i + r, j, d);
                    }
                    cost[i][j] = Math.min(cost[i][j], currentCost);
                }
            }
        }

        // dp[i][c] = min changes to partition prefix of length i into c semi-palindromes
        int[][] dp = new int[n + 1][k + 1];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], INF);
        }
        dp[0][0] = 0;

        for (int i = 1; i <= n; i++) {
            for (int c = 1; c <= Math.min(i, k); c++) {
                for (int j = 0; j < i; j++) {
                    if (dp[j][c - 1] != INF && cost[j][i - 1] != INF) {
                        dp[i][c] = Math.min(dp[i][c], dp[j][c - 1] + cost[j][i - 1]);
                    }
                }
            }
        }

        return dp[n][k];
    }

    // Helper function to calculate changes needed to make a group a palindrome
    private int getPalindromeCost(String s, int start, int end, int d) {
        int changes = 0;
        int left = start;
        int right = end - (end - start) % d; // last element in the same group sequence
        
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                changes++;
            }
            left += d;
            right -= d;
        }
        return changes;
    }
}