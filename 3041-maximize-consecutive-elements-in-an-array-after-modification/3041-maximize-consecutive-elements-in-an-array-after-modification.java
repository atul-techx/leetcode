import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Solution {
    public int maxSelectedElements(int[] nums) {
        Arrays.sort(nums);
        
        // dp.get(v) = max length of consecutive sequence ending at v
        Map<Integer, Integer> dp = new HashMap<>();
        int maxLen = 0;
        
        for (int x : nums) {
            // Update x + 1 first so the current element isn't used twice
            int lenPlusOne = dp.getOrDefault(x, 0) + 1;
            int lenSame = dp.getOrDefault(x - 1, 0) + 1;
            
            dp.put(x + 1, Math.max(dp.getOrDefault(x + 1, 0), lenPlusOne));
            dp.put(x, Math.max(dp.getOrDefault(x, 0), lenSame));
            
            maxLen = Math.max(maxLen, Math.max(dp.get(x), dp.get(x + 1)));
        }
        
        return maxLen;
    }
}