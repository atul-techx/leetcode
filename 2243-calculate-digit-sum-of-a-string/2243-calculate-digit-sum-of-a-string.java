class Solution {
    public String digitSum(String s, int k) {
        // Continue rounding until the string length is at most k
        while (s.length() > k) {
            StringBuilder nextString = new StringBuilder();
            
            // Process the string in chunks of size k
            for (int i = 0; i < s.length(); i += k) {
                int currentSum = 0;
                
                // Sum the digits in the current chunk
                for (int j = i; j < i + k && j < s.length(); j++) {
                    currentSum += s.charAt(j) - '0';
                }
                
                // Append the sum to the builder for the next round
                nextString.append(currentSum);
            }
            
            // Update s with the newly formed string
            s = nextString.toString();
        }
        
        return s;
    }
}