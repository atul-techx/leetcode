class Solution {
    public int numOfSubarrays(int[] arr) {
        long result = 0; 
        int oddCount = 0;
        int evenCount = 1; // Start with 1 to account for the initial sum of 0 (which is even)
        int currentSum = 0;
        int MOD = 1_000_000_007;

        for (int num : arr) {
            currentSum += num;
            
            // Check if the current prefix sum is even or odd
            if (currentSum % 2 == 0) {
                // An even current sum needs a previous odd prefix sum to make an odd subarray sum (Even - Odd = Odd)
                result = (result + oddCount) % MOD;
                evenCount++;
            } else {
                // An odd current sum needs a previous even prefix sum to make an odd subarray sum (Odd - Even = Odd)
                result = (result + evenCount) % MOD;
                oddCount++;
            }
        }

        return (int) result;
    }
}