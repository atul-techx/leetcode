import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public long countNonDecreasingSubarrays(int[] nums, int k) {
        int n = nums.length;
        long ans = 0;
        long cost = 0;
        
        // Deque stores int[]{value, count} representing contiguous segments 
        // forced up to 'value', ordered strictly decreasing from front to back.
        Deque<int[]> dq = new ArrayDeque<>();
        int r = n - 1;
        
        for (int l = n - 1; l >= 0; l--) {
            int val = nums[l];
            int count = 1;
            
            // Merge all elements to the right that are smaller than nums[l]
            // because nums[l] now becomes their prefix maximum.
            while (!dq.isEmpty() && dq.peekFirst()[0] < val) {
                int[] front = dq.pollFirst();
                cost += (long) (val - front[0]) * front[1];
                count += front[1];
            }
            
            dq.offerFirst(new int[]{val, count});
            
            // Shrink the window from the right if total operations exceed k
            while (cost > k) {
                int[] back = dq.peekLast();
                cost -= (back[0] - nums[r]);
                back[1]--;
                if (back[1] == 0) {
                    dq.pollLast();
                }
                r--;
            }
            
            // All subarrays starting at l and ending at any index in [l, r] are valid
            ans += (r - l + 1);
        }
        
        return ans;
    }
}