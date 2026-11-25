import java.util.*;

class Solution {
    private static final long MOD1 = 1_000_000_007L;
    private static final long MOD2 = 1_000_000_009L;
    private static final long BASE1 = 131_542_391L;
    private static final long BASE2 = 265_443_5761L % MOD2;

    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;
        
        // Precompute powers of BASE1 and BASE2
        long[] pow1 = new long[n + 1];
        long[] pow2 = new long[n + 1];
        pow1[0] = 1;
        pow2[0] = 1;
        for (int i = 1; i <= n; i++) {
            pow1[i] = (pow1[i - 1] * BASE1) % MOD1;
            pow2[i] = (pow2[i - 1] * BASE2) % MOD2;
        }

        // Precompute prefix hashes
        long[] pref1 = new long[n + 1];
        long[] pref2 = new long[n + 1];
        for (int i = 0; i < n; i++) {
            pref1[i + 1] = (pref1[i] * BASE1 + nums[i]) % MOD1;
            pref2[i + 1] = (pref2[i] * BASE2 + nums[i]) % MOD2;
        }

        // Binary search for the minimum valid length
        int low = 1, high = n, ans = n;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (hasUniqueSubarray(n, mid, pref1, pref2, pow1, pow2)) {
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return ans;
    }

    private boolean hasUniqueSubarray(int n, int len, long[] pref1, long[] pref2, long[] pow1, long[] pow2) {
        int count = n - len + 1;
        // Pack both 32-bit hashes into a single 64-bit long key
        Map<Long, Integer> freq = new HashMap<>(count * 2);

        for (int i = 0; i <= n - len; i++) {
            long h1 = (pref1[i + len] - (pref1[i] * pow1[len]) % MOD1 + MOD1) % MOD1;
            long h2 = (pref2[i + len] - (pref2[i] * pow2[len]) % MOD2 + MOD2) % MOD2;
            long key = (h1 << 32) | (h2 & 0xFFFFFFFFL);
            
            freq.put(key, freq.getOrDefault(key, 0) + 1);
        }

        for (int val : freq.values()) {
            if (val == 1) {
                return true;
            }
        }
        return false;
    }
}