class Solution {
    public int maxLength(int[] nums) {
        int maxLen = 2; // Any subarray of length 2 is always valid
        int left = 0;
        int windowProd = 1; // At most 2 * 3 * 5 * 7 = 210, never overflows

        for (int right = 0; right < nums.length; right++) {
            // Shrink window until nums[right] is coprime with all elements in window
            while (gcd(windowProd, nums[right]) > 1) {
                windowProd /= nums[left];
                left++;
            }

            windowProd *= nums[right];
            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}