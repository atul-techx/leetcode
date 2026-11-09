class Solution {
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {
        int[] count = new int[k];
        
        // Stores all idle server IDs in sorted order
        TreeSet<Integer> available = new TreeSet<>();
        for (int i = 0; i < k; i++) {
            available.add(i);
        }
        
        // Min-heap storing [endTime, serverId]
        PriorityQueue<int[]> busy = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        
        int n = arrival.length;
        for (int i = 0; i < n; i++) {
            int arrive = arrival[i];
            int duration = load[i];
            
            // Release all servers that have finished before or at current arrival time
            while (!busy.isEmpty() && busy.peek()[0] <= arrive) {
                int[] finished = busy.poll();
                available.add(finished[1]);
            }
            
            // If all servers are busy, drop the request
            if (available.isEmpty()) {
                continue;
            }
            
            // Find the first available server >= (i % k)
            int target = i % k;
            Integer server = available.ceiling(target);
            if (server == null) {
                // Wrap around to the start
                server = available.first();
            }
            
            // Assign request to the selected server
            available.remove(server);
            count[server]++;
            busy.offer(new int[]{arrive + duration, server});
        }
        
        // Find max requests handled
        int maxCount = 0;
        for (int c : count) {
            if (c > maxCount) {
                maxCount = c;
            }
        }
        
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            if (count[i] == maxCount) {
                result.add(i);
            }
        }
        
        return result;
    }
}