import java.util.ArrayList;
import java.util.List;

class ProductOfNumbers {
    private List<Integer> prefix;

    public ProductOfNumbers() {
        prefix = new ArrayList<>();
        prefix.add(1); // Base dummy value to simplify multiplication
    }
    
    public void add(int num) {
        if (num == 0) {
            // Reset prefix products when a 0 is encountered
            prefix = new ArrayList<>();
            prefix.add(1);
        } else {
            int lastProduct = prefix.get(prefix.size() - 1);
            prefix.add(lastProduct * num);
        }
    }
    
    public int getProduct(int k) {
        int n = prefix.size();
        // If k >= n, it means a 0 was added within the last k elements
        if (k >= n) {
            return 0;
        }
        return prefix.get(n - 1) / prefix.get(n - 1 - k);
    }
}