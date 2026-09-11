class Solution {
    public int subarraySum(int[] nums, int k) {
        int n = nums.length;
        HashMap<Integer, Integer> map = new HashMap<>();

        map.put(0,1);
        
        int count = 0;
        int s=0;
        for(int i = 0;i<n;i++){
             s += nums[i];
            if(map.containsKey(s-k)){
                int v = map.get(s-k);
                count += v;
            }
            map.put(s,map.getOrDefault(s,0)+1);
        }
        return count;
    }
}