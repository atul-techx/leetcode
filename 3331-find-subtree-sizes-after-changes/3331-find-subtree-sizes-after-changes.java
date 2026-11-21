import java.util.*;

class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;
        
        // 1. Build initial tree
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int i = 1; i < n; i++) {
            adj.get(parent[i]).add(i);
        }

        // 2. Determine new parents using DFS + backtracking
        int[] newParent = Arrays.copyOf(parent, n);
        int[] lastSeen = new int[26];
        Arrays.fill(lastSeen, -1);
        
        dfsFindParents(0, adj, s, lastSeen, newParent);

        // 3. Build the modified tree
        List<List<Integer>> newAdj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            newAdj.add(new ArrayList<>());
        }
        for (int i = 1; i < n; i++) {
            newAdj.get(newParent[i]).add(i);
        }

        // 4. Compute final subtree sizes
        int[] answer = new int[n];
        dfsSubtreeSize(0, newAdj, answer);
        
        return answer;
    }

    private void dfsFindParents(int u, List<List<Integer>> adj, String s, int[] lastSeen, int[] newParent) {
        int charIdx = s.charAt(u) - 'a';
        int prevAncestor = lastSeen[charIdx];
        
        // If an ancestor with the same character exists, update parent
        if (prevAncestor != -1) {
            newParent[u] = prevAncestor;
        }
        
        // Mark current node as the closest ancestor for this character
        lastSeen[charIdx] = u;
        
        for (int v : adj.get(u)) {
            dfsFindParents(v, adj, s, lastSeen, newParent);
        }
        
        // Backtrack: restore previous ancestor before leaving this subtree
        lastSeen[charIdx] = prevAncestor;
    }

    private int dfsSubtreeSize(int u, List<List<Integer>> newAdj, int[] answer) {
        int size = 1;
        for (int v : newAdj.get(u)) {
            size += dfsSubtreeSize(v, newAdj, answer);
        }
        answer[u] = size;
        return size;
    }
}