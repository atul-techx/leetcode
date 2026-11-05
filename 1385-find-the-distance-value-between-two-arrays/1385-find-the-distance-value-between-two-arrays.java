import java.util.Arrays;

class Solution {
    public int findTheDistanceValue(int[] arr1, int[] arr2, int d) {
        Arrays.sort(arr2);
        int distanceValue = 0;

        for (int x : arr1) {
            int idx = Arrays.binarySearch(arr2, x);

            // If x is present in arr2, |x - x| = 0 <= d, so invalid
            if (idx >= 0) {
                continue;
            }

            // Insertion point is (-idx - 1)
            int insertPos = -idx - 1;
            boolean isValid = true;

            // Check element immediately to the right
            if (insertPos < arr2.length && Math.abs(arr2[insertPos] - x) <= d) {
                isValid = false;
            }

            // Check element immediately to the left
            if (insertPos > 0 && Math.abs(arr2[insertPos - 1] - x) <= d) {
                isValid = false;
            }

            if (isValid) {
                distanceValue++;
            }
        }

        return distanceValue;
    }
}